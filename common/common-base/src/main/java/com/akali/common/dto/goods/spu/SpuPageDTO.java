package com.akali.common.dto.goods.spu;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @ClassName SpuPageDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
@Data
public class SpuPageDTO {
    private Long id;
    /**
     * 品牌id
     */
    private Long brandId;
    /**
     * 商品简称
     */
    private String simpleName;
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
    /**
     * 在售状态
     */
    private Boolean saleable;
    /**
     * 是否合法，审核
     */
    private Boolean valid;
    /**
     * 是否删除
     */
    private Boolean del;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 最后修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date lastUpdateTime;
    private String lastModifyAdmin;
}
