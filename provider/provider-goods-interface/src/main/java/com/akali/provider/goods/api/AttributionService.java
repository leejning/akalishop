package com.akali.provider.goods.api;

import com.akali.common.dto.goods.base.attribute.*;
import com.akali.common.dto.goods.base.category.CateAttributeVO;
import com.akali.common.dto.goods.base.category.CategoryAttrInfoVO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;

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
     * @param attrOptionVO
     * @return
     */
    DubboResponse<Long> createAttributeOption(AttrOptionDTO attrOptionVO);

    /**
     * 创建商品属性
     * @param attributionDTO
     * @return
     */
    DubboResponse<Long> createAttribute(AttributionDTO attributionDTO);

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
    DubboResponse<CategoryAttrInfoVO> queryAllAttributeByCidWithGroup(Long cateId);

    /**
     * 根据三级分类id，查询该分类的全部商品属性
     * @param cid3
     * @return
     */
    DubboResponse<List<CateAttributeVO>> queryAttributesByCid(Long cid3);

    /**
     * 根据spuId获取 商品所有属性值
     * @param spuId
     * @return
     */
    DubboResponse<List<AttrValueVO>> queryProductAttrValueBySpuId(Long spuId);

    /**
     * 根据分类ID查询属性组
     * @param cateId
     * @param attrGroupDTO
     * @return
     */
    DubboResponse<QueryResult<AttrGroupVO>> queryAttrGroupByCid(Long cateId, AttrGroupDTO attrGroupDTO);

    /**
     * 根据分组ID获取属性成功
     * @param groupId
     * @return
     */
    DubboResponse<QueryResult<AttributionVO>> queryAttributionByGroupId(Long groupId);

    /**
     * 修改商品属性
     * @param attrId
     * @param attributionDTO
     * @return
     */
    DubboResponse<Void> updateAttribute(Long attrId,AttributionDTO attributionDTO);

    /**
     * 根据三级分类id查找全部商品属性信息
     * @param cateId
     * @return
     */
    DubboResponse<QueryResult<CateAttributeVO>> queryAllAttributeByCidList(Long cateId);

    /**
     * 根据分类id 获取sku属性
     * @param cateId
     * @return
     */
    DubboResponse<QueryResult<AttributionSimpleVO>> querySkuAttributionByCateId(Long cateId);

    /**
     * 修改属性的搜索分片信息
     * @param attrId
     * @param attrSegmentDTO
     * @return
     */
    DubboResponse<Void> setAttributeSearchSegment(Long attrId, AttrSegmentDTO attrSegmentDTO);

    /**
     * 修改属性选项信息
     * @param optId
     * @param attrOptionDTO
     * @return
     */
    DubboResponse<Void> modifyAttributeOption(Long optId, AttrOptionDTO attrOptionDTO);

    /**
     * 根据分类id 查询所有刻搜索的属性字段
     * @param categoryIds
     * @return
     */
    DubboResponse<List<AttributionSimpleVO>> querySearchingAttrByCateIds(List<Long> categoryIds);
}
