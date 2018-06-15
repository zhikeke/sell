package com.ke.sell.enums;

import lombok.Getter;

/**
 * 支付状态
 * @Author: Mo
 * @Date: Created in  2018/6/14 11:03
 */
@Getter
public enum  PayStatusEnum {
    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功");

    private Integer code;
    private String message;

    PayStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
