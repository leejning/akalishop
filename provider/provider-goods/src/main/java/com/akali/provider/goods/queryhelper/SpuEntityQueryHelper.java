package com.akali.provider.goods.queryhelper;

import com.akali.common.dto.query.SpuQueryDTO;
import com.akali.config.jpa.BaseEntityQueryHelper;
import com.akali.config.jpa.ExtendedSpecification;
import com.akali.provider.goods.bean.PmsSpu;
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
 * @ClassName SpuEntityQueryHelper
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/28 0028
 * @Version V1.0
 **/
@Data
public class SpuEntityQueryHelper extends BaseEntityQueryHelper {
    private String spuTitle;
    private Long cid3;
    private Long brandId;

    public static ExtendedSpecification<PmsSpu> getWhere(SpuEntityQueryHelper queryHelper) {
        return new ExtendedSpecification<PmsSpu>() {

            @Override
            public Predicate toPredicate(Root<PmsSpu> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = Lists.newArrayList();

                if (StringUtils.isNotBlank(queryHelper.getSpuTitle())) {
                    Predicate predicate = cb.equal(root.get("title")
                            .as(String.class), queryHelper.getSpuTitle());
                    predicates.add(predicate);
                }
                if (queryHelper.getCid3() != null) {
                    Predicate predicate = cb.equal(root.get("cid3")
                            .as(Long.class), queryHelper.getCid3());
                    predicates.add(predicate);
                }
                if (queryHelper.getBrandId() != null) {
                    Predicate predicate = cb.equal(root.get("brandId")
                            .as(Long.class), queryHelper.getBrandId());
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

    public static SpuEntityQueryHelper create(SpuQueryDTO spuQueryDTO,Class clazz) {
        SpuEntityQueryHelper spuEntityQueryHelper = new SpuEntityQueryHelper();
        BeanUtils.copyProperties(spuQueryDTO, spuEntityQueryHelper);
        spuEntityQueryHelper.setResultClass(clazz);
        return spuEntityQueryHelper;
    }
    public static SpuEntityQueryHelper create(SpuQueryDTO spuQueryDTO) {
        SpuEntityQueryHelper spuEntityQueryHelper = new SpuEntityQueryHelper();
        BeanUtils.copyProperties(spuQueryDTO, spuEntityQueryHelper);
        return spuEntityQueryHelper;
    }
}
