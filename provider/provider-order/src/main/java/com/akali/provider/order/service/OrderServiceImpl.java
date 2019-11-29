package com.akali.provider.order.service;

import com.akali.provider.order.dao.OrderDao;
import com.akali.provider.order.dao.OrderItemDao;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @ClassName OrderServiceImpl
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/29 0029
 * @Version V1.0
 **/
@Service(version = "1.0.0")
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;




}
