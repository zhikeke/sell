package com.ke.sell.model;

import com.ke.sell.enums.OrderStatusEnum;
import com.ke.sell.enums.PayStatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class OrderMaster {
    /** 订单Id. */
    private String orderId;

    /** 买家名字. */
    private String buyerName;

    /** 买家电话. */
    private String buyerPhone;

    /** 买家地址. */
    private String buyerAddress;

    /** 买家微信openid. */
    private String buyerOpenid;

    /** 订单总金额. */
    private BigDecimal orderAmount;

    /** 订单状态.0 ：新建 */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态.0 ：未支付 */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    private Date createTime;

    private Date updateTime;


}