package com.akali.provider.user.member.queryhelper;

import com.akali.config.jpa.BaseEntityQueryHelper;
import com.akali.config.jpa.ExtendedSpecification;
import com.akali.config.jpa.SelectorBuilder;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName MemberEntityQueryHelper
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/27 0027
 * @Version V1.0
 **/
@Data
public class MemberEntityQueryHelper extends BaseEntityQueryHelper {
    private String account;

    public static <UmsMember> ExtendedSpecification<UmsMember> getWhere(MemberEntityQueryHelper queryHelper) {
        return new ExtendedSpecification<UmsMember>() {
            Map<String, ParameterExpression<Collection<?>>> ipm;
            Map<String, Collection<?>> ipvm;

            @Override
            public Predicate toPredicate(Root<UmsMember> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = Lists.newArrayList();

                if(StringUtils.isNotBlank(queryHelper.getAccount()) ){
                    Predicate predicate = cb.equal(root.get("memberAccount")
                            .as(String.class), queryHelper.getAccount());
                    predicates.add(predicate);
                }

                //封装in查询
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
            public SelectorBuilder getSelectorBuilder() {
                Class<?> resultClass = queryHelper.getResultClass();
                Assert.notNull(resultClass,"结果类不能为null");

                SelectorBuilder selectorBuilder = new SelectorBuilder();

                Field[] fields = resultClass.getDeclaredFields();
                String[] fieldNames = Lists.newArrayList(fields).stream().map(a -> a.getName())
                        .collect(Collectors.toList()).toArray(new String[fields.length]);

                if(queryHelper.getResultClass()!=null){
                    fieldNames = initResultFields(queryHelper.getResultClass());
                }
                return selectorBuilder.append(fieldNames);
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
