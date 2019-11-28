package com.akali.business.cart.web;

import com.akali.business.cart.api.CartControllertApi;
import com.akali.common.dto.cart.CartAddDTO;
import com.akali.common.dto.cart.CartItemDTO;
import com.akali.common.dto.cart.CartQueryPageDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;
import com.akali.common.model.response.ResponseResult;
import com.akali.common.utils.ExceptionCast;
import com.akali.config.member.oauth2.Oauth2JwtUtil;
import com.akali.provider.cart.service.CartItemService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @ClassName CartControllert
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@RestController
@RequestMapping("cart")
public class CartControllert implements CartControllertApi {
    @Reference(version = "1.0.0")
    private CartItemService cartItemService;

    /**
     * 添加商品到购物车
     * @param cartDTO
     * @return
     */
    @PostMapping
    @Override
    public ResponseResult<Void> addProductToCart(CartAddDTO cartDTO) {
        DubboResponse<Void> response = cartItemService.addProductToCart(cartDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS().message("加入购物车成功");
    }

    /**
     * 修改购物车内商品数量
     * @param skuId
     * @param count
     * @param request
     * @return
     */
    @PutMapping("{skuId}")
    @Override
    public ResponseResult<Void> modifyCartItemCount(@PathVariable Long skuId, Integer count, HttpServletRequest request) {
        Long memberId = Oauth2JwtUtil.getLoginMemberId(request);
        DubboResponse<Void> response = cartItemService.modifyCartItemCount(memberId,skuId,count);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS().message("修改购物车商品数量成功");
    }

    /**
     *
     * @return
     */
    @GetMapping("{page}")
    @Override
    public ResponseResult<Void> getCart(@PathVariable Integer page, HttpServletRequest request){
        Long memberId = Oauth2JwtUtil.getLoginMemberId(request);
        CartQueryPageDTO queryPageDTO = new CartQueryPageDTO();
        queryPageDTO.setPageNo(page);
        queryPageDTO.setMemberId(memberId);
        DubboResponse<QueryResult<CartItemDTO>> response = cartItemService.getCart(queryPageDTO);
        if(!response.isSuccess()){
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData()).message("获取购物车");
    }
}
