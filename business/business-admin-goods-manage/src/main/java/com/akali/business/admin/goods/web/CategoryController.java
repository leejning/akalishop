package com.akali.business.admin.goods.web;

import com.akali.business.admin.goods.api.CategoryControllerApi;
import com.akali.common.dto.CheckPostRedoDTO;
import com.akali.common.dto.goods.base.category.CategoryDTO;
import com.akali.common.dto.goods.base.category.CategoryQueryPageDTO;
import com.akali.common.dto.goods.base.category.CategoryVO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResponseResult;
import com.akali.common.model.response.QueryResult;
import com.akali.common.model.response.ResponseResult;
import com.akali.common.utils.ExceptionCast;
import com.akali.provider.goods.api.CategoryService;
import com.akali.provider.validate.service.PostRedoValidateService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("goods/category")
@Slf4j
public class CategoryController implements CategoryControllerApi {
    @Reference(version = "1.0.0")
    private CategoryService categoryService;
//    @Reference(version = "1.0.0")
    private PostRedoValidateService postRedoValidateService;

    /**
     * 创建分类
     *
     * @param level
     * @param categoryDTO
     * @return
     */
    @PostMapping("/{level}")
    @ApiOperation("创建商品分类")
    @Override
    public ResponseResult<Void> createCategory(@PathVariable Integer level, @RequestBody CategoryDTO categoryDTO) {
        CheckPostRedoDTO checkPostRedoDTO = new CheckPostRedoDTO(categoryDTO.getFormKey());
        //检查表单重复提交
        DubboResponse<Void> checkResponse = postRedoValidateService.checkRedo(checkPostRedoDTO);
        if (!checkResponse.isSuccess()) {
            log.info(">>>>>>重复提交的表单：{}"+categoryDTO.getFormKey());
            ExceptionCast.cast(checkResponse.getResultCode());
        }

        DubboResponse response = level == 3 ? categoryService.createCategory(categoryDTO) : categoryService.createBaseCategory(categoryDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS();
    }

    /**
     * 获取各级分类
     *
     * @param pid
     * @return
     */
    @GetMapping("/{level}/{pid}")
    @Override
    public QueryResponseResult<CategoryVO> getCategoryByPid(@PathVariable Integer level, @PathVariable Long pid) {
        DubboResponse<QueryResult<CategoryVO>> response =
                level == 3 ? categoryService.getCategoryByPid(pid) : categoryService.getBaseCategoryByPid(pid);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData());
    }

    /**
     * 查询分类列表
     *
     * @param categoryQueryPageDTO
     * @return
     */
    @GetMapping("/page")
    @Override
    public QueryResponseResult<CategoryVO> queryCategory(CategoryQueryPageDTO categoryQueryPageDTO) {
        if (categoryQueryPageDTO.getLevel() == null) {
            categoryQueryPageDTO.setLevel(3);
        }
        DubboResponse<QueryResult<CategoryVO>> response = categoryService.queryCategory(categoryQueryPageDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData()).message("查询分类成功");
    }

}
