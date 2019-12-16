package com.data.service;

import com.akali.common.dto.goods.base.category.CategoryDTO;
import com.akali.provider.goods.api.CategoryService;
import com.data.bean.PmsBaseCatalog1;
import com.data.bean.PmsBaseCatalog2;
import com.data.bean.PmsBaseCatalog3;
import com.data.dao.PmsBaseCatalog1Dao;
import com.data.dao.PmsBaseCatalog2Dao;
import com.data.dao.PmsBaseCatalog3Dao;
import com.google.common.collect.Lists;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName TransferService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/2 0002
 * @Version V1.0
 **/
@Service
public class TransferService {
    @Autowired
    PmsBaseCatalog1Dao catalog1Dao;
    @Autowired
    PmsBaseCatalog2Dao catalog2Dao;
    @Autowired
    PmsBaseCatalog3Dao catalog3Dao;

    @Reference(version = "1.0.0")
    CategoryService categoryService;

    public void transfer(Integer max) {
//        List<PmsBaseCatalog1> catalog1s = catalog1Dao.findByIdLimit(max);

        PmsBaseCatalog1 catalog1 = catalog1Dao.findById(4).get();
        List<CategoryDTO> data1 = Lists.newArrayList();

//        for (PmsBaseCatalog1 catalog1 : catalog1s) {
            Integer id1 = catalog1.getId();
            String name1 = catalog1.getName();
            CategoryDTO cate1 = new CategoryDTO(name1, 0L, 1, name1);
            List<PmsBaseCatalog2> catalog2s = catalog2Dao.findByIdC1Id(id1);

            List<CategoryDTO> data2 = Lists.newArrayList();
            for (PmsBaseCatalog2 catalog2 : catalog2s) {
                Integer id2 = catalog2.getId();
                String name2 = catalog2.getName();
                CategoryDTO cate2 = new CategoryDTO(name2, (long)id1, 2, name1+"/"+name2);
                List<PmsBaseCatalog3> catalog3s = catalog3Dao.findByIdC2Id(id2);

                List<CategoryDTO> data3 = catalog3s.stream().map(
                        c -> new CategoryDTO(c.getName(), (long) id2, 3, cate2.getFullName() + '/' + c.getName()))
                        .collect(Collectors.toList());
                cate2.setChild(data3);
                data2.add(cate2);
            }
            cate1.setChild(data2);
            data1.add(cate1);
//        }

//        CategoryDTO d1 = data1.get(0);
//        data1.remove(0);
//        data1.add(1,d1);
        categoryService.transferCate(data1);
        System.out.println();
    }

}
