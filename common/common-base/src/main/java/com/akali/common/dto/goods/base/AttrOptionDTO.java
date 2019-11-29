package com.akali.common.dto.goods.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName AttrOptionDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttrOptionDTO {
    private Long id;
    /**
     * 产品参数id
     */
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
