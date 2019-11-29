package com.akali.provider.goods.dao;

import com.akali.config.jpa.ExtendedJpaRepositoryApi;
import com.akali.provider.goods.bean.PmsSpuSaleOption;

/**
 * @ClassName SpuSaleOptionDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
public interface SpuSaleOptionDao extends ExtendedJpaRepositoryApi<PmsSpuSaleOption,Long> {
//    @Query("select new com.akali.common.dto.goods.spu.SpuSaleOptionDTO(s,s.skuAttrIds) from PmsSpuSaleOption s where s.spuId = ?1")
//    List<SpuSaleOptionDTO> findBySpuId(Long spuId);
}
