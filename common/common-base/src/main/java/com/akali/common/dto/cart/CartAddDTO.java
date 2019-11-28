package com.akali.common.dto.cart;

import lombok.Data;

import java.util.List;

/**
 * @ClassName CartAddDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/26 0026
 * @Version V1.0
 **/
@Data
public class CartAddDTO {
    /**
     * 会员ID
     */
    private Long memberId;
    /**
     * spuID
     */
    private Long spuId;
    /**
     * skuId
     */
    private Long skuId;
    /**
     * 商品简称
     */
    private String spuSimpleName;
    /**
     * sku标题
     */
    private String skuTitle;
    /**
     * sku图片
     */
    private String skuImage;
    /**
     * 数量
     */
    private Integer count;

    /**
     * 销售属性  逗号分隔： 魅海蓝,2GB+32GB
     */
    private List<String> saleAttrNames;
}
