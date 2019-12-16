package com.akali.business.admin.goods.api;

import com.akali.common.dto.goods.base.attribute.AttrValueDTO;
import com.akali.common.dto.goods.base.attribute.AttrValueListDTO;
import com.akali.common.dto.goods.base.attribute.AttrValueVO;
import com.akali.common.dto.goods.sku.*;
import com.akali.common.dto.goods.spu.*;
import com.akali.common.dto.query.SpuQueryDTO;
import com.akali.common.model.response.QueryResponseResult;
import com.akali.common.model.response.ResponseResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.Map;

/**
 * @ClassName ProductControllerApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
@Api(value = "商品管理接口",tags = "商品管理接口")
public interface ProductControllerApi {
    @ApiOperation(value = "创建spu基本信息")
    public ResponseResult<Void> createProduct(SpuDTO spuDTO);

    @ApiOperation("创建商品所有属性值")
    public ResponseResult<Void> createProductAttrValue(SpuAttrValueCollectDTO spuAttrValueCollectDTO) throws JsonProcessingException;

    @ApiOperation("获取商品所有属性值")
    public ResponseResult<AttrValueVO> queryProductAllAttrValue(Long spuId);

    @ApiOperation("获取商品详情信息")
    public ResponseResult<SpuDetailVO> querySpuDetail(Long spuId);

    @ApiOperation("把销售选项和一个或多个sku属性进行绑定")
    public ResponseResult<Void> bindSaleOptionAndSkuAttr(SpuSaleOptionVO spuSaleOptionVO) throws JsonProcessingException;

    @ApiOperation("添加商品sku")
    public ResponseResult<Void> createProductSku(SkuCreateDTO skuDTO) throws Exception;

    @ApiOperation("修改商品详情信息")
    public ResponseResult<Void> updateSpuDetail(Long spuId,SpuDetaiModifyDTO spuDetaiModifyDTO);
    @ApiOperation("修改商品详情信息之标题")
    public ResponseResult<Void> updateSpuDetailTitle(Long spuId,SpuTitleDTO spuTitleDTO);
    @ApiOperation("修改商品详情信息之详情描述")
    public ResponseResult<Void> updateSpuDetailDescription(Long spuId,SpuDetaiModifyDTO spuDetaiModifyDTO);
    @ApiOperation("修改商品详情信息之详情包装清单")
    public ResponseResult<Void> updateSpuDetailPackage(Long spuId,SpuDetaiModifyDTO spuDetaiModifyDTO);
    @ApiOperation("修改商品详情信息之详情售后服务")
    public ResponseResult<Void> updateSpuDetailAfterService(Long spuId,SpuDetaiModifyDTO spuDetaiModifyDTO);

    @ApiOperation("获取某商品的sku集合")
    public QueryResponseResult<SkuDTO> queryProductSkus(Long spuId);

    @ApiOperation("获取一页商品spu")
    public QueryResponseResult<SpuVO> queryProductPage(SpuQueryDTO spuQueryDTO);

    @ApiOperation("初次设置商品属性值")
    public ResponseResult<Long> setAttributeValue(AttrValueDTO attrValueDTO);
    @ApiOperation("初次设置多个商品属性值")
    public ResponseResult<Void> setAttributeValueMutl(AttrValueListDTO attrValueListDTO);

    @ApiOperation("修改属性值")
    public ResponseResult<Void> modifyAttributeValue(Long avId,AttrValueDTO attrValueDTO);

    @ApiOperation("获取商品销售选项")
    public QueryResponseResult<SpuSaleOptionVO> querySpuSaleOption(Long spuId);

    @ApiOperation("修改商品销售选项绑定的sku属性")
    public ResponseResult<Void> modifySpuSaleOptionBindIds(Long ssoId, SpuSaleOptionBindAttrDTO spuSaleOptionBindAttrDTO );

    @ApiOperation("获取某商品的sku列表表头信息")
    public QueryResponseResult<SkuTableHeader> getSkuTableHeader(Long spuId);

    @ApiOperation("获取某spu的sku列表数据")
    public QueryResponseResult<SkuTableVO> getSkuTableData(Long spuId);

    @ApiOperation("获取某商品的特有属性和属性值，用于添加新的sku")
    public ResponseResult<SkuAttrAndValueVO> getSkuAttrAndValue(Long cateId, Long spuId);

    @ApiOperation("获取sku的图片")
    public QueryResponseResult<SkuImageVO> querySkuImage(Long skuId);

    @ApiOperation("添加sku图片")
    public ResponseResult<Long> addSkuImage(SkuImageDTO skuImageDTO);
    @ApiOperation("添加多张sku图片")
    public ResponseResult<Map<Integer,Long>> addSkuImageMutl(SkuImageMutlDTO skuImageMutlDTO);

    @ApiOperation("设置sku的主图片")
    public ResponseResult<Void> setSkuMainImage(Long skuId,SkuImageDTO skuImageDTO);

    @ApiOperation("删除sku图片")
    public ResponseResult<Void> deleteSkuImage(Long skuId,Long skuImageId);

    @ApiOperation("创建商品销售选项")
    public ResponseResult<Long> createSpuSaleOption(SpuSaleOptionDTO spuSaleOptionDTO);

    @ApiOperation("设置sku主图片")
    public ResponseResult<Void> setSkuMainImage(Long skuId, Long newMain, Long oldMain);

    @ApiOperation("设置sku图片描述")
    public ResponseResult<Void> setSkuImageDesc(Long skuId,Long skuImageId, SkuImageDescDTO skuImageDesc);

    @ApiOperation("设置sku标题")
    public ResponseResult<Void> setSkuTitle(Long skuId,SkuTitleDTO skuTitleDTO);

    @ApiOperation("设置sku价格")
    public ResponseResult<Void> setSkuPrice(Long skuId,SkuPriceDTO skuPriceDTO);


}
