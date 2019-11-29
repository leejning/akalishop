package com.akali.common.dto.goods.sku;


import com.akali.common.utils.MapperUtils;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.util.Map;

/**
 * @ClassName SkuDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
public class SkuDTO {
    private Long id;
    private Long spuId;
    /**
     * sku标题
     */
    private String title;
    /**
     * 主图片
     */
    private String mainImage;
    /**
     * 价格
     */
    private Long price;
    /**
     * 销售选项下标,用于选择不同的销售选项组合时，标识唯一的sku
     */
    private String indexes;

    /**
     * sku特有属性，Map<Long,Long>
     * {
     *     attrId : attrValueId,
     * }
     */
    private Map<Long,Long> ownSpec;

    public void setOwnSpec(String ownSpec)throws IOException{
        this.ownSpec = MapperUtils.json2mapLong(ownSpec);
    }

    public SkuDTO(Object object,String ownSpec) throws IOException {
        BeanUtils.copyProperties(object,this);
    }
}
