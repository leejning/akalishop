package com.akali.provider.goods.dao;

import com.akali.provider.goods.bean.PmsBaseAttrGroup;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @ClassName BaseAttrGroupDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
public interface BaseAttrGroupDao extends CrudRepository<PmsBaseAttrGroup,Long> {
    @Query
    List<PmsBaseAttrGroup> findByCateId(Long cateId);
}
