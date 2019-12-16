package com.akali.provider.goods.dao;

import com.akali.config.jpa.ExtendedJpaRepositoryApi;
import com.akali.provider.goods.bean.PmsSpuSaleOption;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName SpuSaleOptionDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
public interface SpuSaleOptionDao extends ExtendedJpaRepositoryApi<PmsSpuSaleOption,Long> {
    @Query
    List<PmsSpuSaleOption> findBySpuId(Long spuId);

    @Query("update PmsSpuSaleOption s set s.skuAttrIds = ?2 where s.id = ?1")
    @Modifying
    void updateSkuAttrIdsById(Long ssoId, String skuAttrIs);
//    @Query("select new com.akali.common.dto.goods.spu.SpuSaleOptionVO(s,s.skuAttrIds) from PmsSpuSaleOption s where s.spuId = ?1")
//    List<SpuSaleOptionVO> findBySpuId(Long spuId);
}
