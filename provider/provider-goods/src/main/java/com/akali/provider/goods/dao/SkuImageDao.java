package com.akali.provider.goods.dao;

import com.akali.provider.goods.bean.PmsSkuImage;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @ClassName SkuImageDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/12/9 0009
 * @Version V1.0
 **/
public interface SkuImageDao extends CrudRepository<PmsSkuImage, Long> {
    @Query
    List<PmsSkuImage> findBySkuId(Long skuId);

    @Query("update PmsSkuImage i set i.isMain = true where i.id = ?1")
    @Modifying
    void setImageAsMain(Long imageId);

    @Query("update PmsSkuImage i set i.enable = false where i.id = ?2 and i.skuId = ?1")
    @Modifying
    void setImageDisable(Long skuId, Long skuImageId);

    @Query("update PmsSkuImage i set i.isMain = ?3 where i.id = ?2 and i.skuId = ?1")
    @Modifying
    int updataMainImageById(Long skuId,Long oldMain, boolean b);

    @Query("update PmsSkuImage i set i.description = ?3 where i.id = ?2 and i.skuId = ?1")
    @Modifying
    int updateImageDesc(Long skuId,Long skuImageId, String desc);
}
