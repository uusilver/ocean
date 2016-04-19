package com.tmind.ocean.controller;

import com.google.gson.Gson;
import com.tmind.ocean.entity.*;
import com.tmind.ocean.model.UserTo;
import com.tmind.ocean.service.BatchService;
import com.tmind.ocean.service.ProductService;
import com.tmind.ocean.service.UserAccountService;
import com.tmind.ocean.util.UniqueKeyGenerator;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lijunying on 15/11/21.
 */
@Controller
@RequestMapping("chanpin")
public class ProductController {

    @Resource(name = "productService")
    private ProductService productService;

    @Resource(name = "batchService")
    private BatchService batchService;

    @Resource(name = "userAccountService")
    private UserAccountService userAccountService;


    @RequestMapping("/createProduct")
    public String createProductFromCode(@ModelAttribute("UserProductMetaEntity)") UserProductMetaEntity product,
                                        HttpServletRequest req, HttpServletResponse response){
        UserTo user = LoginController.getLoginUser(req);
        product.setUser_id(user.getUserId());
        product.setProduct_id(strLize(UniqueKeyGenerator.generateShortUuid()));
        product.setQrcode_total_no(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        product.setUpdate_time(sdf.format(new Date()));
        productService.createUserProducet(product);
        System.out.println("新产品创建成功");
        return "chanpin/code";
    }

    @RequestMapping("/createProduct4Edit")
    public String createProduct4Edit(UserProductMetaEntity product,
                                     HttpServletRequest req, HttpServletResponse response){
        UserTo user = LoginController.getLoginUser(req);
        product.setUser_id(user.getUserId());
        product.setProduct_id(strLize(UniqueKeyGenerator.generateShortUuid()));
        product.setQrcode_total_no(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        product.setUpdate_time(sdf.format(new Date()));
        productService.createUserProducet(product);
        System.out.println("新产品创建成功,绑定了批次码");
        return "/chanpin/success";
    }

    @RequestMapping(value="/queryProductData",method = RequestMethod.POST)
    @ResponseBody
    public  String queryProductData(@RequestParam String aoData, HttpServletRequest req){
        JSONArray jsonarray = JSONArray.fromObject(aoData);

        String sEcho = null;
        int iDisplayStart = 0; // 起始索引
        int iDisplayLength = 0; // 每页显示的行数

        for (int i = 0; i < jsonarray.size(); i++) {
            JSONObject obj = (JSONObject) jsonarray.get(i);
            if (obj.get("name").equals("sEcho"))
                sEcho = obj.get("value").toString();

            if (obj.get("name").equals("iDisplayStart"))
                iDisplayStart = obj.getInt("value");

            if (obj.get("name").equals("iDisplayLength"))
                iDisplayLength = obj.getInt("value");
        }

        // 生成20条测试数据
        List<String[]> lst = new ArrayList<String[]>();
        List<UserProductMetaEntity> productList = productService.queryProductInfo(LoginController.getLoginUser(req).getUserId(),iDisplayStart,iDisplayLength);
        for (int i = 0; i < productList.size(); i++) {
            String relatedBatch = null;
            relatedBatch = "<button class=\"addBatch\">添加批次号</button>";

            String[] d = {
                    strLize(productList.get(i).getId()),
                    strLize(productList.get(i).getProduct_id()),
                    strLize(productList.get(i).getProduct_name()),
                    strLize(productList.get(i).getProduct_category()),
                    relatedBatch,
                    strLize(productList.get(i).getQrcode_total_no()),
                    strLize(productList.get(i).getUpdate_time()),
                    "<button class=\"edit\">编辑</button> <button class=\"qrcode\">查看品牌码库</button>  <button class=\"delete\">删除</button>"
            };

            lst.add(d);
        }

        JSONObject getObj = new JSONObject();
        getObj.put("sEcho", sEcho);// 不知道这个值有什么用,有知道的请告知一下
        getObj.put("iTotalRecords", lst.size());//实际的行数
        getObj.put("iTotalDisplayRecords", productService.getProductMetaTotalNo(LoginController.getLoginUser(req).getUserId()));//显示的行数,这个要和上面写的一样
        getObj.put("aaData", lst);//要以JSON格式返回
        return getObj.toString();
    }

    @RequestMapping(value = "/deleteProduct/{productId}", method = RequestMethod.GET)
    public @ResponseBody String deleteProduct4ProductId(@PathVariable String productId, HttpServletRequest req){
        if(productService.deleteProduct4ProductId(LoginController.getLoginUser(req).getUserId(),productId)){
            return "success";
        }else{
            return "failed";
        }

    }

    @RequestMapping(value = "/queryProductById/{productId}", method = RequestMethod.GET)
    public @ResponseBody String queryProductById(@PathVariable String productId,HttpServletRequest req){
        UserProductMetaEntity m = productService.queryProductInfoById(LoginController.getLoginUser(req).getUserId(),productId);
        return new Gson().toJson(m);
    }

    @RequestMapping(value = "/batchExistOrNot/{productId}", method = RequestMethod.GET)
    public @ResponseBody String batchExistOrNot(@PathVariable String productId,HttpServletRequest req){
        if (batchService.queryBatchInfoByProductId(LoginController.getLoginUser(req).getUserId(),productId)){
            return "have";
        }else{
            return "not";
        }
    }

    @RequestMapping(value="/updateProductById", method = RequestMethod.POST)
    public  @ResponseBody String updateProductById(UserProductMetaEntity productEntityFake, HttpServletRequest req){
        UserProductMetaEntity realProductEntity = productService.queryProductInfoById(LoginController.getLoginUser(req).getUserId(),productEntityFake.getProduct_id());
        realProductEntity.setProduct_name(productEntityFake.getProduct_name());
        realProductEntity.setProduct_category(productEntityFake.getProduct_category());
        realProductEntity.setProduct_desc(productEntityFake.getProduct_desc());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        realProductEntity.setUpdate_time(sdf.format(new Date()));
        if (productService.updateProductById(realProductEntity))
            return "success";
        else
            return "failed";
    }

    @RequestMapping(value="/createNewProductBatch", method = RequestMethod.POST)
    public  @ResponseBody String createNewProductBatch(@RequestParam String productId,
                                                       @RequestParam String batchNo,
                                                       @RequestParam String batchParams,
                                                       @RequestParam String sellArthor,
                                                       @RequestParam String selectedTemplate,
                                                       HttpServletRequest req){
        UserProductMetaEntity productMetaInfo = productService.queryProductInfoById(LoginController.getLoginUser(req).getUserId(),productId);
        UserProductEntity mUserProductEntity = new UserProductEntity();
        mUserProductEntity.setProduct_id(productMetaInfo.getProduct_id());
        mUserProductEntity.setRelate_batch(batchNo);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mUserProductEntity.setUpdate_time(sdf.format(new Date()));
        mUserProductEntity.setUser_id(productMetaInfo.getUser_id());
        mUserProductEntity.setQrcode_total_no(0);
        mUserProductEntity.setAdvice_temp(productMetaInfo.getAdvice_temp());
        mUserProductEntity.setBatch_params(batchParams);
        mUserProductEntity.setSellArthor(sellArthor);
        mUserProductEntity.setAdvice_temp(selectedTemplate);
        if (productService.createNewProductBatch(mUserProductEntity)) {
            return productMetaInfo.getProduct_id();
        }
        else
            return "failed";
    }


    //创建二维码访问地址
    @RequestMapping(value="/createQrcode", method = RequestMethod.POST)
    public  @ResponseBody String createQrcode(UserProductEntity productEntityFake, HttpServletRequest req){
        //普通用户只可以一批次生成10000个二维码
        //VIP用户则可以生成多个，但是导出需要通知后台
        if(productEntityFake.getQrcode_total_no()>10000){
            String userType = LoginController.getLoginUser(req).getUser_type();
            String t = userType.split(":")[1];
            if(t.equals("a")){
                return "limit";
            }else  if(t.equals("vip")){
                //如果是VIP则可以生成二维码，但是导出必须通过sql--->联系厂家
                if(userAccountService.judgeCanPrintQrCodeOrNot(Integer.valueOf(productEntityFake.getQrcode_total_no()), LoginController.getLoginUser(req).getUserId())) {
                    Integer userId = LoginController.getLoginUser(req).getUserId();
                    userType = LoginController.getLoginUser(req).getUser_type();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    //创建二维码
                    if(productService.createQrcode(productEntityFake, LoginController.getLoginUser(req).getUserId(), userType)){
                        userAccountService.updateUserAccountForConsuming(Integer.valueOf(productEntityFake.getQrcode_total_no()), userId);
                        productService.updateProductAndBatchQrTotalAccount(userId, productEntityFake.getProduct_id(), productEntityFake.getRelate_batch(), Integer.valueOf(productEntityFake.getQrcode_total_no()));
                        //更新用户消费纪录;
                        UserAccountEntity account = userAccountService.queryAccountForDisplay(userId);
                        UserAccountOptEntity opt = new UserAccountOptEntity();
                        opt.setUser_id(userId);
                        opt.setAccount_consume(Integer.valueOf(productEntityFake.getQrcode_total_no()));
                        opt.setCurrent_left(account.getCurrency());
                        opt.setUpdate_time(sdf.format(new Date()));
                        opt.setReason("二维码生产");
                        userAccountService.purchaseQrAmountAndKeepRecordIntoOptTable(opt);
                        return "vip";
                    }

                }else{
                    return "charge";
                }
            }
        }else{
            //正常生成
            if(userAccountService.judgeCanPrintQrCodeOrNot(Integer.valueOf(productEntityFake.getQrcode_total_no()), LoginController.getLoginUser(req).getUserId())) {
                Integer userId = LoginController.getLoginUser(req).getUserId();
                String userType = LoginController.getLoginUser(req).getUser_type();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //创建二维码
                if(productService.createQrcode(productEntityFake, LoginController.getLoginUser(req).getUserId(), userType)){
                    userAccountService.updateUserAccountForConsuming(Integer.valueOf(productEntityFake.getQrcode_total_no()), userId);
                    productService.updateProductAndBatchQrTotalAccount(userId, productEntityFake.getProduct_id(), productEntityFake.getRelate_batch(), Integer.valueOf(productEntityFake.getQrcode_total_no()));
                    //更新用户消费纪录;
                    UserAccountEntity account = userAccountService.queryAccountForDisplay(userId);
                    UserAccountOptEntity opt = new UserAccountOptEntity();
                    opt.setUser_id(userId);
                    opt.setAccount_consume(Integer.valueOf(productEntityFake.getQrcode_total_no()));
                    opt.setCurrent_left(account.getCurrency());
                    opt.setUpdate_time(sdf.format(new Date()));
                    opt.setReason("二维码生产");
                    userAccountService.purchaseQrAmountAndKeepRecordIntoOptTable(opt);

                    return "success";
                }

            }else{
                return "charge";
            }
        }


        return null;
    }

    //给产品添加新的批次号
    @RequestMapping(value="/createNewBatchForOldProduct", method=RequestMethod.POST)
    public @ResponseBody String createNewBatchForOldProduct(UserProductMetaEntity productEntityFake, HttpServletRequest req){
        UserProductMetaEntity realProductEntity = new UserProductMetaEntity();
        realProductEntity.setUser_id(LoginController.getLoginUser(req).getUserId());
        realProductEntity.setProduct_name(productEntityFake.getProduct_name());
        realProductEntity.setProduct_id(productEntityFake.getProduct_id());
        realProductEntity.setProduct_category(productEntityFake.getProduct_category());
        realProductEntity.setProduct_desc(productEntityFake.getProduct_desc());
//        realProductEntity.setRelate_batch(productEntityFake.getRelate_batch());
        realProductEntity.setQrcode_total_no(0);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        realProductEntity.setUpdate_time(sdf.format(new Date()));
        if (productService.createUserProducet(realProductEntity)) {
            return "success";
        }
        else
            return "failed";
    }

    //查询产品名称是否存在
    @RequestMapping(value="/checkProductNameExisted", method = RequestMethod.POST)
    public @ResponseBody String checkProductNameExisted(@RequestParam String productName, HttpServletRequest request){
        if(productService.checkProductNameExist(LoginController.getLoginUser(request).getUserId(),productName)){
            return "failed";
        }else{
            return "success";
        }
    }

    //产品产品批次是否存在
    @RequestMapping(value="/checkProductBatchExist", method = RequestMethod.POST)
    public @ResponseBody String checkProductBatchExist(@RequestParam String productId, @RequestParam String batchNo, HttpServletRequest request){
        if(productService.checkProductBatchExist(LoginController.getLoginUser(request).getUserId(), productId, batchNo)){
            return "failed";
        }else{
            return "success";
        }
    }

    //获得用户的参数属性
    @RequestMapping(value="/loadUserParams", method = RequestMethod.GET)
    public @ResponseBody String loadUserParams(HttpServletRequest request){
        List<UserParamsEntity> list = productService.loadUserParams(LoginController.getLoginUser(request).getUserId());
        return new Gson().toJson(list);
    }

    //获得用户的模版属性
    @RequestMapping(value="/loadUserAdviceTemplate", method = RequestMethod.GET)
    public @ResponseBody String loadUserAdviceTemplate(HttpServletRequest request){
        Integer userId = LoginController.getLoginUserId(request);
        List<UserAdviceTemplateEntity> adviceTemplates = batchService.queryBatch(userId);
        return generateOptions("a", adviceTemplates); //设置a,通用模版为默认模版
    }

    //private
    private String strLize(Object obj){
        return String.valueOf(obj);
    }


    //将一个list转换成一个下拉列表框
    //TODO 变成一个范型通用方法
    private  String generateOptions(String selectValue, List<UserAdviceTemplateEntity> templates){
        StringBuilder sb = new StringBuilder();
        sb.append("<select class='tempClass'>");
        for(UserAdviceTemplateEntity temp : templates){
            if(temp.getTemplate_name().equalsIgnoreCase(selectValue)){
                sb.append(" <option selected=\\\"selected\\\" value="+temp.getTemplate_name()+">"+temp.getTemplate_label()+"</option>");
            }else{
                sb.append(" <option value="+temp.getTemplate_name()+">"+temp.getTemplate_label()+"</option>");
            }
        }
        sb.append("</select>");
        return sb.toString();
    }



}

