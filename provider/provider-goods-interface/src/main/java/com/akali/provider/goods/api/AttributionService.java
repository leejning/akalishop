package com.akali.provider.goods.api;

import com.akali.common.model.response.DubboResponse;
import com.akali.provider.goods.dto.AttrGroupDTO;
import com.akali.provider.goods.dto.AttrOptionDTO;
import com.akali.provider.goods.dto.AttributionDTO;
import com.akali.provider.goods.dto.CategoryAttrInfoDTO;

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
     * 根据三级分类id查找全部商品属性信息
     * @param cateId
     * @return
     */
    DubboResponse<CategoryAttrInfoDTO> queryAllAttributeInfoByCid(Long cateId);
}
