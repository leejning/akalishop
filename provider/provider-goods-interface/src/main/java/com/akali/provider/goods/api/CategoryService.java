package com.akali.provider.goods.api;

import com.akali.common.dto.goods.CategoryDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;


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
     * @param categoryDTO
     * @return
     */
    DubboResponse createBaseCategory(CategoryDTO categoryDTO);
    /**
     * 添加三级分类
     * @param categoryDTO
     * @return
     */
    DubboResponse createCategory(CategoryDTO categoryDTO);

    /**
     * 根据二级id获取三级分类
     * @param pid
     * @return
     */
    DubboResponse<QueryResult<CategoryDTO>> getCategoryByPid(Long  pid);

    /**
     * 根据父级id获取一级或二级分类
     * @param pid
     * @return
     */
    DubboResponse<QueryResult<CategoryDTO>> getBaseCategoryByPid(Long pid);

    /**
     * 某商品 获取全分类名
     * @param cid1
     * @param cid2
     * @param cid3
     * @return  分类1/分类2/分类3
     */
    DubboResponse<String> queryFullCateName(Long cid1, Long cid2, Long cid3);
}
