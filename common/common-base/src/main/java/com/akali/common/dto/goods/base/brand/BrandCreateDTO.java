package com.akali.common.dto.goods.base.brand;

import lombok.Data;

/**
 * @ClassName BrandCreateDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
@Data
public class BrandCreateDTO {
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
