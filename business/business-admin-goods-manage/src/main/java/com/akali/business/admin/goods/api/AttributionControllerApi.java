package com.akali.business.admin.goods.api;

import com.akali.common.dto.goods.base.attribute.*;
import com.akali.common.dto.goods.base.category.CateAttributeVO;
import com.akali.common.dto.goods.base.category.CategoryAttrInfoVO;
import com.akali.common.model.response.QueryResponseResult;
import com.akali.common.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName AttributionControllerApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Api(value = "商品分类属性管理接口",tags = "商品属性管理接口")
public interface AttributionControllerApi {

    @ApiOperation("创建属性分组")
    public ResponseResult<Void> createAttributeGroup(AttrGroupDTO attrGroupDTO);

    @ApiOperation("创建属性")
    public ResponseResult<Long> createAttribute(AttributionDTO attributionDTO);
    @ApiOperation("修改属性信息")
    public ResponseResult<Void> updateAttribute(Long attrId,AttributionDTO attributionDTO);

    @ApiOperation("添加属性选项")
    public ResponseResult<Long> createAttributeOption(AttrOptionDTO attrOptionDTO);

    @ApiOperation("修改属性选项")
    public ResponseResult<Void> modifyAttributeOption(Long optId,AttrOptionDTO attrOptionDTO);

    @ApiOperation("根据分类id，按组获取该分类的全部属性信息")
    public ResponseResult<CategoryAttrInfoVO> queryAllAttributeByCidGroup(Long cateId);

    @ApiOperation("根据分类id，获取该分类的全部属性信息")
    public QueryResponseResult<CateAttributeVO> queryAllAttributeByCidList(Long cateId);

    @ApiOperation("根据分类id，获取属性分组信息")
    public QueryResponseResult<AttrGroupVO> queryAttrGroupByCid(Long cateId, AttrGroupDTO attrGroupDTO);

    @ApiOperation("根据属性组id，获取属性信息")
    public QueryResponseResult<AttributionVO> queryAttributionByGroupId(Long groupId);

    @ApiOperation("根据分类id，获取sku属性信息")
    public QueryResponseResult<AttributionSimpleVO> querySkuAttributionByCateId(Long cateId);

    @ApiOperation("修改属性的搜索分片信息")
    public ResponseResult<Void> setAttributeSearchSegment(Long attrId, AttrSegmentDTO attrSegmentDTO);
}
