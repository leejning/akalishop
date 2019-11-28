package com.akali.provider.es.service;

import com.akali.common.utils.IdUtil;
import com.akali.provider.es.bean.Product;
import com.akali.provider.es.dao.ProductDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BuildServiceTest {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private ProductDao productDao;

    @Autowired
    private BuildService buildService;

    @Test
    public void createIndex() {
        // 创建索引
        elasticsearchTemplate.createIndex(Product.class);
        // 配置映射
        elasticsearchTemplate.putMapping(Product.class);
    }
    @Test
    public void deleteIndex(){

        elasticsearchTemplate.deleteIndex("products");

    }

    @Test
    public void buildProductBySpuId() throws JsonProcessingException {
        buildService.buildProductBySpuId(1L);
    }

    @Test
    public void findById(){
        Product product = productDao.findById(1L).get();
        System.out.println(product);
        System.out.println(IdUtil.nextId());
        System.out.println(Long.MAX_VALUE);
    }


}