package com.akali.common.dto.goods.base.attribute;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName AttrKeyValueVO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/9 0009
 * @Version V1.0
 **/
@Data
public class AttrKeyValueVO implements Serializable {
    private static final long serialVersionUID = -5023184533893222710L;
    private Long id;
    private String name;
    private List<AttrValueVO> values = new ArrayList<>();
}
