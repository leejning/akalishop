package com.akali.common.dto.goods;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SkuEsDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/16 0016
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkuEsDTO {
    private Long id;
    private Long spuId;
    /**
     * sku标题
     */
    private String title;
    /**
     * 主图片
     */
    private String mainImage;
    /**
     * 价格
     */
    private Long price;
}
