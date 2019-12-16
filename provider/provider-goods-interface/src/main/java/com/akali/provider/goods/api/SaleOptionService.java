package com.akali.provider.goods.api;

import com.akali.common.dto.goods.base.SaleOptionDTO;
import com.akali.common.dto.goods.base.SaleOptionVO;
import com.akali.common.dto.query.SaleOptionQueryDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;

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

    /**
     * 搜索获取销售属性，商品选择销售属性时使用
     * @param saleOptionQueryDTO
     * @return
     */
    DubboResponse<QueryResult<SaleOptionVO>> querySaleOption(SaleOptionQueryDTO saleOptionQueryDTO);

    /**
     * 获取销售选项分页列表
     * @param saleOptionQueryDTO
     * @return
     */
    DubboResponse<QueryResult<SaleOptionVO>> querySaleOptionPage(SaleOptionQueryDTO saleOptionQueryDTO);
}
