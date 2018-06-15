package com.ke.sell.param;

import com.ke.sell.enums.ProductStatusEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author: Mo
 * @Date: Created in  2018/6/14 14:50
 */
@Getter
@Setter
@ToString
public class ProductInfoParam {
    private Integer id;

    /** 商品Id. */
    @NotBlank(message = "商品Id不能为空!!!")
    private String productId;

    /** 商品名称. */
    @NotBlank(message = "商品名称不能为空!!!")
    private String productName;

    /** 商品价格. */
    @NotNull(message = "商品价格不能为空!!!")
    private BigDecimal productPrice;

    /** 商品库存. */
    @NotNull(message = "商品库存不能为空!!!")
    private Integer productStock;

    /** 商品描述. */
    @Length(max = 64, message = "商品描述应在64字内!!!")
    private String productDescription;

    /** 商品图片链接 */
    @NotBlank(message = "商品图片链接不能为空!!!")
    private String productIcon;

    /** 商品类目. */
    @NotNull(message = "商品类目编号不能为空!!!")
    private Integer categoryType;

    /** 商品状态 */
    private Integer productStatus = ProductStatusEnum.UP.getCode();
}
