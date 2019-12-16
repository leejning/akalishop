package com.akali.common.dto.goods.base.brand;

import lombok.Data;

/**
 * @ClassName BrandVO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/5 0005
 * @Version V1.0
 **/
@Data
public class BrandVO {
    private Long id;
    /**
     * 品牌名
     */
    private String name;
    /**
     * logo
     */
    private String logo;
    /**
     * 品牌首字母
     */
    private String firstLetter;
}
