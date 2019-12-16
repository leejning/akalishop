package com.akali.common.dto.goods.base.brand;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName BrandSimpleVO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/15 0015
 * @Version V1.0
 **/
@Data
public class BrandSimpleVO implements Serializable {
    private static final long serialVersionUID = 5551313407308344022L;
    Long id;
    String name;
}
