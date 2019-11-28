package com.akali.provider.goods.api;

import com.akali.common.dto.goods.*;
import com.akali.common.model.response.DubboResponse;

import java.util.List;

/**
 * @ClassName AttributionService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
public interface AttributionService {
    /**
     * 创建属性分组
     * @param attrOptionDTO
     * @return
     */
    DubboResponse<Void> createAttributeOption(AttrOptionDTO attrOptionDTO);

    /**
     * 创建商品属性
     * @param attributionDTO
     * @return
     */
    DubboResponse<Void> createAttribute(AttributionDTO attributionDTO);

    /**
     * 添加固定属性选项
     * @param attrGroupDTO
     * @return
     */
    DubboResponse<Void> createAttributeGroup(AttrGroupDTO attrGroupDTO);

    /**
     * 根据三级分类id,查找全部商品属性信息，按分组存放
     * @param cateId
     * @return
     */
    DubboResponse<CategoryAttrInfoDTO> queryAllAttributeByCidWithGroup(Long cateId);

    /**
     * 根据三级分类id，查询该分类的全部商品属性
     * @param cid3
     * @return
     */
    DubboResponse<List<CateAttributeDTO>> queryAttributesByCid(Long cid3);

    /**
     * 根据spuId获取 商品所有属性值
     * @param spuId
     * @return
     */
    DubboResponse<List<AttrValueDTO>> queryProductAttrValueBySpuId(Long spuId);
}
