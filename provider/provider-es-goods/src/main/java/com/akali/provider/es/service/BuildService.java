package com.akali.provider.es.service;

import com.akali.common.dto.goods.base.attribute.AttrOptionVO;
import com.akali.common.dto.goods.base.attribute.AttrValueVO;
import com.akali.common.dto.goods.base.category.CateAttributeVO;
import com.akali.common.dto.goods.spu.SpuDetailVO;
import com.akali.common.dto.goods.spu.SpuEsVO;
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
        DubboResponse<SpuEsVO> resp = productEsService.queryProductEsBySpuId(SpuId);
        if (!resp.isSuccess()) {
            ExceptionCast.cast(resp.getResultCode());
        }
        Product product = buildProduct(resp.getData());
        productDao.save(product);
    }

    public Product buildProduct(SpuEsVO spuEsVO) throws JsonProcessingException {
        //获取全部属性信息
        DubboResponse<List<CateAttributeVO>> attrQuery = attributionService.queryAttributesByCid(spuEsVO.getCid3());
        if (!attrQuery.isSuccess()) {
            ExceptionCast.cast(attrQuery.getResultCode());
        }
        List<CateAttributeVO> attrs = attrQuery.getData();

        //处理固定选项的属性
        List<AttrOptionVO> attrOptions = Lists.newArrayList();
        attrs.stream().filter(a -> a.getHasOptions()).forEach(a -> attrOptions.addAll(a.getOptions()));
        Map<Long, AttrOptionVO> attrOptMap = attrOptions.stream().collect(Collectors.toMap(a -> a.getId(), a -> a));

        /**
         * 预处理属性
         */
        //spu属性集合
        List<CateAttributeVO> spuAttrs = Lists.newArrayList();
        //sku属性集合
        List<CateAttributeVO> skuAttrs = Lists.newArrayList();
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
        DubboResponse<List<AttrValueVO>> attrValueQuery = attributionService.queryProductAttrValueBySpuId(spuEsVO.getSpuId());
        if (!attrValueQuery.isSuccess()) {
            ExceptionCast.cast(attrValueQuery.getResultCode());
        }
        List<AttrValueVO> attrValues = attrValueQuery.getData();

        /**
         * 预处理属性值
         */
        //key是 attrId ，value 是 attrValue
        Map<Long, AttrValueVO> spuAttrValueMap = Maps.newHashMap();
        Map<Long, List<AttrValueVO>> skuAttrValueMap = Maps.newHashMap();
        attrValues.stream().forEach(av -> {
            if (!attrFlagMap.containsKey(av.getAttrId())) {

            }
            //spu属性
            else if (attrFlagMap.get(av.getAttrId())) {
                spuAttrValueMap.put(av.getAttrId(), av);
            }
            //sku属性
            else {
                if (!skuAttrValueMap.containsKey(av.getAttrId())) {
                    skuAttrValueMap.put(av.getAttrId(), Lists.newArrayList());
                }
                skuAttrValueMap.get(av.getAttrId()).add(av);
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
            AttrValueVO attrValueVO = spuAttrValueMap.get(a.getId());
            String value = attrValueVO.getValue();
            //该属性带有固定选项, 且是数字类型的属性值
            if (a.getHasOptions() && a.getNumeric()) {
                AttrOptionVO attrOptionVO = attrOptMap.get(attrValueVO.getOptionId());
                //处理数字分段查找
                Float numValue = attrOptionVO.getNumValue();
                value = chooesSegment(numValue, attrValueVO.getValue(), a.getSegments(), a.getUnit());
            }
            //该属性带有固定选项
            else if (a.getHasOptions()) {
                AttrOptionVO attrOptionVO = attrOptMap.get(attrValueVO.getOptionId());
                value = attrOptionVO.getContent();
            }
            //不带固定选项，但是值为数字的属性
            else if (a.getNumeric()) {
                Float numValue = new Float(value);
                //处理数字分段查找
                value = chooesSegment(numValue, attrValueVO.getValue(), a.getSegments(), a.getUnit());
            }
            attrMap.put(key, value);
        });

        //构建attrs中sku的部分
        skuAttrs.stream().forEach(a -> {
            String key = a.getName();

            List<AttrValueVO> attrValueVOS = skuAttrValueMap.get(a.getId());
            List<String> finalValues = Lists.newArrayList();
            //该属性带有固定选项, 且是数字类型的属性值
            if (a.getHasOptions() && a.getNumeric()) {
                for (AttrValueVO v : attrValueVOS) {
                    AttrOptionVO attrOptionVO = attrOptMap.get(v.getOptionId());
                    //处理数字分段查找
                    Float numValue = attrOptionVO.getNumValue();
                    String value = chooesSegment(numValue, v.getValue(), a.getSegments(), a.getUnit());
                    finalValues.add(value);
                }
            }
            //该属性带有固定选项
            else if (a.getHasOptions()) {
                List<String> values = attrValueVOS.stream().map(AttrValueVO::getValue)
                        .collect(Collectors.toList());
                finalValues.addAll(values);
            }
            //不带固定选项，但是值为数字的属性
            else if (a.getNumeric()) {
                for (AttrValueVO v : attrValueVOS) {
                    Float numValue = new Float(v.getValue());
                    //处理数字分段查找
                    finalValues.add(chooesSegment(numValue, v.getValue() + a.getUnit(), a.getSegments(), a.getUnit()));
                }
            } else {
                List<String> values = attrValueVOS.stream().map(AttrValueVO::getValue)
                        .collect(Collectors.toList());
                finalValues = values;
            }
            attrMap.put(key, finalValues);
        });

        DubboResponse<String> response = categoryService.getCateFullNameById(spuEsVO.getCid3());

        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }

        Product product = new Product();
        product.setCategoryFullName(response.getData());
        BeanUtils.copyProperties(spuEsVO, product);
        SpuDetailVO spuDetail = spuEsVO.getSpuDetail();
        product.setTitle(spuDetail.getTitle());
        product.setSubTitle(spuDetail.getSubTitle());
        product.setCreateTime(new Date());
        product.setSkus(MapperUtils.obj2json(spuEsVO.getSkus()));
        product.setAttrs(attrMap);
        product.setId(spuEsVO.getSpuId());
        String all = product.getTitle() + product.getCategoryFullName() +
                product.getSkus() + MapperUtils.obj2json(product.getAttrs());
        product.setAll(all);
        return product;
    }

    private String chooesSegment(Float numValue, String oraginValue, String segments, String unit) {
        String result = "其它";
        if (StringUtils.isBlank(segments)) {
            return oraginValue;
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
