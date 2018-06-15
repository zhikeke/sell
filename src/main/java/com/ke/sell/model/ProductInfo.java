package com.ke.sell.model;

import com.ke.sell.enums.ProductStatusEnum;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {
    private Integer id;

    /** 商品Id. */
    private String productId;

    /** 商品名称. */
    private String productName;

    /** 商品价格. */
    private BigDecimal productPrice;

    /** 商品库存. */
    private Integer productStock;

    /** 商品描述. */
    private String productDescription;

    /** 商品图片链接 */
    private String productIcon;

    /** 商品类目. */
    private Integer categoryType;

    /** 商品状态 */
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

}