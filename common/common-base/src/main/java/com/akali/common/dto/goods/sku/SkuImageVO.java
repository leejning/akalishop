package com.akali.common.dto.goods.sku;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

/**
 * @ClassName SkuImageVO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/9 0009
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
public class SkuImageVO implements Serializable {

    private static final long serialVersionUID = 8971095560870576554L;
    private Long id;
    private Long skuId;
    private String imageUrl;
    private Boolean isMain;
    private Boolean enable;
    private String description;

    public SkuImageVO(Object object) {
        BeanUtils.copyProperties(object,this);
    }
}
