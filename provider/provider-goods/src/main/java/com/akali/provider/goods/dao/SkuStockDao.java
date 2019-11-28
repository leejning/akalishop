package com.akali.provider.goods.dao;

import com.akali.provider.goods.bean.PmsSkuStock;
import org.springframework.data.repository.CrudRepository;

/**
 * @ClassName SkuStockDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/14 0014
 * @Version V1.0
 **/
public interface SkuStockDao extends CrudRepository<PmsSkuStock,Long> {
}
