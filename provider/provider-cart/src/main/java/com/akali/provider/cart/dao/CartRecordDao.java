package com.akali.provider.cart.dao;

import com.akali.provider.cart.bean.OmsCartRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.Optional;

/**
 * @ClassName CartRecordDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/27 0027
 * @Version V1.0
 **/
public interface CartRecordDao extends MongoRepository<OmsCartRecord,String> {
    @Query
    Optional<OmsCartRecord> findByMemberId(Long memberId);
}
