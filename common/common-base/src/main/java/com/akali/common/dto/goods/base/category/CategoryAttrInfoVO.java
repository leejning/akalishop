package com.akali.common.dto.goods.base.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @ClassName CategoryAttrInfoVO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAttrInfoVO implements Serializable {

    private static final long serialVersionUID = 6617680915080798179L;
    private List<CateAttrGroupVO> groups;
}
