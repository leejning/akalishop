package com.akali.provider.es.service;

import com.akali.common.code.CommonCode;
import com.akali.common.dto.search.ProductDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.provider.es.bean.Product;
import com.akali.provider.es.dao.ProductDao;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

/**
 * @ClassName SearchServiceImpl
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/24 0024
 * @Version V1.0
 **/
@Service(version = "1.0.0")
public class SearchServiceImpl implements SearchService{
    @Autowired
    private ProductDao productDao;

    @Override
    public DubboResponse<ProductDTO> queryProductById(Long spuId) {
        Optional<Product> opt = productDao.findById(spuId);
        if(!opt.isPresent()){
            DubboResponse.FAIL(CommonCode.FAIL);
        }
        ProductDTO productDTO = new ProductDTO();
        BeanUtils.copyProperties(opt.get(),productDTO);
        return DubboResponse.SUCCESS(productDTO);
    }
}
