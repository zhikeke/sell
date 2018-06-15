package com.ke.sell.dao;

import com.ke.sell.model.ProductCategory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProductCategoryMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(ProductCategory record);

    int insertSelective(ProductCategory record);

    ProductCategory selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(ProductCategory record);

    int updateByPrimaryKey(ProductCategory record);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypes(@Param("categoryTypeList") List<Integer> categoryTypeList);

    int getCountByNameAndId(@Param("categoryName") String categoryName, @Param("categoryId") Integer categoryId);

    int getCountByCategoryTypeAndId(@Param("categoryType") Integer categoryType, @Param("categoryId") Integer categoryId);

    ProductCategory findByCategoryType(@Param("categoryType") Integer categoryType);
}