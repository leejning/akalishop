package com.akali.provider.goods.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @ClassName PmsSpuSaleOption
 * @Description: TODO 买家购买某商品时，商品的可选性，例如买手机时选颜色和版本。spu关联PmsSaleOption与PmsBaseAttribution
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
@Entity
@Table(name = "pms_spu_sale_option")
@Data
@NoArgsConstructor
public class PmsSpuSaleOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long spuId;

    /**
     * 销售选项id
     */
    private Long saleOptionId;

    /**
     * 绑定的sku属性的id，PmsBaseAttribution类的id且generic值是false ，List<Long> 的json格式
     */
    @Column(length = 100)
    private String skuAttrIds;
}


