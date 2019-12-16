package com.data.dao;

import com.data.bean.PmsBaseCatalog1;
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
public interface PmsBaseCatalog1Dao extends CrudRepository<PmsBaseCatalog1,Integer> {

    @Query("from PmsBaseCatalog1 where id <= ?1")
    List<PmsBaseCatalog1> findByIdLimit(Integer max);
}
