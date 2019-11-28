package com.akali.provider.goods.queryhelper;

import com.akali.config.jpa.BaseEntityQueryHelper;
import com.akali.config.jpa.ExtendedSpecification;
import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @ClassName AttributionEntityQueryHelper
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
@Data
public class AttributionEntityQueryHelper extends BaseEntityQueryHelper {

    private Boolean hasOption;

    public static<PmsBaseAttrition> ExtendedSpecification<PmsBaseAttrition> getWhere(AttributionEntityQueryHelper queryHelper){
        return new ExtendedSpecification<PmsBaseAttrition>() {
            Map<String,ParameterExpression<Collection<?>>>  ipm;
            Map<String,Collection<?>> ipvm;

            @Override
            public Predicate toPredicate(Root<PmsBaseAttrition> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();

                if(queryHelper.getHasOption()!=null){
                    Predicate predicate = cb.equal(root.get("hasOptions").as(Boolean.class), queryHelper.getHasOption());
                    predicates.add(predicate);
                }

                /**
                 * in 查询条件
                 */
                if(queryHelper.isWithIn()){
                    Assert.isNull(queryHelper.getInField(),"in 查询字段不能为空");
                    Path<?> path = root.get(queryHelper.getInField());
                    ParameterExpression<Collection<?>> parameter =
                            (ParameterExpression<Collection<?>>) (ParameterExpression) cb.parameter(Collection.class);
                    ipm = Maps.newHashMap();
                    ipm.put(queryHelper.getInField(),parameter);
                    ipvm = Maps.newHashMap();
                    ipvm.put(queryHelper.getInField(),queryHelper.getInValues());

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
