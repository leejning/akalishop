package com.akali.common.dto.goods.base.attribute;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName AttributionSimpleVO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/8 0008
 * @Version V1.0
 **/
@Data
public class AttributionSimpleVO implements Serializable {

    private static final long serialVersionUID = 8256142404883325964L;
    private Long id;
    private String name;
    private String enName;
}
