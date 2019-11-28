package com.akali.provider.cart.service;

import com.akali.common.dto.cart.CartAddDTO;
import com.akali.common.dto.cart.CartItemDTO;
import com.akali.common.dto.cart.CartQueryPageDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;

/**
 * @ClassName CartItemService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/25 0025
 * @Version V1.0
 **/
public interface CartItemService {
    /**
     * 添加商品到购物车
     * @param cartAddDTO
     * @return
     */
    DubboResponse<Void> addProductToCart(CartAddDTO cartAddDTO);

    /**
     * 修改购物车内商品id为skuId 的数量
     * @param skuId
     * @param count
     * @return
     */
    DubboResponse<Void> modifyCartItemCount(Long memberId,Long skuId, Integer count);

    /**
     * 获取购物车
     * @param queryPageDTO
     * @return
     */
    DubboResponse<QueryResult<CartItemDTO>> getCart( CartQueryPageDTO queryPageDTO);
}
