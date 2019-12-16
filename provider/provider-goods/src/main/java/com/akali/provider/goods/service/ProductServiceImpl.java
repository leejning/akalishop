package com.akali.provider.goods.service;

import com.akali.common.code.CommonCode;
import com.akali.common.code.ProductCode;
import com.akali.common.data_help.DataJpaPageUtils;
import com.akali.common.data_help.PageAndSortObj;
import com.akali.common.dto.goods.base.attribute.*;
import com.akali.common.dto.goods.sku.*;
import com.akali.common.dto.goods.spu.*;
import com.akali.common.dto.goods.spu.SpuSaleOptionVO;
import com.akali.common.dto.query.SpuQueryDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;
import com.akali.common.utils.MapperUtils;
import com.akali.provider.goods.api.ProductService;
import com.akali.provider.goods.bean.*;
import com.akali.provider.goods.dao.*;
import com.akali.provider.goods.queryhelper.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
    @Autowired
    private SkuStockDao skuStockDao;
    @Autowired
    private CategoryDao categoryDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private SkuImageDao skuImageDao;
    @Autowired
    private BaseSaleOptionDao baseSaleOptionDao;


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
        Optional<String> copt = categoryDao.findFullNameById(spuDTO.getCid3());
        pmsSpu.setCategoryName(copt.get());
        Optional<String> bopt = brandDao.findNameById(spuDTO.getBrandId());
        pmsSpu.setBrandName(bopt.get());
        spuDao.save(pmsSpu);
        PmsSpuDetail spuDetail = new PmsSpuDetail();
        spuDetail.setSpuId(pmsSpu.getId());
        spuDetailDao.save(spuDetail);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 根据spuId 获取商品所有属性值
     *
     * @param spuId
     * @return
     */
    @Override
    public DubboResponse<QueryResult<AttrValueVO>> queryProductAllAttrValue(Long spuId) {
        //构建查询helper
        AttrValueEntityQueryHelper queryHelper = AttrValueEntityQueryHelper.create(AttrValueVO.class);
        queryHelper.setSpuId(spuId);
        //查询数据
        List<AttrValueVO> data = baseAttrValueDao
                .findAll(AttrValueEntityQueryHelper.getWhere(queryHelper), AttrValueVO.class);
        //返回
        return DubboResponse.SUCCESS(QueryResult.create(data, (long) data.size()));
    }

    /**
     * 根据spuid获取商品spu详细信息
     *
     * @param spuId
     * @return
     */
    @Override
    public DubboResponse<SpuDetailVO> querySpuDetail(Long spuId) {
        Optional<PmsSpuDetail> opt = spuDetailDao.findById(spuId);
        if (!opt.isPresent()) {
            DubboResponse.FAIL(ProductCode.SPU_DETAIL_NOT_EXSIST);
        }
        return DubboResponse.SUCCESS(new SpuDetailVO(opt.get()));
    }

    /**
     * 把销售选项和一个或多个sku属性进行绑定
     *
     * @param spuSaleOptionVO
     * @return
     */
    @Override
    public DubboResponse<Void> bindSaleOptionAndSkuAttr(SpuSaleOptionVO spuSaleOptionVO) throws JsonProcessingException {
        PmsSpuSaleOption pmsSpuSaleOption = new PmsSpuSaleOption();
        pmsSpuSaleOption.setSpuId(spuSaleOptionVO.getSpuId());
        pmsSpuSaleOption.setSaleOptionId(spuSaleOptionVO.getSaleOptionId());
        pmsSpuSaleOption.setSkuAttrIds(MapperUtils.obj2json(spuSaleOptionVO.getSkuAttrIds()));
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
        Long spuId = skuCreateDTO.getSpuId();
        //检查sku是否存在
//        if (checkExistsSku(skuCreateDTO)) {
//            return DubboResponse.FAIL(ProductCode.THE_SKU_IS_EXSIST);
//        }

        //1、获取该sku对应的的PmsBaseAttrValue的信息
        Map<Long, Long> ownSpec = skuCreateDTO.getOwnSpec();
        List<PmsBaseAttrValue> attrValues = baseAttrValueDao.findAllById(ownSpec.values());
        //使用map存放，方便获取
        Map<Long, PmsBaseAttrValue> attrValueMap =
                attrValues.stream().collect(Collectors.toMap(av -> av.getAttrId(), av -> av));

        //2、获取有固定选项的PmsBaseAttribution
        //与sku有关的所有PmsBaseAttribution的id集合
        Set<Long> attrIds = attrValueMap.keySet();
        //构造查询条件
        AttributionEntityQueryHelper attributionQueryHelper = new AttributionEntityQueryHelper();
        attributionQueryHelper.setHasOption(true);
        attributionQueryHelper.setInField("attrId");
        attributionQueryHelper.setInValues(attrIds);
        //查询获取结果
        List<PmsBaseAttribution> hasOptionAttributes =
                baseAttributionDao.findAll(AttributionEntityQueryHelper.getWhere(attributionQueryHelper));


        //3.获取有固定选项的PmsBaseAttribution集合对应所有PmsBaseAttrOption的集合
        List<Long> hasOptionAttrIds = hasOptionAttributes.stream().map(a -> a.getId()).collect(Collectors.toList());
        boolean hasOptionAttr = !hasOptionAttrIds.isEmpty();
        //使用map存放，方便获取
        Map<Long, PmsBaseAttrOption> attrOptMap = Collections.emptyMap();
        if (hasOptionAttr) {
            AttrOptionEntityQueryHelper queryDTO = new AttrOptionEntityQueryHelper();
            queryDTO.setInField("attrId");
            queryDTO.setInValues(hasOptionAttrIds);
            queryDTO.setWithIn(true);
            List<PmsBaseAttrOption> attrOpts =
                    baseAttrOptionDao.findAll(AttrOptionEntityQueryHelper.getWhere(queryDTO));
            attrOptMap = attrOpts.stream().collect(Collectors.toMap(a -> a.getId(), a -> a));
        }

        //4、获取spu销售选项SpuSaleOption
//        List<SpuSaleOptionVO> spuSaleOpts = spuSaleOptionDao.findBySpuId(spuId);

        SpuSaleOptionEntityQueryHelper spuSaleOptQueryHelper = SpuSaleOptionEntityQueryHelper.create(SpuSaleOptionVO.class, spuId);
        List<SpuSaleOptionVO> spuSaleOpts = spuSaleOptionDao
                .findAll(SpuSaleOptionEntityQueryHelper.getWhere(spuSaleOptQueryHelper), SpuSaleOptionVO.class);


        //5、构造和保存销售选项的值对象PmsSpuSaleOptionValue
        List<PmsSpuSaleOptionValue> spuSaleOptionValues = Lists.newArrayList();
        for (SpuSaleOptionVO spuSaleOpt : spuSaleOpts) {
            PmsSpuSaleOptionValue optionValue = new PmsSpuSaleOptionValue();
            optionValue.setSpuSaleOptionId(spuSaleOpt.getSaleOptionId());

            StringBuilder value = new StringBuilder();
            for (Long skuAttrId : spuSaleOpt.getSkuAttrIds()) {
                PmsBaseAttrValue pmsBaseAttrValue = attrValueMap.get(skuAttrId);
                String content = pmsBaseAttrValue.getValue();
                if (hasOptionAttr && hasOptionAttrIds.contains(skuAttrId)) {
                    content = attrOptMap.get(pmsBaseAttrValue.getOptionId()).getContent();
                }
                value.append(content + "+");
            }
            String v = value.substring(0, value.length() - 1).toString();
            optionValue.setValue(v);

            optionValue.setSpuId(spuId);
            spuSaleOptionValues.add(optionValue);
        }
        //保存
        spuSaleOptionValueDao.saveAll(spuSaleOptionValues);

        //6、更新对应spuDetail 的 saleOptionAttr字段
        PmsSpuDetail spuDetail = spuDetailDao.findSaleOptionAttrBySpuId(spuId);
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
                saleOptionAttrMap.put(key, ids);
            }
            indexes.append(key + ":" + saleOptionAttrMap.get(key).size() + ",");
            saleOptionAttrMap.get(key).add(ov.getId());
        });

        saleOptionAttr = MapperUtils.obj2json(saleOptionAttrMap);
        spuDetailDao.updateSaleOptionAttrById(saleOptionAttr, spuId);

        //6、保存sku
        PmsSku pmsSku = new PmsSku(skuCreateDTO);
        pmsSku.setIndexes(indexes.toString());
        List<String> title = attrValues.stream().map(a -> a.getValue()).collect(Collectors.toList());
        String skuTitle = StringUtils.join(title, " ");
        String simpleName = spuDao.findSimpleTitleById(spuId);
        pmsSku.setTitle(simpleName + " " + skuTitle);
        skuDao.save(pmsSku);
        //创建库存
        PmsSkuStock pmsSkuStock = new PmsSkuStock(pmsSku.getId(), spuId, 0);
        skuStockDao.save(pmsSkuStock);

        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 判断sku是否存在
     *
     * @param skuCreateDTO
     * @return
     */
    @Override
    public Boolean checkExistsSku(SkuCreateDTO skuCreateDTO) {
        int count = skuDao.existsByOwnSpecAndSpuId(MapperUtils.mapToJson(skuCreateDTO.getOwnSpec()), skuCreateDTO.getSpuId());
        return count > 0;
    }

    /**
     * 修改商品详情信息
     *
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
     * 根据spuId 获取所有的sku
     *
     * @param spuId
     * @return
     */
    @Override
    public DubboResponse<QueryResult<SkuDTO>> queryProductSkus(Long spuId) {
        //构建查询条件helper
        SkuEntityQueryHelper queryHelper = SkuEntityQueryHelper.create(SkuDTO.class);
        queryHelper.setSpuId(spuId);
        //查询数据
        List<SkuDTO> data = skuDao.findAll(SkuEntityQueryHelper.getWhere(queryHelper), SkuDTO.class);
        return DubboResponse.SUCCESS(QueryResult.create(data, (long) data.size()));
    }

    /**
     * 获取某个商品的销售页信息
     *
     * @param spuId
     * @return
     */
    @Override
    public DubboResponse<ProductSalePageInfoVO> getProductSalePageInfo(Long spuId) {
        List<PmsSkuStock> skuStocks = skuStockDao.findBySpuId(spuId);
        if (skuStocks.isEmpty()) {
            DubboResponse.FAIL(ProductCode.SPU_NOT_EXSIST);
        }
        List<SkuStockPriceDTO> data = skuStocks.stream()
                .map(s -> new SkuStockPriceDTO(s.getSkuId(), s.getStock()))
                .collect(Collectors.toList());
        return DubboResponse.SUCCESS(new ProductSalePageInfoVO(data));
    }

    /**
     * @param spuQueryDTO
     * @return
     */
    @Override
    public DubboResponse<QueryResult<SpuVO>> queryProductPage(SpuQueryDTO spuQueryDTO) {
        //初始化分页
        PageAndSortObj pageAndSortObj = DataJpaPageUtils.initPageAndSort(spuQueryDTO);
        //构造查询helper
        SpuEntityQueryHelper queryHelper = SpuEntityQueryHelper.create(spuQueryDTO, SpuVO.class);
        //查询数据
        Page<SpuVO> page = (Page<SpuVO>) spuDao
                .findAll(SpuEntityQueryHelper.getWhere(queryHelper), pageAndSortObj.getPageable(), SpuVO.class);
        //封装结果
        QueryResult<SpuVO> queryResult = DataJpaPageUtils.setQueryResult(page, spuQueryDTO);
        //返回
        return DubboResponse.SUCCESS(queryResult);
    }

    /**
     * 修改商品详情信息 开始
     */
    @Override
    @Transactional
    public DubboResponse<Void> updateSpuDetailAfterService(Long spuId, String afterService) {
        spuDetailDao.updateAfterServiceById(spuId, afterService);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    @Override
    @Transactional
    public DubboResponse<Void> updateSpuDetailPackage(Long spuId, String packingList) {
        spuDetailDao.updatePackingListById(spuId, packingList);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    @Override
    @Transactional
    public DubboResponse<Void> updateSpuDetailDescription(Long spuId, String description) {
        spuDetailDao.updateDescriptionById(spuId, description);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    @Override
    @Transactional
    public DubboResponse<Void> updateSpuDetailTitle(Long spuId, SpuTitleDTO spuTitleDTO) {
        spuDetailDao.updateTitlesById(spuId, spuTitleDTO);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 初始设置商品属性的值
     *
     * @param attrValueDTO
     * @return
     */
    @Override
    public DubboResponse<Long> setAttributeValue(AttrValueDTO attrValueDTO) {
        PmsBaseAttrValue pmsBaseAttrValue = new PmsBaseAttrValue();
        BeanUtils.copyProperties(attrValueDTO, pmsBaseAttrValue);
        baseAttrValueDao.save(pmsBaseAttrValue);
        return DubboResponse.SUCCESS(pmsBaseAttrValue.getId());
    }

    /**
     * 修改商品属性的值
     *
     * @param avId
     * @param attrValueDTO
     * @return
     */
    @Override
    public DubboResponse<Void> modifyAttributeValue(Long avId, AttrValueDTO attrValueDTO) {
        Optional<PmsBaseAttrValue> opt = baseAttrValueDao.findById(avId);
        if (!opt.isPresent()) {
            return DubboResponse.FAIL(CommonCode.FAIL);
        }
        PmsBaseAttrValue value = opt.get();
        value.setValue(attrValueDTO.getValue());
        value.setOptionId(attrValueDTO.getOptionId());
        baseAttrValueDao.save(value);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 设置多个属性值
     *
     * @param attrValueListDTO
     * @return
     */
    @Override
    public DubboResponse<Void> setAttributeValueMutl(AttrValueListDTO attrValueListDTO) {
        List<PmsBaseAttrValue> saveDatas = new ArrayList<>(attrValueListDTO.getAttrValues().size());
        for (AttrValueDTO attrValue : attrValueListDTO.getAttrValues()) {
            PmsBaseAttrValue value = new PmsBaseAttrValue();
            BeanUtils.copyProperties(attrValue, value);
            saveDatas.add(value);
        }
        baseAttrValueDao.saveAll(saveDatas);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }
    /**
     *修改商品详情信息 结束
     */


    /**
     * 获取商品的销售选项列表
     *
     * @param spuId
     * @return
     */
    @Override
    public DubboResponse<QueryResult<com.akali.common.dto.goods.base.SpuSaleOptionVO>> querySpuSaleOption(Long spuId) {
        List<PmsSpuSaleOption> saleOptions = spuSaleOptionDao.findBySpuId(spuId);
        List<com.akali.common.dto.goods.base.SpuSaleOptionVO> data = saleOptions.stream().map(a -> new com.akali.common.dto.goods.base.SpuSaleOptionVO(a, a.getSkuAttrIds())).collect(Collectors.toList());
        QueryResult<com.akali.common.dto.goods.base.SpuSaleOptionVO> queryResult = DataJpaPageUtils.setQueryResult(data, (long) data.size());
        return DubboResponse.SUCCESS(queryResult);
    }

    /**
     * 修改商品销售选项绑定的sku属性
     *
     * @param ssoId
     * @param spuSaleOptionBindAttrDTO
     * @return
     */
    @Override
    @Transactional
    public DubboResponse<Void> modifySpuSaleOptionBindIds(Long ssoId, SpuSaleOptionBindAttrDTO spuSaleOptionBindAttrDTO) {
        String skuAttrIs = null;
        try {
            skuAttrIs = MapperUtils.obj2json(spuSaleOptionBindAttrDTO.getSkuAttrIds());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            DubboResponse.FAIL(CommonCode.FAIL);
        }
        spuSaleOptionDao.updateSkuAttrIdsById(ssoId, skuAttrIs);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 获取某商品的sku列表表头信息
     *
     * @param spuId
     * @return
     */
    @Override
    public DubboResponse<QueryResult<SkuTableHeader>> getSkuTableHeader(Long spuId) {
        List<PmsSpuSaleOption> saleOptions = spuSaleOptionDao.findBySpuId(spuId);
        List<com.akali.common.dto.goods.base.SpuSaleOptionVO> saleOptionVOs = saleOptions.stream().map(a -> new com.akali.common.dto.goods.base.SpuSaleOptionVO(a, a.getSkuAttrIds())).collect(Collectors.toList());
        List<Long> attrIds = saleOptionVOs.stream().flatMap(s -> s.getSkuAttrIds().stream()).collect(Collectors.toList());

        AttributionEntityQueryHelper queryHelper = AttributionEntityQueryHelper.create("id", attrIds, AttributionSimpleVO.class);
        List<AttributionSimpleVO> attributes = baseAttributionDao.findAll(AttributionEntityQueryHelper.getWhere(queryHelper), AttributionSimpleVO.class);

        Map<Long, AttributionSimpleVO> attributeMap = attributes.stream().collect(Collectors.toMap(a -> a.getId(), a -> a));
        List<SkuTableHeader> headers = new ArrayList<>(saleOptions.size());
        for (com.akali.common.dto.goods.base.SpuSaleOptionVO saleOptionVO : saleOptionVOs) {
            SkuTableHeader skuTableHeader = new SkuTableHeader();
            skuTableHeader.setSsoId(saleOptionVO.getId());
            skuTableHeader.setSaleOptionName(saleOptionVO.getSaleOptionName());
            List<Long> skuAttrIds = saleOptionVO.getSkuAttrIds();
            List<AttributionSimpleVO> attrSVOs = new ArrayList<>(skuAttrIds.size());
            for (Long skuAttrId : skuAttrIds) {
                attrSVOs.add(attributeMap.get(skuAttrId));
            }
            skuTableHeader.setAttributes(attrSVOs);
            headers.add(skuTableHeader);

        }
        QueryResult<SkuTableHeader> queryResult = DataJpaPageUtils.setQueryResult(headers, (long) headers.size());
        return DubboResponse.SUCCESS(queryResult);
    }

    /**
     * 获取某spu的sku列表数据
     *
     * @param spuId
     * @return
     */
    @Override
    public DubboResponse<QueryResult<SkuTableVO>> getSkuTableData(Long spuId) {
        SkuEntityQueryHelper skuQueryHelper = SkuEntityQueryHelper.create(SkuTableVO.class);
        skuQueryHelper.setSpuId(spuId);
        List<SkuTableVO> skuTableVOs = skuDao.findAll(SkuEntityQueryHelper.getWhere(skuQueryHelper), SkuTableVO.class);
        List<Long> attrIds = skuTableVOs.stream().flatMap(s -> s.getOwnSpec().keySet().stream()).collect(Collectors.toList());
        List<Long> attrValueIds = skuTableVOs.stream().flatMap(s -> s.getOwnSpec().values().stream()).collect(Collectors.toList());

        AttributionEntityQueryHelper attrQueryHelper = AttributionEntityQueryHelper.create("id", attrIds, AttributionSimpleVO.class);

        List<AttributionSimpleVO> attributes = baseAttributionDao.findAll(AttributionEntityQueryHelper.getWhere(attrQueryHelper), AttributionSimpleVO.class);
        Map<Long, AttributionSimpleVO> attrMap = attributes.stream().collect(Collectors.toMap(a -> a.getId(), a -> a));
        List<PmsBaseAttrValue> attrValues = baseAttrValueDao.findAllById(attrValueIds);
        Map<Long, PmsBaseAttrValue> attrValueMap = attrValues.stream().collect(Collectors.toMap(a -> a.getId(), a -> a));


        for (SkuTableVO skuTableVO : skuTableVOs) {
            Map<String, String> props = Maps.newHashMap();
            for (Map.Entry<Long, Long> entry : skuTableVO.getOwnSpec().entrySet()) {
                String enName = attrMap.get(entry.getKey()).getEnName();
                String value = attrValueMap.get(entry.getValue()).getValue();
                props.put(enName, value);
            }
            skuTableVO.setProps(props);
        }

        QueryResult<SkuTableVO> queryResult = DataJpaPageUtils.setQueryResult(skuTableVOs, (long) skuTableVOs.size());
        return DubboResponse.SUCCESS(queryResult);
    }

    /**
     * 获取某商品的特有属性和属性值，用于添加新的sku
     *
     * @param cateId
     * @param spuId
     * @return
     */
    @Override
    public DubboResponse<SkuAttrAndValueVO> getSkuAttrAndValue(Long cateId, Long spuId) {
        //获取属性
        AttributionEntityQueryHelper attrQueryHelper = AttributionEntityQueryHelper.create(AttrKeyValueVO.class);
        attrQueryHelper.setCateId(cateId);
        attrQueryHelper.setGeneric(false);
        List<AttrKeyValueVO> attrs = baseAttributionDao.findAll(
                AttributionEntityQueryHelper.getWhere(attrQueryHelper), AttrKeyValueVO.class);

        //获取属性值
        Map<Long, AttrKeyValueVO> attrMap = attrs.stream().collect(Collectors.toMap(a -> a.getId(), a -> a));

        AttrValueEntityQueryHelper attrValueQueryHelper = AttrValueEntityQueryHelper.
                create("attrId", attrMap.keySet(), AttrValueVO.class);
        attrValueQueryHelper.setSpuId(spuId);
        List<AttrValueVO> attrValues = baseAttrValueDao.findAll(
                AttrValueEntityQueryHelper.getWhere(attrValueQueryHelper), AttrValueVO.class);
        for (AttrValueVO attrValue : attrValues) {
            attrMap.get(attrValue.getAttrId()).getValues().add(attrValue);
        }

        return DubboResponse.SUCCESS(new SkuAttrAndValueVO(attrs));
    }

    /**
     * 获取sku的图片
     *
     * @param skuId
     * @return
     */
    @Override
    public DubboResponse<QueryResult<SkuImageVO>> querySkuImage(Long skuId) {
        List<PmsSkuImage> images = skuImageDao.findBySkuId(skuId);
        List<SkuImageVO> data = images.stream().map(i -> new SkuImageVO(i)).collect(Collectors.toList());
        QueryResult<SkuImageVO> queryResult = DataJpaPageUtils.setQueryResult(data, (long) data.size());
        return DubboResponse.SUCCESS(queryResult);
    }

    /**
     * 添加sku图片
     *
     * @param skuImageDTO
     * @return
     */
    @Override
    public DubboResponse<Long> addSkuImage(SkuImageDTO skuImageDTO) {
        PmsSkuImage skuImage = new PmsSkuImage();
        BeanUtils.copyProperties(skuImageDTO, skuImage);
        skuImageDao.save(skuImage);
        return DubboResponse.SUCCESS(skuImage.getId());
    }

    /**
     * 设置sku猪图片
     *
     * @param imageId
     * @param skuImageDTO
     * @return
     */
    @Transactional
    @Override
    public DubboResponse<Long> setSkuMainImage(Long imageId, SkuImageDTO skuImageDTO) {
        skuImageDao.setImageAsMain(imageId);
        skuDao.setSkuMainImage(skuImageDTO.getSkuId(), skuImageDTO.getImageUrl());
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 删除sku的图片
     *
     * @param skuId
     * @param skuImageId
     * @return
     */
    @Transactional
    @Override
    public DubboResponse<Long> deleteSkuImage(Long skuId, Long skuImageId) {
        skuImageDao.setImageDisable(skuId, skuImageId);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 添加多张sku图片
     *
     * @param skuImageMutlDTO
     * @return
     */
    @Override
    public DubboResponse<Map<Integer, Long>> addSkuImageMutl(SkuImageMutlDTO skuImageMutlDTO) {
        Long skuId = skuImageMutlDTO.getSkuId();
        List<PmsSkuImage> images = skuImageMutlDTO.getListImage().stream()
                .map(url -> new PmsSkuImage(skuId, url)).collect(Collectors.toList());
        skuImageDao.saveAll(images);
        AtomicInteger index = new AtomicInteger(0);
        Map<Integer, Long> result = images.stream().collect(Collectors.toMap(a -> index.getAndIncrement(), a -> a.getId()));
        return DubboResponse.SUCCESS(result);
    }

    /**
     * 创建商品销售选项
     *
     * @param spuSaleOptionDTO
     * @return
     */
    @Override
    public DubboResponse<Long> createSpuSaleOption(SpuSaleOptionDTO spuSaleOptionDTO) {
        PmsSpuSaleOption spuSaleOption = new PmsSpuSaleOption();
        spuSaleOption.setSaleOptionId(spuSaleOptionDTO.getSaleOptionId());
        spuSaleOption.setSpuId(spuSaleOptionDTO.getSpuId());
        spuSaleOption.setSkuAttrIds("[]");
        String saleOptionName = baseSaleOptionDao.findNameById(spuSaleOptionDTO.getSaleOptionId());
        if (StringUtils.isBlank(saleOptionName)) {
            return DubboResponse.FAIL(ProductCode.SALE_OPTION_NOT_EXIST);
        }
        spuSaleOption.setSaleOptionName(saleOptionName);
        spuSaleOptionDao.save(spuSaleOption);
        return DubboResponse.SUCCESS(spuSaleOption.getId());
    }

    /**
     * 设置sku主图片
     *
     * @param skuId
     * @param newMain
     * @param oldMain
     * @return
     */
    @Transactional
    @Override
    public DubboResponse<Void> setSkuMainImage(Long skuId, Long newMain, Long oldMain) {
        if (oldMain != -1) {
            int se = skuImageDao.updataMainImageById(skuId, oldMain, false);
            if (se != 1) {
                return DubboResponse.FAIL(ProductCode.SKU_IMAGE_NOT_EXIST);
            }
        }
        Optional<PmsSkuImage> opt = skuImageDao.findById(newMain);
        if (!opt.isPresent()) {
            return DubboResponse.FAIL(ProductCode.SKU_IMAGE_NOT_EXIST);
        }
        PmsSkuImage skuImage = opt.get();
        skuImage.setIsMain(true);
        skuDao.setSkuMainImage(skuId, skuImage.getImageUrl());
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 设置sku图片描述
     *
     * @param skuImageId
     * @param skuImageDesc
     * @return
     */
    @Transactional
    @Override
    public DubboResponse<Void> setSkuImageDesc(Long skuId, Long skuImageId, SkuImageDescDTO skuImageDesc) {
        int re = skuImageDao.updateImageDesc(skuId, skuImageId, skuImageDesc.getDesc());
        if (re != 1) {
            return DubboResponse.FAIL(ProductCode.SKU_IMAGE_NOT_EXIST);
        }
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 设置sku标题
     *
     * @param skuId
     * @param skuTitleDTO
     * @return
     */
    @Transactional
    @Override
    public DubboResponse<Void> setSkuTitle(Long skuId, SkuTitleDTO skuTitleDTO) {
        int re = skuDao.setSkuTitle(skuId, skuTitleDTO.getTitle());
        if (re != 1) {
            return DubboResponse.FAIL(ProductCode.SKU_NOT_EXSIST);
        }
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 设置sku价格
     *
     * @param skuId
     * @param skuPriceDTO
     * @return
     */
    @Transactional
    @Override
    public DubboResponse<Void> setSkuPrice(Long skuId, SkuPriceDTO skuPriceDTO) {
        int re = skuDao.setSkuPrice(skuId, skuPriceDTO.getPrice());
        if (re != 1) {
            return DubboResponse.FAIL(ProductCode.SKU_NOT_EXSIST);
        }
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 创建商品所有属性值
     *
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
