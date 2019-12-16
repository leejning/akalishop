package com.akali.provider.goods.dao;

import com.akali.common.dto.goods.spu.SpuDetaiModifyDTO;
import com.akali.common.dto.goods.spu.SpuTitleDTO;
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


    @Query("update PmsSpuDetail s set s.afterService = ?2 where s.spuId = ?1")
    @Modifying
    void updateAfterServiceById(Long spuId, String afterService);

    @Query("update PmsSpuDetail s set " +
            "s.title = :#{#titles.title} ,s.subTitle = :#{#titles.subTitle}" +
            " where s.spuId = ?1")
    @Modifying
    void updateTitlesById(Long spuId, @Param("titles")  SpuTitleDTO spuTitleDTO);

    @Query("update PmsSpuDetail s set s.description = ?2 where s.spuId = ?1")
    @Modifying
    void updateDescriptionById(Long spuId, String description);

    @Query("update PmsSpuDetail s set s.packingList = ?2 where s.spuId = ?1")
    @Modifying
    void updatePackingListById(Long spuId, String packingList);
}
