package com.akali.provider.goods.dao;

import com.akali.config.jpa.ExtendedJpaRepositoryApi;
import com.akali.provider.goods.bean.PmsBrand;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * @ClassName BrandDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
public interface BrandDao extends ExtendedJpaRepositoryApi<PmsBrand,Long> {
    @Query("select name from PmsBrand where id = ?1")
    Optional<String> findNameById(Long brandId);
}
