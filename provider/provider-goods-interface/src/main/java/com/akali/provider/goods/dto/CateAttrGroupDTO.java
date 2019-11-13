package com.akali.provider.goods.dto;

import lombok.Data;

import java.util.List;

/**
 * @ClassName CateAttrGroupDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Data
public class CateAttrGroupDTO {
    private Long id;
    /**
     * 三级分类id
     */
    private Long cateId;
    /**
     * 参数组名称
     */
    private String name;
    /**
     * 组内商品属性
     */
    private List<CateAttributeDTO> attributes;
}
