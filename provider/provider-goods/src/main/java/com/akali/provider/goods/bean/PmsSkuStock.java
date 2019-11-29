package com.akali.provider.goods.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

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
@NoArgsConstructor
@AllArgsConstructor
public class PmsSkuStock {
    @Id
    private Long skuId;
    private Long spuId;
    /**
     * 总库存
     */
    @Column(length=9)
    private Integer stock;
    /**
     * 价格
     */
    @Column(length=15)
    private Long price;

}
