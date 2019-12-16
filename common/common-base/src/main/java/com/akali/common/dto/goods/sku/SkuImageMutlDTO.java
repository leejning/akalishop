package com.akali.common.dto.goods.sku;

import lombok.Data;

import java.util.List;

/**
 * @ClassName SkuImageMutlDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/9 0009
 * @Version V1.0
 **/
@Data
public class SkuImageMutlDTO{
    private Long skuId;
    private List<String> listImage;
}
