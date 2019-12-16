package com.akali.common.dto.goods.base;

import com.akali.common.utils.MapperUtils;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName SpuSaleOptionVO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/8 0008
 * @Version V1.0
 **/
@Data
public class SpuSaleOptionVO implements Serializable {
    private static final long serialVersionUID = 8128960565137788789L;
    private Long id;
    private Long spuId;
    /**
     * 销售选项id
     */
    private Long saleOptionId;
    private String saleOptionName;
    /**
     * 绑定的sku属性的id，PmsBaseAttribution类的id且generic值是false ，List<Long> 的json格式
     */
    private List<Long> skuAttrIds;

    public SpuSaleOptionVO(Object object,String skuAttrIds) {
        BeanUtils.copyProperties(object,this);
        try {
            this.skuAttrIds = MapperUtils.json2list(skuAttrIds, Long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
