package com.ke.sell.vo;
// TODO:
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import java.math.BigDecimal;

/**
 * @Author: Mo
 * @Date: Created in  2018/6/14 17:07
 */
@Data
public class ProductInfoVo {
    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
