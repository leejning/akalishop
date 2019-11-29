package com.akali.provider.goods.service;

import com.akali.common.code.CommonCode;
import com.akali.common.dto.goods.base.BrandCreateDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.provider.goods.api.BrandService;
import com.akali.provider.goods.bean.PmsBrand;
import com.akali.provider.goods.bean.PmsBrandCategory;
import com.akali.provider.goods.dao.BrandCategoryDao;
import com.akali.provider.goods.dao.BrandDao;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName BrandServiceImpl
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@Service(version = "1.0.0")
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private BrandCategoryDao brandCategoryDao;

    /**
     * 添加新品牌
     * @param brandCreateDTO
     * @return
     */
    @Transactional
    @Override
    public DubboResponse<Void> createBrand(BrandCreateDTO brandCreateDTO) {
        PmsBrand pmsBrand = new PmsBrand(brandCreateDTO);
        brandDao.save(pmsBrand);
        List<PmsBrandCategory> collect = brandCreateDTO.getCate3Ids().stream()
                .map(id -> new PmsBrandCategory(pmsBrand.getId(),id)).collect(Collectors.toList());
        brandCategoryDao.saveAll(collect);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }
}
