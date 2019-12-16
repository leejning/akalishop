package com.akali.provider.goods.service.fallback;

import com.akali.common.code.CommonCode;
import com.akali.common.dto.goods.base.category.CategoryVO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName CategoryServiceFallback
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Slf4j
public class CategoryServiceFallback {

    public static DubboResponse<Void> createCategory(CategoryVO categoryVO, Throwable e){
        log.warn("invoke createBaseCategory: "+ e.getClass().getName());
        e.printStackTrace();
        return DubboResponse.FAIL(CommonCode.TIMEOUT);
    }

    public static DubboResponse<QueryResult<CategoryVO>> getCategory(Long pid, Throwable e){
        log.warn("invoke getCategory: "+ e.getClass().getName());
        e.printStackTrace();
        return DubboResponse.FAIL(CommonCode.TIMEOUT);
    }
}
