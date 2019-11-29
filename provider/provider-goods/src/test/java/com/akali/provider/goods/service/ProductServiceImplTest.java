package com.akali.provider.goods.service;

import com.akali.common.dto.goods.sku.SkuCreateDTO;
import com.akali.common.dto.goods.sku.SkuDTO;
import com.akali.common.dto.goods.spu.SpuDetaiModifyDTO;
import com.akali.common.model.response.DubboResponse;
import com.akali.common.model.response.QueryResult;
import com.akali.provider.goods.api.ProductService;
import com.google.common.collect.Maps;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Reference(version = "1.0.0")
    private ProductService productService;

    @Test
    public void createProductSku() throws Exception {
        SkuCreateDTO skuCreateDTO = new SkuCreateDTO();
        skuCreateDTO.setSpuId(1L);
        skuCreateDTO.setTitle("锤子(smartisan) 坚果Pro3 8GB+256GB 白色 骁龙855PLUS 4800万四摄 UFS3.0 全网通双卡双待 全面屏游戏手机");
        skuCreateDTO.setMainImage("http://img14.360buyimg.com/n1/s450x450_jfs/t1/55959/5/14592/40628/5dbadae0Ed5facac2/6121e53fe1a724bb.jpg");
        skuCreateDTO.setPrice(319900L);
        Map<Long, Long> map = Maps.newHashMap();
        map.put(1L,2L);
        map.put(2L,4L);
        map.put(5L,8L);
        skuCreateDTO.setOwnSpec(map);
        productService.createProductSku(skuCreateDTO);
    }

    @Test
    public void checkExsitsSku(){
        SkuCreateDTO skuCreateDTO = new SkuCreateDTO();
        skuCreateDTO.setSpuId(1L);
        Map<Long, Long> map = Maps.newHashMap();
        map.put(1L,2L);
        map.put(5L,8L);
        map.put(2L,4L);
        skuCreateDTO.setOwnSpec(map);
        System.out.println(productService.checkExistsSku(skuCreateDTO));
    }

    @Test
    public void updateSpuDetail(){
        SpuDetaiModifyDTO spuDetaiModifyDTO = new SpuDetaiModifyDTO();
        spuDetaiModifyDTO.setSpuId(1L);
        spuDetaiModifyDTO.setDescription("22222");
        spuDetaiModifyDTO.setPackingList("22222");
        productService.updateSpuDetail(spuDetaiModifyDTO);
    }

    @Test
    public void queryProductSkus(){
        DubboResponse<QueryResult<SkuDTO>> response = productService.queryProductSkus(1L);
    }
}
