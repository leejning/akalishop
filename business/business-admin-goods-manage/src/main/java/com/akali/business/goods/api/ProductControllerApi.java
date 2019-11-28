package com.akali.business.goods.api;

import com.akali.common.dto.goods.*;
import com.akali.common.model.response.QueryResponseResult;
import com.akali.common.model.response.ResponseResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName ProductControllerApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
@Api(value = "商品管理接口",tags = "商品管理接口")
public interface ProductControllerApi {
    @ApiOperation("创建spu基本信息")
    public ResponseResult<Void> createProduct(SpuDTO spuDTO);

    @ApiOperation("创建商品所有属性值")
    public ResponseResult<Void> createProductAttrValue(SpuAttrValueCollectDTO spuAttrValueCollectDTO) throws JsonProcessingException;

    @ApiOperation("获取商品所有属性值")
    public QueryResponseResult<AttrValueDTO> queryProductAllAttrValue(Long spuId);

    @ApiOperation("获取商品详情信息")
    public ResponseResult<SpuDetailDTO> querySpuDetail(Long spuId);

    @ApiOperation("把销售选项和一个或多个sku属性进行绑定")
    public ResponseResult<Void> bindSaleOptionAndSkuAttr(SpuSaleOptionDTO spuSaleOptionDTO) throws JsonProcessingException;

    @ApiOperation("添加商品sku")
    public ResponseResult<Void> createProductSku(SkuCreateDTO skuDTO) throws Exception;

    @ApiOperation("修改商品详情信息")
    public ResponseResult<Void> updateSpuDetail(SpuDetaiModifyDTO spuDetaiModifyDTO);

    @ApiOperation("获取某商品的sku集合")
    public QueryResponseResult<SkuDTO> queryProductSkus(Long spuId);

}
