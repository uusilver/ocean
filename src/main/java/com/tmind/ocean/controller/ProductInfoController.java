package com.tmind.ocean.controller;

import com.tmind.ocean.entity.ProductShowInfoEntity;
import com.tmind.ocean.service.ProductInfoService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by lijunying on 15/12/7.
 */
@Controller
@RequestMapping("wuliao")
public class ProductInfoController {

    @Resource(name="productInfoService")
    private ProductInfoService productInfoService;

    @RequestMapping(value = "/queryProductInfo/{productId}/{batchno}", method = RequestMethod.GET)
    @ResponseBody
    public String queryProductInfo(@PathVariable("productId") String productId, @PathVariable("batchno") String batchno, HttpServletRequest req){
        Integer userId = LoginController.getLoginUser(req).getUserId();
        ProductShowInfoEntity info = productInfoService.queryInfoBySpecificInfo(productId,batchno,userId);
        if(info==null){
            return "empty";
        }else{
            return info.getProduce_date()+","+info.getProduce_address()+","+info.getSell_area()+","+info.getSell_author();
        }
    }

    @RequestMapping(value = "/createOrUpdateProductInfo", method = RequestMethod.POST)
    @ResponseBody
    public String createOrUpdateProductInfo(ProductShowInfoEntity info, HttpServletRequest req){
        info.setUser_id(LoginController.getLoginUser(req).getUserId());
        if(productInfoService.createProductInfo(info)){
            return "success";
        }else{
            return  "failed";
        }
    }
}
