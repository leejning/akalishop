package com.akali.provider.goods.service;

import com.akali.common.code.CommonCode;
import com.akali.common.dto.goods.base.SaleOptionDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;
import com.akali.provider.goods.api.SaleOptionService;
import com.akali.provider.goods.bean.PmsSaleOption;
import com.akali.provider.goods.dao.BaseSaleOptionDao;
import com.akali.provider.goods.queryhelper.SaleOptionEntityQueryHelper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

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
        PmsSaleOption pmsSaleOption = new PmsSaleOption(saleOptionDTO);
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
}
