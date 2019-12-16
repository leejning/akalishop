package com.akali.provider.goods.api;

import com.akali.common.dto.goods.base.category.CategoryDTO;
import com.akali.common.dto.goods.base.category.CategoryQueryPageDTO;
import com.akali.common.dto.goods.base.category.CategorySimpleVO;
import com.akali.common.dto.goods.base.category.CategoryVO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;

import java.util.List;


/**
 * @ClassName CategoryService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
public interface CategoryService {
    /**
     * 添加一级或二级分类
     * @param categoryVO
     * @return
     */
    DubboResponse createBaseCategory(CategoryDTO categoryVO);
    /**
     * 添加三级分类
     * @param categoryVO
     * @return
     */
    DubboResponse createCategory(CategoryDTO categoryVO);

    /**
     * 根据二级id获取三级分类
     * @param pid
     * @return
     */
    DubboResponse<QueryResult<CategoryVO>> getCategoryByPid(Long  pid);

    /**
     * 根据父级id获取一级或二级分类
     * @param pid
     * @return
     */
    DubboResponse<QueryResult<CategoryVO>> getBaseCategoryByPid(Long pid);

    /**
     * 某商品 获取全分类名
     * @param cid1
     * @param cid2
     * @param cid3
     * @return  分类1/分类2/分类3
     */
    DubboResponse<String> queryFullCateName(Long cid1, Long cid2, Long cid3);

    /**
     * 查询分类列表
     * @param categoryQueryPageDTO
     * @return
     */
    DubboResponse<QueryResult<CategoryVO>> queryCategory(CategoryQueryPageDTO categoryQueryPageDTO);

    void transferCate(List<CategoryDTO> data1);

    /**
     * 获取分类全名
     * @param cid3
     * @return
     */
    DubboResponse<String> getCateFullNameById(Long cid3);

    /**
     * 根据ID查询分类
     * @param categoruIds
     * @return
     */
    List<CategorySimpleVO> queryCategorySimpleByIds(List<Long> categoruIds);
}
