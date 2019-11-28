package com.akali.provider.goods.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName PmsBaseAttrOption
 * @Description: TODO 固定参数选项
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Entity
@Table(name = "pms_base_attr_option")
@Data
public class PmsBaseAttrOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 产品参数id
     */
    private Long attrId;

    /**
     * 选项内容
     */
    @Column(length=32)
    private String content;

    /**
     * 数值类型的值
     */
    @Column(length=10)
    private Float numValue;

}
