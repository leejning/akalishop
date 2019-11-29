package com.akali.provider.order.bean;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/**
 * @ClassName OmsOrderItem
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/29 0029
 * @Version V1.0
 **/
@Data
@Entity
@Table(name = "oms_order_item")
public class OmsOrderItem {
    private String id;
    private String orderId;
    private String spuId;
    private String productPic;
    private String productName;
    private String brand;
    private String price;
    private String skuId;
    private String categoryId;
    private List<String> saleAttrNames;
}
