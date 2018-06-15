package com.ke.sell.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
public class OrderDetail {
    /** 订单详情Id. */
    private String detailId;

    /** 订单Id. */
    private String orderId;

    /** 商品Id. */
    private String productId;

    /** 商品名称. */
    private String productName;

    /** 商品价格. */
    private BigDecimal productPrice;

    /** 商品数量. */
    private Integer productQuantity;

    /** 商品图片链接. */
    private String productIcon;

    /** 创建时间. */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

}