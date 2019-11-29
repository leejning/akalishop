package com.akali.business.admin.goods.api;

import com.akali.common.dto.goods.base.AttrGroupDTO;
import com.akali.common.dto.goods.base.AttrOptionDTO;
import com.akali.common.dto.goods.base.AttributionDTO;
import com.akali.common.dto.goods.base.CategoryAttrInfoDTO;
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
    public ResponseResult<Void> createAttribute(AttributionDTO attributionDTO);

    @ApiOperation("添加属性选项")
    public ResponseResult<Void> createAttributeOption(AttrOptionDTO attrOptionDTO);

    @ApiOperation("根据分类id，获取该分类的全部属性信息")
    public ResponseResult<CategoryAttrInfoDTO> queryAllAttributeInfoByCid(Long cateId);



}
