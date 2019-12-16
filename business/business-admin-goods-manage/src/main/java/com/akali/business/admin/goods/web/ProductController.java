package com.akali.business.admin.goods.web;

import com.akali.business.admin.goods.api.ProductControllerApi;
import com.akali.common.dto.goods.base.attribute.AttrValueDTO;
import com.akali.common.dto.goods.base.attribute.AttrValueListDTO;
import com.akali.common.dto.goods.base.attribute.AttrValueVO;
import com.akali.common.dto.goods.sku.*;
import com.akali.common.dto.goods.spu.*;
import com.akali.common.dto.query.SpuQueryDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResponseResult;
import com.akali.common.model.response.QueryResult;
import com.akali.common.model.response.ResponseResult;
import com.akali.common.utils.ExceptionCast;
import com.akali.provider.goods.api.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName ProductController
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
@RestController
@RequestMapping("goods/product")
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
        return ResponseResult.SUCCESS().message("添加商品成功，可以去编辑商品详情");
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
    @GetMapping("attrs/{spuId}")
    @Override
    public QueryResponseResult<AttrValueVO> queryProductAllAttrValue(@PathVariable Long spuId) {
        DubboResponse<QueryResult<AttrValueVO>> response = productService.queryProductAllAttrValue(spuId);
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
    public ResponseResult<SpuDetailVO> querySpuDetail(@PathVariable Long spuId) {
        DubboResponse<SpuDetailVO> response = productService.querySpuDetail(spuId);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData());
    }

    /**
     * 把销售选项和一个或多个sku属性进行绑定
     *
     * @param spuSaleOptionVO
     * @return
     */
    @PostMapping("sale/option/attr")
    @Override
    public ResponseResult<Void> bindSaleOptionAndSkuAttr(SpuSaleOptionVO spuSaleOptionVO) throws JsonProcessingException {
        DubboResponse<Void> response = productService.bindSaleOptionAndSkuAttr(spuSaleOptionVO);
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
     *
     * @param spuId
     * @param spuDetaiModifyDTO
     * @return
     */
    @PostMapping("spu/detail/{spuId}")
    @Override
    public ResponseResult<Void> updateSpuDetail(@PathVariable Long spuId, @RequestBody SpuDetaiModifyDTO spuDetaiModifyDTO) {
        DubboResponse<Void> response = productService.updateSpuDetail(spuDetaiModifyDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS();
    }

    /**
     * 修改商品详情的标题
     *
     * @param spuId
     * @param spuTitleDTO
     * @return
     */
    @PutMapping("spu/detail/titles/{spuId}")
    @Override
    public ResponseResult<Void> updateSpuDetailTitle(@PathVariable Long spuId, @RequestBody SpuTitleDTO spuTitleDTO) {
        DubboResponse<Void> response = productService.updateSpuDetailTitle(spuId, spuTitleDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS().message("修改商品详情的标题成功");
    }

    /**
     * 修改商品详情的描述
     *
     * @param spuId
     * @param spuDetaiModifyDTO
     * @return
     */
    @PutMapping("spu/detail/description/{spuId}")
    @Override
    public ResponseResult<Void> updateSpuDetailDescription(@PathVariable Long spuId, @RequestBody SpuDetaiModifyDTO spuDetaiModifyDTO) {
        DubboResponse<Void> response = productService.updateSpuDetailDescription(spuId, spuDetaiModifyDTO.getDescription());
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS().message("修改商品详情的描述成功");
    }

    /**
     * 修改商品详情的包装清单
     *
     * @param spuId
     * @param spuDetaiModifyDTO
     * @return
     */
    @PutMapping("spu/detail/packing/{spuId}")
    @Override
    public ResponseResult<Void> updateSpuDetailPackage(@PathVariable Long spuId, @RequestBody SpuDetaiModifyDTO spuDetaiModifyDTO) {
        DubboResponse<Void> response = productService.updateSpuDetailPackage(spuId, spuDetaiModifyDTO.getPackingList());
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS().message("修改商品详情的包装清单成功");
    }

    /**
     * 修改商品详情的售后服务
     *
     * @param spuId
     * @param spuDetaiModifyDTO
     * @return
     */
    @PutMapping("spu/detail/afterService/{spuId}")
    @Override
    public ResponseResult<Void> updateSpuDetailAfterService(@PathVariable Long spuId, @RequestBody SpuDetaiModifyDTO spuDetaiModifyDTO) {
        DubboResponse<Void> response = productService.updateSpuDetailAfterService(spuId, spuDetaiModifyDTO.getAfterService());
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS().message("修改商品详情的售后服务成功");
    }

    /**
     * 根据spuId 获取所有的sku
     *
     * @param spuId
     * @return
     */
    @GetMapping("skus/{spuId}")
    @Override
    public QueryResponseResult<SkuDTO> queryProductSkus(@PathVariable Long spuId) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        DubboResponse<QueryResult<SkuDTO>> response = productService.queryProductSkus(spuId);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData());
    }

    /**
     * 查询一页spu数据
     *
     * @param spuQueryDTO
     * @return
     */
    @GetMapping("{page}")
    @Override
    public QueryResponseResult<SpuVO> queryProductPage(SpuQueryDTO spuQueryDTO) {
        DubboResponse<QueryResult<SpuVO>> response = productService.queryProductPage(spuQueryDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData());
    }

    /**
     * 初次设置属性值
     * @param attrValueDTO
     * @return
     */
    @PostMapping("attr/value")
    @Override
    public ResponseResult<Long> setAttributeValue(@RequestBody AttrValueDTO attrValueDTO) {
        DubboResponse<Long> response = productService.setAttributeValue(attrValueDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData()).message("设置商品属性值成功");
    }

    /**
     * 设置多个属性值
     * @param attrValueListDTO
     * @return
     */
    @PostMapping("attr/values")
    @Override
    public ResponseResult<Void> setAttributeValueMutl(@RequestBody AttrValueListDTO attrValueListDTO) {
        DubboResponse<Void> response = productService.setAttributeValueMutl(attrValueListDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS().message("设置商品属性值成功");
    }

    /**
     * 修改属性值
     * @param avId
     * @param attrValueDTO
     * @return
     */
    @PutMapping("attr/value/{avId}")
    @Override
    public ResponseResult<Void> modifyAttributeValue(@PathVariable Long avId,@RequestBody AttrValueDTO attrValueDTO) {
        DubboResponse<Void> response = productService.modifyAttributeValue(avId,attrValueDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS().message("设置商品属性值成功");
    }

    /**
     * 获取商品的销售选项列表
     * @param spuId
     * @return
     */
    @GetMapping("spu/sale/option/{spuId}")
    @Override
    public QueryResponseResult<SpuSaleOptionVO> querySpuSaleOption(@PathVariable Long spuId) {
        DubboResponse<QueryResult<com.akali.common.dto.goods.base.SpuSaleOptionVO>> response = productService.querySpuSaleOption(spuId);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData()).message("获取商品销售选项成功");
    }

    /**
     * 修改商品销售选项绑定的sku属性
     * @param ssoId
     * @param spuSaleOptionBindAttrDTO
     * @return
     */
    @PutMapping("spu/sale/option/bind/attr/{ssoId}")
    @Override
    public ResponseResult<Void> modifySpuSaleOptionBindIds(@PathVariable Long ssoId, @RequestBody SpuSaleOptionBindAttrDTO spuSaleOptionBindAttrDTO) {
        DubboResponse<Void> response = productService.modifySpuSaleOptionBindIds(ssoId,spuSaleOptionBindAttrDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS().message("修改商品销售选项绑定的sku属性成功");
    }

    /**
     * 获取某商品的sku列表表头信息
     * @param spuId
     * @return
     */
    @GetMapping("sku/table/header/{spuId}")
    @Override
    public QueryResponseResult<SkuTableHeader> getSkuTableHeader(@PathVariable Long spuId) {
        DubboResponse<QueryResult<SkuTableHeader>> response = productService.getSkuTableHeader(spuId);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData()).message("获取sku列表表头信息成功");
    }

    /**
     * 获取某spu的sku列表数据
     * @param spuId
     * @return
     */
    @GetMapping("sku/table/data/{spuId}")
    @Override
    public QueryResponseResult<SkuTableVO> getSkuTableData(@PathVariable Long spuId) {
        DubboResponse<QueryResult<SkuTableVO>> response = productService.getSkuTableData(spuId);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData()).message("获取某商品的sku列表数据成功");
    }

    /**
     * 获取某商品的特有属性和属性值，用于添加新的sku
     * @param cateId
     * @param spuId
     * @return
     */
    @GetMapping("sku/attr/and/value/{cateId}/{spuId}")
    @Override
    public ResponseResult<SkuAttrAndValueVO> getSkuAttrAndValue(@PathVariable Long cateId,@PathVariable Long spuId) {
        DubboResponse<SkuAttrAndValueVO> response = productService.getSkuAttrAndValue(cateId,spuId);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData()).message("获取某商品的获取特有属性和属性值成功");
    }

    /**
     * 获取sku的图片
     * @param skuId
     * @return
     */
    @GetMapping("sku/image/list/{skuId}")
    @Override
    public QueryResponseResult<SkuImageVO> querySkuImage(@PathVariable Long skuId) {
        DubboResponse<QueryResult<SkuImageVO>> response = productService.querySkuImage(skuId);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData()).message("获取sku的图片成功");
    }

    /**
     * 添加sku图片
     *
     * @param skuImageDTO
     * @return
     */
    @PostMapping("sku/image")
    @Override
    public ResponseResult<Long> addSkuImage(@RequestBody SkuImageDTO skuImageDTO) {
        DubboResponse<Long> response = productService.addSkuImage(skuImageDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData()).message("添加sku图片成功");
    }

    /**
     * 添加多张sku图片
     * @param skuImageMutlDTO
     * @return
     */
    @PostMapping("sku/image/mutl")
    @Override
    public ResponseResult<Map<Integer,Long>> addSkuImageMutl(@RequestBody SkuImageMutlDTO skuImageMutlDTO) {
        DubboResponse<Map<Integer,Long>> response = productService.addSkuImageMutl(skuImageMutlDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData()).message("添加多张sku图片成功");
    }

    /**
     * 设置sku猪图片
     * @param skuId
     * @param skuImageDTO
     * @return
     *
     */
