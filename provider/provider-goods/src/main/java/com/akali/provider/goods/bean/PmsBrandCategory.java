package com.akali.provider.goods.bean;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @ClassName PmsBrandCategory
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
@Entity
@Table(name = "pms_brand_category")
@Data
@NoArgsConstructor
public class PmsBrandCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 三级分类id
     */
    private Long cate3Id;

    public PmsBrandCategory(Long brandId, Long cate3Id) {
        this.brandId = brandId;
        this.cate3Id = cate3Id;
    }
}
