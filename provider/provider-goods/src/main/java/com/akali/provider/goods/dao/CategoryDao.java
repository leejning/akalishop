package com.akali.provider.goods.dao;

import com.akali.config.jpa.ExtendedJpaRepositoryApi;
import com.akali.provider.goods.bean.PmsCategory;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName CategoryDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
public interface CategoryDao extends ExtendedJpaRepositoryApi<PmsCategory,Long> {
    @Query
    List<PmsCategory> findByParentId(Long pid);

    @Query("select fullName from PmsCategory where id = ?1")
    Optional<String> findFullNameById(Long cid3);
}
