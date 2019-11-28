package com.akali.provider.goods.dao;

import com.akali.common.dto.goods.AttrValueDTO;
import com.akali.config.jpa.ExtendedJpaRepositoryApi;
import com.akali.provider.goods.bean.PmsBaseAttrValue;
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
    @Query("select new com.akali.common.dto.goods.AttrValueDTO(p) from PmsBaseAttrValue p where p.spuId = ?1")
    List<AttrValueDTO> findBySpuId(Long spuId);
}
