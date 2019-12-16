package com.akali.common.dto.goods.spu;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName SpuSaleOptionBindAttrDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/8 0008
 * @Version V1.0
 **/
@Data
public class SpuSaleOptionBindAttrDTO implements Serializable {
    private static final long serialVersionUID = -119151707859805883L;
    List<Long> skuAttrIds;
}
