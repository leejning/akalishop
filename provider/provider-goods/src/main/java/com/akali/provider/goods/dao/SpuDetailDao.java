package com.akali.provider.goods.dao;

import com.akali.common.dto.goods.SpuDetaiModifyDTO;
import com.akali.provider.goods.bean.PmsSpuDetail;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @ClassName SpuDetailDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
public interface SpuDetailDao extends CrudRepository<PmsSpuDetail,Long> {
    @Query
    PmsSpuDetail findSaleOptionAttrBySpuId(Long spuId);

    @Query("update PmsSpuDetail s set s.saleOptionAttr = ?1 where s.spuId = ?2")
    @Modifying
    void updateSaleOptionAttrById(String saleOptionAttr, Long spuId);

    @Query("update PmsSpuDetail s set " +
            "s.description = CASE WHEN :#{#detail.description} IS NULL THEN s.description ELSE :#{#detail.description} END ," +
            "s.afterService = CASE WHEN :#{#detail.afterService} IS NULL THEN s.afterService ELSE :#{#detail.afterService} END ," +
            "s.packingList = CASE WHEN :#{#detail.packingList} IS NULL THEN s.packingList ELSE :#{#detail.packingList} END " +
            "where s.spuId = :#{#detail.spuId}")
    @Modifying
    void modifyBySpuId(@Param("detail") SpuDetaiModifyDTO spuDetaiModifyDTO);
}
