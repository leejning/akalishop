package com.akali.common.dto.goods.spu;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName SpuVO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
@Data
public class SpuVO {
    private Long id;
    /**
     * 品牌id
     */
    private Long brandId;
    private String brandName;
    /**
     * 商品简称
     */
    private String simpleName;
    /**
     * 分类id
     */
    private Long cid1;
    private Long cid2;
    private Long cid3;
    private String categoryName;
    /**
     * 状态 1:未审核 ，2:已审核，3:未上架，4:已上架，0:审核未通过
     */
    private Integer status;
    /**
     * 是否删除
     */
    private Boolean del;
    /**
     * 创建时间
     */
    private Date createTime;
}
