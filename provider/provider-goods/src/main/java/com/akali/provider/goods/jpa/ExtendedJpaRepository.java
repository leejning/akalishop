package com.akali.provider.goods.jpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName ExtendedJpaRepository
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
public class ExtendedJpaRepository<T,ID> extends SimpleJpaRepository<T, ID> implements ExtendedJpaRepositoryApi<T,ID>{

    public ExtendedJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    @Override
    public List<T> findAllHasInCondition(Collection<?> inValues, Specification<T> spec) {
        ExtendedSpecification<T> mySpec = (ExtendedSpecification<T>) spec;
        TypedQuery<T> query = getQuery(mySpec, Sort.unsorted());
        return query.setParameter(mySpec.getInParameter(), inValues).getResultList();
    }
}
