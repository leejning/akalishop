package com.akali.provider.goods.queryhelper;

import com.akali.common.dto.goods.base.brand.BrandQueryDTO;
import com.akali.config.jpa.BaseEntityQueryHelper;
import com.akali.config.jpa.ExtendedSpecification;
import com.akali.config.jpa.ExtendedSpecificationAdapter;
import com.akali.provider.goods.bean.PmsBrand;
import com.google.common.collect.Lists;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * @ClassName BrandEntityQueryHelper
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/5 0005
 * @Version V1.0
 **/
@Data
public class BrandEntityQueryHelper extends BaseEntityQueryHelper {
    String name;
    String firstLetter;

    public static ExtendedSpecification<PmsBrand> getWhere(BrandEntityQueryHelper queryHelper) {
        BrandSpecification brandSpecification = new BrandSpecification();
        brandSpecification.setQueryHelper(queryHelper);
        return brandSpecification;
    }

    public static BrandEntityQueryHelper create(Class clazz, BrandQueryDTO brandQueryDTO) {
        BrandEntityQueryHelper queryHelper = new BrandEntityQueryHelper();
        BeanUtils.copyProperties(brandQueryDTO,queryHelper);
        queryHelper.setResultClass(clazz);
        return queryHelper;
    }
    public static BrandEntityQueryHelper create(Class clazz) {
        BrandEntityQueryHelper queryHelper = new BrandEntityQueryHelper();
        queryHelper.setResultClass(clazz);
        return queryHelper;
    }
}
class BrandSpecification<PmsBaseAttrValue> extends ExtendedSpecificationAdapter<PmsBaseAttrValue> {

    @Override
    public Predicate toPredicate(Root<PmsBaseAttrValue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = Lists.newArrayList();
        BrandEntityQueryHelper queryHelper = (BrandEntityQueryHelper) getQueryHelper();

        if (StringUtils.isNotBlank(queryHelper.getName())) {
            Predicate predicate = cb.like(root.get("name")
                    .as(String.class), "%"+queryHelper.getName()+"%");
            predicates.add(predicate);
        }
        if (StringUtils.isNotBlank(queryHelper.getFirstLetter())) {
            Predicate predicate = cb.equal(root.get("firstLetter")
                    .as(String.class), queryHelper.getFirstLetter());
            predicates.add(predicate);
        }
        /**
         * in 查询条件
         */
        setInQuery(predicates,queryHelper,root,cb);
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }
}
