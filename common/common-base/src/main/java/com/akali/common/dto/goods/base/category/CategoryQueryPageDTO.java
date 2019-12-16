package com.akali.common.dto.goods.base.category;

import com.akali.common.dto.query.QueryPageBase;
import lombok.Data;

/**
 * @ClassName CategoryQueryPageDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/2 0002
 * @Version V1.0
 **/
@Data
public class CategoryQueryPageDTO extends QueryPageBase {
    private Integer level;
    private String name;
}
