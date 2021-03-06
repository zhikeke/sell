package com.ke.sell.service.impl;

import com.google.common.base.Preconditions;
import com.ke.sell.dao.ProductInfoMapper;
import com.ke.sell.dto.CartDTO;
import com.ke.sell.param.ProductInfoParam;
import com.ke.sell.enums.ProductStatusEnum;
import com.ke.sell.exception.ParamException;
import com.ke.sell.model.ProductCategory;
import com.ke.sell.model.ProductInfo;
import com.ke.sell.service.CategoryService;
import com.ke.sell.service.ProductService;
import com.ke.sell.util.BeanVaildator;
import com.ke.sell.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Author: Mo
 * @Date: Created in  2018/6/14 14:56
 */
@Service
@Slf4j
public class ProudctServiceImpl implements ProductService {
    @Autowired
    private ProductInfoMapper productInfoMapper;
    @Autowired
    private CategoryService categoryService;

    @Override
    public ProductInfo findOne(String productId) {
        if (StringUtils.isBlank(productId)) {
            log.info("商品Id为空!!!");
            throw new ParamException("商品Id为空!!!");
        }
        return productInfoMapper.selectByPrimaryKey(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
         return productInfoMapper.findUpAll(ProductStatusEnum.UP.getCode());
    }

    @Override
    public List<ProductInfo> findAll() {
        return productInfoMapper.findAll();
    }

    @Override
    public void save(ProductInfoParam param) {
        BeanVaildator.check(param);
        if (checkExistByProductIdAndId(param.getProductId(), param.getId())) {
            log.info("商品Id已存在!!!, param:{}", JsonMapper.obj2String(param));
            throw new ParamException("商品Id已存在!!!");
        }

        if (checkExistByProductNameAndId(param.getProductName(), param.getId())) {
            log.info("商品名称已存在!!!, param:{}", JsonMapper.obj2String(param));
            throw new ParamException("商品名称已存在!!!");
        }

        ProductCategory productCategory = categoryService.findByCategoryType(param.getCategoryType());
        Preconditions.checkNotNull(productCategory, "商品类目编号不存在");

       ProductInfo productInfo = ProductInfo.builder().productName(param.getProductName())
               .productId(param.getProductId()).productPrice(param.getProductPrice())
               .productIcon(param.getProductIcon()).productStock(param.getProductStock())
               .productDescription(param.getProductDescription()).productStatus(param.getProductStatus())
               .categoryType(param.getCategoryType()).build();
        productInfo.setCreateTime(new Date());
        productInfo.setUpdateTime(new Date());

        productInfoMapper.insertSelective(productInfo);
    }

    @Override
    public void update(ProductInfoParam param) {
        BeanVaildator.check(param);
        ProductInfo beforeProduct = productInfoMapper.selectByPrimaryKey(param.getProductId());

        Preconditions.checkNotNull(beforeProduct, "待更新的商品不存在!!!");

        if (checkExistByProductIdAndId(param.getProductId(), param.getId())) {
            log.info("商品Id已存在!!!, param:{}", JsonMapper.obj2String(param));
            throw new ParamException("商品Id已存在!!!");
        }

        if (checkExistByProductNameAndId(param.getProductName(), param.getId())) {
            log.info("商品名称已存在!!!, param:{}", JsonMapper.obj2String(param));
            throw new ParamException("商品名称已存在!!!");
        }

        ProductCategory productCategory = categoryService.findByCategoryType(param.getCategoryType());
        Preconditions.checkNotNull(productCategory, "商品类目编号不存在");

        ProductInfo productInfo = ProductInfo.builder().id(param.getId()).productName(param.getProductName())
                .productId(param.getProductId()).productPrice(param.getProductPrice())
                .productIcon(param.getProductIcon()).productStock(param.getProductStock())
                .productDescription(param.getProductDescription()).productStatus(param.getProductStatus())
                .categoryType(param.getCategoryType()).build();
        productInfo.setCreateTime(new Date());
        productInfo.setUpdateTime(new Date());

        productInfoMapper.updateByPrimaryKeySelective(productInfo);
    }

    @Override
    public void delete(String productId) {
        if (StringUtils.isBlank(productId)) {
            log.info("商品Id为空!!!");
            throw new ParamException("商品Id为空!!!");
        }
        productInfoMapper.deleteByPrimaryKey(productId);
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(cartDTO.getProductId());
            Preconditions.checkNotNull(productInfo, "商品不存在!!!");

            Integer res = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (res < 0) {
                log.info("【减少库存】 商品库存不足!!! param:{}", JsonMapper.obj2String(cartDTO));
                throw new ParamException("【减少库存】 商品库存不足!!!");
            }
            productInfo.setProductStock(res);
            productInfoMapper.updateByPrimaryKeySelective(productInfo);
        }
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO : cartDTOList) {
            ProductInfo productInfo = productInfoMapper.selectByPrimaryKey(cartDTO.getProductId());
            Preconditions.checkNotNull(productInfo, "商品不存在!!!");

            Integer res = productInfo.getProductStock() + cartDTO.getProductQuantity();

            productInfo.setProductStock(res);
            productInfoMapper.updateByPrimaryKeySelective(productInfo);
        }
    }

    private boolean checkExistByProductIdAndId(String productId, Integer id) {
        return productInfoMapper.getCountByProductIdAndId(productId, id) > 0;
    }

    private boolean checkExistByProductNameAndId(String productName, Integer id) {
        return productInfoMapper.getCountByProductNameAndId(productName, id) > 0;
    }


}
