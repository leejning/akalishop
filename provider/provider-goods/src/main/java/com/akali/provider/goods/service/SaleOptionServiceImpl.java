package com.akali.provider.goods.service;

import com.akali.common.code.CommonCode;
import com.akali.common.data_help.DataJpaPageUtils;
import com.akali.common.data_help.PageAndSortObj;
import com.akali.common.dto.goods.base.SaleOptionDTO;
import com.akali.common.dto.goods.base.SaleOptionVO;
import com.akali.common.dto.query.SaleOptionQueryDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;
import com.akali.provider.goods.api.SaleOptionService;
import com.akali.provider.goods.bean.PmsSaleOption;
import com.akali.provider.goods.dao.BaseSaleOptionDao;
import com.akali.provider.goods.queryhelper.SaleOptionEntityQueryHelper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @ClassName SaleOptionServiceImpl
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/13 0013
 * @Version V1.0
 **/
@Service(version = "1.0.0")
public class SaleOptionServiceImpl implements SaleOptionService {
    @Autowired
    private BaseSaleOptionDao baseSaleOptionDao;

    /**
     * 创建某类商品销售选项
     * @param saleOptionDTO
     * @return
     */
    @Override
    public DubboResponse<Void> createSaleOption(SaleOptionDTO saleOptionDTO) {
        PmsSaleOption pmsSaleOption = new PmsSaleOption();
        pmsSaleOption.setName(saleOptionDTO.getName());
        baseSaleOptionDao.save(pmsSaleOption);
        return DubboResponse.SUCCESS(CommonCode.SUCCESS);
    }

    /**
     * 根据三级分类获取该了类商品的所有销售选项
     * @param cateId
     * @return
     */
    @Override
    public DubboResponse<QueryResult<SaleOptionDTO>> querySaleOptionByCateId(Long cateId) {
        //构建查询条件helper
        SaleOptionEntityQueryHelper queryHelper = SaleOptionEntityQueryHelper.create(SaleOptionDTO.class);
        //查询数据
        List<SaleOptionDTO> data = baseSaleOptionDao
                .findAll(SaleOptionEntityQueryHelper.getWhere(queryHelper), SaleOptionDTO.class);
        //返回结果
        return DubboResponse.SUCCESS(QueryResult.create(data,(long)data.size()));
    }

    /**
     * 搜索获取销售属性，商品选择销售属性时使用
     *
     * @param saleOptionQueryDTO
     * @return
     */
    @Override
    public DubboResponse<QueryResult<SaleOptionVO>> querySaleOption(SaleOptionQueryDTO saleOptionQueryDTO) {
        SaleOptionEntityQueryHelper queryHelper = SaleOptionEntityQueryHelper.create(SaleOptionVO.class);
        queryHelper.setName(saleOptionQueryDTO.getName());
        List<SaleOptionVO> data = baseSaleOptionDao.findAll(
                SaleOptionEntityQueryHelper.getWhere(queryHelper), SaleOptionVO.class);
        QueryResult<SaleOptionVO> queryResult = DataJpaPageUtils.setQueryResult(data, (long) data.size());
        return DubboResponse.SUCCESS(queryResult);
    }

    /**
     * 获取销售选项分页列表
     *
     * @param saleOptionQueryDTO
     * @return
     */
    @Override
    public DubboResponse<QueryResult<SaleOptionVO>> querySaleOptionPage(SaleOptionQueryDTO saleOptionQueryDTO) {
        PageAndSortObj pageAndSortObj = DataJpaPageUtils.initPageAndSort(saleOptionQueryDTO);
        SaleOptionEntityQueryHelper queryHelper = SaleOptionEntityQueryHelper.create(SaleOptionVO.class);
        queryHelper.setName(saleOptionQueryDTO.getName());
        Page<SaleOptionVO> data = (Page<SaleOptionVO>) baseSaleOptionDao.findAll(
                SaleOptionEntityQueryHelper.getWhere(queryHelper),pageAndSortObj.getPageable(), SaleOptionVO.class);
        QueryResult<SaleOptionVO> queryResult = DataJpaPageUtils.setQueryResult(data,saleOptionQueryDTO);
        return DubboResponse.SUCCESS(queryResult);
    }


}
