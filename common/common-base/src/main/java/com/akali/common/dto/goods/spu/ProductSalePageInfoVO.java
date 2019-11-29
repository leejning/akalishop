package com.akali.common.dto.goods.spu;

import com.akali.common.dto.goods.sku.SkuStockPriceDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName ProductSalePageInfoVO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSalePageInfoVO {
    List<SkuStockPriceDTO> skuInfos;
}
