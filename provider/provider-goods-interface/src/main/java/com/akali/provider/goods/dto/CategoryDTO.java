package com.akali.provider.goods.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName CategoryDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
public class CategoryDTO {
    /**
     * 分类名称
     */
    private String name;

    /**
     * 父级分类id
     */
    private Long parentId;
    /**
     * 分类等级
     */
    private Integer level;

    public CategoryDTO(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }
}
