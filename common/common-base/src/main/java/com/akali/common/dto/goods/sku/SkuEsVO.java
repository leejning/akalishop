package com.akali.common.dto.goods.sku;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @ClassName SkuEsVO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/16 0016
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkuEsVO implements Serializable {

    private static final long serialVersionUID = 6158045394560636157L;
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
