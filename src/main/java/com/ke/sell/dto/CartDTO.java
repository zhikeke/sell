package com.ke.sell.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Mo
 * @Date: Created in  2018/6/15 13:47
 */
@Getter
@Setter
@AllArgsConstructor
public class CartDTO {
    /** 商品id. */
    private String productId;

    /** 购买数量. */
    private Integer productQuantity;


}
