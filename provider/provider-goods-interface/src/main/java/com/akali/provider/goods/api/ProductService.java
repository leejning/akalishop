package com.akali.provider.goods.api;

import com.akali.common.dto.goods.base.attribute.AttrValueDTO;
import com.akali.common.dto.goods.base.attribute.AttrValueListDTO;
import com.akali.common.dto.goods.base.attribute.AttrValueVO;
import com.akali.common.dto.goods.sku.*;
import com.akali.common.dto.goods.spu.*;
import com.akali.common.dto.query.SpuQueryDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;

/**
 * @ClassName ProductService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
public interface ProductService {
    /**
     *  创建商品所有属性值
     * @param spuAttrValueCollectDTO
     * @return
     */
    DubboResponse<Void> createProductAttrValue(SpuAttrValueCollectDTO spuAttrValueCollectDTO) throws JsonProcessingException;
    /**
     * 创建spu基本信息
     * @param spuDTO
     * @return
     */
    DubboResponse<Void> createProduct(SpuDTO spuDTO);

    /**
     * 根据spuId 获取商品所有属性值
     * @param spuId
     * @return
     */
    DubboResponse<QueryResult<AttrValueVO>> queryProductAllAttrValue(Long spuId);

    /**
     * 根据spuid获取商品spu详细信息
     * @param spuId
     * @return
     */
    DubboResponse<SpuDetailVO> querySpuDetail(Long spuId);

    /**
     * 把销售选项和一个或多个sku属性进行绑定
     * @param spuSaleOptionVO
     * @return
     */
    DubboResponse<Void> bindSaleOptionAndSkuAttr(SpuSaleOptionVO spuSaleOptionVO) throws JsonProcessingException;

    /**
     * 添加商品sku
     * @param skuDTO
     * @return
     */
    DubboResponse<Void> createProductSku(SkuCreateDTO skuDTO) throws Exception;

    /**
     * 判断sku是否存在
     * @param skuCreateDTO
     * @return
     */
    public Boolean checkExistsSku(SkuCreateDTO skuCreateDTO);

    /**
     * 修改商品详情信息
     * @param spuDetaiModifyDTO
     * @return
     */
    DubboResponse<Void> updateSpuDetail(SpuDetaiModifyDTO spuDetaiModifyDTO);

    /**
     * 根据spuId 获取所有的sku
     * @param spuId
     * @return
     */
    DubboResponse<QueryResult<SkuDTO>> queryProductSkus(Long spuId);
    /**
     * 获取某个商品的销售页信息
     * @param spuId
     * @return
     */
    DubboResponse<ProductSalePageInfoVO> getProductSalePageInfo(Long spuId);

    /**
     * 
     * @param spuQueryDTO
     * @return
     */
    DubboResponse<QueryResult<SpuVO>> queryProductPage(SpuQueryDTO spuQueryDTO);

    DubboResponse<Void> updateSpuDetailAfterService(Long spuId, String afterService);

    DubboResponse<Void> updateSpuDetailPackage(Long spuId, String packingList);

    DubboResponse<Void> updateSpuDetailDescription(Long spuId, String description);

    DubboResponse<Void> updateSpuDetailTitle(Long spuId, SpuTitleDTO spuTitleDTO);

    /**
     * 初始设置商品属性的值
     * @param attrValueDTO
     * @return
     */
    DubboResponse<Long> setAttributeValue(AttrValueDTO attrValueDTO);

    /**
     * 修改商品属性的值
     * @param avId
     * @param attrValueDTO
     * @return
     */
    DubboResponse<Void> modifyAttributeValue(Long avId, AttrValueDTO attrValueDTO);

    /**
     * 设置多个属性值
     * @param attrValueListDTO
     * @return
     */
    DubboResponse<Void> setAttributeValueMutl(AttrValueListDTO attrValueListDTO);
    /**
     * 获取商品的销售选项列表
     * @param spuId
     * @return
     */
    DubboResponse<QueryResult<com.akali.common.dto.goods.base.SpuSaleOptionVO>> querySpuSaleOption(Long spuId);

    /**
     * 修改商品销售选项绑定的sku属性
     * @param ssoId
     * @param skuAttrIs
     * @return
     */
    DubboResponse<Void> modifySpuSaleOptionBindIds(Long ssoId, SpuSaleOptionBindAttrDTO skuAttrIs);

    /**
     * 获取某商品的sku列表表头信息
     * @param spuId
     * @return
     */
    DubboResponse<QueryResult<SkuTableHeader>> getSkuTableHeader(Long spuId);

    /**
     * 获取某spu的sku列表数据
     * @param spuId
     * @return
     */
    DubboResponse<QueryResult<SkuTableVO>> getSkuTableData(Long spuId);

    /**
     * 获取某商品的特有属性和属性值，用于添加新的sku
     * @param cateId
     * @param spuId
     * @return
     */
    DubboResponse<SkuAttrAndValueVO> getSkuAttrAndValue(Long cateId, Long spuId);
    /**
     * 获取sku的图片
     * @param skuId
     * @return
     */
    DubboResponse<QueryResult<SkuImageVO>> querySkuImage(Long skuId);

    /**
     * 添加sku图片
     *
     * @param skuImageDTO
     * @return
     */
    DubboResponse<Long> addSkuImage(SkuImageDTO skuImageDTO);

    /**
     * 设置sku猪图片
     * @param skuId
     * @param skuImageDTO
     * @return
     */
    DubboResponse<Long> setSkuMainImage(Long skuId, SkuImageDTO skuImageDTO);
    /**
     * 删除sku的图片
     * @param skuId
     * @param skuImageId
     * @return
     */
    DubboResponse<Long> deleteSkuImage(Long skuId, Long skuImageId);

    /**
     * 添加多张sku图片
     * @param skuImageMutlDTO
     * @return
     */
    DubboResponse<Map<Integer, Long>> addSkuImageMutl(SkuImageMutlDTO skuImageMutlDTO);

    /**
     * 创建商品销售选项
     * @param spuSaleOptionDTO
     * @return
     */
    DubboResponse<Long> createSpuSaleOption(SpuSaleOptionDTO spuSaleOptionDTO);

    /**
     * 设置sku主图片
     * @param skuId
     * @param newMain
     * @param oldMain
     * @return
     */
    DubboResponse<Void> setSkuMainImage(Long skuId, Long newMain, Long oldMain);

    /**
     * 设置sku图片描述
     * @param skuImageId
     * @param skuImageDesc
     * @return
     */
    DubboResponse<Void> setSkuImageDesc(Long skuId,Long skuImageId, SkuImageDescDTO skuImageDesc);

    /**
     * 设置sku标题
     * @param skuId
     * @param skuTitleDTO
     * @return
     */
    DubboResponse<Void> setSkuTitle(Long skuId, SkuTitleDTO skuTitleDTO);
    /**
     * 设置sku价格
     * @param skuId
     * @param skuPriceDTO
     * @return
     */
    DubboResponse<Void> setSkuPrice(Long skuId, SkuPriceDTO skuPriceDTO);
}
