package com.akali.provider.goods.service;

import com.akali.common.model.response.DubboResponse;
import com.akali.provider.goods.api.AttributionService;
import com.akali.provider.goods.bean.PmsBaseAttrOption;
import com.akali.provider.goods.dao.BaseAttrOptionDao;
import com.akali.provider.goods.dto.CategoryAttrInfoDTO;
import com.akali.provider.goods.querydto.AttrOptionQueryDTO;
import org.apache.dubbo.config.annotation.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AttributionServiceImplTest {

    @Reference(version = "1.0.0")
    AttributionService attributionService;
    @Autowired
    private BaseAttrOptionDao baseAttrOptionDao;

    @Test
    public void findOptionByAttrIds(){
//        List<PmsBaseAttrOption> byAttrIds = baseAttrOptionDao.findByAttrIds("2,");
//        System.out.println(byAttrIds.toString());
        Long[] ids = new Long[]{1l,2l};
        List<Long> longs = Arrays.asList(ids);
        List<PmsBaseAttrOption> optionsByAttrIds =
                baseAttrOptionDao.findAllHasInCondition(longs, AttrOptionQueryDTO.getWhere());
        System.out.println(optionsByAttrIds.toString());

    }

    @Test
    public void queryAllAttributeInfoByCid(){
        DubboResponse<CategoryAttrInfoDTO> res = attributionService.queryAllAttributeInfoByCid(1l);
        System.out.println(res);
    }

}
