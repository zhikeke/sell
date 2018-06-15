package com.ke.sell.param;

import com.ke.sell.enums.OrderStatusEnum;
import com.ke.sell.enums.PayStatusEnum;
import com.ke.sell.model.OrderDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.util.List;

/**
 * @Author: Mo
 * @Date: Created in  2018/6/15 10:48
 */
@Getter
@Setter
@ToString
public class OrderParam {
    /** 买家名字. */
    @NotBlank(message = "买家名字不能为空!!!")
    private String buyerName;

    /** 买家电话. */
    @NotBlank(message = "买家电话号码不能为空!!!")
    @Pattern(regexp = "^1[34578]\\d{9}$", message = "请输入正确的手机号码!!!")
    private String buyerPhone;

    /** 买家地址. */
    @NotBlank(message = "买家地址不能为空!!!")
    private String buyerAddress;

    /** 买家微信openid. */
    @NotBlank(message = "买家微信openid不能为空!!!")
    private String buyerOpenid;

    /** 购买的商品列表 */
    @NotEmpty(message = "购买商品不能为空!!!")
    private List<OrderDetail> orderDetailList;

    /** 订单状态.0 ：新建 */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();

    /** 支付状态.0 ：未支付 */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
}
