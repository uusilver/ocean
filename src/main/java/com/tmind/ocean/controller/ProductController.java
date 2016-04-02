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
    public String createProductFromCode(@ModelAttribute("M_USER_PRODUCT_META)") M_USER_PRODUCT_META product,
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
    public String createProduct4Edit(M_USER_PRODUCT_META product,
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
        List<M_USER_PRODUCT_META> productList = productService.queryProductInfo(LoginController.getLoginUser(req).getUserId(),iDisplayStart,iDisplayLength);
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
        M_USER_PRODUCT_META m = productService.queryProductInfoById(LoginController.getLoginUser(req).getUserId(),productId);
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
    public  @ResponseBody String updateProductById(M_USER_PRODUCT_META productEntityFake, HttpServletRequest req){
        M_USER_PRODUCT_META realProductEntity = productService.queryProductInfoById(LoginController.getLoginUser(req).getUserId(),productEntityFake.getProduct_id());
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
    public  @ResponseBody String createNewProductBatch(@RequestParam String productId, @RequestParam String batchNo, @RequestParam String batchParams, @RequestParam String sellArthor, HttpServletRequest req){
        M_USER_PRODUCT_META productMetaInfo = productService.queryProductInfoById(LoginController.getLoginUser(req).getUserId(),productId);
        M_USER_PRODUCT_ENTITY mUserProductEntity = new M_USER_PRODUCT_ENTITY();
        mUserProductEntity.setProduct_id(productMetaInfo.getProduct_id());
        mUserProductEntity.setRelate_batch(batchNo);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        mUserProductEntity.setUpdate_time(sdf.format(new Date()));
        mUserProductEntity.setUser_id(productMetaInfo.getUser_id());
        mUserProductEntity.setQrcode_total_no(0);
        mUserProductEntity.setAdvice_temp(productMetaInfo.getAdvice_temp());
        mUserProductEntity.setBatch_params(batchParams);
        mUserProductEntity.setSellArthor(sellArthor);
        if (productService.createNewProductBatch(mUserProductEntity)) {
            return productMetaInfo.getProduct_id();
        }
        else
            return "failed";
    }


    //创建二维码访问地址
    @RequestMapping(value="/createQrcode", method = RequestMethod.POST)
    public  @ResponseBody String createQrcode(M_USER_PRODUCT_ENTITY productEntityFake, HttpServletRequest req){
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
                        M_USER_ACCOUNT account = userAccountService.queryAccountForDisplay(userId);
                        M_USER_ACCOUNT_OPT opt = new M_USER_ACCOUNT_OPT();
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
                    M_USER_ACCOUNT account = userAccountService.queryAccountForDisplay(userId);
                    M_USER_ACCOUNT_OPT opt = new M_USER_ACCOUNT_OPT();
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
    public @ResponseBody String createNewBatchForOldProduct(M_USER_PRODUCT_META productEntityFake, HttpServletRequest req){
        M_USER_PRODUCT_META realProductEntity = new M_USER_PRODUCT_META();
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
            //新增批次环节不再支持二维码生成操作，注释掉一下代码，其中返回"charge"代表用户需要充值
//            Integer userId = LoginController.getLoginUser(req).getUserId();
//            //检测二维码余额是否够打印
//            if(userAccountService.judgeCanPrintQrCodeOrNot(Integer.valueOf(productEntityFake.getQrcode_total_no()), userId)){
//                //创建二维码
//                for(int i=0;i<Integer.valueOf(productEntityFake.getQrcode_total_no());i++){
//                    M_USER_QRCODE_ENTITY m_user_qrcode_entity = new M_USER_QRCODE_ENTITY();
//                    m_user_qrcode_entity.setUser_id(LoginController.getLoginUser(req).getUserId());
//                    m_user_qrcode_entity.setProduct_id(productEntityFake.getProduct_id());
//                    m_user_qrcode_entity.setProduct_batch(productEntityFake.getRelate_batch());
//                    //绑定唯一码
//                    String qrcodeQueryString = generateQRCodeString(productEntityFake.getProduct_id());
//                    m_user_qrcode_entity.setQr_query_string(qrcodeQueryString);
//                    productService.createQrcode(m_user_qrcode_entity);
//                }
//                //更新二维码创建信息
//                userAccountService.updateUserAccountForConsuming(Integer.valueOf(productEntityFake.getQrcode_total_no()), userId);
//                productService.updateProductAndBatchQrTotalAccount(userId, productEntityFake.getProduct_id(), productEntityFake.getRelate_batch(), Integer.valueOf(productEntityFake.getQrcode_total_no()));
//                //更新用户消费纪录;
//                M_USER_ACCOUNT account = userAccountService.queryAccountForDisplay(userId);
//                M_USER_ACCOUNT_OPT opt = new M_USER_ACCOUNT_OPT();
//                opt.setUser_id(userId);
//                opt.setAccount_consume(Integer.valueOf(productEntityFake.getQrcode_total_no()));
//                opt.setCurrent_left(account.getCurrency());
//                opt.setUpdate_time(sdf.format(new Date()));
//                opt.setReason("二维码生产");
//                userAccountService.purchaseQrAmountAndKeepRecordIntoOptTable(opt);
            return "success";
//            }else{
//                return "charge";
//            }
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
        List<M_USER_PARAMS_ENTITY> list = productService.loadUserParams(LoginController.getLoginUser(request).getUserId());
        return new Gson().toJson(list);
    }

    //private methods
    private String strLize(Object obj){
        return String.valueOf(obj);
    }






}

