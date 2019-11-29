package com.akali.business.free.goods.api;

import com.akali.common.dto.goods.spu.ProductSalePageInfoVO;
import com.akali.common.model.response.QueryResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName ProductControllerApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
@Api(value = "商品详情公共服务接口")
public interface ProductControllerApi {
    @ApiOperation("获取某个商品的销售页信息")
    public QueryResponseResult<ProductSalePageInfoVO> getProductSalePageInfo(Long spuId);
}
