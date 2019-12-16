package com.akali.provider.goods.queryhelper;

import com.akali.common.dto.goods.base.attribute.AttrGroupDTO;
import com.akali.config.jpa.BaseEntityQueryHelper;
import com.akali.config.jpa.ExtendedSpecification;
import com.akali.provider.goods.bean.PmsBaseAttrGroup;
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
 * @ClassName AttrGroupEntityQueryHelper
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/5 0005
 * @Version V1.0
 **/
@Data
public class AttrGroupEntityQueryHelper extends BaseEntityQueryHelper {
    private String name;
    private Long cateId;

    public static <PmsBaseAttrOption> ExtendedSpecification<PmsBaseAttrGroup> getWhere(AttrGroupEntityQueryHelper queryHelper) {
        return new ExtendedSpecification<PmsBaseAttrGroup>() {
            @Override
            public Predicate toPredicate(Root<PmsBaseAttrGroup> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = Lists.newArrayList();
                if (StringUtils.isNotBlank(queryHelper.getName())) {
                    Predicate predicate = cb.equal(
                            root.get("name").as(String.class),
                            queryHelper.getName());
                    predicates.add(predicate);
                }
                if (queryHelper.getCateId() != null) {
                    Predicate predicate = cb.equal(
                            root.get("cateId").as(Long.class),
                            queryHelper.getCateId());
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


    public static AttrGroupEntityQueryHelper create(Class clazz, AttrGroupDTO attrGroupDTO) {
        AttrGroupEntityQueryHelper queryHelper = new AttrGroupEntityQueryHelper();
        BeanUtils.copyProperties(attrGroupDTO,queryHelper);
        queryHelper.setResultClass(clazz);
        return queryHelper;
    }


}
