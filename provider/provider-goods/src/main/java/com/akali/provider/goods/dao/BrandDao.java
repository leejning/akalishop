package com.akali.provider.goods.dao;

import com.akali.provider.goods.bean.PmsBrand;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @ClassName BrandDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
public interface BrandDao extends PagingAndSortingRepository<PmsBrand,Long> {
}
