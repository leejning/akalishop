package com.akali.provider.goods.dto;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ClassName SpuAttrValueCollectDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
@Data
public class SpuAttrValueCollectDTO implements Serializable {
    private static final long serialVersionUID = 4095110342794534372L;
    private Long spuId;
    /**
     * sku属性参数样式 attrId : attrValue
     * {
     * 	1 : ["魅海蓝", "幻夜黑"],
     * 	2 : [1, 3, 5]
     * }
     */
    private Map<Long, List<String>> skuAttr;
    /**
     * spu属性参数样式  attrId : attrValue
     * {
     * 	7 : "玻璃后盖",
     * 	4 : 2019
     * }
     */
    private Map<Long,String> spuAttr;

    public SpuAttrValueCollectDTO(Object obj) {
        BeanUtils.copyProperties(obj,this);
    }
}
