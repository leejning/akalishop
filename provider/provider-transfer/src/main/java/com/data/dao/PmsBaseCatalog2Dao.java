package com.data.dao;

import com.data.bean.PmsBaseCatalog2;
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
public interface PmsBaseCatalog2Dao extends CrudRepository<PmsBaseCatalog2,Integer> {

    @Query("from PmsBaseCatalog2 c where c.catalog1_id = ?1")
    List<PmsBaseCatalog2> findByIdC1Id(Integer c1ids);
}
