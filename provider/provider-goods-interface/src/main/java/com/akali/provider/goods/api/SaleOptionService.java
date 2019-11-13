package com.akali.provider.goods.api;

import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;
import com.akali.provider.goods.dto.SaleOptionDTO;

/**
 * @ClassName SaleOptionService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
public interface SaleOptionService {
    /**
     * 创建某类商品销售选项
     * @param saleOptionDTO
     * @return
     */
    DubboResponse<Void> createSaleOption(SaleOptionDTO saleOptionDTO);

    /**
     * 根据三级分类获取该了类商品的销售选项
     * @param cateId
     * @return
     */
    DubboResponse<QueryResult<SaleOptionDTO>> querySaleOptionByCateId(Long cateId);
}
