package com.akali.provider.goods.dao;

import com.akali.config.jpa.ExtendedJpaRepositoryApi;
import com.akali.provider.goods.bean.PmsBaseAttrGroup;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName BaseAttrGroupDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
public interface BaseAttrGroupDao extends ExtendedJpaRepositoryApi<PmsBaseAttrGroup,Long> {
    @Query
    List<PmsBaseAttrGroup> findByCateId(Long cateId);
}
