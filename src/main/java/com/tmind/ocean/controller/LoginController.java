package com.tmind.ocean.controller;

import com.google.code.kaptcha.Producer;
import com.tmind.ocean.entity.AgentUser;
import com.tmind.ocean.entity.UserEntity;
import com.tmind.ocean.model.UserTo;
import com.tmind.ocean.service.UserAccountService;
import com.tmind.ocean.service.UserValidationService;
import com.tmind.ocean.util.Constants;
import com.tmind.ocean.util.MailUtil;
import com.tmind.ocean.util.UniqueKeyGenerator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by lijunying on 15/11/13.
 */

@Controller
@RequestMapping("login")
public class LoginController {

    Logger log = Logger.getLogger(LoginController.class);


    // Standard JSR250 Injection
    @Resource(name = "userValService")
    private UserValidationService userValidation;

    @Resource(name = "userAccountService")
    private UserAccountService userAccountService;

    //普通会员登陆
    @RequestMapping(params = "weblogin")
    public ModelAndView login(@ModelAttribute("user") UserTo userTo,
                              HttpServletRequest req) {
        UserEntity userEntity = userValidation.findUserEntity(userTo.getUsername(), userTo.getPassword());
        Map<String, Object> model = new HashMap<String, Object>();
        if (userEntity != null) {
            if (userEntity.getActive_flag() == 0) {
                model.put("error", "用户被暂停，请联系您的代理商");
                return new ModelAndView("fail", model);

            } else {
                prepareUserTo(userEntity, userTo);
                req.getSession().setAttribute("userInSession", userTo);
                String home = userEntity.getHome_page();
                if(home ==null || home.length()==0)
                    return new ModelAndView("redirect:index.html", model);
                else
                    return new ModelAndView("redirect:"+home, model);

            }
        } else {
            model.put("error", "用户名或密码错误");
            return new ModelAndView("fail", model);

        }
    }

    //代理登陆
    @RequestMapping(params = "agencyLogin")
    public String agencyLogin(@ModelAttribute("user") UserTo userTo,
                              HttpServletRequest req, HttpServletResponse response) {
        AgentUser user = userValidation.findAgentUserInDatabase(userTo.getUsername(), userTo.getPassword());
        userTo.setUserId(user.getId());
        req.getSession().setAttribute("userInSession", userTo);
        return "agency/index";
    }

    @RequestMapping(value = "getNewPass")
    public
    @ResponseBody
    String getNewPass(@RequestParam String username, @RequestParam String email) {
        UserEntity userEntity = userValidation.findUserEntityByUsername(username, email);
        String result = null;
        if (userEntity != null) {
            String newPass = UniqueKeyGenerator.generateShortUuid();
            if (MailUtil.sendMail(userEntity.getUser_email(), "密码重置", "您好, 新密码是:" + newPass)) {
                userEntity.setPassword(newPass);
                if (userAccountService.updateUserInfo(userEntity))
                    result = Constants.SUCCESS;
            }
        } else {
            result = Constants.FAILED;
        }
        return result;
    }

    //生成Kaptcha验证码
    @Autowired
    private Producer captchaProducer = null;
    @RequestMapping(value = "/code")
    public ModelAndView getCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        String code = (String)session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        System.out.println("******************验证码是: " + code + "******************");

        response.setDateHeader("Expires", 0);

        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        // return a jpeg
        response.setContentType("image/jpeg");

        // create the text for the image
        String capText = captchaProducer.createText();

        // store the text in the session
        session.setAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY, capText);

        // create the image with the text
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();

        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }

    //验证码校验
    @RequestMapping(value = "validateJCode", method = RequestMethod.POST)
    public @ResponseBody String validateJCode(@RequestParam String jCode, HttpServletRequest request){
        String jCodeInSession = (String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if(jCodeInSession!=null&&jCodeInSession.equals(jCode)){
            return "success";
        }else{
            return "failed";
        }
    }

    // Get Login user session
    public static UserTo getLoginUser(HttpServletRequest req) {
        return (UserTo) req.getSession().getAttribute("userInSession");
    }

    // 获得session中的userId
    public static Integer getLoginUserId(HttpServletRequest req) {
        UserTo userTo = (UserTo) req.getSession().getAttribute("userInSession");
        return userTo.getUserId();
    }

    //privates

    //将必要的信息存入userTo中间类，返回
    private UserTo prepareUserTo(UserEntity userEntity, UserTo userTo){
        userTo.setUserId(userEntity.getId());
        userTo.setQuery_qrcode_table(userEntity.getQuery_qrcode_table());
        userTo.setUser_type(userEntity.getUser_type());
        userTo.setUser_email(userEntity.getUser_email());
        userTo.setUser_factory_name(userEntity.getUser_factory_name());
        userTo.setUser_factory_address(userEntity.getUser_factory_address());
        userTo.setUser_contact_person_name(userEntity.getUser_contact_person_name());
        userTo.setLottery_ability_flag(userEntity.getLottery_ability_flag());
        userTo.setUser_telno(userEntity.getUser_telno());
        return userTo;
    }

}
