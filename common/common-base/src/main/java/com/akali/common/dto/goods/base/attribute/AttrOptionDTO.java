package com.akali.common.dto.goods.base.attribute;

import lombok.Data;

/**
 * @ClassName AttrOptionDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/10 0010
 * @Version V1.0
 **/
@Data
public class AttrOptionDTO {
    private Long attrId;

    /**
     * 选项内容
     */
    private String content;

    /**
     * 数值类型的值
     */
    private Float numValue;
}
