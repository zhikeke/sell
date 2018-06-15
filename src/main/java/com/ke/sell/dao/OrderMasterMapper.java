package com.ke.sell.dao;

import com.ke.sell.model.OrderDetail;
import com.ke.sell.model.OrderMaster;
import com.ke.sell.page.PageQuery;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.Pattern;
import java.util.List;

public interface OrderMasterMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(OrderMaster record);

    int insertSelective(OrderMaster record);

    OrderMaster selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(OrderMaster record);

    int updateByPrimaryKey(OrderMaster record);

    List<OrderMaster> findList(@Param("openid") String buyerOpenId, @Param("page") PageQuery page);
}