package com.akali.provider.goods.dao;

import com.akali.common.dto.goods.SpuSaleOptionDTO;
import com.akali.provider.goods.bean.PmsSpuSaleOption;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @ClassName SpuSaleOptionDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
public interface SpuSaleOptionDao extends CrudRepository<PmsSpuSaleOption,Long> {
    @Query("select new com.akali.common.dto.goods.SpuSaleOptionDTO(s,s.skuAttrIds) from PmsSpuSaleOption s where s.spuId = ?1")
    List<SpuSaleOptionDTO> findBySpuId(Long spuId);
}
