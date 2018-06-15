package com.ke.sell.dao;

import com.ke.sell.model.OrderDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderDetailMapper {
    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);

    List<OrderDetail> selectByOrderId(@Param("orderId") String orderId);
}