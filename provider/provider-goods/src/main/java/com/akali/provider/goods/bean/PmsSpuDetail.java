package com.akali.provider.goods.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

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
     * 主标题
     */
    @Column(length=256)
    private String title;
    /**
     * 促销信息
     */
    @Column(length=256)
    private String subTitle;
    /**
     * 最后修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date lastUpdateTime;
    @Column(length=64)
    private String lastModifyAdmin;
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
    @Column(length=3000)
    private String afterService;
}
