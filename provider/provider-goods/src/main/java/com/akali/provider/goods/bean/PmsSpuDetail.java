package com.akali.provider.goods.bean;

import lombok.Data;

import javax.persistence.*;

/**
 * @ClassName PmsSpuDetail
 * @Description: TODO 商品整体信息
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
@Entity
@Table(name = "pms_spu_detail")
@Data
public class PmsSpuDetail {
    @Id
    private Long spuId;
    /**
     * 商品介绍
     */
    @Column(length=3000)
    private String description;
    /**
     * 通用属性，Map<Long,Long> 的json格式
     * {
     *     attrId : attrValueId,
     * }
     */
    @Column(length=1000)
    private String genericAttr;
    /**
     * 销售选项，Map<Long,List<Long>> json格式
     *
     * {
     *   spuSaleOptId : [saleOptValueId1,saleOptValueId2]
     * }
     */
    @Column(length=300)
    private String saleOptionAttr;
    /**
     * 包装清单
     */
    @Column(length=1000)
    private String packingList;
    /**
     * 售后服务
     */
    @Column(length=1000)
    private String afterService;
}
