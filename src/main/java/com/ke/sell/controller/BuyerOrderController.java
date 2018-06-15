package com.ke.sell.controller;

import com.ke.sell.common.JsonData;
import com.ke.sell.dto.OrderDTO;
import com.ke.sell.page.PageQuery;
import com.ke.sell.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @Author: Mo
 * @Date: Created in  2018/6/15 14:51
 */
@Controller
@RequestMapping("/buyer/order")
public class BuyerOrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 获取某个用户的所有订单
     * @param openId 用户openid
     * @param pageQuery 分页参数
     * @return
     */
   @GetMapping("/list")
   @ResponseBody
    public JsonData list(@RequestParam("openid") String openId, PageQuery pageQuery) {
        List<OrderDTO> orderDTOList = orderService.findList(openId, pageQuery);
        return JsonData.success(orderDTOList);
    }

    /**
     * 查询订单详情
     * @param openid  用户openid
     * @param orderId  订单id
     * @return
     */
    @GetMapping("/detail")
    @ResponseBody
    public JsonData detail(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId) {
        OrderDTO orderDTO = orderService.findOne(openid, orderId);
        return JsonData.success(orderDTO);
    }

    /**
     * 查询订单详情
     * @param openid  用户openid
     * @param orderId  订单id
     * @return
     */
    @GetMapping("/cancel")
    @ResponseBody
    public JsonData cancel(@RequestParam("openid") String openid, @RequestParam("orderId") String orderId) {
        orderService.cancel(openid, orderId);
        return JsonData.success();
    }



}
