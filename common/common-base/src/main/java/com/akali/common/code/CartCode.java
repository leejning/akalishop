package com.akali.common.code;

import com.google.common.collect.ImmutableMap;

/**
 * @ClassName CartCode
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/27 0027
 * @Version V1.0
 **/
public enum CartCode implements ResultCode{
    /**
     *
     */
    SKU_NOT_IN_CART(false,90012,"购物车内没有该商品！"),
    ;

    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private CartCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    private static final ImmutableMap<Integer, CartCode> CACHE;
    static {
        final ImmutableMap.Builder<Integer, CartCode> builder = ImmutableMap.builder();
        for (CartCode commonCode : values()) {
            builder.put(commonCode.code(), commonCode);
        }
        CACHE = builder.build();
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
