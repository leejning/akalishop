package com.akali.provider.goods.service;

import com.akali.common.code.CommonCode;
import com.akali.common.code.ProductCode;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;
import com.akali.common.utils.MapperUtils;
import com.akali.provider.goods.api.ProductService;
import com.akali.provider.goods.bean.*;
import com.akali.provider.goods.dao.*;
import com.akali.provider.goods.dto.*;
import com.akali.provider.goods.querydto.AttrOptionQueryDTO;
import com.akali.provider.goods.querydto.AttributionQueryDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName ProductServiceImpl
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
@Service(version = "1.0.0")
public class ProductServiceImpl implements ProductService {
    @Autowired
    private SpuDao spuDao;
    @Autowired
    private BaseAttributionDao baseAttributionDao;
    @Autowired
    private BaseAttrOptionDao baseAttrOptionDao;
    @Autowired
    private BaseAttrValueDao baseAttrValueDao;
    @Autowired
    private SpuDetailDao spuDetailDao;
    @Autowired
    private SpuSaleOptionDao spuSaleOptionDao;
    @Autowired
    private SpuSaleOptionValueDao spuSaleOptionValueDao;
    @Autowired
    private SkuDao skuDao;

    /**
     * 创建spu基本信息
     *
     * @param spuDTO
     * @return
     */
    @Override
    public DubboResponse<Void> createProduct(SpuDTO spuDTO) {
        PmsSpu pmsSpu = new PmsSpu();
        BeanUtils.copyProperties(spuDTO, pmsSpu);
        spuDao.save(pmsSpu);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 根据spuId 获取商品所有属性值
     *
     * @param spuId
     * @return
     */
    @Override
    public DubboResponse<QueryResult<AttrValueDTO>> queryProductAllAttrValue(Long spuId) {
        List<AttrValueDTO> quertData = baseAttrValueDao.findBySpuId(spuId);
        return DubboResponse.SUCCESS(QueryResult.create(quertData, (long) quertData.size()));
    }

    /**
     * 根据spuid获取商品spu详细信息
     *
     * @param spuId
     * @return
     */
    @Override
    public DubboResponse<SpuDetaiDTO> querySpuDetail(Long spuId) {
        Optional<PmsSpuDetail> opt = spuDetailDao.findById(spuId);
        if (!opt.isPresent()) {
            DubboResponse.FAIL(ProductCode.SPU_DETAIL_NOT_EXSIST);
        }
        return DubboResponse.SUCCESS(new SpuDetaiDTO(opt.get()));
    }

    /**
     * 把销售选项和一个或多个sku属性进行绑定
     *
     * @param spuSaleOptionDTO
     * @return
     */
    @Override
    public DubboResponse<Void> bindSaleOptionAndSkuAttr(SpuSaleOptionDTO spuSaleOptionDTO) throws JsonProcessingException {
        PmsSpuSaleOption pmsSpuSaleOption = new PmsSpuSaleOption();
        pmsSpuSaleOption.setSpuId(spuSaleOptionDTO.getSpuId());
        pmsSpuSaleOption.setSaleOptionId(spuSaleOptionDTO.getSaleOptionId());
        pmsSpuSaleOption.setSkuAttrIds(MapperUtils.obj2json(spuSaleOptionDTO.getSkuAttrIds()));
        spuSaleOptionDao.save(pmsSpuSaleOption);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 添加商品sku
     *
     * @param skuCreateDTO
     * @return
     */
    @Transactional
    @Override
    public DubboResponse<Void> createProductSku(SkuCreateDTO skuCreateDTO) throws Exception {
        if (checkExistsSku(skuCreateDTO)) {
            return DubboResponse.FAIL(ProductCode.THE_SKU_IS_EXSIST);
        }

        //1、获取该sku对应的的PmsBaseAttrValue的信息
        Map<Long, Long> ownSpec = skuCreateDTO.getOwnSpec();
        List<PmsBaseAttrValue> attrValues = baseAttrValueDao.findAllById(ownSpec.values());
        Map<Long, PmsBaseAttrValue> attrValueMap = attrValues.stream().collect(Collectors.toMap(av -> av.getAttrId(), av -> av));

        //2、获取sku的PmsBaseAttrValue的对应的PmsBaseAttribution
        AttributionQueryDTO attributionQueryDTO = new AttributionQueryDTO();
        attributionQueryDTO.setHasOption(true);
        List<PmsBaseAttribution> hasOptionAttributes = baseAttributionDao.findAllHasInCondition(attrValueMap.keySet(), AttributionQueryDTO.getWhere(attributionQueryDTO));

        //2.1 处理固定选项的属性，如手机的内存大小，cpu核心数
        List<Long> hasOptionAttrIds = hasOptionAttributes.stream().map(a -> a.getId()).collect(Collectors.toList());
        boolean hasOptionAttr = !hasOptionAttrIds.isEmpty();
        Map<Long, PmsBaseAttrOption> attrOptMap = Collections.emptyMap();
        if (hasOptionAttr) {
            List<PmsBaseAttrOption> attrOpts = baseAttrOptionDao.findAllHasInCondition(hasOptionAttrIds, AttrOptionQueryDTO.getWhere());
            attrOptMap = attrOpts.stream().collect(Collectors.toMap(a -> a.getId(), a -> a));
        }

        //3、获取SpuSaleOption
        List<SpuSaleOptionDTO> spuSaleOpts = spuSaleOptionDao.findBySpuId(skuCreateDTO.getSpuId());


        //4、构造和保存销售选项的值对象PmsSpuSaleOptionValue
        List<PmsSpuSaleOptionValue> spuSaleOptionValues = new ArrayList<>();
        for (SpuSaleOptionDTO spuSaleOpt : spuSaleOpts) {
            PmsSpuSaleOptionValue optionValue = new PmsSpuSaleOptionValue();
            optionValue.setSpuSaleOptionId(spuSaleOpt.getSaleOptionId());

            StringBuilder value = new StringBuilder();
            for (Long skuAttrId : spuSaleOpt.getSkuAttrIds()) {
                PmsBaseAttrValue pmsBaseAttrValue = attrValueMap.get(skuAttrId);
                String content = pmsBaseAttrValue.getValue();
                if (hasOptionAttr && hasOptionAttrIds.contains(skuAttrId)) {
                    content = attrOptMap.get(new Long(content)).getContent();
                }
                value.append(content + "+");
            }
            String v = value.substring(0, value.length() - 1).toString();
            optionValue.setValue(v);

            optionValue.setSpuId(skuCreateDTO.getSpuId());
            spuSaleOptionValues.add(optionValue);
        }

        spuSaleOptionValueDao.saveAll(spuSaleOptionValues);

        //5、更新对应spuDetail 的 saleOptionAttr字段
        PmsSpuDetail spuDetail = spuDetailDao.findSaleOptionAttrBySpuId(skuCreateDTO.getSpuId());
        String saleOptionAttr = spuDetail.getSaleOptionAttr();
        Map<String, List<Long>> saleOptionAttrMap;
        if (StringUtils.isBlank(saleOptionAttr)) {
            saleOptionAttrMap = Maps.newHashMap();

        } else {
            saleOptionAttrMap = MapperUtils.json2maplist(saleOptionAttr);
        }
        StringBuilder indexes = new StringBuilder();
        spuSaleOptionValues.stream().forEach(ov -> {
            String key = ov.getSpuSaleOptionId().toString();
            if (!saleOptionAttrMap.containsKey(key)) {
                List<Long> ids = Lists.newArrayList();
                saleOptionAttrMap.put(key,ids);
            }
            indexes.append(key+":"+saleOptionAttrMap.get(key).size()+",");
            saleOptionAttrMap.get(key).add(ov.getId());
        });

        saleOptionAttr = MapperUtils.obj2json(saleOptionAttrMap);
        spuDetailDao.updateSaleOptionAttrById(saleOptionAttr, skuCreateDTO.getSpuId());

        //6、保存sku
        PmsSku pmsSku = new PmsSku(skuCreateDTO);
        pmsSku.setIndexes(indexes.toString());
        skuDao.save(pmsSku);

        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 判断sku是否存在
     * @param skuCreateDTO
     * @return
     */
    @Override
    public Boolean checkExistsSku(SkuCreateDTO skuCreateDTO){
        int count = skuDao.existsByOwnSpecAndSpuId(MapperUtils.mapToJson(skuCreateDTO.getOwnSpec()),skuCreateDTO.getSpuId());
        return count>0;
    }

    /**
     * 修改商品详情信息
     * @param spuDetaiModifyDTO
     * @return
     */
    @Transactional
    @Override
    public DubboResponse<Void> updateSpuDetail(SpuDetaiModifyDTO spuDetaiModifyDTO) {
        spuDetailDao.modifyBySpuId(spuDetaiModifyDTO);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 创建商品所有属性值
     * @param spuAttrValueCollectDTO
     * @return
     */
    @Override
    @Transactional
    public DubboResponse<Void> createProductAttrValue(SpuAttrValueCollectDTO spuAttrValueCollectDTO) throws JsonProcessingException {
        PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
        //处理sku属性
        Set<Long> attrkey = spuAttrValueCollectDTO.getSkuAttr().keySet();
        Long spuId = spuAttrValueCollectDTO.getSpuId();
        List<PmsBaseAttrValue> attrValues = new ArrayList<>();
        for (Long key : attrkey) {
            List<PmsBaseAttrValue> collect = spuAttrValueCollectDTO.getSkuAttr().get(key).stream()
                    .map(n -> new PmsBaseAttrValue(key, spuId, n)).collect(Collectors.toList());
            attrValues.addAll(collect);
        }
        //处理spu属性
        List<PmsBaseAttrValue> spuAttrs = spuAttrValueCollectDTO.getSpuAttr().entrySet().stream()
                .map(n -> new PmsBaseAttrValue(n.getKey(), spuId, n.getValue())).collect(Collectors.toList());
        attrValues.addAll(spuAttrs);
        baseAttrValueDao.saveAll(attrValues);
        Map<Long, Long> collect = spuAttrs.stream().collect(Collectors.toMap(a -> a.getAttrId(), a -> a.getId()));

        PmsSpuDetail pmsSpuDetail = new PmsSpuDetail();
        String genericAttr = MapperUtils.obj2json(collect);
        pmsSpuDetail.setGenericAttr(genericAttr);

        pmsSpuDetail.setSpuId(spuId);
        spuDetailDao.save(pmsSpuDetail);

        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }


}
