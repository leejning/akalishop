package com.akali.common.dto.goods.base.brand;

import com.akali.common.dto.query.QueryPageBase;
import lombok.Data;

/**
 * @ClassName BrandQueryDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/5 0005
 * @Version V1.0
 **/
@Data
public class BrandQueryDTO extends QueryPageBase {
    String name;
    String firstLetter;
}
