package com.akali.provider.goods.service;

import com.akali.common.code.CommonCode;
import com.akali.common.code.ProductCode;
import com.akali.common.data_help.DataJpaPageUtils;
import com.akali.common.data_help.PageAndSortObj;
import com.akali.common.dto.goods.base.attribute.*;
import com.akali.common.dto.goods.base.category.CateAttrGroupVO;
import com.akali.common.dto.goods.base.category.CateAttributeVO;
import com.akali.common.dto.goods.base.category.CategoryAttrInfoVO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;
import com.akali.provider.goods.api.AttributionService;
import com.akali.provider.goods.bean.PmsBaseAttrGroup;
import com.akali.provider.goods.bean.PmsBaseAttrOption;
import com.akali.provider.goods.bean.PmsBaseAttribution;
import com.akali.provider.goods.dao.BaseAttrGroupDao;
import com.akali.provider.goods.dao.BaseAttrOptionDao;
import com.akali.provider.goods.dao.BaseAttrValueDao;
import com.akali.provider.goods.dao.BaseAttributionDao;
import com.akali.provider.goods.queryhelper.AttrGroupEntityQueryHelper;
import com.akali.provider.goods.queryhelper.AttrOptionEntityQueryHelper;
import com.akali.provider.goods.queryhelper.AttrValueEntityQueryHelper;
import com.akali.provider.goods.queryhelper.AttributionEntityQueryHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @ClassName AttributionServiceImpl
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Service(version = "1.0.0")
public class AttributionServiceImpl implements AttributionService {
    @Autowired
    private BaseAttrGroupDao baseAttrGroupDao;
    @Autowired
    private BaseAttributionDao baseAttributionDao;
    @Autowired
    private BaseAttrOptionDao baseAttrOptionDao;
    @Autowired
    private BaseAttrValueDao baseAttrValueDao;

