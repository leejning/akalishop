package com.akali.provider.goods.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName PmsSkuStock
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
@Entity
@Table(name = "pms_sku_stock")
@Data
public class PmsSkuStock {
    @Id
    private Long skuId;
    /**
     * 总库存
     */
    @Column(length=9)
    private Integer stock;
}
