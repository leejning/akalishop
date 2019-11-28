package com.akali.provider.es.service;

import com.akali.common.dto.search.ProductDTO;
import com.akali.common.model.response.DubboResponse;

/**
 * @ClassName SearchService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/24 0024
 * @Version V1.0
 **/
public interface SearchService {
    DubboResponse<ProductDTO> queryProductById(Long spuId);
}
