package com.akali.business.admin.goods.api;

import com.akali.common.dto.goods.base.SaleOptionDTO;
import com.akali.common.dto.goods.base.SaleOptionVO;
import com.akali.common.dto.query.SaleOptionQueryDTO;
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

    @ApiOperation("创建销售选项")
    public ResponseResult<Void> createSaleOption(SaleOptionDTO saleOptionDTO);

    @ApiOperation("根据三级分类获取销售选项")
    public QueryResponseResult<SaleOptionDTO> querySaleOptionByCateId(Long cateId);

    @ApiOperation("获取销售选项分页列表")
    public QueryResponseResult<SaleOptionVO> querySaleOptionPage(SaleOptionQueryDTO saleOptionQueryDTO);

    @ApiOperation("搜索获取销售属性，商品选择销售属性时使用")
    public QueryResponseResult<SaleOptionVO> querySaleOption(SaleOptionQueryDTO saleOptionQueryDTO);



}
