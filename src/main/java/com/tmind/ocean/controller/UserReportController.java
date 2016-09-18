package com.tmind.ocean.controller;

import com.google.gson.Gson;
import com.tmind.ocean.model.UserReportModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

/**
 * Created by lijunying on 16/9/18.
 */
@Controller
@RequestMapping("report")
public class UserReportController {

    @RequestMapping(value="/queryLineReport",method = RequestMethod.GET)
    public @ResponseBody
    String queryUserAllQrCodeInfo(HttpServletRequest req) {
        Integer userId = LoginController.getLoginUserId(req);

        UserReportModel userReportModel = new UserReportModel();

        //前6个月的统计数据
        String[] last6Months = getLast6Months();
        StringBuilder yearMonthBuilder = new StringBuilder();
        yearMonthBuilder.append("[");
        int index = 0;
        for(String s : last6Months){
            if(index<5)
                yearMonthBuilder.append("'").append(s).append("'").append(",");
            else
                yearMonthBuilder.append("'").append(s).append("'");
            index++;
        }
            yearMonthBuilder.append("]");
        userReportModel.setMonth(yearMonthBuilder.toString());

        //抬头
        userReportModel.setTitle(last6Months[0]+"月～"+last6Months[5]+"月扫码分析统计");

        //正常扫码数

        //异常扫码数

        //平均数


        Gson gson = new Gson();
        return gson.toJson(userReportModel);
    }

    private static String[] getLast6Months(){

        String[] last6Months = new String[6];

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+1); //要先+1,才能把本月的算进去</span>
        for(int i=0; i<6; i++){
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)-1); //逐次往前推1个月
            try {
                last6Months[5 - i] = cal.get(Calendar.YEAR) + "-" + cal.get(Calendar.MONTH);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

        return last6Months;
    }
}
