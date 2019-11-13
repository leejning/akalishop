package com.akali.provider.goods.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName PmsBaseAttribution
 * @Description: TODO 产品参数
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Entity
@Table(name = "pms_base_attribution")
@Data
public class PmsBaseAttribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 三级分类id
     */
    private Long cateId;
    /**
     * 参数所属分组
     */
    private Long groupId;
    /**
     * 参数名
     */
    @Column(length=16)
    private String name;
    /**
     * 是否是数值类型 true是，false不是
     */
    @Column(name="`numeric`",columnDefinition="tinyint",length=2)
    private Boolean numeric;
    /**
     * 数组类型的单位
     */
    @Column(length=16)
    private String unit;
    /**
     * 数值类型 分段查找 如：2000mAh-3000mAh
     */
    @Column(length=40)
    private String segments;
    /**
     * 是否是销售属性（用户可选择的属性），true是，false不是
     */
    @Column(columnDefinition="tinyint",length=2)
    private Boolean saleAttr;
    /**
     * 是否是通用属性，true是，false不是
     */
    @Column(columnDefinition="tinyint",length=2)
    private Boolean generic;
    /**
     * 是否参与搜索
     */
    @Column(columnDefinition="tinyint",length=2)
    private Boolean searching;
    /**
     * 是否是固定选项，true是，false不是
     */
    @Column(columnDefinition="tinyint",length=2)
    private Boolean hasOptions;

}
