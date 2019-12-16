package com.akali.provider.goods.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @ClassName PmsSkuImage
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/9 0009
 * @Version V1.0
 **/
@Data
@Entity
@Table(name = "pms_sku_image")
@NoArgsConstructor
public class PmsSkuImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long skuId;
    private String imageUrl;
    private Boolean isMain;
    private Boolean enable;
    private String description;

    public PmsSkuImage(Long skuId,String imageUrl) {
        this.skuId = skuId;
        this.imageUrl = imageUrl;
        this.isMain = false;
        this.enable = true;
    }
}
