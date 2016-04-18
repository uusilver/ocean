package com.tmind.ocean.controller;

import com.google.gson.Gson;
import com.tmind.ocean.entity.UserAccountEntity;
import com.tmind.ocean.entity.UserAccountOptEntity;
import com.tmind.ocean.entity.UserEntity;
import com.tmind.ocean.service.UserAccountService;
import com.tmind.ocean.util.SecurityUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lijunying on 15/11/29.
 */
@Controller
@RequestMapping("zhanghao")
public class UserAccountController {

    @Resource(name="userAccountService")
    private UserAccountService userAccountService;

    @RequestMapping(value="/queryUserAccount", method = RequestMethod.GET)
    @ResponseBody
    public String queryUserAccount(HttpServletRequest req){
        UserAccountEntity model = userAccountService.queryAccountForDisplay(LoginController.getLoginUser(req).getUserId());
        return model.getAccount()+"-"+model.getCurrency();
    }

    @RequestMapping(value="/purChaseQrAccount/{no4Purchase}", method = RequestMethod.GET)
    @ResponseBody
    public String purChaseQrAccount(@PathVariable String no4Purchase, HttpServletRequest req){
        Integer userId = LoginController.getLoginUser(req).getUserId();
        //再一次检查
        UserAccountEntity model = userAccountService.queryAccountForDisplay(LoginController.getLoginUser(req).getUserId());
        Integer purchaseAmount = Integer.valueOf(no4Purchase);
        //购买的二维码数量所需的金额
        BigDecimal tempBig = BigDecimal.valueOf(purchaseAmount);
        //实际需要金额
        BigDecimal realBig = tempBig.multiply(BigDecimal.valueOf(0.1));
        //账户余额要大于，可购买的
        if(model.getCurrency().compareTo(realBig)==1||model.getCurrency().compareTo(realBig)==0){
            //开始购买操作，先减款
            model.setCurrency(model.getCurrency().subtract(realBig));
            model.setAccount(model.getAccount()+purchaseAmount);
            //更新用户购买纪录
            UserAccountOptEntity opt = new UserAccountOptEntity();
            opt.setAccount_purchase(purchaseAmount);
            opt.setCurrent_left(model.getCurrency().subtract(realBig));
            opt.setUser_id(userId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            opt.setUpdate_time(sdf.format(new Date()));
            opt.setReason("充值");
            userAccountService.purchaseQrAmountAndKeepRecordIntoOptTable(model,opt);
            return "success";
        }else{
            return "failed";
        }

    }

    @RequestMapping(value="/queryUserDetail", method = RequestMethod.POST)
    @ResponseBody
    public  String queryUserDetail(@RequestParam String aoData, HttpServletRequest req){
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
        List<UserAccountOptEntity> list = userAccountService.queryUserOpt(LoginController.getLoginUser(req).getUserId(),iDisplayStart,iDisplayLength );
        for (int i = 0; i < list.size(); i++) {

            String[] d = {
                    strLize(list.get(i).getId()),
                    strLize(list.get(i).getUpdate_time()),
                    strLize(list.get(i).getAccount_purchase()),
                    strLize(list.get(i).getAccount_consume()),
                    strLize(list.get(i).getCurrent_left()),
                    strLize(list.get(i).getReason())

            };

            lst.add(d);
        }

        JSONObject getObj = new JSONObject();
        getObj.put("sEcho", sEcho);// 不知道这个值有什么用,有知道的请告知一下
        getObj.put("iTotalRecords", lst.size());//实际的行数
        getObj.put("iTotalDisplayRecords", userAccountService.getUserOptTotalNo(LoginController.getLoginUser(req).getUserId()));//显示的行数,这个要和上面写的一样

        getObj.put("aaData", lst);//要以JSON格式返回


        return getObj.toString();
    }


    //获得用户基本信息
    @RequestMapping(value="/queryUserInfo")
    public @ResponseBody String queryUserInfo(HttpServletRequest request){
        UserEntity userEntity = userAccountService.queryUserInfo(LoginController.getLoginUser(request).getUserId());
        if(userEntity!=null){
            return new Gson().toJson(userEntity);
        }else{
            return "failed";
        }
    }


    //更新用户基本信息n
    @RequestMapping(value="/updateUserInfo", method = RequestMethod.POST)
    public @ResponseBody String updateUserInfo(@RequestParam String oldPass, @RequestParam String newPass, @RequestParam String email, @RequestParam String telno, HttpServletRequest request){
        UserEntity userEntity = userAccountService.queryUserInfo(LoginController.getLoginUser(request).getUserId());
        String result = null;
        if(userEntity!=null){
            //只是密码的修改
            if(oldPass.length()!=0 && newPass.length()!=0){
                if(!SecurityUtil.encodeWithMd5Hash(oldPass).equals(userEntity.getPassword())){
                    result = "wrongpass";
                }else{
                    userEntity.setPassword(SecurityUtil.encodeWithMd5Hash(newPass));
                    userEntity.setUser_email(email);
                    userEntity.setUser_telno(telno);
                    if(userAccountService.updateUserInfo(userEntity))
                        result = "success";
                }
            }
            else{
                userEntity.setUser_email(email);
                userEntity.setUser_telno(telno);
                if(userAccountService.updateUserInfo(userEntity))
                    result = "success";
            }
        }else{
            result = "failed";
        }
        return result;
    }

    private String strLize(Object obj){
        return String.valueOf(obj);
    }
}
