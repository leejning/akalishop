package com.akali.common.dto.search;

import lombok.Data;

/**
 * @ClassName ProductDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/24 0024
 * @Version V1.0
 **/
@Data
public class ProductDTO {
    private Long id;
    /**
     * 所有需要被搜索的信息，包含标题，分类，甚至品牌
     */
    private String title;
    /**
     * 卖点
     */
    private String subTitle;
    /**
     * 分类全名
     */
    private String categoryFullName;
    private String skus;
}
