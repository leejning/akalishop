package com.akali.common.dto.goods.base.attribute;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName AttrGroupVO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/5 0005
 * @Version V1.0
 **/
@Data
public class AttrGroupVO implements Serializable {
    private static final long serialVersionUID = 681564056870517928L;
    private Long id;
    /**
     * 三级分类id
     */
    private Long cateId;
    /**
     * 参数组名称
     */
    private String name;


}
