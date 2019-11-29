package com.akali.business.admin.goods.api;

import com.akali.common.dto.goods.base.SaleOptionDTO;
import com.akali.common.model.response.QueryResponseResult;
import com.akali.common.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName SaleOptionControllerApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
@Api(value = "销售选项管理",tags = "销售选项管理api")
public interface SaleOptionControllerApi {

    @ApiOperation("创建某类商品销售选项")
    public ResponseResult<Void> createSaleOption(SaleOptionDTO saleOptionDTO);

    @ApiOperation("根据三级分类获取该了类商品的销售选项")
    public QueryResponseResult<SaleOptionDTO> querySaleOptionByCateId(Long cateId);

}
