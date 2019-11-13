package com.akali.provider.goods.jpa;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Collection;
import java.util.List;

/**
 * @ClassName ExtendedJpaRepositoryApi
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
@NoRepositoryBean
public interface ExtendedJpaRepositoryApi<T,ID> extends JpaRepository<T,ID>,JpaSpecificationExecutor<T> {
    public List<T> findAllHasInCondition(Collection<?> inValues, Specification<T> spec);
}
