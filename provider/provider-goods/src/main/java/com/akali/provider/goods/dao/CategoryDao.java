package com.akali.provider.goods.dao;

import com.akali.provider.goods.bean.PmsCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @ClassName CategoryDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
public interface CategoryDao extends PagingAndSortingRepository<PmsCategory,Long> {
    @Query
    List<PmsCategory> findByParentId(Long pid);
}
