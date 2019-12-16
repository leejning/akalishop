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
import java.util.List;

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
        AttrOptionSpecification attrOptionSpecification = new AttrOptionSpecification();
        attrOptionSpecification.setQueryHelper(queryHelper);
        return attrOptionSpecification;
    }
}
class AttrOptionSpecification<PmsBaseAttrOption> extends ExtendedSpecificationAdapter<PmsBaseAttrOption> {

    @Override
    public Predicate toPredicate(Root<PmsBaseAttrOption> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = Lists.newArrayList();
        AttrOptionEntityQueryHelper queryHelper = (AttrOptionEntityQueryHelper) getQueryHelper();
        /**
         * in 查询条件
         */
        setInQuery(predicates,queryHelper,root,cb);
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }


}
