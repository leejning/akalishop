package org.springframework.data.jpa.repository.support;


import com.akali.config.jpa.ExtendedJpaRepositoryApi;
import com.akali.config.jpa.ExtendedSpecification;
import com.akali.config.jpa.SelectorBuilder;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.jpa.repository.query.QueryUtils.toOrders;

/**
 * @ClassName ExtendedJpaRepository
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
@Slf4j
public class ExtendedJpaRepository<T, ID> extends SimpleJpaRepository<T, ID> implements ExtendedJpaRepositoryApi<T, ID> {
    private EntityManager entityManager;

    private JpaEntityInformation<T, ?> entityInformation;

    public ExtendedJpaRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
        this.entityInformation = entityInformation;
    }

    /**
     *
     *  下面几个查询是把结果封装到某个DTO类
     *
     */

    /**
     * @param spec
     * @param resultClass DTO类
     * @return
     */
    @Override
    public <S> List<S> findAll(ExtendedSpecification<T> spec, Class<S> resultClass) {
        TypedQuery<Tuple> query = getQuery(spec, Sort.unsorted(), resultClass);
        return applyQueryHints(query, resultClass, spec);
    }

    @Override
    public <S> List<S> findAll(ExtendedSpecification<T> spec, Sort sort, Class<S> resultClass) {
        TypedQuery<Tuple> query = getQuery(spec, sort, resultClass);
        return applyQueryHints(query, resultClass, spec);
    }

    @Override
    public Page<?> findAll(ExtendedSpecification<T> spec, Pageable pageable, Class<?> resultClass) {
        TypedQuery<Tuple> query = getQuery(spec, pageable, resultClass);
        return pageable.isUnpaged() ? new PageImpl(query.getResultList())
                : readPage(query, getDomainClass(), pageable, spec, resultClass);
    }

    protected Page<?> readPage(TypedQuery<Tuple> query, final Class<T> domainClass, Pageable pageable,
                               @Nullable Specification<T> spec, Class<?> resultClass) {
        if (pageable.isPaged()) {
            query.setFirstResult((int) pageable.getOffset());
            query.setMaxResults(pageable.getPageSize());
        }
        List<?> resultList = applyQueryHints(query, resultClass, (ExtendedSpecification<T>) spec);
        return PageableExecutionUtils.getPage(resultList, pageable,
                () -> executeCountQuery(getCountQuery(spec, domainClass)));
    }

    private static long executeCountQuery(TypedQuery<Long> query) {

        Assert.notNull(query, "TypedQuery must not be null!");

        List<Long> totals = query.getResultList();
        long total = 0L;

        for (Long element : totals) {
            total += element == null ? 0 : element;
        }
        return total;
    }

    private TypedQuery<Tuple> getQuery(ExtendedSpecification<T> spec, Pageable pageable, Class<?> resultClass) {
        Sort sort = pageable.isPaged() ? pageable.getSort() : Sort.unsorted();
        return getQuery(spec, sort, resultClass);
    }


    /**
     * 结果集是实体类的某个DTO类的getQuery方法重载
     *
     * @param spec
     * @param sort
     * @param resultClass DTO类
     * @return
     */
    protected TypedQuery<Tuple> getQuery(@Nullable ExtendedSpecification<T> spec, Sort sort, Class<?> resultClass) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tuple> query = builder.createTupleQuery();
        Root<T> root = query.from(getDomainClass());

        SelectorBuilder selectorBuilder = spec.getSelectorBuilder();
        Assert.notNull(selectorBuilder, "SelectorBuilder选择器不能为空！可能是resultClass为空导致");

        /**
         * 构造select...
         */
        query.multiselect(selectorBuilder.bulid(builder, root));

        /**
         * 构造where...
         */
        Predicate predicate = spec.toPredicate(root, query, builder);
        if (predicate != null) {
            query.where(predicate);
        }

        /**
         * 构造order...
         */
        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }
        TypedQuery<Tuple> typeQuery = entityManager.createQuery(query);
        /**
         * 填充in查询的值
         */
        if (spec.hasInCondition()) {
            fillInfieldValues(typeQuery, spec);
        }

        return typeQuery;
    }

    /**
     * 封装查询结果到某个DTO类
     *
     * @param query
     * @param resultClass
     * @param spec
     * @param <S>
     * @return
     */
    private <S> List<S> applyQueryHints(TypedQuery<Tuple> query, Class<S> resultClass, ExtendedSpecification<T> spec) {
        List<Tuple> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return Collections.emptyList();
        }
        Tuple tuple1 = resultList.get(0);
        List<? extends Class<?>> filedTypes = tuple1.getElements().stream().map(t -> t.getJavaType()).collect(Collectors.toList());
        Class[] classes = filedTypes.toArray(new Class[(filedTypes.size())]);
        Constructor<S> constructor = null;
        boolean noArgsConstructor = false;
        try {
            //获取全参构造器
            constructor = resultClass.getConstructor(classes);
            if (constructor == null) {
                //采用setter注入
                noArgsConstructor = true;
                //拿无参构造器
                constructor = resultClass.getConstructor();
            }
        } catch (NoSuchMethodException e) {
            //采用setter注入
            noArgsConstructor = true;
            try {
                //拿无参构造器
                constructor = resultClass.getConstructor();
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
            }
        }

        //setter注入先拿到所有setter方法
        List<Method> methods = null;
        if (noArgsConstructor) {
            SelectorBuilder selectorBuilder = spec.getSelectorBuilder();
            List<String> selectorList = selectorBuilder.getSelectorList();
            methods = new ArrayList<Method>(selectorList.size());
            int i = 0;
            for (String fieldName : selectorList) {
                StringBuilder sb = new StringBuilder(fieldName);
                String s = sb.substring(0, 1).toUpperCase();
                sb.deleteCharAt(0).insert(0, s).insert(0, "set");
                String methodName = sb.toString();

                Class<?> filedType = filedTypes.get(i);
                try {
                    Method method = resultClass.getMethod(methodName, filedType);
                    methods.add(method);
                } catch (NoSuchMethodException e) {
                    log.error(">>>>>>找不到{} 名为{} 的setter方法",fieldName,methodName);
                    e.printStackTrace();
                }

                i++;
            }

        }
        List<S> list = Lists.newArrayList();
        for (Tuple tuple : resultList) {
            S item = null;
            if (noArgsConstructor) {
                item = settingInject(tuple, methods, constructor);
            } else {
                item = constructorInject(tuple, constructor, filedTypes);
            }
            list.add(item);
        }
        return list;
    }

    private <S> S settingInject(Tuple tuple, List<Method> methods, Constructor<S> constructor) {
        S object = null;

        try {
            object = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


        int i = 0;
        for (Method method : methods) {
            try {
                method.invoke(object,tuple.get(i));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            i++;
        }

        return object;
    }


    private <S> S constructorInject(Tuple tuple, Constructor<S> constructor, List<? extends Class<?>> collect) {
        Object[] objects = new Object[collect.size()];
        for (int i = 0; i < collect.size(); i++) {
            objects[i] = tuple.get(i);
        }
        S item = null;
        try {
            item = constructor.newInstance(objects);
        } catch (InstantiationException | InvocationTargetException | IllegalAccessException e) {
            log.error("反射创建 {} 实例失败", constructor.getName());
            e.printStackTrace();
        }
        return item;
    }


    /**
     * ================================================================================================================
     * | 下面的查询方法返回的是实体类
     * ================================================================================================================
     */

    @Override
    public List<T> findAll(ExtendedSpecification<T> spec) {
        return getQuery(spec, Sort.unsorted()).getResultList();
    }

    @Override
    public List<T> findAll(ExtendedSpecification<T> spec, Sort sort) {
        return getQuery(spec, sort).getResultList();
    }

    @Override
    public Page<T> findAll(ExtendedSpecification<T> spec, Pageable pageable) {
        TypedQuery<T> query = getQuery(spec, pageable);
        return pageable.isUnpaged() ? new PageImpl<T>(query.getResultList())
                : readPage(query, getDomainClass(), pageable, spec);
    }

    /**
     * 重载方法 SimpleJpaRepository的方法
     *
     * @param spec
     * @param pageable
     * @return
     */
    protected TypedQuery<T> getQuery(@Nullable ExtendedSpecification<T> spec, Pageable pageable) {
        Sort sort = pageable.isPaged() ? pageable.getSort() : Sort.unsorted();
        return getQuery(spec, sort);
    }

    /**
     * 重载方法 SimpleJpaRepository的方法
     *
     * @param spec
     * @param sort
     * @return
     */
    protected TypedQuery<T> getQuery(@Nullable ExtendedSpecification<T> spec, Sort sort) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(getDomainClass());

        Root<T> root = query.from(getDomainClass());

        /**
         * 构造select...
         */
        if (spec.getSelectorBuilder() != null) {
            query.multiselect(spec.getSelectorBuilder().bulid(builder, root));
        } else {
            query.select(root);
        }
        /**
         * 构造where...
         */
        Predicate predicate = spec.toPredicate(root, query, builder);
        if (predicate != null) {
            query.where(predicate);
        }

        /**
         * 构造order...
         */
        if (sort.isSorted()) {
            query.orderBy(toOrders(sort, root, builder));
        }
        TypedQuery<T> typeQuery = applyRepositoryMethodMetadataCustom(entityManager.createQuery(query));
        /**
         * 填充in查询的值
         */
        if (spec.hasInCondition()) {
            fillInfieldValues(typeQuery, spec);
        }
        return typeQuery;
    }

    /**
     * 填充in范围查询的范围值
     *
     * @param query
     * @param spec
     */
    private void fillInfieldValues(TypedQuery<?> query, ExtendedSpecification<?> spec) {
        Map<String, ParameterExpression<Collection<?>>> ipm = spec.getInParameterMap();
        Map<String, Collection<?>> ipvm = spec.getInParameterValueMap();

        Assert.notNull(ipm, "in范围查询的字段Map集合不能为null");
        Assert.notNull(ipvm, "in范围查询的字段对应的值集合不能为null!");

        ipvm.entrySet().stream().forEach(en -> query.setParameter(ipm.get(en.getKey()), en.getValue()));
    }

    /**
     * 重载方法，解析Specification，构造where... ，数量查询时才会调用
     *
     * @param spec
     * @param domainClass
     * @param query
     * @param <S>
     * @param <U>
     * @return
     */
    private <S, U extends T> Root<U> applySpecificationToCriteria(@Nullable ExtendedSpecification<U> spec, Class<U> domainClass,
                                                                  CriteriaQuery<S> query) {
        Assert.notNull(domainClass, "Domain class must not be null!");
        Assert.notNull(query, "CriteriaQuery must not be null!");
        Root<U> root = query.from(domainClass);
        if (spec == null) {
            return root;
        }
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        Predicate predicate = spec.toPredicate(root, query, builder);
        if (predicate != null) {
            query.where(predicate);
        }
        return root;
    }

    /**
     * 重写 SimpleJpaRepository 的数量查询，分页时会调用。
     *
     * @param spec
     * @param domainClass
     * @param <S>
     * @return
     */
    @Override
    protected <S extends T> TypedQuery<Long> getCountQuery(@Nullable Specification<S> spec, Class<S> domainClass) {

        if (!(spec instanceof ExtendedSpecification)) {
            return super.getCountQuery(spec, domainClass);
        }

        ExtendedSpecification<S> mySpec = (ExtendedSpecification<S>) spec;

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);

        Root<S> root = applySpecificationToCriteria(mySpec, domainClass, query);

        if (query.isDistinct()) {
            query.select(builder.countDistinct(root));
        } else {
            query.select(builder.count(root));
        }

        // Remove all Orders the Specifications might have applied
        query.orderBy(Collections.<Order>emptyList());
        TypedQuery<Long> typeQuery = entityManager.createQuery(query);
        /**
         * 填充in查询的值
         */
        if (mySpec.hasInCondition()) {
            fillInfieldValues(typeQuery, mySpec);
        }
        return typeQuery;
    }


    /**
     * 父类 SimpleJpaRepository 的applyRepositoryMethodMetadata方法是私有的，所有自己复制一个
     *
     * @param query
     * @param <T>
     * @return
     */
    private <T> TypedQuery<T> applyRepositoryMethodMetadataCustom(TypedQuery<T> query) {
        CrudMethodMetadata metadata = getRepositoryMethodMetadata();
        if (metadata == null) {
            return query;
        }

        LockModeType type = metadata.getLockModeType();
        TypedQuery<T> toReturn = type == null ? query : query.setLockMode(type);
        applyQueryHintsCustom(toReturn);

        return toReturn;
    }

    /**
     * 父类 SimpleJpaRepository 的applyQueryHints方法是私有的，所有自己复制一个
     *
     * @param query
     */
    private void applyQueryHintsCustom(Query query) {
        for (Map.Entry<String, Object> hint : getQueryHints().withFetchGraphs(entityManager)) {
            query.setHint(hint.getKey(), hint.getValue());
        }
    }
}
