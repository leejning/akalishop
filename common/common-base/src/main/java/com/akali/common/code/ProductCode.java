package com.akali.common.code;

import com.google.common.collect.ImmutableMap;

/**
 * @ClassName ProductCode
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
public enum ProductCode implements ResultCode{
    /**
     *
     */
    SPU_DETAIL_NOT_EXSIST(false,30025,"商品详情不存在"),
    SPU_NOT_EXSIST(false,30026,"商品不存在"),
    SKU_NOT_EXSIST(false,30027,"商品sku不存在"),
    THE_SKU_IS_EXSIST(false,30029,"添加的商品sku已存在"),
    SALE_OPTION_NOT_EXIST(false,30039,"销售选项不存在"),
    SKU_IMAGE_NOT_EXIST(false,30038,"sku图片不存在"),
    OLD_SKU_MAIN_IMAGE_NOT_EXIST(false,30036,"sku主图片不存在"),
    ATTRIBUTE_NOT_EXIST(false,30037,"属性不存在"),
    ATTR_OPTION_NOT_EXIST(false,30035,"属性选项不存在"),
    CATEGORY_NOT_EXIST(false,30034,"分类不存在"),
    ;

    boolean success;
    //操作代码
    int code;
    //提示信息
    String message;
    private ProductCode(boolean success, int code, String message){
        this.success = success;
        this.code = code;
        this.message = message;
    }
    private static final ImmutableMap<Integer, ProductCode> CACHE;
    static {
        final ImmutableMap.Builder<Integer, ProductCode> builder = ImmutableMap.builder();
        for (ProductCode commonCode : values()) {
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
