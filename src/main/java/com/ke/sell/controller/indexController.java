package com.ke.sell.controller;

import com.ke.sell.common.JsonData;
import com.ke.sell.dto.OrderDTO;
import com.ke.sell.model.OrderDetail;
import com.ke.sell.param.OrderParam;
import com.ke.sell.service.CategoryService;
import com.ke.sell.service.OrderService;
import com.ke.sell.service.ProductService;
import com.ke.sell.vo.ProductInfoVo;
import com.ke.sell.vo.ProductVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Mo
 * @Date: Created in  2018/6/13 16:50
 */
@Controller
@Slf4j
public class indexController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderService orderService;

    @RequestMapping("/index")
    @ResponseBody
    public void index() {
        log.info("kekekda");
    }


    @RequestMapping("/test")
    @ResponseBody
    public JsonData test() {
        OrderParam orderParam = new OrderParam();
        orderParam.setBuyerName("keke");
        orderParam.setBuyerAddress("广州市");
        orderParam.setBuyerOpenid("abc1234");
        orderParam.setBuyerPhone("13719118572");

        List<OrderDetail> orderDetails = new ArrayList<>();
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setProductId("2222");
        orderDetail.setProductQuantity(20);
        orderDetails.add(orderDetail);

        orderParam.setOrderDetailList(orderDetails);


       return JsonData.success(orderService.create(orderParam));
    }

}
