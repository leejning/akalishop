package com.akali.provider.goods.queryhelper;

import com.akali.config.jpa.BaseEntityQueryHelper;
import com.akali.config.jpa.ExtendedSpecification;
import com.akali.provider.goods.bean.PmsSaleOption;
import com.google.common.collect.Lists;
import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @ClassName SaleOptionEntityQueryHelper
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
@Data
public class SaleOptionEntityQueryHelper extends BaseEntityQueryHelper {
    private Long cateId;

    public static ExtendedSpecification<PmsSaleOption> getWhere(SaleOptionEntityQueryHelper queryHelper) {
        return new ExtendedSpecification<PmsSaleOption>() {

            @Override
            public Predicate toPredicate(Root<PmsSaleOption> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = Lists.newArrayList();

                if (queryHelper.getCateId() != null) {
                    Predicate predicate = cb.equal(root.get("cateId")
                            .as(Long.class), queryHelper.getCateId());
                    predicates.add(predicate);
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }

            @Override
            public Class getResultClass() {
                return queryHelper.getResultClass();
            }
        };
    }

    public static SaleOptionEntityQueryHelper create(Class clazz) {
        SaleOptionEntityQueryHelper queryHelper = new SaleOptionEntityQueryHelper();
        queryHelper.setResultClass(clazz);
        return queryHelper;
    }

}
