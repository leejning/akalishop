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
    /**
     * 主标题
     */
    @Column(length=250)
    private String title;
    /**
     * 促销信息
     */
    @Column(length=250)
    private String subTitle;
    /**
     * 分类id
     */
    @Column(length=20)
    private Long cid1;
    @Column(length=20)
    private Long cid2;
    @Column(length=20)
    private Long cid3;
    /**
     * 在售状态
     */
    @Column(columnDefinition="tinyint",length=2)
    private Boolean saleable;
    /**
     * 是否合法，审核
     */
    @Column(columnDefinition="tinyint",length=2)
    private Boolean valid;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 最后修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date lastUpdateTime;
}
