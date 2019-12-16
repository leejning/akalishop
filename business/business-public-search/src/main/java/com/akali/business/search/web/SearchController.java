package com.akali.business.search.web;

import com.akali.business.search.api.SearchControllerApi;
import com.akali.common.dto.search.ProductVO;
import com.akali.common.dto.search.SearchDTO;
import com.akali.common.dto.search.SearchQueryResult;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.ResponseResult;
import com.akali.common.utils.ExceptionCast;
import com.akali.provider.es.service.SearchService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/{spuId}")
    @Override
    public ResponseResult<ProductVO> queryProductById(@PathVariable Long spuId) {
        DubboResponse<ProductVO> response = searchService.queryProductById(spuId);
        if (!response.isSuccess()){
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData());
    }

    /**
     * 页面商品搜索
     * @param searchDTO
     * @return
     */
    @PostMapping("page")
    @Override
    public ResponseResult<SearchQueryResult> searchProduct(@RequestBody SearchDTO searchDTO) {
        DubboResponse<SearchQueryResult> response = searchService.searchProduct(searchDTO);
        if (!response.isSuccess()){
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData()).message("搜索商品成功");
    }
}
