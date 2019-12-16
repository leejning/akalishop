package com.akali.provider.goods.dao;

import com.akali.config.jpa.ExtendedJpaRepositoryApi;
import com.akali.provider.goods.bean.PmsSaleOption;
import org.springframework.data.jpa.repository.Query;

/**
 * @ClassName BaseSaleOptionDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
public interface BaseSaleOptionDao extends ExtendedJpaRepositoryApi<PmsSaleOption,Long> {
    @Query("select s.name from PmsSaleOption s where s.id = ?1")
    String findNameById(Long saleOptionId);
}
