package com.akali.provider.goods.querydto;

import com.akali.provider.goods.jpa.ExtendedSpecification;
import lombok.Data;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName AttributionQueryDTO
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
@Data
public class AttributionQueryDTO {
    private Boolean hasOption;

    public static<PmsBaseAttrition> ExtendedSpecification<PmsBaseAttrition> getWhere(AttributionQueryDTO queryDTO){
        return new ExtendedSpecification<PmsBaseAttrition>() {
            ParameterExpression<Collection<?>> parameter = null;
            @Override
            public ParameterExpression<Collection<?>> getInParameter() {
                return parameter;
            }

            @Override
            public Predicate toPredicate(Root<PmsBaseAttrition> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();

                if(queryDTO.getHasOption()!=null){
                    Predicate predicate = cb.equal(root.get("hasOptions").as(Boolean.class), queryDTO.getHasOption());
                    predicates.add(predicate);
                }

                Path<?> path = root.get("id");
                parameter =
                        (ParameterExpression<Collection<?>>) (ParameterExpression) cb.parameter(Collection.class);
                predicates.add(path.in(parameter));
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };
    }
}
