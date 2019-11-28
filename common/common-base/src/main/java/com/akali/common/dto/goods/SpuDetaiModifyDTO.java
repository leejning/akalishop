package com.akali.common.dto.goods;

import lombok.Data;

/**
 * @ClassName SpuDetaiModifyDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
@Data
public class SpuDetaiModifyDTO {
    private Long spuId;
    /**
     * 商品介绍
     */
    private String description;
    /**
     * 包装清单
     */
    private String packingList;
    /**
     * 售后服务
     */
    private String afterService;
}
