package com.akali.provider.goods.api;

import com.akali.common.dto.goods.SpuEsDTO;
import com.akali.common.model.response.DubboResponse;

/**
 * @ClassName ProductEsService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/16 0016
 * @Version V1.0
 **/
public interface ProductEsService {
    /**
     * 根据spuId获取构建elasticsearch 搜索库，所需要的商品信息
     * @param spuId
     * @return
     */
    DubboResponse<SpuEsDTO> queryProductEsBySpuId(Long spuId);
}
