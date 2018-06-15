package com.ke.sell.controller;

import com.google.common.collect.Lists;
import com.ke.sell.common.JsonData;
import com.ke.sell.dto.ProductCategoryDTO;
import com.ke.sell.dto.ProductInfoDTO;
import com.ke.sell.model.ProductInfo;
import com.ke.sell.service.CategoryService;
import com.ke.sell.service.ProductService;
import com.ke.sell.vo.ProductInfoVo;
import com.ke.sell.vo.ProductVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author: Mo
 * @Date: Created in  2018/6/13 16:50
 */
@Controller
@Slf4j
public class indexController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    @RequestMapping("/index")
    @ResponseBody
    public void index() {
        log.info("kekekda");
    }


    @RequestMapping("/test")
    @ResponseBody
    public JsonData test() {
        JsonData jsonData = new JsonData();
        ProductInfoVo productInfoVo = new ProductInfoVo();
        ProductVo productVo = new ProductVo();
        productVo.setCategoryName("jeje");
        productVo.setCategoryType(1);
        productVo.setProductInfoVoList(Arrays.asList(productInfoVo));
        jsonData.setMsg("成功");
        jsonData.setCode(0);
        jsonData.setData(Arrays.asList(productVo));
       return jsonData;
    }

}
