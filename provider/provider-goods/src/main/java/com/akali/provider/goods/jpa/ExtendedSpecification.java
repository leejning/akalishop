package com.akali.provider.goods.jpa;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.ParameterExpression;
import java.util.Collection;

/**
 * @ClassName ExtendedSpecification
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
public interface ExtendedSpecification<T> extends Specification<T> {
    /*@Nullable
    ParameterExpression<Collection<?>> parameter;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        Path<?> path = root.get("attrId");
        parameter = (ParameterExpression<Collection<?>>) (ParameterExpression) cb.parameter(Collection.class);

        return path.in(parameter);
    }*/

    /**
     * 构造 in 范围查询的字段参数
     * @return
     */
    public ParameterExpression<Collection<?>> getInParameter();

}
