package com.akali.business.goods.web;

import com.akali.business.goods.api.ProductControllerApi;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResponseResult;
import com.akali.common.model.response.QueryResult;
import com.akali.common.model.response.ResponseResult;
import com.akali.config.exception.util.ExceptionCast;
import com.akali.provider.goods.api.ProductService;
import com.akali.provider.goods.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName ProductController
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
@RestController
@RequestMapping("product")
public class ProductController implements ProductControllerApi {
    @Reference(version = "1.0.0")
    private ProductService productService;

    /**
     * 创建spu基本信息
     *
     * @param spuDTO
     * @return
     */
    @PostMapping("spu")
    @Override
    public ResponseResult<Void> createProduct(@RequestBody SpuDTO spuDTO) {
        DubboResponse<Void> response = productService.createProduct(spuDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS();
    }

    /**
     * 创建商品所有属性值
     *
     * @param spuAttrValueCollectDTO
     * @return
     */
    @PostMapping("attrs")
    @Override
    public ResponseResult<Void> createProductAttrValue(@RequestBody SpuAttrValueCollectDTO spuAttrValueCollectDTO) throws JsonProcessingException {
        DubboResponse<Void> response = productService.createProductAttrValue(spuAttrValueCollectDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS();
    }

    /**
     * 根据spuId 获取商品所有属性值
     *
     * @param spuId
     * @return
     */
    @GetMapping("attrs")
    @Override
    public QueryResponseResult<AttrValueDTO> queryProductAllAttrValue(Long spuId) {
        DubboResponse<QueryResult<AttrValueDTO>> response = productService.queryProductAllAttrValue(spuId);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData());
    }

    /**
     * 根据spuid获取商品spu详细信息
     *
     * @param spuId
     * @return
     */
    @GetMapping("/spu/detail/{spuId}")
    @Override
    public ResponseResult<SpuDetaiDTO> querySpuDetail(@PathVariable Long spuId) {
        DubboResponse<SpuDetaiDTO> response = productService.querySpuDetail(spuId);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData());
    }

    /**
     * 把销售选项和一个或多个sku属性进行绑定
     *
     * @param spuSaleOptionDTO
     * @return
     */
    @PostMapping("sale/option/attr")
    @Override
    public ResponseResult<Void> bindSaleOptionAndSkuAttr(SpuSaleOptionDTO spuSaleOptionDTO) throws JsonProcessingException {
        DubboResponse<Void> response = productService.bindSaleOptionAndSkuAttr(spuSaleOptionDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS();
    }

    /**
     * 添加商品sku
     *
     * @param skuCreateDTO
     * @return
     */
    @PostMapping("sku")
    @Override
    public ResponseResult<Void> createProductSku(@RequestBody SkuCreateDTO skuCreateDTO) throws Exception {
        DubboResponse<Void> response = productService.createProductSku(skuCreateDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS();
    }

    /**
     * 修改商品详情信息
     * @param spuDetaiModifyDTO
     * @return
     */
    @Override
    public ResponseResult<Void> updateSpuDetail(SpuDetaiModifyDTO spuDetaiModifyDTO) {
        DubboResponse<Void> response = productService.updateSpuDetail(spuDetaiModifyDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS();
    }
}
