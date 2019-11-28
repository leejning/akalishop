package com.akali.common.dto.cart;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @ClassName CartItemDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/27 0027
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
public class CartItemDTO {
    private String id;
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


    public CartItemDTO(Object object) {
        BeanUtils.copyProperties(object,this);
    }
}
