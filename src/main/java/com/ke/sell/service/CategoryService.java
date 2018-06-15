package com.ke.sell.service;

import com.ke.sell.dto.ProductCategoryDTO;
import com.ke.sell.model.ProductCategory;

import java.util.List;

/**
 * 商品类目
 * @Author: Mo
 * @Date: Created in  2018/6/14 11:15
 */
public interface CategoryService {
    /**
     * 根据Id 查找商品
     * @param categoryId
     * @return
     */
    ProductCategory findOne(Integer categoryId);

    /**
     * 查询所有的商品
     * @return
     */
    List<ProductCategory> findAll();

    /**
     * 根据类目类型查找商品
     * @param categoryTypeList 类目类型列表
     * @return
     */
    List<ProductCategory> findByCategoryTypes(List<Integer> categoryTypeList);

    /**
     * 根据类目类型查找商品
     * @param categoryType 类目类型
     * @return
     */
    ProductCategory findByCategoryType(Integer categoryType);

    /**
     * 添加新类目
     * @param productCategoryDTO
     * @return
     */
    void save(ProductCategoryDTO productCategoryDTO);

    /**
     * 修改类目
     * @param productCategoryDTO
     * @return
     */
    void update(ProductCategoryDTO productCategoryDTO);

    /**
     * 删除类目
     * @param categoryId 类目Id
     */
   void delete(Integer categoryId);

}
