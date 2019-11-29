package com.akali.provider.order.dao;

import com.akali.config.jpa.ExtendedJpaRepositoryApi;
import com.akali.provider.order.bean.OmsOrder;

/**
 * @ClassName OrderDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/29 0029
 * @Version V1.0
 **/
public interface OrderDao extends ExtendedJpaRepositoryApi<OmsOrder,Long> {
}
