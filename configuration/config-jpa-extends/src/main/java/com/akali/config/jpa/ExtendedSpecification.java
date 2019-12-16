package com.akali.config.jpa;

import com.google.common.collect.Lists;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName ExtendedSpecification
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
public interface ExtendedSpecification<T> extends Specification<T> {
    /**
     * 返回构造 in 范围查询的字段参数的map集合，key是字段名fieldName
     *
     * @return
     */
    default Map<String, ParameterExpression<Collection<?>>> getInParameterMap() {
        return null;
    }

    /**
     * 返回in范围查询字段的查找值的map集合，key是字段名fieldName，value是范围集合
     *
     * @return
     */
    default Map<String, Collection<?>> getInParameterValueMap() {
        return null;
    }

    /**
     * 返回一个结果字段建造器
     *
     * @return
     */
    default SelectorBuilder getSelectorBuilder() {
        Class resultClass = getResultClass();
        if (resultClass ==null) {
            return null;
        }
        //使用反射获取查询结果所需要的字段
        Field[] fields = resultClass.getDeclaredFields();
        List<String> collect = Lists.newArrayList(fields).stream()
                .filter(a -> excludeType(a))
                .map(a -> a.getName()).collect(Collectors.toList());
        String[] fieldNames = collect.toArray(new String[collect.size()]);
        return SelectorBuilder.create().append(fieldNames);
    }

    default List<String> excludeName(){
        return Collections.emptyList();
    }


    default boolean excludeType(Field field){
        if("serialVersionUID".equals(field.getName())){
            return false;
        }
        for (String name : excludeName()) {
            if(name.equals(field.getName())){
                return false;
            }
        }
        return true;
    }


    default Class getResultClass() {
        return null;
    }

    /**
     * 是否有in查找字段
     *
     * @return
     */
    default boolean hasInCondition() {
        return false;
    }

    /**
     * 构建in查询
     * @param predicates
     * @param queryHelper
     * @param root
     * @param cb
     */
    default void setInQuery(List<Predicate> predicates, BaseEntityQueryHelper queryHelper, Root<?> root, CriteriaBuilder cb){
        if(queryHelper.isWithIn()){
            if(queryHelper.getInValues().isEmpty()){
                queryHelper.setWithIn(false);
            }
            else{
                Assert.notNull(queryHelper.getInField(),"in 查询字段不能为空");
                Path<?> path = root.get(queryHelper.getInField());
                ParameterExpression<Collection<?>> parameter =
                        (ParameterExpression<Collection<?>>) (ParameterExpression) cb.parameter(Collection.class);
                getInParameterMap().put(queryHelper.getInField(),parameter);
                getInParameterValueMap().put(queryHelper.getInField(),queryHelper.getInValues());
                predicates.add(path.in(parameter));
            }
        }

    }

}
