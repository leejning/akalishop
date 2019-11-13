package com.akali.provider.goods.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName PmsCategory
 * @Description: TODO 三级分类
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Entity
@Table(name = "pms_category")
@Data
public class PmsCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 分类名
     */
    @Column(length = 20)
    private String name;
    /**
     * 二级分类id
     */
    @Column(length = 20)
    private Long parentId;
}
