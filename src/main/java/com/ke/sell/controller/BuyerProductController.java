package com.ke.sell.controller;

import com.ke.sell.common.JsonData;
import com.ke.sell.model.ProductCategory;
import com.ke.sell.model.ProductInfo;
import com.ke.sell.service.CategoryService;
import com.ke.sell.service.ProductService;
import com.ke.sell.vo.ProductInfoVo;
import com.ke.sell.vo.ProductVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品
 * @Author: Mo
 * @Date: Created in  2018/6/15 8:55
 */
@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;


    @GetMapping("/list")
    public JsonData list() {
        // 查询所有上架的商品
        List<ProductInfo> productInfoList = productService.findUpAll();

        // 查询所有类目(lambda)
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(productInfo -> productInfo.getCategoryType())
                .collect(Collectors.toList());

        List<ProductVo> productVoList = new ArrayList<>();
        List<ProductCategory> productCategoryList = categoryService.findByCategoryTypes(categoryTypeList);

        for (ProductCategory productCategory : productCategoryList) {
            ProductVo productVo = new ProductVo();
            productVo.setCategoryName(productCategory.getCategoryName());
            productVo.setCategoryType(productCategory.getCategoryType());

            List<ProductInfoVo> productInfoVoList = new ArrayList<>();
            for (ProductInfo productInfo : productInfoList) {

                System.out.println(productInfo.getCategoryType().equals(productCategory.getCategoryType()));
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())) {
                    ProductInfoVo productInfoVo = new ProductInfoVo();
                    BeanUtils.copyProperties(productInfo, productInfoVo);
                    productInfoVoList.add(productInfoVo);
                }
            }
            productVo.setProductInfoVoList(productInfoVoList);
            productVoList.add(productVo);
        }

      return JsonData.success(productVoList);
    }
}
