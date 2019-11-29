package com.akali.business.admin.goods.web;

import com.akali.business.admin.goods.api.BrandControllerApi;
import com.akali.common.dto.goods.base.BrandCreateDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.ResponseResult;
import com.akali.common.utils.ExceptionCast;
import com.akali.provider.goods.api.BrandService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName BrandController
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@RestController
@RequestMapping("brand")
public class BrandController implements BrandControllerApi {
    @Reference(version = "1.0.0")
    private BrandService brandService;

    /**
     * 添加新品牌
     *
     * @param brandCreateDTO
     * @return
     */
    @Override
    public ResponseResult<Void> createBrand(BrandCreateDTO brandCreateDTO) {
        DubboResponse<Void> response = brandService.createBrand(brandCreateDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS();
    }

}
