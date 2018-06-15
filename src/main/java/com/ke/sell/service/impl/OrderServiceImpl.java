package com.ke.sell.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.ke.sell.dao.OrderDetailMapper;
import com.ke.sell.dao.OrderMasterMapper;
import com.ke.sell.dto.CartDTO;
import com.ke.sell.dto.OrderDTO;
import com.ke.sell.enums.OrderStatusEnum;
import com.ke.sell.enums.PayStatusEnum;
import com.ke.sell.exception.ParamException;
import com.ke.sell.model.OrderDetail;
import com.ke.sell.model.OrderMaster;
import com.ke.sell.model.ProductInfo;
import com.ke.sell.page.PageQuery;
import com.ke.sell.param.OrderParam;
import com.ke.sell.service.OrderService;
import com.ke.sell.service.ProductService;
import com.ke.sell.util.BeanVaildator;
import com.ke.sell.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.ke.sell.util.KeyUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: Mo
 * @Date: Created in  2018/6/15 11:06
 */
@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailMapper orderDetailMapper;
    @Autowired
    private OrderMasterMapper orderMasterMapper;


    @Override
    @Transactional
    public String create(OrderParam orderParam) {
        BeanVaildator.check(orderParam);

        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(0);

        for (OrderDetail orderDetail : orderParam.getOrderDetailList()) {
            // 查询商品(数量，价格)
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            Preconditions.checkNotNull(productInfo, "【新建订单】 购买的商品不存在!!!");

            if (orderDetail.getProductQuantity() > productInfo.getProductStock()) {
                log.info("【新建订单】 购买商品库存不足!!!, param:{}", JsonMapper.obj2String(orderDetail));
                throw new ParamException("【新建订单】 商品库存不足!!!");
            }

            // 计算总价
            orderAmount = productInfo.getProductPrice().
                    multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);

            // 订单详情入库
            OrderDetail orderDetail1 = OrderDetail.builder().detailId(KeyUtil.genUniqueKey())
                    .orderId(orderId).productIcon(productInfo.getProductIcon()).productId(productInfo.getProductId())
                    .productName(productInfo.getProductName()).productPrice(productInfo.getProductPrice())
                    .productQuantity(orderDetail.getProductQuantity()).build();


            orderDetail1.setCreateTime(new Date());
            orderDetail1.setUpdateTime(new Date());

            // 订单详情表写入记录
            orderDetailMapper.insertSelective(orderDetail1);
        }
        // 订单表
        OrderMaster orderMaster = new OrderMaster();
        // 先拷贝后设置
        BeanUtils.copyProperties(orderParam, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setCreateTime(new Date());
        orderMaster.setUpdateTime(new Date());


        // 订单表写入记录
        orderMasterMapper.insertSelective(orderMaster);

        // 减库存
        List<CartDTO> cartDTOList = new ArrayList<>();
        orderParam.getOrderDetailList().stream().map(e-> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
        return orderId;
    }

    @Override
    public OrderDTO findOne(String openid, String orderId) {
         if (StringUtils.isBlank(openid)) {
             log.info("【查找订单】 openid 为空!!!");
             throw new ParamException("【查找订单】 openid 为空!!!");
         }

         if (StringUtils.isBlank(orderId)) {
             log.info("【查找订单】 订单Id为空!!!");
             throw new ParamException("【查找订单】 订单Id为空!!!");
         }

         OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderId);
         if (!orderMaster.getBuyerOpenid().equals(openid)) {
             log.info("【查找订单】 订单不存在!!!, openid:{}, orderId:{}", openid, orderId);
             throw new ParamException("【查找订单】 订单不存在!!!");
         }

         OrderDTO orderDTO = OrderDTO.adapt(orderMaster);

         List<OrderDetail> orderDetailList = orderDetailMapper.selectByOrderId(orderId);
         orderDTO.setOrderDetailList(orderDetailList);
         return orderDTO;
    }


    @Override
    public List<OrderDTO> findList(String buyerOpenId, PageQuery page) {
         BeanVaildator.check(page);

         List<OrderMaster> orderMasters = orderMasterMapper.findList(buyerOpenId, page);

         List<OrderDTO> orderDTOList = Lists.newArrayList();
         for (OrderMaster orderMaster : orderMasters) {
             OrderDTO orderDTO = OrderDTO.adapt(orderMaster);
             orderDTOList.add(orderDTO);
         }

         return orderDTOList;
    }

    @Override
    @Transactional
    public void cancel(String openid, String orderId) {
        if (StringUtils.isBlank(openid)) {
            log.info("【取消订单】 openid 为空!!!");
            throw new ParamException("【取消订单】 openid 为空!!!");
        }

        if (StringUtils.isBlank(orderId)) {
            log.info("【取消订单】 订单Id为空!!!");
            throw new ParamException("【取消订单】 订单Id为空!!!");
        }

        OrderMaster orderMaster = orderMasterMapper.selectByPrimaryKey(orderId);
        if (!orderMaster.getBuyerOpenid().equals(openid)) {
            log.info("【取消订单】 订单不存在!!!, openid:{}, orderId:{}", openid, orderId);
            throw new ParamException("【取消订单】 订单不存在!!!");
        }

        if (!orderMaster.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.info("【取消订单】 当前订单不可取消");
            throw new ParamException("【取消订单】 当前订单不可取消");
        }

        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        orderMasterMapper.updateByPrimaryKeySelective(orderMaster);

        List<OrderDetail> orderDetailList = orderDetailMapper.selectByOrderId(orderId);
        // 增加库存
        if (CollectionUtils.isEmpty(orderDetailList)) {
            log.info("【取消订单】 订单中无商品详情，orderId:{}", orderId);
            throw new ParamException("【取消订单】 订单中无商品详情");
        }
        List<CartDTO> cartDTOList = orderDetailList.stream()
                .map(e-> new CartDTO(e.getProductId(), e.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

       // 如果已支付，退款
        if (orderMaster.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            // TODO: 退款
        }

    }

    @Override
    public void finish(OrderDTO orderDTO) {
          // 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW)) {
            log.error("【完结订单】 订单状态不正确, orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new ParamException("【完结订单】 订单状态不正确!!!");
        }
        // 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMasterMapper.updateByPrimaryKeySelective(orderMaster);
    }

    @Override
    public void paid(OrderDTO orderDTO) {
        // 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW)) {
            log.error("【完结订单】 订单状态不正确, orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new ParamException("【完结订单】 订单状态不正确!!!");
        }

        // 判断支付状态
        if (!orderDTO.getOrderStatus().equals(PayStatusEnum.WAIT)) {
            log.error("【完结订单】 订单支付状态不正确, orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new ParamException("【完结订单】 订单支付状态不正确!!!");
        }

       // 修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMasterMapper.updateByPrimaryKeySelective(orderMaster);

    }
}
