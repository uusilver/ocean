package com.tmind.ocean.controller;

import com.google.gson.Gson;
import com.tmind.ocean.model.UserReportModel;
import com.tmind.ocean.util.HibernateUtil;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.List;

/**
 * Created by lijunying on 16/9/18.
 */
@SuppressWarnings("ALL")
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

        //正常扫码数
        StringBuilder normalScanBuilder = new StringBuilder();
        normalScanBuilder.append("[");

        //异常扫码数
        StringBuilder warningScanBuilder = new StringBuilder();
        warningScanBuilder.append("[");

        //平均数
        StringBuilder averageScanBuilder = new StringBuilder();
        averageScanBuilder.append("[");

        int pieNormalScan = 0;
        int pieWarningScan = 0;

        int index = 0;
        for(String s : last6Months){
            //获取sql
            SQLQuery query = HibernateUtil.getSessionFactory().openSession().createSQLQuery("select normal_scan_times, warning_scan_times, average from user_statistics where user_id = ? and year_month_str = ?");
            query.setParameter(0, userId);
            query.setParameter(1, s);
            List<Object[]> list = query.list();
            if(list.size()==0){
                normalScanBuilder.append("0").append(",");
                warningScanBuilder.append("0").append(",");
                averageScanBuilder.append("0").append(",");
            }
            else{
                Object[] objects = list.get(0);
                normalScanBuilder.append(objects[0]).append(",");
                warningScanBuilder.append(objects[1]).append(",");
                averageScanBuilder.append(objects[2]).append(",");
                pieNormalScan+=Integer.valueOf(String.valueOf(objects[0]));
                pieWarningScan+=Integer.valueOf(String.valueOf(objects[1]));


            }

                yearMonthBuilder.append("'").append(s).append("'").append(",");

            index++;
        }
        yearMonthBuilder.append("]");
        normalScanBuilder.append("]");
        warningScanBuilder.append("]");
        averageScanBuilder.append("]");
        userReportModel.setMonth(yearMonthBuilder.toString());

        //抬头
        userReportModel.setTitle(last6Months[0]+"月～"+last6Months[5]+"月扫码分析统计");

        userReportModel.setPieChartNormalScan(pieNormalScan);
        userReportModel.setPieChartWarningScan(pieWarningScan);

        userReportModel.setNormalScan(normalScanBuilder.toString());
        userReportModel.setWarningScan(warningScanBuilder.toString());
        userReportModel.setAverageScan(averageScanBuilder.toString());

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
