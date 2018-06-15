package com.ke.sell.param;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: Mo
 * @Date: Created in  2018/6/14 11:35
 */
@Getter
@Setter
public class ProductCategoryParam {

    private Integer categoryId;

    /** 类目名称. */
    @NotBlank(message = "类目名称不能为空!!!")
    private String categoryName;

    /** 类目编号. */
    @NotNull(message = "类目类型编号不能为空!!!")
    private Integer categoryType;

}
