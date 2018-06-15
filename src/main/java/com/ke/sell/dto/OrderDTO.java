package com.ke.sell.dto;

import com.google.common.collect.Lists;
import com.ke.sell.model.OrderDetail;
import com.ke.sell.model.OrderMaster;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @Author: Mo
 * @Date: Created in  2018/6/15 10:54
 */
@Getter
@Setter
@ToString
public class OrderDTO extends OrderMaster {
    private List<OrderDetail> orderDetailList;

    public static OrderDTO adapt(OrderMaster orderMaster) {
        OrderDTO dto = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, dto);
        return dto;
    }
}
