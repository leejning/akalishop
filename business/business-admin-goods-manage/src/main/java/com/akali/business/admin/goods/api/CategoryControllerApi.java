package com.akali.business.admin.goods.api;

import com.akali.common.dto.goods.base.CategoryDTO;
import com.akali.common.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName CategoryControllerApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Api(value = "商品分类业务接口",tags = "分类管理接口")
public interface CategoryControllerApi {

    @ApiOperation("创建商品分类")
    public ResponseResult<Void> createCategory(Integer level, CategoryDTO categoryDTO);

    @ApiOperation("获取各级分类")
    public ResponseResult<CategoryDTO> getCategoryByPid(Integer level, Long pid);



}
