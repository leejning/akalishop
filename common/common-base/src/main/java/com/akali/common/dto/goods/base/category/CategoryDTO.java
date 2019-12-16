package com.akali.common.dto.goods.base.category;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName CategoryDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/2 0002
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
public class CategoryDTO {
    private String name;
    /**
     * 父级分类id
     */
    private Long parentId;
    /**
     * 分类等级
     */
    private Integer level;

    private String fullName;

    List<CategoryDTO> child;

    Long id;

    private String formKey;

    public CategoryDTO(String name, Long parentId, Integer level, String fullName) {
        this.name = name;
        this.parentId = parentId;
        this.level = level;
        this.fullName = fullName;
    }
}
