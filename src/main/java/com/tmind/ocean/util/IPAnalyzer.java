package com.tmind.ocean.util;

import org.apache.log4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by lijunying on 15/12/17.
 */
public class IPAnalyzer {

    //用来分析ip地址，来获取物理地址
    //TODO freeapi.ipip.net有问题，需要寻找一个备份的方案
    final static int BUFFER_SIZE = 4096;

    private  static Logger log = Logger.getLogger(IPAnalyzer.class);


    public static String queryAddressByIp(String ip) {
        //http://freeapi.ipip.net/58.213.20.42
        String content = null;

        // 提交模式
        try {
            URL url = new URL("http://freeapi.ipip.net/" + ip);

            //打开restful链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestMethod("GET");//POST GET PUT DELETE
            conn.setConnectTimeout(10000);//连接超时 单位毫秒
            conn.setReadTimeout(2000);//读取超时 单位毫秒
            //读取请求返回值
            InputStream inStream = conn.getInputStream();
            content = InputStreamTOString(inStream);

        } catch (ProtocolException e) {
            log.error(e.getMessage());
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return content;
    }

    private static String InputStreamTOString(InputStream in) throws Exception {

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        while ((count = in.read(data, 0, BUFFER_SIZE)) != -1)
            outStream.write(data, 0, count);
        return new String(outStream.toByteArray(), "UTF-8");
    }
}
