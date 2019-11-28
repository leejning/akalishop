package com.akali.provider.cart.service;

import com.akali.common.dto.cart.CartAddDTO;
import com.akali.common.dto.cart.CartItemDTO;
import com.akali.common.dto.cart.CartQueryPageDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;
import com.google.common.collect.Lists;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CartItemServiceImplTest {
    @Reference(version = "1.0.0")
    private CartItemService cartItemService;

    @Test
    public void addProductToCart() {
        CartAddDTO cartAddDTO = new CartAddDTO();
        cartAddDTO.setSpuId(1L);
        cartAddDTO.setSkuId(5L);
        cartAddDTO.setSpuSimpleName("华为手机");
        cartAddDTO.setSkuTitle("华为p30");
        cartAddDTO.setCount(1);
        cartAddDTO.setMemberId(1L);
        cartAddDTO.setSaleAttrNames(Lists.newArrayList("珠光贝母","8GB+256GB"));
        cartItemService.addProductToCart(cartAddDTO);


    }

    @Test
    public void getCart(){
        CartQueryPageDTO cartQueryPageDTO = new CartQueryPageDTO();
        cartQueryPageDTO.setMemberId(1L);
        cartQueryPageDTO.setPageNo(1);
        cartQueryPageDTO.setPageSize(2);
        DubboResponse<QueryResult<CartItemDTO>> cart = cartItemService.getCart(cartQueryPageDTO);
        System.out.println();


    }
}