package com.akali.business.admin.goods.api;

import com.akali.common.dto.goods.base.BrandCreateDTO;
import com.akali.common.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName BrandControllerApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Api(value = "品牌管理业务接口",tags = "品牌")
public interface BrandControllerApi {

    @ApiOperation("添加新品牌")
    public ResponseResult<Void> createBrand(BrandCreateDTO brandCreateDTO);

}
