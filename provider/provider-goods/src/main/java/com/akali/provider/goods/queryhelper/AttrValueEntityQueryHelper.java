package com.akali.provider.goods.queryhelper;

import com.akali.config.jpa.BaseEntityQueryHelper;
import com.akali.config.jpa.ExtendedSpecification;
import com.akali.config.jpa.ExtendedSpecificationAdapter;
import com.akali.provider.goods.bean.PmsBaseAttrValue;
import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.List;

/**
 * @ClassName AttrValueEntityQueryHelper
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
@Data
public class AttrValueEntityQueryHelper extends BaseEntityQueryHelper {
    private Long spuId;

    public static ExtendedSpecification<PmsBaseAttrValue> getWhere(AttrValueEntityQueryHelper queryHelper) {
        AttrValueSpecification attrValueSpecification = new AttrValueSpecification();
        attrValueSpecification.setQueryHelper(queryHelper);
        return attrValueSpecification;
    }

    public static AttrValueEntityQueryHelper create(String inField, Collection<?> inValues, Class clazz) {
        AttrValueEntityQueryHelper queryHelper = new AttrValueEntityQueryHelper();
        queryHelper.setResultClass(clazz);
        queryHelper.setInValues(inValues);
        queryHelper.setInField(inField);
        queryHelper.setWithIn(true);
        return queryHelper;
    }
    public static AttrValueEntityQueryHelper create(Class clazz) {
        AttrValueEntityQueryHelper queryHelper = new AttrValueEntityQueryHelper();
        queryHelper.setResultClass(clazz);
        return queryHelper;
    }

}
class AttrValueSpecification<PmsBaseAttrValue> extends ExtendedSpecificationAdapter<PmsBaseAttrValue> {

    @Override
    public Predicate toPredicate(Root<PmsBaseAttrValue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = Lists.newArrayList();
        AttrValueEntityQueryHelper queryHelper = (AttrValueEntityQueryHelper) getQueryHelper();

        if (queryHelper.getSpuId() != null) {
            Predicate predicate = cb.equal(root.get("spuId")
                    .as(Long.class), queryHelper.getSpuId());
            predicates.add(predicate);
        }
        /**
         * in 查询条件
         */
        setInQuery(predicates,queryHelper,root,cb);
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}