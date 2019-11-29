package com.akali.common.dto.goods.spu;

import com.akali.common.utils.MapperUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @ClassName SpuSaleOptionDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@ToString
public class SpuSaleOptionDTO {

    private Long spuId;
    /**
     * 销售选项id
     */
    private Long saleOptionId;
    /**
     * 绑定的sku属性的id集（PmsBaseAttribution的id）
     */
    private List<Long> skuAttrIds;

    public SpuSaleOptionDTO(Object obj1, String skuAttrIds) {
        BeanUtils.copyProperties(obj1,this);
        try {
            this.skuAttrIds = MapperUtils.json2list(skuAttrIds, Long.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSkuAttrIds(String skuAttrIds) throws Exception {
        this.skuAttrIds = MapperUtils.json2list(skuAttrIds, Long.class);
    }



//    public static void main(String[] args) {
//        SpuSaleOptionDTO obj = new SpuSaleOptionDTO(new Object(), "[2,5]");
//        System.out.println(obj);
//    }
}