    /**
     * 添加属性分组
     *
     * @param attrGroupDTO
     * @return
     */
    @Override
    public DubboResponse<Void> createAttributeGroup(AttrGroupDTO attrGroupDTO) {
        PmsBaseAttrGroup pmsBaseAttrGroup = new PmsBaseAttrGroup();
        BeanUtils.copyProperties(attrGroupDTO, pmsBaseAttrGroup);
        baseAttrGroupDao.save(pmsBaseAttrGroup);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 根据三级分类id查找全部商品属性信息，按分组存放
     *
     * @param cateId
     * @return
     */
    @Override
    public DubboResponse<CategoryAttrInfoVO> queryAllAttributeByCidWithGroup(Long cateId) {
        //查询
        List<PmsBaseAttrGroup> groupDatas = baseAttrGroupDao.findByCateId(cateId);
        List<PmsBaseAttribution> attrDatas = baseAttributionDao.findByCateId(cateId);
        Map<Long, List<AttrOptionVO>> optionMap = getAttrOptionForMap(attrDatas);
        Map<Long, List<CateAttributeVO>> attrMap = new HashMap<>();

        //封装结果
        attrDatas.stream().forEach(a -> {
            CateAttributeVO attr = new CateAttributeVO();
            BeanUtils.copyProperties(a, attr);
            if (attr.getHasOptions()) {
                attr.setOptions(optionMap.get(a.getId()));
            }
            if (!attrMap.containsKey(a.getGroupId())) {
                attrMap.put(a.getGroupId(), new ArrayList<>());
            }
            attrMap.get(a.getGroupId()).add(attr);
        });
        List<CateAttrGroupVO> groups = groupDatas.stream().map(g -> {
            CateAttrGroupVO group = new CateAttrGroupVO();
            BeanUtils.copyProperties(g, group);
            group.setAttributes(attrMap.get(g.getId()));
            return group;
        }).collect(Collectors.toList());
        return DubboResponse.SUCCESS(new CategoryAttrInfoVO(groups));
    }

    private Map<Long, List<AttrOptionVO>> getAttrOptionForMap(List<PmsBaseAttribution> attributions){
        //获得所有固定选项的属性的id
        List<Long> hasOptAttrIds = attributions.stream().filter(a -> a.getHasOptions()).map(a -> a.getId()).collect(Collectors.toList());
        //查询所有的选项
        AttrOptionEntityQueryHelper queryDTO = new AttrOptionEntityQueryHelper();
        queryDTO.setInValues(hasOptAttrIds);
        queryDTO.setInField("attrId");
        List<PmsBaseAttrOption> optionDatas = baseAttrOptionDao.findAll(AttrOptionEntityQueryHelper.getWhere(queryDTO));
        //封装Option
        Map<Long, List<AttrOptionVO>> optionMap = new HashMap<>();
        optionDatas.stream().forEach(o -> {
            AttrOptionVO optDTO = new AttrOptionVO(o.getId(),o.getAttrId(), o.getContent(), o.getNumValue());
            if (!optionMap.containsKey(o.getAttrId())) {
                optionMap.put(o.getAttrId(), new ArrayList<>());
            }
            optionMap.get(o.getAttrId()).add(optDTO);
        });

        return optionMap;
    }


    /**
     * 根据三级分类id，查询该分类的全部商品属性
     *
     * @param cid3
     * @return
     */
    @Override
    public DubboResponse<List<CateAttributeVO>> queryAttributesByCid(Long cid3) {
        List<PmsBaseAttribution> attrs = baseAttributionDao.findByCateId(cid3);
        List<Long> hasOptAttrIds = attrs.stream().filter(a -> a.getHasOptions()).map(a -> a.getId()).collect(Collectors.toList());
        boolean hasOpt = !hasOptAttrIds.isEmpty();
        Map<Long, List<AttrOptionVO>> optionMap = Maps.newHashMap();
        if (hasOpt) {
            AttrOptionEntityQueryHelper queryDTO = new AttrOptionEntityQueryHelper();
            queryDTO.setInValues(hasOptAttrIds);
            queryDTO.setInField("attrId");
            List<PmsBaseAttrOption> optionDatas = baseAttrOptionDao.findAll(AttrOptionEntityQueryHelper.getWhere(queryDTO));
            optionDatas.stream().forEach(o -> {
                AttrOptionVO optDTO = new AttrOptionVO(o.getId(),o.getAttrId(), o.getContent(), o.getNumValue());
                if (!optionMap.containsKey(o.getAttrId())) {
                    optionMap.put(o.getAttrId(), new ArrayList<>());
                }
                optionMap.get(o.getAttrId()).add(optDTO);
            });
        }

        List<CateAttributeVO> resDatas = Lists.newArrayList();
        attrs.stream().forEach(attr -> {
            CateAttributeVO cateAttributeVO = new CateAttributeVO();
            BeanUtils.copyProperties(attr, cateAttributeVO);
            if (attr.getHasOptions()) {
                cateAttributeVO.setOptions(optionMap.get(attr.getId()));
            }
            resDatas.add(cateAttributeVO);
        });

        return DubboResponse.SUCCESS(resDatas);
    }
    /**
     * 根据spuId获取 商品所有属性值
     * @param spuId
     * @return
     */
    @Override
    public DubboResponse<List<AttrValueVO>> queryProductAttrValueBySpuId(Long spuId) {
        AttrValueEntityQueryHelper queryHelper = AttrValueEntityQueryHelper.create(AttrValueVO.class);
        queryHelper.setSpuId(spuId);
        //查询数据
        List<AttrValueVO> data = baseAttrValueDao
                .findAll(AttrValueEntityQueryHelper.getWhere(queryHelper), AttrValueVO.class);
        return DubboResponse.SUCCESS(data);
    }

    /**
     * 根据分类ID查询属性组
     *
     * @param cateId
     * @param attrGroupDTO
     * @return
     */
    @Override
    public DubboResponse<QueryResult<AttrGroupVO>> queryAttrGroupByCid(Long cateId, AttrGroupDTO attrGroupDTO) {
        PageAndSortObj pas = DataJpaPageUtils.initPageAndSort(attrGroupDTO);
        AttrGroupEntityQueryHelper queryHelper = AttrGroupEntityQueryHelper.create(AttrGroupVO.class, attrGroupDTO);
        Page<AttrGroupVO> page = (Page<AttrGroupVO>) baseAttrGroupDao.findAll(
                AttrGroupEntityQueryHelper.getWhere(queryHelper), pas.getPageable(), AttrGroupVO.class);
        QueryResult<AttrGroupVO> queryResult = DataJpaPageUtils.setQueryResult(page, attrGroupDTO);
        return DubboResponse.SUCCESS(queryResult);
    }

    /**
     * 根据分组ID获取属性成功
     *
     * @param groupId
     * @return
     */
    @Override
    public DubboResponse<QueryResult<AttributionVO>> queryAttributionByGroupId(Long groupId) {
        List<PmsBaseAttribution> attrs = baseAttributionDao.findByGroupId(groupId);
        AttributionEntityQueryHelper attrQueryHelper = AttributionEntityQueryHelper.create(AttributionVO.class);
        attrQueryHelper.setGroupId(groupId);
        Map<Long, AttributionVO> dataMap = baseAttributionDao.findAll(AttributionEntityQueryHelper.getWhere(attrQueryHelper), AttributionVO.class).stream()
                .collect(Collectors.toMap(a -> a.getId(), a -> a));
        //查询所有属性相关的固定选项
        AttrOptionEntityQueryHelper queryHelper = new AttrOptionEntityQueryHelper();
        queryHelper.setInField("attrId");
        queryHelper.setWithIn(true);
        queryHelper.setResultClass(AttrOptionVO.class);
        queryHelper.setInValues(dataMap.keySet());
        List<AttrOptionVO> options = baseAttrOptionDao.findAll(AttrOptionEntityQueryHelper.getWhere(queryHelper), AttrOptionVO.class);
        //包装结果
        for (AttrOptionVO option : options) {
            dataMap.get(option.getAttrId()).getOptions().add(option);
        }
        QueryResult<AttributionVO> queryResult = DataJpaPageUtils.setQueryResult(
                Lists.newArrayList(dataMap.values()), (long) dataMap.size());
        return DubboResponse.SUCCESS(queryResult);
    }

    /**
     * 修改商品属性
     *@param attrId
     * @param attributionDTO
     * @return
     */
    @Override
    public DubboResponse<Void> updateAttribute(Long attrId, AttributionDTO attributionDTO) {
        PmsBaseAttribution pmsBaseAttribution = new PmsBaseAttribution();
        BeanUtils.copyProperties(attributionDTO, pmsBaseAttribution);
        pmsBaseAttribution.setId(attrId);
        baseAttributionDao.save(pmsBaseAttribution);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 根据三级分类id查找全部商品属性信息
     *
     * @param cateId
     * @return
     */
    @Override
    public DubboResponse<QueryResult<CateAttributeVO>> queryAllAttributeByCidList(Long cateId) {
        List<PmsBaseAttribution> attrs = baseAttributionDao.findByCateId(cateId);
        Map<Long, List<AttrOptionVO>> attrOptionForMap = getAttrOptionForMap(attrs);
        List<CateAttributeVO> resutl = attrs.stream().map(a -> new CateAttributeVO(a)).collect(Collectors.toList());
        for (CateAttributeVO cateAttributeVO : resutl) {
            cateAttributeVO.setOptions(attrOptionForMap.get(cateAttributeVO.getId()));
        }
        QueryResult<CateAttributeVO> queryResult = DataJpaPageUtils.setQueryResult(resutl, (long) resutl.size());
        return DubboResponse.SUCCESS(queryResult);
    }

    /**
     * 根据分类id 获取sku属性
     *
     * @param cateId
     * @return
     */
    @Override
    public DubboResponse<QueryResult<AttributionSimpleVO>> querySkuAttributionByCateId(Long cateId) {
        AttributionEntityQueryHelper queryHelper = AttributionEntityQueryHelper.create(AttributionSimpleVO.class);
        queryHelper.setCateId(cateId);
        queryHelper.setGeneric(false);
        List<AttributionSimpleVO> data = baseAttributionDao.findAll(
                AttributionEntityQueryHelper.getWhere(queryHelper), AttributionSimpleVO.class);
        QueryResult<AttributionSimpleVO> queryResult = DataJpaPageUtils.setQueryResult(data, (long) data.size());
        return DubboResponse.SUCCESS(queryResult);
    }

    /**
     * 修改属性的搜索分片信息
     *
     * @param attrId
     * @param attrSegmentDTO
     * @return
     */
    @Transactional
    @Override
    public DubboResponse<Void> setAttributeSearchSegment(Long attrId, AttrSegmentDTO attrSegmentDTO) {
        List<String> segments = attrSegmentDTO.getSegments();
        String segment = StringUtils.join(segments, ",");
        int re = baseAttributionDao.updateAttributeSearchSegment(attrId, segment);
        if(re!=1){
            return DubboResponse.FAIL(ProductCode.ATTRIBUTE_NOT_EXIST);
        }
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 修改属性选项信息
     *
     * @param optId
     * @param attrOptionDTO
     * @return
     */
    @Transactional
    @Override
    public DubboResponse<Void> modifyAttributeOption(Long optId, AttrOptionDTO attrOptionDTO) {
        Optional<PmsBaseAttrOption> opt = baseAttrOptionDao.findById(optId);
        if(!opt.isPresent()||!opt.get().getAttrId().equals(attrOptionDTO.getAttrId())){
            return DubboResponse.FAIL(ProductCode.ATTR_OPTION_NOT_EXIST);
        }
        PmsBaseAttrOption option = opt.get();
        BeanUtils.copyProperties(attrOptionDTO,option);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 根据分类id 查询所有刻搜索的属性字段
     *
     * @param categoryIds
     * @return
     */
    @Override
    public DubboResponse<List<AttributionSimpleVO>> querySearchingAttrByCateIds(List<Long> categoryIds) {
        AttributionEntityQueryHelper queryHelper = AttributionEntityQueryHelper.create(AttributionSimpleVO.class);
        queryHelper.buildIn("cateId",categoryIds);
        queryHelper.setSearching(true);
        List<AttributionSimpleVO> data = baseAttributionDao.findAll(
                AttributionEntityQueryHelper.getWhere(queryHelper), AttributionSimpleVO.class);
        return DubboResponse.SUCCESS(data);
    }


    /**
     * 添加商品属性
     *
     * @param attributionDTO
     * @return
     */
    @Override
    public DubboResponse<Long> createAttribute(AttributionDTO attributionDTO) {
        PmsBaseAttribution pmsBaseAttribution = new PmsBaseAttribution();
        BeanUtils.copyProperties(attributionDTO, pmsBaseAttribution);
        baseAttributionDao.save(pmsBaseAttribution);
        return DubboResponse.SUCCESS(pmsBaseAttribution.getId());
    }

    /**
     * 添加固定属性选项
     *
     * @param attrOptionVO
     * @return
     */
    @Override
    public DubboResponse<Long> createAttributeOption(AttrOptionDTO attrOptionVO) {
        PmsBaseAttrOption pmsBaseAttrOption = new PmsBaseAttrOption();
        BeanUtils.copyProperties(attrOptionVO, pmsBaseAttrOption);
        baseAttrOptionDao.save(pmsBaseAttrOption);
        return DubboResponse.SUCCESS(pmsBaseAttrOption.getId());
    }
}
