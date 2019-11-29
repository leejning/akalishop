package com.akali.business.free.goods.web;

import com.akali.business.free.goods.api.ProductControllerApi;
import com.akali.common.dto.goods.spu.ProductSalePageInfoVO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResponseResult;
import com.akali.common.utils.ExceptionCast;
import com.akali.provider.goods.api.ProductService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ProductController
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
@RestController
@RequestMapping("/product")
public class ProductController implements ProductControllerApi {

    @Reference(version = "1.0.0")
    private ProductService productService;

    /**
     * 获取某个商品的销售页信息
     * @param spuId
     * @return
     */
    @GetMapping("sale/page/info/{spuId}")
    @Override
    public QueryResponseResult<ProductSalePageInfoVO> getProductSalePageInfo(@PathVariable Long spuId) {
        DubboResponse<ProductSalePageInfoVO> response = productService.getProductSalePageInfo(spuId);
        if(!response.isSuccess()){
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData()).message("获取商品的销售页信息成功");
    }
}
