package com.akali.provider.goods.dao;

import com.akali.provider.goods.bean.PmsSku;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * @ClassName SkuDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
public interface SkuDao extends CrudRepository<PmsSku,Long> {
    @Query("select count(id) from PmsSku where spuId = ?2 and ownSpec = ?1")
    int existsByOwnSpecAndSpuId(String ownSpec, Long spuId);
}
