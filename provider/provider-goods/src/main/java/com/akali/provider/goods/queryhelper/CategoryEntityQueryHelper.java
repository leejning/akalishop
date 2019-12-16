package com.akali.provider.goods.queryhelper;

import com.akali.common.dto.goods.base.category.CategoryQueryPageDTO;
import com.akali.config.jpa.BaseEntityQueryHelper;
import com.akali.config.jpa.ExtendedSpecification;
import com.akali.config.jpa.ExtendedSpecificationAdapter;
import com.akali.provider.goods.bean.PmsBaseCategory;
import com.akali.provider.goods.bean.PmsCategory;
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
 * @ClassName CategoryEntityQueryHelper
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/2 0002
 * @Version V1.0
 **/
@Data
public class CategoryEntityQueryHelper extends BaseEntityQueryHelper {
    private Integer level;
    private String name;

    public static ExtendedSpecification<PmsBaseCategory> getWhere(CategoryEntityQueryHelper queryHelper) {
        return new ExtendedSpecification<PmsBaseCategory>() {
            @Override
            public Predicate toPredicate(Root<PmsBaseCategory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = Lists.newArrayList();

                if (StringUtils.isNotBlank(queryHelper.getName())) {
                    Predicate predicate = cb.like(root.get("fullName")
                            .as(String.class), "%"+queryHelper.getName()+"%");
                    predicates.add(predicate);
                }
                if (queryHelper.getLevel() != null) {
                    Predicate predicate = cb.equal(root.get("level")
                            .as(Integer.class), queryHelper.getLevel());
                    predicates.add(predicate);
                }
                return cb.and(predicates.toArray(new Predicate[predicates.size()]));
            }

            @Override
            public List<String> excludeName() {
                return Lists.newArrayList("children");
            }

            @Override
            public Class getResultClass() {
                return queryHelper.getResultClass();
            }
        };
    }
    public static ExtendedSpecification<PmsCategory> getWhere2(CategoryEntityQueryHelper queryHelper) {
        CategorySpecification categorySpecification = new CategorySpecification();
        categorySpecification.setQueryHelper(queryHelper);
        return categorySpecification;
    }


    public static CategoryEntityQueryHelper create(Class clazz, CategoryQueryPageDTO queryPageDTO) {
        CategoryEntityQueryHelper queryHelper = new CategoryEntityQueryHelper();
        BeanUtils.copyProperties(queryPageDTO,queryHelper);
        queryHelper.setResultClass(clazz);
        return queryHelper;
    }
    public static CategoryEntityQueryHelper create(Class clazz) {
        CategoryEntityQueryHelper queryHelper = new CategoryEntityQueryHelper();
        queryHelper.setResultClass(clazz);
        return queryHelper;
    }
}

class CategorySpecification<PmsBaseAttrValue> extends ExtendedSpecificationAdapter<PmsBaseAttrValue> {

    @Override
    public Predicate toPredicate(Root<PmsBaseAttrValue> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = Lists.newArrayList();
        CategoryEntityQueryHelper queryHelper = (CategoryEntityQueryHelper) getQueryHelper();

        if (queryHelper.getName() != null) {
            Predicate predicate = cb.like(root.get("fullName")
                    .as(String.class), "%"+queryHelper.getName()+"%");
            predicates.add(predicate);
        }
        /**
         * in 查询条件
         */
        setInQuery(predicates,queryHelper,root,cb);
        return cb.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    @Override
    public List<String> excludeName() {
        return Lists.newArrayList("children");
    }
}

