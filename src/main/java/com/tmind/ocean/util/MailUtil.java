package com.tmind.ocean.util;

import com.sun.istack.internal.logging.Logger;
import org.apache.log4j.spi.LoggerFactory;

/**
 * Created by lijunying on 15/12/24.
 *
 */

//TODO 换成我们315kc.com的官方邮箱, 或者改成用户可配置的邮箱地址
public class MailUtil {

    private  static Logger log = Logger.getLogger(MailUtil.class);

    public static boolean sendMail(String to, String subject, String content){
        String smtp = "smtp.163.com";
        String from = "13851483034@163.com";
        String username4Mail="13851483034@163.com";
        String password4Mail="19850924";
        if(Mail.send(smtp, from, to, subject, content, username4Mail, password4Mail)){
            return  true;
        }else{
            return false;
        }
    }
}
