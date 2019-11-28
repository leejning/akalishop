package com.akali.provider.es.dao;

import com.akali.provider.es.bean.Product;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @ClassName ProductDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/16 0016
 * @Version V1.0
 **/
public interface ProductDao extends ElasticsearchRepository<Product,Long> {
}
