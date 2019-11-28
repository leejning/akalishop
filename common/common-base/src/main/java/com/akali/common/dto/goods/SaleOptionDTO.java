package com.akali.common.dto.goods;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @ClassName SaleOptionDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
public class SaleOptionDTO {
    private Long id;
    /**
     * 三级分类id
     */
    private Long cateId;

    /**
     * 选项名
     */
    private String name;

    public SaleOptionDTO(Object object) {
        BeanUtils.copyProperties(object,this);
    }
}
