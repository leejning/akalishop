package com.akali.common.dto.goods;

import lombok.Data;

import java.util.List;

/**
 * @ClassName SpuEsDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/16 0016
 * @Version V1.0
 **/
@Data
public class SpuEsDTO {
    private Long spuId;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 主标题
     */
    private String title;
    /**
     * 促销信息
     */
    private String subTitle;
    /**
     * 分类id
     */
    private Long cid1;
    private Long cid2;
    private Long cid3;
    /**
     * sku集合
     */
    private List<SkuEsDTO> skus;
    /**
     * 商品详情
     */
    private SpuDetailDTO spuDetail;
}
