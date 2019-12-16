package com.akali.common.dto.goods.base.attribute;

import lombok.Data;

/**
 * @ClassName AttrValueDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/8 0008
 * @Version V1.0
 **/
@Data
public class AttrValueDTO {
    /**
     * 属性id
     */
    private Long attrId;
    /**
     * 商品id
     */
    private Long spuId;
    /**
     * 属性值
     */
    private String value;
    private Long optionId;
}
