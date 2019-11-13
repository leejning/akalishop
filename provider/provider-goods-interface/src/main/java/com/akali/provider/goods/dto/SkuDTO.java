package com.akali.provider.goods.dto;

import com.akali.provider.goods.api.MapperUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Maps;
import lombok.Data;

import java.util.Map;

/**
 * @ClassName SkuDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
@Data
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

    public static void main(String[] args) throws JsonProcessingException {
        Map<Long, Long> map = Maps.newHashMap();
        map.put(1L,2L);
        map.put(5L,8L);
        map.put(2L,4L);
        System.out.println(MapperUtils.obj2json(map));
    }
}
