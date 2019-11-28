package com.akali.config.mong;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

/**
 * @ClassName MongoBaseDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/10/4 0004
 * @Version V1.0
 **/
public interface MongoBaseDao<T, ID> extends MongoRepository<T, ID> {
    public <S extends T> Page<S> findAll(final Example<S> example, final Query query, Pageable pageable);
    public <S extends T> Page<S> findAll(Query query, Pageable pageable, Class<S> clazz);
    public <S extends T> Optional<S> findOne(Query query, Class<S> clazz);

}
