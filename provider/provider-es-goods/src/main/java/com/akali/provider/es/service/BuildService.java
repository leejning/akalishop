package com.akali.provider.es.service;

import com.akali.common.dto.goods.base.AttrOptionDTO;
import com.akali.common.dto.goods.base.AttrValueDTO;
import com.akali.common.dto.goods.base.CateAttributeDTO;
import com.akali.common.dto.goods.spu.SpuEsDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.utils.ExceptionCast;
import com.akali.common.utils.MapperUtils;
import com.akali.provider.es.bean.Product;
import com.akali.provider.es.dao.ProductDao;
import com.akali.provider.goods.api.AttributionService;
import com.akali.provider.goods.api.CategoryService;
import com.akali.provider.goods.api.ProductEsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName BuildService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/16 0016
 * @Version V1.0
 **/
@Service
public class BuildService {

    @Autowired
    private ProductDao productDao;
    @Reference(version = "1.0.0")
    private ProductEsService productEsService;
    @Reference(version = "1.0.0")
    private AttributionService attributionService;
    @Reference(version = "1.0.0")
    private CategoryService categoryService;


    @Transactional
    public void buildProductBySpuId(Long SpuId) throws JsonProcessingException {
        DubboResponse<SpuEsDTO> resp = productEsService.queryProductEsBySpuId(SpuId);
        if (!resp.isSuccess()) {
            ExceptionCast.cast(resp.getResultCode());
        }
        Product product = buildProduct(resp.getData());
        productDao.save(product);
    }

