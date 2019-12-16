package com.akali.common.dto.goods.sku;

import com.akali.common.dto.goods.base.attribute.AttributionSimpleVO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName SkuTableHeader
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/8 0008
 * @Version V1.0
 **/
@Data
public class SkuTableHeader implements Serializable {
    private static final long serialVersionUID = 6174835838131412252L;
    Long ssoId;
    String saleOptionName;
    List<AttributionSimpleVO> attributes;
}
