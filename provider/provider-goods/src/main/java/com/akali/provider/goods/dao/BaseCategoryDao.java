package com.akali.provider.goods.dao;

import com.akali.provider.goods.bean.PmsBaseCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

/**
 * @ClassName BaseCategoryDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
public interface BaseCategoryDao extends PagingAndSortingRepository<PmsBaseCategory,Long> {
    @Query
    List<PmsBaseCategory> findByParentId(Long pid);
}
