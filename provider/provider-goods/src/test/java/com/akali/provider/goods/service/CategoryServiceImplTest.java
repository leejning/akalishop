package com.akali.provider.goods.service;


import com.akali.common.model.response.DubboResponse;
import com.akali.provider.goods.api.CategoryService;
import com.akali.provider.goods.dto.CategoryDTO;
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
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("电子书刊");
        categoryDTO.setParentId(1L);
        DubboResponse response =  categoryService.createBaseCategory(categoryDTO);
        System.out.println(response);
    }

    /**
     * 添加三级分类
     */
    @Test
    public void createCategory(){
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("电子书");
        categoryDTO.setParentId(2L);
        DubboResponse response =  categoryService.createCategory(categoryDTO);
        System.out.println(response);
    }



}
