package com.akali.common.dto.goods.sku;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SkuStockPriceDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkuStockPriceDTO {
    private Long skuId;
    /**
     * 价格
     */
    private Long price;
    /**
     * 总库存
     */
    private Integer stock;

}
