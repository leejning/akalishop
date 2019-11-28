package com.akali.provider.goods.service;

import com.akali.common.code.ProductCode;
import com.akali.common.dto.goods.SkuEsDTO;
import com.akali.common.dto.goods.SpuDetailDTO;
import com.akali.common.dto.goods.SpuEsDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.provider.goods.api.ProductEsService;
import com.akali.provider.goods.bean.PmsSpu;
import com.akali.provider.goods.bean.PmsSpuDetail;
import com.akali.provider.goods.dao.SkuDao;
import com.akali.provider.goods.dao.SpuDao;
import com.akali.provider.goods.dao.SpuDetailDao;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

/**
 * @ClassName ProductEsServiceImpl
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/16 0016
 * @Version V1.0
 **/
@Service(version = "1.0.0")
public class ProductEsServiceImpl implements ProductEsService {
    @Autowired
    private SpuDao spuDao;
    @Autowired
    private SkuDao skuDao;
    @Autowired
    private SpuDetailDao spuDetailDao;

    /**
     * 根据spuId获取构建elasticsearch 搜索库，所需要的商品信息
     * @param spuId
     * @return
     */
    @Override
    public DubboResponse<SpuEsDTO> queryProductEsBySpuId(Long spuId) {
        Optional<PmsSpu> opt = spuDao.findById(spuId);
        if(!opt.isPresent()){
            DubboResponse.FAIL(ProductCode.SPU_NOT_EXSIST);
        }
        PmsSpu spu = opt.get();
        SpuEsDTO spuEsDTO = new SpuEsDTO();
        BeanUtils.copyProperties(spu,spuEsDTO);
        spuEsDTO.setSpuId(spu.getId());
        PmsSpuDetail spuDetail = spuDetailDao.findById(spu.getId()).get();
        SpuDetailDTO spuDetailDTO = new SpuDetailDTO(spuDetail);
        spuEsDTO.setSpuDetail(spuDetailDTO);

        List<SkuEsDTO> skuEsDTOS = skuDao.findBySpuIdEs(spu.getId());
        spuEsDTO.setSkus(skuEsDTOS);

        return DubboResponse.SUCCESS(spuEsDTO);
    }
}
