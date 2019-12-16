package com.akali.common.dto.goods.base.category;

import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName CategorySimpleVO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/15 0015
 * @Version V1.0
 **/
@Data
public class CategorySimpleVO implements Serializable {
    private static final long serialVersionUID = 2249607719320668481L;
    Long id;
    String name;
}