    public Product buildProduct(SpuEsDTO spuEsDTO) throws JsonProcessingException {
        //获取全部属性信息
        DubboResponse<List<CateAttributeDTO>> attrQuery = attributionService.queryAttributesByCid(spuEsDTO.getCid3());
        if (!attrQuery.isSuccess()) {
            ExceptionCast.cast(attrQuery.getResultCode());
        }
        List<CateAttributeDTO> attrs = attrQuery.getData();

        //处理固定选项的属性
        List<AttrOptionDTO> attrOptions = Lists.newArrayList();
        attrs.stream().filter(a -> a.getHasOptions()).forEach(a -> attrOptions.addAll(a.getOptions()));
        Map<Long, AttrOptionDTO> attrOptMap = attrOptions.stream().collect(Collectors.toMap(a -> a.getId(), a -> a));

        /**
         * 预处理属性
         */
        //spu属性集合
        List<CateAttributeDTO> spuAttrs = Lists.newArrayList();
        //sku属性集合
        List<CateAttributeDTO> skuAttrs = Lists.newArrayList();
        /**
         * 标记attr是否是通用属性，key是attrId
         */
        Map<Long, Boolean> attrFlagMap = Maps.newHashMap();
        attrs.stream().filter(a -> a.getSearching()).forEach(a -> {
            attrFlagMap.put(a.getId(), a.getGeneric());
            if (a.getGeneric()) {
                spuAttrs.add(a);
            } else {
                skuAttrs.add(a);
            }
        });

        //获取所有属性值
        DubboResponse<List<AttrValueDTO>> attrValueQuery = attributionService.queryProductAttrValueBySpuId(spuEsDTO.getSpuId());
        if (!attrValueQuery.isSuccess()) {
            ExceptionCast.cast(attrValueQuery.getResultCode());
        }
        List<AttrValueDTO> attrValues = attrValueQuery.getData();

        /**
         * 预处理属性值
         */
        //key是 attrId ，value 是 attrValue
        Map<Long, String> spuAttrValueMap = Maps.newHashMap();
        Map<Long, List<String>> skuAttrValueMap = Maps.newHashMap();
        attrValues.stream().forEach(av -> {
            if(!attrFlagMap.containsKey(av.getAttrId())){

            }
            //spu属性
            else if (attrFlagMap.get(av.getAttrId())) {
                spuAttrValueMap.put(av.getAttrId(), av.getValue());
            }
            //sku属性
            else {
                if (!skuAttrValueMap.containsKey(av.getAttrId())) {
                    skuAttrValueMap.put(av.getAttrId(), Lists.newArrayList());
                }
                skuAttrValueMap.get(av.getAttrId()).add(av.getValue());
            }
        });


        /**
         * 开始构造Product对象
         */


        /**
         * 构建 Product 的attrs
         */
        Map<String, Object> attrMap = Maps.newHashMap();
        //构建attrs中spu的部分
        spuAttrs.stream().forEach(a -> {
            String key = a.getName();
            String value = spuAttrValueMap.get(a.getId());
            //该属性带有固定选项 value 是 optId, 且是数字类型的属性值
            if (a.getHasOptions() && a.getNumeric()) {
                AttrOptionDTO attrOptionDTO = attrOptMap.get(new Long(value));
                //处理数字分段查找
                Float numValue = attrOptionDTO.getNumValue();
                value = chooesSegment(numValue, a.getSegments(), a.getUnit());
            }
            //该属性带有固定选项 value 是 optId
            else if (a.getHasOptions()) {
                AttrOptionDTO attrOptionDTO = attrOptMap.get(new Long(value));
                value = attrOptionDTO.getContent();
            }
            //不带固定选项，但是值为数字的属性
            else if (a.getNumeric()) {
                Float numValue = new Float(value);
                //处理数字分段查找
                value = chooesSegment(numValue, a.getSegments(), a.getUnit());
            }
            attrMap.put(key, value);
        });

        //构建attrs中sku的部分
        skuAttrs.stream().forEach(a -> {
            String key = a.getName();
            List<String> values = skuAttrValueMap.get(a.getId());
            List<String> finalValues = Lists.newArrayList();
            //该属性带有固定选项 value 是 optId, 且是数字类型的属性值
            if (a.getHasOptions() && a.getNumeric()) {
                for (String v : values) {
                    AttrOptionDTO attrOptionDTO = attrOptMap.get(new Long(v));
                    //处理数字分段查找
                    Float numValue = attrOptionDTO.getNumValue();
                    finalValues.add(chooesSegment(numValue, a.getSegments(), a.getUnit()));
                }
            }
            //该属性带有固定选项 value 是 optId
            else if (a.getHasOptions()) {
                for (String v : values) {
                    AttrOptionDTO attrOptionDTO = attrOptMap.get(new Long(v));
                    finalValues.add(attrOptionDTO.getContent());
                };
            }
            //不带固定选项，但是值为数字的属性
            else if (a.getNumeric()) {
                for (String v : values) {
                    Float numValue = new Float(v);
                    //处理数字分段查找
                    finalValues.add(chooesSegment(numValue, a.getSegments(), a.getUnit()));
                };
            }
            else{
                finalValues = values;
            }
            attrMap.put(key, finalValues);
        });

        DubboResponse<String> response = categoryService.queryFullCateName(spuEsDTO.getCid1(), spuEsDTO.getCid2(), spuEsDTO.getCid3());
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }

        Product product = new Product();
        product.setCategoryFullName(response.getData());
        BeanUtils.copyProperties(spuEsDTO, product);
        product.setCreateTime(new Date());
        product.setSkus(MapperUtils.obj2json(spuEsDTO.getSkus()));
        product.setAttrs(attrMap);
        product.setId(spuEsDTO.getSpuId());
        String all = product.getTitle() + product.getCategoryFullName() +
                product.getSkus() + MapperUtils.obj2json(product.getAttrs());
        product.setAll(all);
        return product;
    }

    private String chooesSegment(Float numValue, String segments, String unit) {
        String result = "其它";
        if (StringUtils.isBlank(segments)) {
            return numValue + " " + unit;
        }
        for (String segment : segments.split(",")) {
            String[] segs = segment.split("-");
            // 获取数值范围
            float begin = NumberUtils.toFloat(segs[0]);
            float end = Float.MAX_VALUE;
            if (segs.length == 2) {
                end = NumberUtils.toFloat(segs[1]);
            }
            // 判断是否在范围内
            if (numValue >= begin && numValue < end) {
                if (segs.length == 1) {
                    result = segs[0] + unit + "以上";
                } else if (begin == 0) {
                    result = segs[1] + unit + "以下";
                } else {
                    result = segment + unit;
                }
                break;
            }
        }
        return result;
    }


}
