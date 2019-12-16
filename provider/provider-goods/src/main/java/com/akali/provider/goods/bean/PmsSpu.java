package com.akali.provider.goods.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @ClassName PmsSpu
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
@Entity
@Table(name = "pms_spu")
@Data
public class PmsSpu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 品牌id
     */
    private Long brandId;
    private String brandName;
    /**
     * 商品简称
     */
    @Column(length=64)
    private String simpleName;
    /**
     * 分类id
     */
    @Column(length=20)
    private Long cid1;
    @Column(length=20)
    private Long cid2;
    @Column(length=20)
    private Long cid3;
    @Column(length = 60)
    private String categoryName;
    /**
     * 状态 1:未审核 ，2:已审核，3:未上架，4:已上架，0:审核未通过
     */
    @Column(length = 1)
    private Integer status;
    /**
     * 删除状态
     */
    @Column(columnDefinition="tinyint",length=2)
    private Boolean del;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date createTime;
}
