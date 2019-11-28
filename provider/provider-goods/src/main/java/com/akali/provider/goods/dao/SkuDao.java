package com.akali.provider.goods.dao;

import com.akali.common.dto.goods.SkuDTO;
import com.akali.common.dto.goods.SkuEsDTO;
import com.akali.provider.goods.bean.PmsSku;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @ClassName SkuDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
public interface SkuDao extends CrudRepository<PmsSku,Long> {
    @Query("select count(id) from PmsSku where spuId = ?2 and ownSpec = ?1")
    int existsByOwnSpecAndSpuId(String ownSpec, Long spuId);

    @Query("select new com.akali.common.dto.goods.SkuDTO(s,s.ownSpec) from PmsSku s where s.spuId = ?1")
    List<SkuDTO> findBySpuId(Long spuId);

    @Query("select new com.akali.common.dto.goods.SkuEsDTO(s.id,s.spuId,s.title,s.mainImage,s.price) from PmsSku s where s.spuId = ?1")
    List<SkuEsDTO> findBySpuIdEs(Long id);
}
