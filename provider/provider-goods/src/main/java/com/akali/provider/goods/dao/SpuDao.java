package com.akali.provider.goods.dao;

import com.akali.common.utils.MapperUtils;
import com.akali.provider.goods.bean.PmsSpu;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName SpuDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/12 0012
 * @Version V1.0
 **/
public interface SpuDao extends PagingAndSortingRepository<PmsSpu,Long> {
    public static void main(String[] args) throws Exception {
        Map<Long, Long> longLongMap = new HashMap<>();
        longLongMap.put(3L,5L);
        longLongMap.put(1L,4L);
        System.out.println(MapperUtils.obj2json(longLongMap));
    }
}
