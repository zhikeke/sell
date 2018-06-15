package com.ke.sell.service.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.ke.sell.dao.ProductCategoryMapper;
import com.ke.sell.param.ProductCategoryParam;
import com.ke.sell.exception.ParamException;
import com.ke.sell.model.ProductCategory;
import com.ke.sell.service.CategoryService;
import com.ke.sell.util.BeanVaildator;
import com.ke.sell.util.JsonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @Author: Mo
 * @Date: Created in  2018/6/14 11:39
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {
   @Autowired
   private ProductCategoryMapper productCategoryMapper;

    @Override
    public ProductCategory findOne(Integer categoryId) {
         if (categoryId == null) {
             log.error("类别Id为空!!!");
             throw new ParamException(("类别Id为空!!!"));
         }
         return productCategoryMapper.selectByPrimaryKey(categoryId);
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryMapper.findAll();
    }

    @Override
    public ProductCategory findByCategoryType(Integer categoryType) {
        if (categoryType == null) {
            log.error("类目编号为空!!!");
            throw new ParamException("类目编号为空!!!");
        }
        return productCategoryMapper.findByCategoryType(categoryType);
    }

    @Override
    public List<ProductCategory> findByCategoryTypes(List<Integer> categoryTypeList) {
        if (CollectionUtils.isEmpty(categoryTypeList)) {
            log.error("类目编号为空!!!");
            return Lists.newArrayList();
        }
        return productCategoryMapper.findByCategoryTypes(categoryTypeList);
    }

    @Override
    public void save(ProductCategoryParam param) {
        BeanVaildator.check(param);
        if (checkExistByCategoryNameAndId(param.getCategoryName(), param.getCategoryId())) {
            log.info("该类别已存在!!!, param:{}", JsonMapper.obj2String(param));
            throw new ParamException("该类别已存在!!!");
        }

        if (checkExistBycategoryTypeAndId(param.getCategoryType(), param.getCategoryId())) {
            log.info("该类别编号已存在!!!, param:{}", JsonMapper.obj2String(param));
            throw new ParamException("该类别类别编号已存在!!!");
        }

        ProductCategory productCategory = ProductCategory.builder().categoryName(param.getCategoryName())
                .categoryType(param.getCategoryType()).build();
        productCategory.setCreateTime(new Date());
        productCategory.setUpdateTime(new Date());

        productCategoryMapper.insertSelective(productCategory);
    }

    @Override
    public void update(ProductCategoryParam param) {
        BeanVaildator.check(param);

        ProductCategory beforeOroductCategory = productCategoryMapper.selectByPrimaryKey(param.getCategoryId());
        Preconditions.checkNotNull(beforeOroductCategory, "待更新对象不存在!!!");

        if (checkExistByCategoryNameAndId(param.getCategoryName(), param.getCategoryId())) {
            log.info("该类别已存在!!!, param:{}", JsonMapper.obj2String(param));
            throw new ParamException("该类别已存在!!!");
        }

        if (checkExistBycategoryTypeAndId(param.getCategoryType(), param.getCategoryId())) {
            log.info("该类别编号已存在!!!, param:{}", JsonMapper.obj2String(param));
            throw new ParamException("该类别类别编号已存在!!!");
        }

        ProductCategory productCategory = ProductCategory.builder().categoryId(param.getCategoryId()).categoryName(param.getCategoryName())
                .categoryType(param.getCategoryType()).build();
        productCategory.setUpdateTime(new Date());

        productCategoryMapper.updateByPrimaryKeySelective(productCategory);
    }

    @Override
    public void delete(Integer categoryId) {
        if (categoryId == null) {
            log.info("需要删除的类目Id不能为空!!!");
            throw new ParamException("需要删除的类目Id不能为空!!!");
        }
        productCategoryMapper.deleteByPrimaryKey(categoryId);
    }

    private boolean checkExistByCategoryNameAndId(String categoryName, Integer categoryId) {
         return productCategoryMapper.getCountByNameAndId(categoryName, categoryId) > 0;
    }

    private boolean checkExistBycategoryTypeAndId(Integer categoryType, Integer categoryId) {
        return productCategoryMapper.getCountByCategoryTypeAndId(categoryType, categoryId) > 0;
    }

}
