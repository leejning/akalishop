package com.akali.provider.es.service;

import com.akali.common.dto.search.ProductVO;
import com.akali.common.dto.search.SearchDTO;
import com.akali.common.dto.search.SearchQueryResult;
import com.akali.common.model.response.DubboResponse;

/**
 * @ClassName SearchService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/24 0024
 * @Version V1.0
 **/
public interface SearchService {
    DubboResponse<ProductVO> queryProductById(Long spuId);
    /**
     * 页面商品搜索
     * @param searchDTO
     * @return
     */
    DubboResponse<SearchQueryResult> searchProduct(SearchDTO searchDTO);
}
