package com.akali.common.dto.query;

import lombok.Data;

/**
 * @ClassName QueryPageBase
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/9/30 0030
 * @Version V1.0
 **/
@Data
public class QueryPageBase {
    private Integer pageNo;
    /**
     * 页数
     */
    private Integer pageSize;

    private String descOrAsc;
    private String sortby;
}
