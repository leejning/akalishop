package com.akali.provider.goods.api;

import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;
import com.akali.provider.goods.dto.CategoryDTO;


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
}
