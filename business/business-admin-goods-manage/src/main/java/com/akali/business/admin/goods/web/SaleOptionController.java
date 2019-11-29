package com.akali.business.admin.goods.web;

import com.akali.business.admin.goods.api.SaleOptionControllerApi;
import com.akali.common.dto.goods.base.SaleOptionDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResponseResult;
import com.akali.common.model.response.QueryResult;
import com.akali.common.model.response.ResponseResult;
import com.akali.common.utils.ExceptionCast;
import com.akali.provider.goods.api.SaleOptionService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName SaleOptionController
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
@RestController
@RequestMapping("sale/option")
public class SaleOptionController implements SaleOptionControllerApi {
    @Reference(version = "1.0.0")
    private SaleOptionService saleOptionService;

    /**
     * 创建某类商品销售选项
     * @param saleOptionDTO
     * @return
     */
    @PostMapping
    @Override
    public ResponseResult<Void> createSaleOption(SaleOptionDTO saleOptionDTO) {
        DubboResponse<Void> response = saleOptionService.createSaleOption(saleOptionDTO);
        if(!response.isSuccess()){
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS();
    }

    /**
     * 根据三级分类获取该了类商品的销售选项
     * @param cateId
     * @return
     */
    @GetMapping("{cateId}")
    @Override
    public QueryResponseResult<SaleOptionDTO> querySaleOptionByCateId(@PathVariable Long cateId) {
        DubboResponse<QueryResult<SaleOptionDTO>> response = saleOptionService.querySaleOptionByCateId(cateId);
        if(!response.isSuccess()){
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData());
    }
}
