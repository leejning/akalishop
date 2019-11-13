package com.akali.provider.goods.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName PmsSpuSaleOptionValue
 * @Description: TODO  销售商品的用户可选项的值，不同选项值的组合决定某个sku
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
@Entity
@Table(name = "pms_spu_sale_option_value")
@Data
public class PmsSpuSaleOptionValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long spuId;
    private Long spuSaleOptionId;

    /**
     * 选项值,AttrValue的name字段用 '+' 连接
     */
    @Column(length = 40)
    private String value;

    /**
     * 是否有效
     */
    @Column(columnDefinition="tinyint",length=2)
    private Boolean enable;

}
