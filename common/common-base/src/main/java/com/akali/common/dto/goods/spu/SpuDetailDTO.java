package com.akali.common.dto.goods.spu;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @ClassName SpuDetailDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
public class SpuDetailDTO implements Serializable {
    private static final long serialVersionUID = -4244028356308698536L;
    private Long spuId;
    /**
     * 商品介绍
     */
    private String description;
    /**
     * 通用属性，Map<Long,Long> 的json格式
     * {
     *     attrId : attrValueId,
     * }
     */
    private String genericAttr;
    /**
     * 销售选项，json格式
     *
     * {
     *   saleOptId : {
     *      name : "版本",
     *      value : [
     *          {saleOptValueId:1,saleOptValueName:"4GB+64GB"},
     *          {saleOptValueId:5,saleOptValueName:"6GB+128GB"}
     *      ]
     *   },
     *   saleOptId : {
     *       name:"颜色",
     *       value:[
     *           {saleOptValueId:2,saleOptValueName:"魅海蓝"},
     *           {saleOptValueId:4,saleOptValueName:"幻夜黑"}
     *       ]
     *   }
     * }
     *
     * mysql中的实际值：{"1":[2,4],"2":[1,3]}
     */
    private String saleOptionAttr;
    /**
     * 包装清单
     */
    private String packingList;
    /**
     * 售后服务
     */
    private String afterService;

    public SpuDetailDTO(Object object) {
        BeanUtils.copyProperties(object,this);
    }
}
