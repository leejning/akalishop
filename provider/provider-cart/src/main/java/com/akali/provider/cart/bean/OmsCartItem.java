package com.akali.provider.cart.bean;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

/**
 * @ClassName OmsCartItem
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/25 0025
 * @Version V1.0
 **/

@Data
@ToString
@Document(collection = "oms_cart_item")
public class OmsCartItem {
    @Id
    private String id;
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
     * 价格
     */
    private Long addTimePrice;

    /**
     * 销售属性  [ 魅海蓝 , 2GB+32GB ]
     */
    private List<String> saleAttrNames;

}