//    @PutMapping("sku/main/image/{skuId}")
    @Override
    public ResponseResult<Void> setSkuMainImage(@PathVariable Long skuId, @RequestBody SkuImageDTO skuImageDTO) {
        DubboResponse<Long> response = productService.setSkuMainImage(skuId,skuImageDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData()).message("添加sku图片成功");
    }

    /**
     * 删除sku的图片
     * @param skuId
     * @param skuImageId
     * @return
     */
    @DeleteMapping("sku/image/{skuId}/{skuImageId}")
    @Override
    public ResponseResult<Void> deleteSkuImage(@PathVariable Long skuId, @PathVariable Long skuImageId) {
        DubboResponse<Long> response = productService.deleteSkuImage(skuId,skuImageId);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData()).message("添加sku图片成功");
    }

    /**
     * 创建商品销售选项
     * @param spuSaleOptionDTO
     * @return
     */
    @PostMapping("spu/sale/option")
    @Override
    public ResponseResult<Long> createSpuSaleOption(@RequestBody SpuSaleOptionDTO spuSaleOptionDTO) {
        DubboResponse<Long> response = productService.createSpuSaleOption(spuSaleOptionDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData()).message("创建商品销售选项成功");
    }

    /**
     * 设置sku主图片
     * @param skuId
     * @param newMain
     * @param oldMain
     * @return
     */
    @PutMapping("sku/main/image/{skuId}/{newMain}/{oldMain}")
    @Override
    public ResponseResult<Void> setSkuMainImage(@PathVariable Long skuId, @PathVariable Long newMain, @PathVariable Long oldMain) {
        DubboResponse<Void> response = productService.setSkuMainImage(skuId,newMain,oldMain);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData()).message("设置sku主图片成功");
    }

    /**
     * 设置sku图片描述
     * @param skuImageId
     * @param skuImageDesc
     * @return
     */
    @PutMapping("sku/image/desc/{skuId}/{skuImageId}")
    @Override
    public ResponseResult<Void> setSkuImageDesc(@PathVariable Long skuId,@PathVariable Long skuImageId, @RequestBody SkuImageDescDTO skuImageDesc) {
        DubboResponse<Void> response = productService.setSkuImageDesc(skuId,skuImageId,skuImageDesc);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData()).message("设置sku图片描述成功");
    }

    /**
     * 设置sku标题
     * @param skuId
     * @param skuTitleDTO
     * @return
     */
    @PutMapping("sku/title/{skuId}")
    @Override
    public ResponseResult<Void> setSkuTitle(@PathVariable Long skuId,@RequestBody SkuTitleDTO skuTitleDTO) {
        DubboResponse<Void> response = productService.setSkuTitle(skuId,skuTitleDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData()).message("设置sku标题成功");
    }

    /**
     * 设置sku价格
     * @param skuId
     * @param skuPriceDTO
     * @return
     */
    @PutMapping("sku/price/{skuId}")
    @Override
    public ResponseResult<Void> setSkuPrice(@PathVariable Long skuId,@RequestBody SkuPriceDTO skuPriceDTO) {
        DubboResponse<Void> response = productService.setSkuPrice(skuId,skuPriceDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData()).message("设置sku价格成功");
    }
}
