package com.akali.provider.goods.api;

import com.akali.common.dto.goods.base.BrandCreateDTO;
import com.akali.common.model.response.DubboResponse;

/**
 * @ClassName BrandService
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
public interface BrandService {
    /**
     * 添加新品牌
     *
     * @param brandCreateDTO
     * @return
     */
    DubboResponse<Void> createBrand(BrandCreateDTO brandCreateDTO);
}
