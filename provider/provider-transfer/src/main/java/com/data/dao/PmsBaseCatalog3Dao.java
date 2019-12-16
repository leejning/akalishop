package com.data.dao;

import com.data.bean.PmsBaseCatalog2;
import com.data.bean.PmsBaseCatalog3;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @ClassName PmsBaseCatalog2Dao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/2 0002
 * @Version V1.0
 **/
public interface PmsBaseCatalog3Dao extends CrudRepository<PmsBaseCatalog3,Long> {

    @Query("from PmsBaseCatalog3 c where c.catalog2_id in ?1")
    List<PmsBaseCatalog3> findByIdC2Id(Integer c2ids);
}
