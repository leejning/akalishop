package com.akali.common.dto.goods.base.attribute;

import lombok.Data;

import java.util.List;

/**
 * @ClassName AttrValueListDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/8 0008
 * @Version V1.0
 **/
@Data
public class AttrValueListDTO {
    List<AttrValueDTO> attrValues;
}
