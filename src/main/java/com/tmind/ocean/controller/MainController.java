package com.tmind.ocean.controller;

import com.google.gson.Gson;
import com.tmind.ocean.entity.UserAccountEntity;
import com.tmind.ocean.model.MainPageModelTo;
import com.tmind.ocean.model.UserVistorMapModelTo;
import com.tmind.ocean.service.LoadSystemInfoService;
import com.tmind.ocean.service.UserAccountService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by lijunying on 15/11/20.
 */
@Controller
@RequestMapping("mainController")
public class MainController {

    Logger log = Logger.getLogger(MainController.class);

    @Resource(name = "loadSysInfo")
    private LoadSystemInfoService loadSystemInfoService;

    @Resource(name = "userAccountService")
    private UserAccountService userAccountService;

    /*
     * @desc: 加载用户登陆后的首页信息
     */
    @RequestMapping(value = "/getSystemInfo", method = RequestMethod.GET)
    public
    @ResponseBody
    String getSystemInfo(HttpServletRequest req) {
        String message = loadSystemInfoService.loadSysInfo();
        UserAccountEntity userAccount = userAccountService.queryAccountForDisplay(LoginController.getLoginUserId(req));
        MainPageModelTo model = new MainPageModelTo();
        model.setMessage(message);
        //设置显示在首页的信息
        return new Gson().toJson(setMainPageInfo(model, userAccount));
    }

    /*
     * @desc: 获得登陆首页的地区统计表信息
     */
    @RequestMapping(value = "/getStatisticInfo", method = RequestMethod.GET)
    public
    @ResponseBody
    String getStatisticInfo(HttpServletRequest req) {
        //TODO 当前伪造信息，创建统计表，从表中获取信息
        List<UserVistorMapModelTo> list = new ArrayList<UserVistorMapModelTo>();
        UserVistorMapModelTo m1 = new UserVistorMapModelTo("江苏", 45, "#4572a7");
        UserVistorMapModelTo m2 = new UserVistorMapModelTo("北京", 25, "#aa4643");
        UserVistorMapModelTo m3 = new UserVistorMapModelTo("上海", 17, "#89a54e");
        UserVistorMapModelTo m4 = new UserVistorMapModelTo("北京", 13, "#CDAF95");
        list.add(m1);
        list.add(m2);
        list.add(m3);
        list.add(m4);

        return new Gson().toJson(list);
    }

    @RequestMapping(value = "/getUserName", method = RequestMethod.GET)
    public @ResponseBody String getUserName(HttpServletRequest request){
        return LoginController.getLoginUser(request).getUsername();
    }

    @RequestMapping(value = "/logout")
    public @ResponseBody String logout(HttpServletRequest request){
        log.info(LoginController.getLoginUser(request).getUsername()+"在"+new Date()+"退出系统");
        request.getSession().removeAttribute("userInSession");
        return "success";
    }

    //privates
    private MainPageModelTo setMainPageInfo(MainPageModelTo model, UserAccountEntity userAccount ){
        model.setAccount(userAccount.getAccount());
        model.setQr_total_user(userAccount.getQr_total_user());
        model.setScan_total_user(userAccount.getScan_total_user());
        model.setWarning_qr_code_no(userAccount.getWarning_qr_code_no());
        return model;
    }


}
