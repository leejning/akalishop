package com.akali.common.dto.goods.base;

import lombok.Data;

/**
 * @ClassName AttrGroupDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Data
public class AttrGroupDTO {
    /**
     * 三级分类id
     */
    private Long cateId;
    /**
     * 参数组名称
     */
    private String name;
}
