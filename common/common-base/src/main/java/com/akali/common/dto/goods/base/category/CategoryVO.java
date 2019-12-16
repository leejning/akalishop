package com.akali.common.dto.goods.base.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Collections;
import java.util.List;

/**
 * @ClassName CategoryVO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryVO {
    private Long id;
    /**
     * 分类名称
     */
    private String name;

    /**
     * 父级分类id
     */
    private Long parentId;

    private String fullName;
    /**
     * 子节点
     */
    private List<CategoryVO> children = Collections.emptyList();

    public CategoryVO(Object object) {
        BeanUtils.copyProperties(object,this);
    }
}
