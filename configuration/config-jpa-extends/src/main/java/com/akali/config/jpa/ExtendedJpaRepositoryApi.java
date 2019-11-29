package com.akali.config.jpa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

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
    public List<T> findAll(ExtendedSpecification<T> spec);

    public List<T> findAll(ExtendedSpecification<T> spec, Sort sort);

    public Page<T> findAll(ExtendedSpecification<T> spec, Pageable pageable);

    /**
     * 下面几个是自动把查询结果封装到指定的包装类 resultClass
     *
     */
    public <S> List<S> findAll(ExtendedSpecification<T> spec, Class<S> resultClass);

    public <S> List<S> findAll(ExtendedSpecification<T> spec, Sort sort, Class<S> resultClass);

    public Page<?> findAll(ExtendedSpecification<T> spec, Pageable pageable, Class<?> resultClass);
}
