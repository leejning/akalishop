package com.akali.provider.goods.service;

import com.akali.common.code.CommonCode;
import com.akali.common.dto.goods.*;
import com.akali.common.model.response.DubboResponse;
import com.akali.provider.goods.api.AttributionService;
import com.akali.provider.goods.bean.PmsBaseAttrGroup;
import com.akali.provider.goods.bean.PmsBaseAttrOption;
import com.akali.provider.goods.bean.PmsBaseAttribution;
import com.akali.provider.goods.dao.BaseAttrGroupDao;
import com.akali.provider.goods.dao.BaseAttrOptionDao;
import com.akali.provider.goods.dao.BaseAttrValueDao;
import com.akali.provider.goods.dao.BaseAttributionDao;
import com.akali.provider.goods.queryhelper.AttrOptionEntityQueryHelper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public DubboResponse<CategoryAttrInfoDTO> queryAllAttributeByCidWithGroup(Long cateId) {
        //查询
        List<PmsBaseAttrGroup> groupDatas = baseAttrGroupDao.findByCateId(cateId);
        List<PmsBaseAttribution> attrDatas = baseAttributionDao.findByCateId(cateId);
        List<Long> hasOptAttrIds = attrDatas.stream().filter(a -> a.getHasOptions()).map(a -> a.getId()).collect(Collectors.toList());
        AttrOptionEntityQueryHelper queryDTO = new AttrOptionEntityQueryHelper();
        queryDTO.setInValues(hasOptAttrIds);
        queryDTO.setInField("attrId");
        List<PmsBaseAttrOption> optionDatas = baseAttrOptionDao.findAll(AttrOptionEntityQueryHelper.getWhere(queryDTO));

        //封装结果
        Map<Long, List<AttrOptionDTO>> optionMap = new HashMap<>();

        optionDatas.stream().forEach(o -> {
            AttrOptionDTO optDTO = new AttrOptionDTO(o.getId(),o.getAttrId(), o.getContent(), o.getNumValue());
            if (!optionMap.containsKey(o.getAttrId())) {
                optionMap.put(o.getAttrId(), new ArrayList<>());
            }
            optionMap.get(o.getAttrId()).add(optDTO);
        });
        Map<Long, List<CateAttributeDTO>> attrMap = new HashMap<>();
        attrDatas.stream().forEach(a -> {
            CateAttributeDTO attr = new CateAttributeDTO();
            BeanUtils.copyProperties(a, attr);
            if (attr.getHasOptions()) {
                attr.setOptions(optionMap.get(a.getId()));
            }
            if (!attrMap.containsKey(a.getGroupId())) {
                attrMap.put(a.getGroupId(), new ArrayList<>());
            }
            attrMap.get(a.getGroupId()).add(attr);
        });

        List<CateAttrGroupDTO> groups = groupDatas.stream().map(g -> {
            CateAttrGroupDTO group = new CateAttrGroupDTO();
            BeanUtils.copyProperties(g, group);
            group.setAttributes(attrMap.get(g.getId()));
            return group;
        }).collect(Collectors.toList());

        return DubboResponse.SUCCESS(new CategoryAttrInfoDTO(groups));
    }

    /**
     * 根据三级分类id，查询该分类的全部商品属性
     *
     * @param cid3
     * @return
     */
    @Override
    public DubboResponse<List<CateAttributeDTO>> queryAttributesByCid(Long cid3) {
        List<PmsBaseAttribution> attrs = baseAttributionDao.findByCateId(cid3);
        List<Long> hasOptAttrIds = attrs.stream().filter(a -> a.getHasOptions()).map(a -> a.getId()).collect(Collectors.toList());
        boolean hasOpt = !hasOptAttrIds.isEmpty();
        Map<Long, List<AttrOptionDTO>> optionMap = Maps.newHashMap();
        if (hasOpt) {
            AttrOptionEntityQueryHelper queryDTO = new AttrOptionEntityQueryHelper();
            queryDTO.setInValues(hasOptAttrIds);
            queryDTO.setInField("attrId");
            List<PmsBaseAttrOption> optionDatas = baseAttrOptionDao.findAll(AttrOptionEntityQueryHelper.getWhere(queryDTO));
            optionDatas.stream().forEach(o -> {
                AttrOptionDTO optDTO = new AttrOptionDTO(o.getId(),o.getAttrId(), o.getContent(), o.getNumValue());
                if (!optionMap.containsKey(o.getAttrId())) {
                    optionMap.put(o.getAttrId(), new ArrayList<>());
                }
                optionMap.get(o.getAttrId()).add(optDTO);
            });
        }

        List<CateAttributeDTO> resDatas = Lists.newArrayList();
        attrs.stream().forEach(attr -> {
            CateAttributeDTO cateAttributeDTO = new CateAttributeDTO();
            BeanUtils.copyProperties(attr, cateAttributeDTO);
            if (attr.getHasOptions()) {
                cateAttributeDTO.setOptions(optionMap.get(attr.getId()));
            }
            resDatas.add(cateAttributeDTO);
        });

        return DubboResponse.SUCCESS(resDatas);
    }

    /**
     * 根据spuId获取 商品所有属性值
     * @param spuId
     * @return
     */
    @Override
    public DubboResponse<List<AttrValueDTO>> queryProductAttrValueBySpuId(Long spuId) {
        List<AttrValueDTO> data = baseAttrValueDao.findBySpuId(spuId);
        return DubboResponse.SUCCESS(data);
    }

    /**
     * 添加商品属性
     *
     * @param attributionDTO
     * @return
     */
    @Override
    public DubboResponse<Void> createAttribute(AttributionDTO attributionDTO) {
        PmsBaseAttribution pmsBaseAttribution = new PmsBaseAttribution();
        BeanUtils.copyProperties(attributionDTO, pmsBaseAttribution);
        baseAttributionDao.save(pmsBaseAttribution);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 添加固定属性选项
     *
     * @param attrOptionDTO
     * @return
     */
    @Override
    public DubboResponse<Void> createAttributeOption(AttrOptionDTO attrOptionDTO) {
        PmsBaseAttrOption pmsBaseAttrOption = new PmsBaseAttrOption();
        BeanUtils.copyProperties(attrOptionDTO, pmsBaseAttrOption);
        baseAttrOptionDao.save(pmsBaseAttrOption);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }
}
