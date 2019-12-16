package com.akali.provider.goods.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName PmsBaseCategory
 * @Description: TODO 一级和二级分类
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Entity
@Table(name = "pms_base_category")
@Data
public class PmsBaseCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 一级或二级分类名称
     */
    @Column(length = 20)
    private String name;
    /**
     * 父级分类id
     */
    @Column(length = 20)
    private Long parentId;
    /**
     * 分类等级 1 或者 2
     */
    @Column(length = 1)
    private Integer level;
    /**
     *
     */
    @Column(length = 40)
    private String fullName;
}
