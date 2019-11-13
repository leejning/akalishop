package com.akali.provider.goods.querydto;

import com.akali.provider.goods.jpa.ExtendedSpecification;

import javax.persistence.criteria.*;
import java.util.Collection;

/**
 * @ClassName AttrOptionQueryDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
public class AttrOptionQueryDTO {

    public static<PmsBaseAttrOption> ExtendedSpecification<PmsBaseAttrOption> getWhere(){
        return new ExtendedSpecification<PmsBaseAttrOption>() {
            ParameterExpression<Collection<?>> parameter = null;
            @Override
            public ParameterExpression<Collection<?>> getInParameter() {
                return parameter;
            }

            @Override
            public Predicate toPredicate(Root<PmsBaseAttrOption> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Path<?> path = root.get("attrId");
                parameter =
                        (ParameterExpression<Collection<?>>) (ParameterExpression) cb.parameter(Collection.class);
                return path.in(parameter);
            }
        };
    }
}
