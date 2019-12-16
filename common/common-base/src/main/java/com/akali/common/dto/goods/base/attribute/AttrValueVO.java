package com.akali.common.dto.goods.base.attribute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName AttrValueVO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttrValueVO implements Serializable {
    private static final long serialVersionUID = 3949311662286116966L;
    private Long id;
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

//    public AttrValueVO(Object object) {
//        BeanUtils.copyProperties(object,this);
//    }
}
