package com.akali.provider.goods.service;

import com.akali.common.code.CommonCode;
import com.akali.common.code.ProductCode;
import com.akali.common.data_help.DataJpaPageUtils;
import com.akali.common.data_help.PageAndSortObj;
import com.akali.common.dto.goods.base.category.CategoryDTO;
import com.akali.common.dto.goods.base.category.CategoryQueryPageDTO;
import com.akali.common.dto.goods.base.category.CategorySimpleVO;
import com.akali.common.dto.goods.base.category.CategoryVO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;
import com.akali.provider.goods.api.CategoryService;
import com.akali.provider.goods.bean.PmsBaseCategory;
import com.akali.provider.goods.bean.PmsCategory;
import com.akali.provider.goods.dao.BaseCategoryDao;
import com.akali.provider.goods.dao.CategoryDao;
import com.akali.provider.goods.queryhelper.CategoryEntityQueryHelper;
import com.akali.provider.goods.service.fallback.CategoryServiceFallback;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.google.common.collect.Lists;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
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
     *
     * @param categoryDTO
     * @return
     */
    @SentinelResource(value = "createCategory", fallback = "createCategory", fallbackClass = CategoryServiceFallback.class)
    @Override
    public DubboResponse createBaseCategory(CategoryDTO categoryDTO) {
        if (categoryDTO.getLevel() == 0) {
            throw new IllegalArgumentException("分类等级不合法");
        }
        PmsBaseCategory pmsBaseCategory = new PmsBaseCategory();
        BeanUtils.copyProperties(categoryDTO, pmsBaseCategory);
        Optional<PmsBaseCategory> opt = baseCategoryDao.findById(categoryDTO.getParentId());
        if (opt.isPresent()) {
            pmsBaseCategory.setFullName(opt.get().getFullName() +"/"+ categoryDTO.getName());
        }
        else{
            pmsBaseCategory.setFullName(categoryDTO.getName());
        }
        baseCategoryDao.save(pmsBaseCategory);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 添加三级分类
     *
     * @param categoryDTO
     * @return
     */
    @Override
    public DubboResponse createCategory(CategoryDTO categoryDTO) {
        PmsCategory pmsCategory = new PmsCategory();
        BeanUtils.copyProperties(categoryDTO, pmsCategory);
        PmsBaseCategory par = baseCategoryDao.findById(categoryDTO.getParentId()).get();
        pmsCategory.setFullName(par.getFullName() + categoryDTO.getName());
        categoryDao.save(pmsCategory);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 根据二级分类id获取三级分类
     *
     * @param pid
     * @return
     */
    @Override
    public DubboResponse<QueryResult<CategoryVO>> getCategoryByPid(Long pid) {
        List<PmsCategory> categories = categoryDao.findByParentId(pid);
        List<CategoryVO> data = categories.stream().map(c -> new CategoryVO(c)).collect(Collectors.toList());
        return DubboResponse.SUCCESS(QueryResult.create(data, (long) data.size()));
    }

    /**
     * 根据父级id获取一级或二级分类
     *
     * @param pid
     * @return
     */
    @SentinelResource(value = "createCategory", fallback = "getCategory", fallbackClass = CategoryServiceFallback.class)
    @Override
    public DubboResponse<QueryResult<CategoryVO>> getBaseCategoryByPid(Long pid) {
        List<PmsBaseCategory> categories = baseCategoryDao.findByParentId(pid);
        List<CategoryVO> data = categories.stream()
                .map(c -> new CategoryVO(c))
                .collect(Collectors.toList());
        return DubboResponse.SUCCESS(QueryResult.create(data, (long) data.size()));
    }

    /**
     * 某商品 获取全分类名
     *
     * @param cid1
     * @param cid2
     * @param cid3
     * @return 分类1/分类2/分类3
     */
    @Override
    public DubboResponse<String> queryFullCateName(Long cid1, Long cid2, Long cid3) {
        List<PmsBaseCategory> category = (List<PmsBaseCategory>) baseCategoryDao.findAllById(Lists.newArrayList(cid1, cid2));
        PmsCategory pmsCategory = categoryDao.findById(cid3).get();
        String fullName = category.get(0).getName() + "/" + category.get(1).getName() + "/" + pmsCategory.getName();
        return DubboResponse.SUCCESS(fullName);
    }

    /**
     * 查询分类列表
     *
     * @param queryPageDTO
     * @return
     */
    @Override
    public DubboResponse<QueryResult<CategoryVO>> queryCategory(CategoryQueryPageDTO queryPageDTO) {
        PageAndSortObj pageAndSortObj = DataJpaPageUtils.initPageAndSort(queryPageDTO);
        CategoryEntityQueryHelper queryHelper = CategoryEntityQueryHelper.create(CategoryVO.class, queryPageDTO);
        Page<CategoryVO> page;
        if (queryPageDTO.getLevel() != 3) {
            page = (Page<CategoryVO>) baseCategoryDao
                    .findAll(CategoryEntityQueryHelper.getWhere(queryHelper), pageAndSortObj.getPageable(), CategoryVO.class);
        }
        else{
            page = (Page<CategoryVO>) categoryDao
                    .findAll(CategoryEntityQueryHelper.getWhere2(queryHelper), pageAndSortObj.getPageable(), CategoryVO.class);
        }
        QueryResult<CategoryVO> result = DataJpaPageUtils.setQueryResult(page, queryPageDTO);
        return DubboResponse.SUCCESS(result);
    }

    @Override
    public void transferCate(List<CategoryDTO> data1) {

        List<PmsBaseCategory> c1s = Lists.newArrayList();
        List<PmsBaseCategory> c2s = Lists.newArrayList();
        List<PmsCategory> c3s = Lists.newArrayList();
        for (CategoryDTO cate1 : data1) {
            PmsBaseCategory c1 = new PmsBaseCategory();
            BeanUtils.copyProperties(cate1,c1);
            baseCategoryDao.save(c1);

            for (CategoryDTO cate2 : cate1.getChild()) {
                PmsBaseCategory c2 = new PmsBaseCategory();
                BeanUtils.copyProperties(cate2,c2);
                c2.setParentId(c1.getId());
                c2s.add(c2);

            }
        }
        baseCategoryDao.saveAll(c2s);

        int i = 0;
        for (CategoryDTO cate2 : data1.get(0).getChild()) {
            for (CategoryDTO cate3 : cate2.getChild()) {
                PmsCategory c3 = new PmsCategory();
                BeanUtils.copyProperties(cate3,c3);
                c3.setParentId(c2s.get(i).getId());
                c3s.add(c3);
            }
            i++;
        }

        categoryDao.saveAll(c3s);

    }

    /**
     * 获取分类全名
     *
     * @param cid3
     * @return
     */
    @Override
    public DubboResponse<String> getCateFullNameById(Long cid3) {
        Optional<String> opt = categoryDao.findFullNameById(cid3);
        if(!opt.isPresent()){
            return DubboResponse.FAIL(ProductCode.CATEGORY_NOT_EXIST);
        }
        return DubboResponse.SUCCESS(opt.get());
    }

    /**
     * 根据ID查询分类
     *
     * @param categoryIds
     * @return
     */
    @Override
    public List<CategorySimpleVO> queryCategorySimpleByIds(List<Long> categoryIds) {
        CategoryEntityQueryHelper queryHelper = CategoryEntityQueryHelper.create(CategorySimpleVO.class);
        queryHelper.setInField("id");
        queryHelper.setWithIn(true);
        queryHelper.setInValues(categoryIds);
        List<CategorySimpleVO> data = categoryDao.findAll(CategoryEntityQueryHelper.getWhere2(queryHelper), CategorySimpleVO.class);
        return data;
    }
}
