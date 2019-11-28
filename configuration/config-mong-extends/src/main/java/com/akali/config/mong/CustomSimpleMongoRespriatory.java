package com.akali.config.mong;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName CustomSimpleMongoRespriatory
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/10/5 0005
 * @Version V1.0
 **/
public class CustomSimpleMongoRespriatory<T, ID extends Serializable> extends SimpleMongoRepository<T, ID> {
    private final MongoOperations mongoOperations;
    private final MongoEntityInformation<T, ID> entityInformation;

    /**
     * Creates a new {@link SimpleMongoRepository} for the given {@link MongoEntityInformation} and {@link MongoTemplate}.
     *
     * @param metadata        must not be {@literal null}.
     * @param mongoOperations must not be {@literal null}.
     */
    public CustomSimpleMongoRespriatory(MongoEntityInformation<T, ID> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
        this.entityInformation = metadata;
        this.mongoOperations = mongoOperations;
    }

    public <S extends T> Page<S> findAll(final Example<S> example, final Query query, Pageable pageable) {
        Assert.notNull(example, "Sample must not be null!");
        query.addCriteria((new Criteria()).alike(example)).with(pageable);
        List<S> list = this.mongoOperations.find(query, example.getProbeType(), this.entityInformation.getCollectionName());
        return PageableExecutionUtils.getPage(list, pageable,
                () -> mongoOperations.count(query, example.getProbeType(), entityInformation.getCollectionName())
        );
    }

    //去除Example的方法
    public <S extends T> Page<S> findAll(Query query, Pageable pageable, Class<S> clazz) {
        final Class<S> clazz2 = (Class<S>) ClassUtils.getUserClass(clazz);
        query.with(pageable);
        List<S> list = this.mongoOperations.find(query, clazz,
                this.entityInformation.getCollectionName());
        return PageableExecutionUtils.getPage(list, pageable,
                () -> mongoOperations.count(query, clazz2,
                        entityInformation.getCollectionName()));
    }

    public <S extends T> Optional<S> findOne(Query query, Class<S> clazz) {
        final Class<S> clazz2 = (Class<S>) ClassUtils.getUserClass(clazz);
        return Optional
                .ofNullable(mongoOperations.findOne(query, clazz, this.entityInformation.getCollectionName()));
    }
}
