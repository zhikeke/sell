package com.ke.sell.dao;

import com.ke.sell.model.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

public interface OrderDetailMapper {
    int insert(OrderDetail record);

    int insertSelective(OrderDetail record);
}