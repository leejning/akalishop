package com.akali.business.admin.goods.web;

import com.akali.business.admin.goods.api.AttributionControllerApi;
import com.akali.common.dto.goods.base.attribute.*;
import com.akali.common.dto.goods.base.category.CateAttributeVO;
import com.akali.common.dto.goods.base.category.CategoryAttrInfoVO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResponseResult;
import com.akali.common.model.response.QueryResult;
import com.akali.common.model.response.ResponseResult;
import com.akali.common.utils.ExceptionCast;
import com.akali.provider.goods.api.AttributionService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName AttributionController
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@RestController
@RequestMapping("goods/attribute")
public class AttributionController implements AttributionControllerApi {
    @Reference(version = "1.0.0")
    private AttributionService attributionService;

    /**
     * 创建属性分组
     * @param attrGroupDTO
     * @return
     */
    @PostMapping("group")
    @Override
    public ResponseResult<Void> createAttributeGroup(@RequestBody @Validated AttrGroupDTO attrGroupDTO) {
        DubboResponse<Void> response = attributionService.createAttributeGroup(attrGroupDTO);
        if(!response.isSuccess()){
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS();
    }

    /**
     * 创建商品属性
     * @param attributionDTO
     * @return
     */
    @PostMapping
    @Override
    public ResponseResult<Long> createAttribute(@RequestBody AttributionDTO attributionDTO) {
        DubboResponse<Long> response = attributionService.createAttribute(attributionDTO);
        if(!response.isSuccess()){
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData());
    }

    /**
     * 修改商品属性
     * @param attrId
     * @param attributionDTO
     * @return
     */
    @PutMapping("{attrId}")
    @Override
    public ResponseResult<Void> updateAttribute(@PathVariable Long attrId,@RequestBody AttributionDTO attributionDTO) {
        DubboResponse<Void> response = attributionService.updateAttribute(attrId,attributionDTO);
        if(!response.isSuccess()){
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS().message("修改属性信息成功");
    }

    /**
     * 添加固定属性的选项
     * @param attrOptionDTO
     * @return
     */
    @PostMapping("option")
    @Override
    public ResponseResult<Long> createAttributeOption(@RequestBody AttrOptionDTO attrOptionDTO) {
        DubboResponse<Long> response = attributionService.createAttributeOption(attrOptionDTO);
        if(!response.isSuccess()){
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData());
    }

    /**
     * 修改属性选项信息
     * @param optId
     * @param attrOptionDTO
     * @return
     */
    @PutMapping("option/{optId}")
    @Override
    public ResponseResult<Void> modifyAttributeOption(@PathVariable Long optId,@RequestBody AttrOptionDTO attrOptionDTO) {
        DubboResponse<Void> response = attributionService.modifyAttributeOption(optId,attrOptionDTO);
        if(!response.isSuccess()){
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData()).message("修改属性选项信息成功");
    }

    /**
     * 根据三级分类id查找全部商品属性信息，按分组存放
     * @param cateId
     * @return
     */
    @GetMapping("/all/bygroup/{cateId}")
    @Override
    public ResponseResult<CategoryAttrInfoVO> queryAllAttributeByCidGroup(@PathVariable Long cateId) {
        DubboResponse<CategoryAttrInfoVO> response = attributionService.queryAllAttributeByCidWithGroup(cateId);
        if(!response.isSuccess()){
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS(response.getData());
    }
    /**
     * 根据三级分类id查找全部商品属性信息
     * @param cateId
     * @return
     */
    @GetMapping("/all/bylist/{cateId}")
    @Override
    public QueryResponseResult<CateAttributeVO> queryAllAttributeByCidList(@PathVariable Long cateId) {
        DubboResponse<QueryResult<CateAttributeVO>> response = attributionService.queryAllAttributeByCidList(cateId);
        if(!response.isSuccess()){
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData()).message("查询分类所有属性信息成功");
    }

    /**
     * 根据分类ID查询属性组
     * @param cateId
     * @param attrGroupDTO
     * @return
     */
    @GetMapping("groups/catelog/{cateId}")
    @Override
    public QueryResponseResult<AttrGroupVO> queryAttrGroupByCid(@PathVariable Long cateId, AttrGroupDTO attrGroupDTO) {
        DubboResponse<QueryResult<AttrGroupVO>> response = attributionService.queryAttrGroupByCid(cateId,attrGroupDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData()).message("查询分类的属性组成功");
    }

    /**
     * 根据分组ID获取属性
     * @param groupId
     * @return
     */
    @GetMapping("bygroup/{groupId}")
    @Override
    public QueryResponseResult<AttributionVO> queryAttributionByGroupId(@PathVariable Long groupId) {
        DubboResponse<QueryResult<AttributionVO>> response = attributionService.queryAttributionByGroupId(groupId);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData()).message("根据分组ID获取属性成功");
    }

    /**
     * 根据分类id 获取sku属性
     * @param cateId
     * @return
     */
    @GetMapping("sku/bycateId/{cateId}")
    @Override
    public QueryResponseResult<AttributionSimpleVO> querySkuAttributionByCateId(@PathVariable Long cateId) {
        DubboResponse<QueryResult<AttributionSimpleVO>> response = attributionService.querySkuAttributionByCateId(cateId);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData()).message("根据分类ID获取sku属性成功");
    }

    /**
     * 修改属性的搜索分片信息
     * @param attrId
     * @param attrSegmentDTO
     * @return
     */
    @PutMapping("search/segment/{attrId}")
    @Override
    public ResponseResult<Void> setAttributeSearchSegment(@PathVariable Long attrId, @RequestBody AttrSegmentDTO attrSegmentDTO) {
        DubboResponse<Void> response = attributionService.setAttributeSearchSegment(attrId,attrSegmentDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS().message("修改属性的搜索分片信息成功");
    }

}
