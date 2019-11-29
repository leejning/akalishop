package com.akali.common.dto.query;

import lombok.Data;

/**
 * @ClassName SpuQueryDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
@Data
public class SpuQueryDTO extends QueryPageBase {
    private String spuTitle;
    private Long cid3;
    private Long brandId;
}
