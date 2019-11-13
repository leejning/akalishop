package com.akali.provider.goods.dao;

import com.akali.provider.goods.bean.PmsBaseAttrValue;
import com.akali.provider.goods.dto.AttrValueDTO;
import com.akali.provider.goods.jpa.ExtendedJpaRepositoryApi;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @ClassName BaseAttrValueDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
public interface BaseAttrValueDao extends ExtendedJpaRepositoryApi<PmsBaseAttrValue,Long> {
    @Query("select new com.akali.provider.goods.dto.AttrValueDTO(p) from PmsBaseAttrValue p where p.spuId = ?1")
    List<AttrValueDTO> findBySpuId(Long spuId);
}
