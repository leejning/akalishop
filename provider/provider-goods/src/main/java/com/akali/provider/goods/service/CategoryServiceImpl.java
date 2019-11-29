package com.akali.provider.goods.service;

import com.akali.common.code.CommonCode;
import com.akali.common.dto.goods.base.CategoryDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;
import com.akali.provider.goods.api.CategoryService;
import com.akali.provider.goods.bean.PmsBaseCategory;
import com.akali.provider.goods.bean.PmsCategory;
import com.akali.provider.goods.dao.BaseCategoryDao;
import com.akali.provider.goods.dao.CategoryDao;
import com.akali.provider.goods.service.fallback.CategoryServiceFallback;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.google.common.collect.Lists;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName CategoryServiceImpl
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Service(version = "1.0.0")
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private BaseCategoryDao baseCategoryDao;
    @Autowired
    private CategoryDao categoryDao;

    /**
     * 添加一级或二级分类
     * @param categoryDTO
     * @return
     */
    @SentinelResource(value = "createCategory", fallback = "createCategory", fallbackClass = CategoryServiceFallback.class)
    @Override
    public DubboResponse createBaseCategory(CategoryDTO categoryDTO) {
        if(categoryDTO.getLevel()==0){
            throw new IllegalArgumentException("分类等级不合法");
        }
        PmsBaseCategory pmsBaseCategory = new PmsBaseCategory();
        BeanUtils.copyProperties(categoryDTO,pmsBaseCategory);
        baseCategoryDao.save(pmsBaseCategory);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 添加三级分类
     * @param categoryDTO
     * @return
     */
    @Override
    public DubboResponse createCategory(CategoryDTO categoryDTO) {
        PmsCategory pmsCategory = new PmsCategory();
        BeanUtils.copyProperties(categoryDTO,pmsCategory);
        categoryDao.save(pmsCategory);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 根据二级分类id获取三级分类
     * @param pid
     * @return
     */
    @Override
    public DubboResponse<QueryResult<CategoryDTO>> getCategoryByPid(Long pid) {
        List<PmsCategory> categories = categoryDao.findByParentId(pid);
        List<CategoryDTO> data = categories.stream().map(c -> new CategoryDTO(c.getName(), c.getParentId())).collect(Collectors.toList());
        return DubboResponse.SUCCESS(QueryResult.create(data,(long)data.size()));
    }

    /**
     * 根据父级id获取一级或二级分类
     * @param pid
     * @return
     */
    @SentinelResource(value = "createCategory", fallback = "getCategory", fallbackClass = CategoryServiceFallback.class)
    @Override
    public DubboResponse<QueryResult<CategoryDTO>> getBaseCategoryByPid(Long pid) {
        List<PmsBaseCategory> categories =  baseCategoryDao.findByParentId(pid);
        List<CategoryDTO> data = categories.stream().map(c -> new CategoryDTO(c.getName(), c.getParentId())).collect(Collectors.toList());
        return DubboResponse.SUCCESS(QueryResult.create(data,(long)data.size()));
    }

    /**
     * 某商品 获取全分类名
     * @param cid1
     * @param cid2
     * @param cid3
     * @return  分类1/分类2/分类3
     */
    @Override
    public DubboResponse<String> queryFullCateName(Long cid1, Long cid2, Long cid3) {
        List<PmsBaseCategory> category = (List<PmsBaseCategory>) baseCategoryDao.findAllById(Lists.newArrayList(cid1, cid2));
        PmsCategory pmsCategory = categoryDao.findById(cid3).get();
        String fullName = category.get(0).getName() + "/" + category.get(1).getName() + "/"+pmsCategory.getName();
        return DubboResponse.SUCCESS(fullName);
    }
}
