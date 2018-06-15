package com.ke.sell.dao;

import com.ke.sell.model.ProductInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductInfoMapper {
    int deleteByPrimaryKey(String productId);

    int insert(ProductInfo record);

    int insertSelective(ProductInfo record);

    ProductInfo selectByPrimaryKey(String productId);

    int updateByPrimaryKeySelective(ProductInfo record);

    int updateByPrimaryKey(ProductInfo record);

    List<ProductInfo> findUpAll(@Param("status") Integer status);

    List<ProductInfo> findAll();

    int getCountByProductIdAndId(@Param("productId") String productId, @Param("id") Integer id);

    int getCountByProductNameAndId(@Param("productName") String productName, @Param("id") Integer id);
}