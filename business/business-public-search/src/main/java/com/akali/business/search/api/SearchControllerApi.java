package com.akali.business.search.api;

import com.akali.common.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName SearchControllerApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/24 0024
 * @Version V1.0
 **/
@Api(value = "搜索服务Api",tags = "搜索服务Api")
public interface SearchControllerApi {
    @ApiOperation("按id搜索")
    public ResponseResult<ProductDTO> queryProductById(Long SpuId);
}
