package com.akali.business.member.cart.api;

import com.akali.common.dto.cart.CartAddDTO;
import com.akali.common.dto.cart.CartItemDTO;
import com.akali.common.model.response.QueryResponseResult;
import com.akali.common.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName CartControllertApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@Api(value = "会员购物车服务接口",tags = "购物车服务API")
public interface CartControllertApi {

    @ApiOperation("添加商品到购物车")
    public ResponseResult<Void> addProductToCart(CartAddDTO cartAddDTO);


    @ApiOperation("修改购物车内商品数量")
    public ResponseResult<Void> modifyCartItemCount(Long skuId, Integer count, HttpServletRequest request);

    @ApiOperation("获取购物车")
    public QueryResponseResult<CartItemDTO> getCart(Integer page, HttpServletRequest request);
}
