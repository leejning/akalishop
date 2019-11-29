package com.akali.business.search.web;

import com.akali.business.search.api.SearchControllerApi;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResponseResult;
import com.akali.common.utils.ExceptionCast;
import com.akali.provider.es.service.SearchService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName SearchController
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/24 0024
 * @Version V1.0
 **/
@RestController
@RequestMapping("search")
public class SearchController implements SearchControllerApi {
    @Reference(version = "1.0.0")
    private SearchService searchService;


    @GetMapping("/{SpuId}")
    @Override
    public QueryResponseResult<ProductDTO> queryProductById(@PathVariable Long SpuId) {
        DubboResponse<ProductDTO> response = searchService.queryProductById(SpuId);
        if (!response.isSuccess()){
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData());
    }
}
