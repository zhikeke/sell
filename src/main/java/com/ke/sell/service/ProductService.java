package com.ke.sell.service;

import com.ke.sell.dto.ProductInfoDTO;
import com.ke.sell.model.ProductInfo;

import java.util.List;

/**
 * @Author: Mo
 * @Date: Created in  2018/6/14 14:47
 */
public interface ProductService {
    /**
     * 查询商品
     * @param productId 商品Id
     * @return
     */
    ProductInfo findOne(String productId);

    /**
     * 查询所有在架商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询所有商品列表
     * @return
     */
    List<ProductInfo> findAll();

    /**
     * 添加商品
     * @param param
     */
    void save(ProductInfoDTO param);

    /**
     * 更新商品
     * @param param
     */
    void update(ProductInfoDTO param);

    /**
     * 删除商品
     * @param productId
     */
    void delete(String productId);
}
