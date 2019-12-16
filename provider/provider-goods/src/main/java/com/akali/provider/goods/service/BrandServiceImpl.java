package com.akali.provider.goods.service;

import com.akali.common.code.CommonCode;
import com.akali.common.data_help.DataJpaPageUtils;
import com.akali.common.data_help.PageAndSortObj;
import com.akali.common.dto.goods.base.brand.BrandCreateDTO;
import com.akali.common.dto.goods.base.brand.BrandQueryDTO;
import com.akali.common.dto.goods.base.brand.BrandSimpleVO;
import com.akali.common.dto.goods.base.brand.BrandVO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;
import com.akali.provider.goods.api.BrandService;
import com.akali.provider.goods.bean.PmsBrand;
import com.akali.provider.goods.dao.BrandCategoryDao;
import com.akali.provider.goods.dao.BrandDao;
import com.akali.provider.goods.queryhelper.BrandEntityQueryHelper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import javax.transaction.Transactional;
import java.util.List;

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
        pmsBrand.setId(null);
        brandDao.save(pmsBrand);;
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 查询品牌列表
     *
     * @param brandQueryDTO
     * @return
     */
    @Override
    public DubboResponse<QueryResult<BrandVO>> queryBrand(BrandQueryDTO brandQueryDTO) {
        PageAndSortObj pas = DataJpaPageUtils.initPageAndSort(brandQueryDTO);
        BrandEntityQueryHelper queryHelper = BrandEntityQueryHelper.create(BrandVO.class, brandQueryDTO);
        Page<BrandVO> page = (Page<BrandVO>) brandDao.findAll(
                BrandEntityQueryHelper.getWhere(queryHelper), pas.getPageable(), BrandVO.class);
        QueryResult<BrandVO> queryResult = DataJpaPageUtils.setQueryResult(page, brandQueryDTO);
        return DubboResponse.SUCCESS(queryResult);
    }

    /**
     * 不分页查询品牌
     * @param brandQueryDTO
     * @return
     */
    @Override
    public DubboResponse<QueryResult<BrandVO>> getBrandList(BrandQueryDTO brandQueryDTO) {
        BrandEntityQueryHelper queryHelper = BrandEntityQueryHelper.create(BrandVO.class, brandQueryDTO);
        List<BrandVO> data = brandDao.findAll(BrandEntityQueryHelper.getWhere(queryHelper), BrandVO.class);
        QueryResult<BrandVO> queryResult = DataJpaPageUtils.setQueryResult(data, (long) data.size());
        return DubboResponse.SUCCESS(queryResult);
    }

    /**
     * 根据Id查询品牌
     *
     * @param brandIds
     * @return
     */
    @Override
    public List<BrandSimpleVO> queryBrandSimpleByIds(List<Long> brandIds) {
        BrandEntityQueryHelper queryHelper = BrandEntityQueryHelper.create(BrandSimpleVO.class);
        queryHelper.setInField("id");
        queryHelper.setWithIn(true);
        queryHelper.setInValues(brandIds);
        List<BrandSimpleVO> data = brandDao.findAll(BrandEntityQueryHelper.getWhere(queryHelper), BrandSimpleVO.class);
        return data;
    }
}
