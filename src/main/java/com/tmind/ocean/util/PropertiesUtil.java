package com.tmind.ocean.util;

import java.io.*;
import java.util.Properties;

/**
 * Created by lijunying on 15/12/5.
 */
public class PropertiesUtil {
    /**
     * 实现对Java配置文件Properties的读取、写入与更新操作
     */

        //属性文件的路径
        static String profilepath="domain.properties";
        /**
         * 采用静态方法
         */
        private static Properties props = new Properties();
        static {
            try {
                props.load(PropertiesUtil.class.getResourceAsStream("domain.properties"));
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

        /**
         * 读取属性文件中相应键的值
         * @param key
         *            主键
         * @return String
         */
        public static String getKeyValue(String key) {
            return props.getProperty(key);
        }

        /**
         * 根据主键key读取主键的值value
         * @param filePath 属性文件路径
         * @param key 键名
         */
        public static String readValue(String filePath, String key) {
            Properties props = new Properties();
            try {
                InputStream in = new BufferedInputStream(new FileInputStream(
                        filePath));
                props.load(in);
                String value = props.getProperty(key);
                System.out.println(key +"键的值是："+ value);
                return value;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        /**
         * 更新（或插入）一对properties信息(主键及其键值)
         * 如果该主键已经存在，更新该主键的值；
         * 如果该主键不存在，则插件一对键值。
         * @param keyname 键名
         * @param keyvalue 键值
         */
        public static void writeProperties(String keyname,String keyvalue) {
            try {
                // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
                // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
                OutputStream fos = new FileOutputStream(profilepath);
                props.setProperty(keyname, keyvalue);
                // 以适合使用 load 方法加载到 Properties 表中的格式，
                // 将此 Properties 表中的属性列表（键和元素对）写入输出流
                props.store(fos, "Update '" + keyname + "' value");
            } catch (IOException e) {
                System.err.println("属性文件更新错误");
            }
        }

        /**
         * 更新properties文件的键值对
         * 如果该主键已经存在，更新该主键的值；
         * 如果该主键不存在，则插件一对键值。
         * @param keyname 键名
         * @param keyvalue 键值
         */
        public void updateProperties(String keyname,String keyvalue) {
            try {
                props.load(new FileInputStream(profilepath));
                // 调用 Hashtable 的方法 put，使用 getProperty 方法提供并行性。
                // 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
                OutputStream fos = new FileOutputStream(profilepath);
                props.setProperty(keyname, keyvalue);
                // 以适合使用 load 方法加载到 Properties 表中的格式，
                // 将此 Properties 表中的属性列表（键和元素对）写入输出流
                props.store(fos, "Update '" + keyname + "' value");
            } catch (IOException e) {
                System.err.println("属性文件更新错误");
            }
        }
        //测试代码
        public static void main(String[] args) {
            readValue("mail.properties", "MAIL_SERVER_PASSWORD");
            writeProperties("MAIL_SERVER_INCOMING", "327@qq.com");
            System.out.println("操作完成");
        }


}
