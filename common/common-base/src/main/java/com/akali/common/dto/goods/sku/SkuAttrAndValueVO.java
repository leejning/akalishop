package com.akali.common.dto.goods.sku;

import com.akali.common.dto.goods.base.attribute.AttrKeyValueVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName SkuAttrAndValueVO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/9 0009
 * @Version V1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkuAttrAndValueVO implements Serializable {
    private static final long serialVersionUID = 2032941515992953055L;
    List<AttrKeyValueVO> attrs;
}
