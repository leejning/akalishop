package com.akali.provider.goods.dao;

import com.akali.config.jpa.ExtendedJpaRepositoryApi;
import com.akali.provider.goods.bean.PmsBaseAttribution;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName BaseAttributionDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
public interface BaseAttributionDao extends ExtendedJpaRepositoryApi<PmsBaseAttribution,Long> {
    @Query
    List<PmsBaseAttribution> findByCateId(Long cateId);
    @Query
    List<PmsBaseAttribution> findByGroupId(Long groupId);

    @Query("update PmsBaseAttribution a set a.segments = ?2 where a.id = ?1")
    @Modifying
    int updateAttributeSearchSegment(Long attrId, String segment);
}
