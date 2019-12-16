package com.akali.provider.goods.service;


import com.akali.common.dto.goods.base.category.CategoryDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.provider.goods.api.CategoryService;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Reference(version = "1.0.0")
    private CategoryService categoryService;

    /**
     * 添加一级或者二级分类
     */
    @Test
    public void createBaseCategory(){
        CategoryDTO categoryVO = new CategoryDTO();
        categoryVO.setName("电子书刊");
        categoryVO.setParentId(1L);
        DubboResponse response =  categoryService.createBaseCategory(categoryVO);
        System.out.println(response);
    }

    /**
     * 添加三级分类
     */
    @Test
    public void createCategory(){
        CategoryDTO categoryVO = new CategoryDTO();
        categoryVO.setName("电子书");
        categoryVO.setParentId(2L);
        DubboResponse response =  categoryService.createCategory(categoryVO);
        System.out.println(response);
    }



}
