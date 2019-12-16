package com.akali.provider.goods.dao;

import com.akali.config.jpa.ExtendedJpaRepositoryApi;
import com.akali.provider.goods.bean.PmsSku;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @ClassName SkuDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
public interface SkuDao extends ExtendedJpaRepositoryApi<PmsSku,Long> {
    @Query("select count(id) from PmsSku where spuId = ?2 and ownSpec = ?1")
    int existsByOwnSpecAndSpuId(String ownSpec, Long spuId);

    @Query("update PmsSku s set s.mainImage = ?2 where s.id = ?1")
    @Modifying
    void setSkuMainImage(Long skuId, String imageUrl);

    @Query("update PmsSku s set s.title = ?2 where s.id = ?1")
    @Modifying
    int setSkuTitle(Long skuId, String title);
    @Query("update PmsSku s set s.price = ?2 where s.id = ?1")
    @Modifying
    int setSkuPrice(Long skuId, Long price);

//    @Query("select new com.akali.common.dto.goods.sku.SkuDTO(s,s.ownSpec) from PmsSku s where s.spuId = ?1")
//    List<SkuDTO> findBySpuId(Long spuId);

//    @Query("select new com.akali.common.dto.goods.SkuEsVO(s.id,s.spuId,s.title,s.mainImage,s.price) from PmsSku s where s.spuId = ?1")
//    List<SkuEsVO> findBySpuIdEs(Long id);
}
