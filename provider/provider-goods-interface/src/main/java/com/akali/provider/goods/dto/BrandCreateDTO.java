package com.akali.provider.goods.dto;

import lombok.Data;

import java.util.List;

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
    /**
     * 三级分类id
     */
    private List<Long> cate3Ids;
}
