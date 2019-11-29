package com.akali.business.admin.goods.web;

import com.akali.business.admin.goods.api.CategoryControllerApi;
import com.akali.common.dto.goods.base.CategoryDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResponseResult;
import com.akali.common.model.response.QueryResult;
import com.akali.common.model.response.ResponseResult;
import com.akali.common.utils.ExceptionCast;
import com.akali.provider.goods.api.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName CategoryController
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@RestController
@RequestMapping("/category")
@Api(value = "商品分类业务接口",tags = "分类")
public class CategoryController implements CategoryControllerApi {
    @Reference(version = "1.0.0")
    private CategoryService categoryService;

    /**
     * 创建分类
     * @param level
     * @param categoryDTO
     * @return
     */
    @PostMapping("/{level}")
    @ApiOperation("创建商品分类")
    @Override
    public ResponseResult<Void> createCategory(@PathVariable Integer level, @RequestBody CategoryDTO categoryDTO) {
        DubboResponse response = level==3?categoryService.createCategory(categoryDTO):categoryService.createBaseCategory(categoryDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS();
    }

    /**
     * 获取各级分类
     * @param pid
     * @return
     */
    @GetMapping("/{level}/{pid}")
    @Override
    public QueryResponseResult<CategoryDTO> getCategoryByPid(@PathVariable Integer level,@PathVariable Long pid) {
        DubboResponse<QueryResult<CategoryDTO>> response =
                level==3?categoryService.getCategoryByPid(pid):categoryService.getBaseCategoryByPid(pid);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData());
    }

}
