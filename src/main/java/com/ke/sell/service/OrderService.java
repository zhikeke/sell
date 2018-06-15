package com.ke.sell.service;

import com.ke.sell.dto.OrderDTO;
import com.ke.sell.page.PageQuery;
import com.ke.sell.param.OrderParam;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * 用户订单
 * @Author: Mo
 * @Date: Created in  2018/6/15 10:47
 */
public interface OrderService {

     /** 创建订单 */
     String create(OrderParam orderParam);

     /** 查询单个订单 */
     OrderDTO findOne(String openid, String orderId);

     /** 查询订单列表 */
      List<OrderDTO> findList(String buyerOpenId, PageQuery page);

     /** 取消订单 */
     void cancel(String openid, String orderId);

     /** 完结订单 */
     void finish(OrderDTO orderDTO);

     /** 支付订单 */
     void paid(OrderDTO orderDTO);

}
