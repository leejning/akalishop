package com.akali.provider.goods.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName PmsBaseAttrGroup
 * @Description: TODO 产品参数组
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Table(name="pms_base_attr_group")
@Entity
@Data
public class PmsBaseAttrGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
