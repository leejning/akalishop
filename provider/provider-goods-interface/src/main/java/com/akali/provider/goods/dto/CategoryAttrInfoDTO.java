package com.akali.provider.goods.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @ClassName CategoryAttrInfoDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAttrInfoDTO {
    private List<CateAttrGroupDTO> groups;
}
