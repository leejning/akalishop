package com.akali.provider.cart.query_helper;

import com.akali.config.mong.BaseEntityQueryHelper;
import com.mongodb.QueryBuilder;
import lombok.Builder;
import lombok.Data;

/**
 * @ClassName CartItemQueryHelper
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
@Builder
@Data
public class CartItemQueryHelper implements BaseEntityQueryHelper {
    private Long memberId;

    @Override
    public void filter(BaseEntityQueryHelper queryHelper, QueryBuilder queryBuilder) {
        if(!(queryHelper instanceof CartItemQueryHelper)){
            return;
        }
        CartItemQueryHelper cartItemQueryHelper = (CartItemQueryHelper) queryHelper;
        if (cartItemQueryHelper.getMemberId()!=null) {
            queryBuilder.and("memberId").is(cartItemQueryHelper.getMemberId());
        }
    }


}
