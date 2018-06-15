package com.ke.sell.enums;

import lombok.Getter;

/**
 * 订单状态
 * @Author: Mo
 * @Date: Created in  2018/6/14 10:49
 */
@Getter
public enum  OrderStatusEnum {
    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "取消");

    private Integer code;
    private String message;

    OrderStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
