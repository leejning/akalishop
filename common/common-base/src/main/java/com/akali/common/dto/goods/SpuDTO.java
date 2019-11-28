package com.akali.common.dto.goods;

import lombok.Data;

/**
 * @ClassName SpuDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
@Data
public class SpuDTO {
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
}
