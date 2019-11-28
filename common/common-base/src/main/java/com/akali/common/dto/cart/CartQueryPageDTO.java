package com.akali.common.dto.cart;

import com.akali.common.dto.QueryPageBase;
import lombok.Data;

/**
 * @ClassName CartQueryPageDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/27 0027
 * @Version V1.0
 **/
@Data
public class CartQueryPageDTO extends QueryPageBase {
    private Long memberId;
}
