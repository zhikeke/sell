package com.ke.sell.enums;

import lombok.Getter;

/**
 * 商品状态
 * @Author: Mo
 * @Date: Created in  2018/6/14 10:49
 */
@Getter
public enum  ProductStatusEnum {
   UP(0, "在架"),
    DOWN(1, "下架");

   private Integer code;
   private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
