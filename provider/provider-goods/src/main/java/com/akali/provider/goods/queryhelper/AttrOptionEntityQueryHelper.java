package com.akali.provider.goods.queryhelper;


import com.akali.config.jpa.BaseEntityQueryHelper;
import com.akali.config.jpa.ExtendedSpecification;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;

import javax.persistence.criteria.*;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AttrOptionEntityQueryHelper
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
@Data
public class AttrOptionEntityQueryHelper extends BaseEntityQueryHelper {

    public static <PmsBaseAttrOption> ExtendedSpecification<PmsBaseAttrOption> getWhere(AttrOptionEntityQueryHelper queryHelper) {
        return new ExtendedSpecification<PmsBaseAttrOption>() {
            Map<String, ParameterExpression<Collection<?>>> ipm;
            Map<String, Collection<?>> ipvm;

            @Override
            public Predicate toPredicate(Root<PmsBaseAttrOption> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = Lists.newArrayList();
                if (queryHelper.isWithIn()) {
                    Path<?> path = root.get(queryHelper.getInField());
                    ParameterExpression<Collection<?>> parameter =
                            (ParameterExpression<Collection<?>>) (ParameterExpression) cb.parameter(Collection.class);
                    ipm = Maps.newHashMap();
                    ipm.put("attrId", parameter);
                    ipvm = Maps.newHashMap();
                    ipvm.put("attrId", queryHelper.getInValues());
                    predicates.add(path.in(parameter));
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }

            @Override
            public Map<String, ParameterExpression<Collection<?>>> getInParameterMap() {
                return ipm;
            }

            @Override
            public Map<String, Collection<?>> getInParameterValueMap() {
                return ipvm;
            }

            @Override
            public boolean hasInCondition() {
                return queryHelper.isWithIn();
            }
        };
    }
}
