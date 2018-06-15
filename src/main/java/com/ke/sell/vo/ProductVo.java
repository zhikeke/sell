package com.ke.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import java.util.List;

/**
 * @Author: Mo
 * @Date: Created in  2018/6/14 17:03
 */
@Data
public class ProductVo {

    @JsonProperty("name")  //TODO: JSON 格式问题
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVo> productInfoVoList;
}
