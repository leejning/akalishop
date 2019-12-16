package com.akali.provider.goods.api;

import com.akali.common.dto.goods.base.brand.BrandCreateDTO;
import com.akali.common.dto.goods.base.brand.BrandQueryDTO;
import com.akali.common.dto.goods.base.brand.BrandSimpleVO;
import com.akali.common.dto.goods.base.brand.BrandVO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;

import java.util.List;

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

    /**
     * 分页查询品牌列表
     *
     * @param brandQueryDTO
     * @return
     */
    DubboResponse<QueryResult<BrandVO>> queryBrand(BrandQueryDTO brandQueryDTO);

    /**
     * 不分页查询品牌
     *
     * @param brandQueryDTO
     * @return
     */
    DubboResponse<QueryResult<BrandVO>> getBrandList(BrandQueryDTO brandQueryDTO);

    /**
     * 根据Id查询品牌
     * @param brandIds
     * @return
     */
    List<BrandSimpleVO> queryBrandSimpleByIds(List<Long> brandIds);
}
