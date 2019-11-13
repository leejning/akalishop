package com.akali.provider.goods.service;

import com.akali.common.code.CommonCode;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;
import com.akali.provider.goods.api.SaleOptionService;
import com.akali.provider.goods.bean.PmsSaleOption;
import com.akali.provider.goods.dao.BaseSaleOptionDao;
import com.akali.provider.goods.dto.SaleOptionDTO;
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
        List<SaleOptionDTO> queryData = baseSaleOptionDao.findByCateId(cateId);
        if (queryData.isEmpty()) {
            //
        }
        return DubboResponse.SUCCESS(QueryResult.create(queryData,(long)queryData.size()));
    }
}
