package com.akali.provider.goods.dao;

import com.akali.provider.goods.bean.PmsBrandCategory;
import org.springframework.data.repository.CrudRepository;

/**
 * @ClassName BrandCategoryDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
public interface BrandCategoryDao extends CrudRepository<PmsBrandCategory,Long> {
}
