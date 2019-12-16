package com.akali.business.admin.goods.web;

import com.akali.business.admin.goods.api.BrandControllerApi;
import com.akali.common.dto.goods.base.brand.BrandCreateDTO;
import com.akali.common.dto.goods.base.brand.BrandQueryDTO;
import com.akali.common.dto.goods.base.brand.BrandVO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResponseResult;
import com.akali.common.model.response.QueryResult;
import com.akali.common.model.response.ResponseResult;
import com.akali.common.utils.ExceptionCast;
import com.akali.provider.goods.api.BrandService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName BrandController
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/11 0011
 * @Version V1.0
 **/
@RestController
@RequestMapping("goods/brand")
public class BrandController implements BrandControllerApi {
    @Reference(version = "1.0.0")
    private BrandService brandService;

    /**
     * 添加新品牌
     *
     * @param brandCreateDTO
     * @return
     */
    @PostMapping
    @Override
    public ResponseResult<Void> createBrand(@RequestBody BrandCreateDTO brandCreateDTO) {
        DubboResponse<Void> response = brandService.createBrand(brandCreateDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return ResponseResult.SUCCESS();
    }

    /**
     * 分页查询品牌列表
     * @param brandCreateDTO
     * @return
     */
    @GetMapping("page")
    @Override
    public QueryResponseResult<BrandVO> queryBrandPage(BrandQueryDTO brandCreateDTO) {
        DubboResponse<QueryResult<BrandVO>> response = brandService.queryBrand(brandCreateDTO);
        if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData()).message("分页查询品牌列表成功");
    }

    /**
     * 查询品牌列表
     * @param brandCreateDTO
     * @return
     */
    @GetMapping("list")
    @Override
    public QueryResponseResult<BrandVO> getBrandList(BrandQueryDTO brandCreateDTO) {
        DubboResponse<QueryResult<BrandVO>> response = brandService.getBrandList(brandCreateDTO);
            if (!response.isSuccess()) {
            ExceptionCast.cast(response.getResultCode());
        }
        return QueryResponseResult.SUCCESS(response.getData()).message("查询品牌列表成功");
    }

}
