package com.akali.business.search.api;

import com.akali.common.dto.search.ProductVO;
import com.akali.common.dto.search.SearchDTO;
import com.akali.common.dto.search.SearchQueryResult;
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
    public ResponseResult<ProductVO> queryProductById(Long SpuId);
    @ApiOperation("页面搜索")
    public ResponseResult<SearchQueryResult> searchProduct(SearchDTO searchDTO);

}
