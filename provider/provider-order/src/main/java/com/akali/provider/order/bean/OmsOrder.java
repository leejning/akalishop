package com.akali.provider.order.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName OmsOrder
 * @Description: TODO
 * @Author Administrator
 * @Date 2019/11/29 0029
 * @Version V1.0
 **/
@Data
@Entity
@Table(name = "oms_order")
public class OmsOrder {
    @Id
    private String id;
    /**
     * 会员id
     */
    private String memberId;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date createTime;
    /**
     * 会员名
     */
    private String memberUsername;
    /**
     * 总价
     */
    private BigDecimal totalAmount;
    /**
     * 支付方式
     */
    private int payType;
    /**
     * 支付状态
     */
    private int status;
    /**
     * 订单类型
     */
    private int orderType;
    /**
     * 物流公司
     */
    private String deliveryCompany;
    /**
     * 快递编号
     */
    private String deliveryNum;
    /**
     * 收货人
     */
    private String receiverName;
    /**
     * 收货人电话
     */
    private String receiverPhone;
    /**
     * 省
     */
    private String receiverProvince;
    /**
     * 市
     */
    private String receiverCity;
    /**
     * 区
     */
    private String receiverRegion;
    /**
     * 详细地址
     */
    private String receiverDetailAddress;
    /**
     * 留言
     */
    private String note;
    /**
     * 收货确认
     */
    private int confirmStatus;
    /**
     * 支付时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date paymentTime;
    /**
     * 发货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date deliveryTime;
    /**
     * 收货时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date receiveTime;
    /**
     * 商品评价时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date commentTime;
}
