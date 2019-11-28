package com.akali.provider.cart.dao;

import com.akali.config.mong.MongoBaseDao;
import com.akali.provider.cart.bean.OmsCartItem;

/**
 * @ClassName CartItemDao
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/25 0025
 * @Version V1.0
 **/
public interface CartItemDao extends MongoBaseDao<OmsCartItem,String> {
}
