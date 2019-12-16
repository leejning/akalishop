package com.akali.provider.goods.queryhelper;

import com.akali.config.jpa.BaseEntityQueryHelper;
import com.akali.config.jpa.ExtendedSpecification;
import com.akali.config.jpa.ExtendedSpecificationAdapter;
import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
    private Long cateId;
    private Boolean generic;
    private Long groupId;
    private Boolean searching;

    public static<PmsBaseAttrition> ExtendedSpecification<PmsBaseAttrition> getWhere(AttributionEntityQueryHelper queryHelper){
        AttributionSpecification extendedSpecification = new AttributionSpecification();
        extendedSpecification.setQueryHelper(queryHelper);
        return extendedSpecification;
    }
    public static AttributionEntityQueryHelper create(String inField, Collection<?> inValues,Class clazz) {
        AttributionEntityQueryHelper queryHelper = new AttributionEntityQueryHelper();
        queryHelper.setResultClass(clazz);
        queryHelper.setWithIn(true);
        queryHelper.setInField(inField);
        queryHelper.setInValues(inValues);
        return queryHelper;
    }
    public static AttributionEntityQueryHelper create(Class clazz) {
        AttributionEntityQueryHelper queryHelper = new AttributionEntityQueryHelper();
        queryHelper.setResultClass(clazz);
        return queryHelper;
    }
}
class AttributionSpecification<PmsBaseAttrition> extends ExtendedSpecificationAdapter<PmsBaseAttrition> {
    @Override
    public List<String> excludeName() {
        return Lists.newArrayList("values","options");
    }

    @Override
    public Predicate toPredicate(Root<PmsBaseAttrition> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        AttributionEntityQueryHelper queryHelper = (AttributionEntityQueryHelper) getQueryHelper();
        List<Predicate> predicates = new ArrayList<Predicate>();
        if(queryHelper.getCateId()!=null){
            Predicate predicate = cb.equal(root.get("cateId").as(Long.class), queryHelper.getCateId());
            predicates.add(predicate);
        }
        if(queryHelper.getGroupId()!=null){
            Predicate predicate = cb.equal(root.get("groupId").as(Long.class), queryHelper.getGroupId());
            predicates.add(predicate);
        }
        if(queryHelper.getHasOption()!=null){
            Predicate predicate = cb.equal(root.get("hasOptions").as(Boolean.class), queryHelper.getHasOption());
            predicates.add(predicate);
        }
        if(queryHelper.getGeneric()!=null){
            Predicate predicate = cb.equal(root.get("generic").as(Boolean.class), queryHelper.getGeneric());
            predicates.add(predicate);
        }
        if(queryHelper.getSearching()!=null){
            Predicate predicate = cb.equal(root.get("searching").as(Boolean.class), queryHelper.getSearching());
            predicates.add(predicate);
        }

        /**
         * in 查询条件
         */
        setInQuery(predicates,queryHelper,root,cb);
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
