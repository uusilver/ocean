package com.tmind.ocean.service;

import com.tmind.ocean.controller.LoginController;
import com.tmind.ocean.entity.UserParamsEntity;
import com.tmind.ocean.entity.UserProductEntity;
import com.tmind.ocean.entity.UserProductMetaEntity;
import com.tmind.ocean.entity.UserQrcodeEntity;
import com.tmind.ocean.util.HibernateUtil;
import com.tmind.ocean.util.PropertiesUtil;
import com.tmind.ocean.util.UniqueKeyGenerator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by lijunying on 15/11/21.
 */
@SuppressWarnings("ALL")
@Service("productService")
public class ProductService {

    private static String pattern = null;
    private static java.text.DecimalFormat numberFormat = null;
    private static SimpleDateFormat dateFormat = null;

    static {
        pattern="000";
        numberFormat = new java.text.DecimalFormat(pattern);
        dateFormat = new SimpleDateFormat("yyyyMMdd");
    }


    Log log = LogFactory.getLog(ProductService.class);
    //创建产品信息
    public boolean createUserProducet(UserProductMetaEntity productMeta){
        Session session = HibernateUtil.getSessionFactory().openSession();
        productMeta.setAdvice_temp("default");
        try{
            Transaction tran=session.beginTransaction();
            session.save(productMeta);
            tran.commit();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return true;
    }

    //查询产品信息

    public List<UserProductMetaEntity> queryProductInfo(Integer userId, Integer firstRecord, Integer maxResult){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UserProductMetaEntity> list = null;
        try {
            String hql = "from UserProductMetaEntity as UserProductMetaEntity where UserProductMetaEntity.user_id=:userId order by UserProductMetaEntity.update_time desc";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setFirstResult(firstRecord);
            query.setMaxResults(maxResult);
            list = query.list();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return list;
    }

    public Integer getProductMetaTotalNo(Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select count(UserProductMetaEntity.id) from UserProductMetaEntity as UserProductMetaEntity where UserProductMetaEntity.user_id=:userId";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            return ((Number) query.iterate().next()).intValue();
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        finally {
            if(session!=null){
                session.close();
            }
        }
        return 0;
    }

    //删除产品信息
    public boolean deleteProduct(Integer userId, String productId, String batchNo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction trans = session.beginTransaction();
            String hql = "delete UserProductMetaEntity as UserProductMetaEntity where UserProductMetaEntity.user_id=:userId and UserProductMetaEntity.product_id=:productId and UserProductMetaEntity.relate_batch=:batchNo";
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            query.setString("batchNo", batchNo);

            int result  = query.executeUpdate();
            trans.commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally{
            if (session != null) {
                session.close();
            }
        }
        return true;
    }

    //删除产品信息(仅根据产品id),同时清空product_meta表和m_user_product表
    public boolean deleteProduct4ProductId(Integer userId, String productId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction trans = session.beginTransaction();
            String hql = "delete UserProductMetaEntity as UserProductMetaEntity where UserProductMetaEntity.user_id=:userId and UserProductMetaEntity.product_id=:productId";
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            int result  = query.executeUpdate();
            hql = "delete UserProductEntity as UserProductEntity where UserProductEntity.user_id=:userId and UserProductEntity.product_id=:productId";
            query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            result  = query.executeUpdate();
            trans.commit();
        } catch (Exception e){
            System.out.println(e.getMessage());
        } finally{
            if (session != null) {
                session.close();
            }
        }
        return true;
    }


    //根据产品id查询
    public UserProductMetaEntity queryProductInfoById(Integer userId, String productId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        UserProductMetaEntity productMeta = null;
        try {
            String hql = "from UserProductMetaEntity as UserProductMetaEntity where UserProductMetaEntity.user_id=:userId and UserProductMetaEntity.product_id=:productId";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            productMeta = (UserProductMetaEntity)query.list().get(0);
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return productMeta;
    }

    //为当前的产品创建一个新的批次-->插入到m_user_product表中
    public boolean createNewProductBatch(UserProductEntity productEntity){
        Session session =null;
        try
        {
            session= HibernateUtil.getSessionFactory().openSession();
            //开启事务.
            Transaction tran = session.beginTransaction();
            session.save(productEntity);
            //提交事务.把内存的改变提交到数据库上.
            tran.commit();

        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            if(session!=null){
                session.close();
            }
        }
        return true;
    }

    //更新产品
    public boolean updateProductById(UserProductMetaEntity meta){
        Session session =null;
        try
        {
            session= HibernateUtil.getSessionFactory().openSession();
            //开启事务.
            Transaction tran = session.beginTransaction();
            session.update(meta);
            //提交事务.把内存的改变提交到数据库上.
            tran.commit();

        }catch(Exception e){
            e.printStackTrace();
            session.getTransaction().rollback();
        }finally{
            if(session!=null){
                session.close();
            }
        }
        return true;
    }

    public boolean createQrcode(UserProductEntity productEntityFake, Integer userId, String userType, String sequenceNo){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Transaction tran=session.beginTransaction();
            //得到缓存信息 和 具体的中奖信息，用于微信红包抽奖
            String lottery_desc = null;
            String cacheFlag = null;
            if(productEntityFake.getLottery_info() != null && productEntityFake.getLottery_info() .length()>0){
                 lottery_desc = productEntityFake.getLottery_info().split("\\|")[0];
                 cacheFlag = productEntityFake.getLottery_info().split("\\|")[1];
            }
            //变量设定
            String formattedUserId = numberFormat.format(userId);
            String formattedDate = dateFormat.format(new Date());
            //获取用户自定义的唯一码生成公式
            //绑定唯一码
            /**
             * D代表数字 D[7]0000001 代表数字长度，从0000001开始
             * R代表随机数
             * S代表字符串
             * X代表混合，包含字母和数字
             * TIME表示日期
             * UIQ代表用户序列号
             * <p>
             * {X10}
             * {X10}{UIQ}
             * {X10}{TIME}
             * {D[8]00000001}
             * {X4}{UIQ}{TIME}{D[7]0000001}
             */
            int currentStep = 0;
            DecimalFormat df = null;
            String regex = sequenceNo;
            if(sequenceNo!=null && sequenceNo.length()>0) {
                if (isSequence(regex)) {
                    currentStep = getStartStep(regex);
                    df = getDecimalFormat(regex);
                }
            }
            //正式开始生成
            for(int i=0;i<Integer.valueOf(productEntityFake.getQrcode_total_no());i++){
                UserQrcodeEntity m_user_qrcode_entity = new UserQrcodeEntity();
                m_user_qrcode_entity.setUser_id(userId);
                m_user_qrcode_entity.setProduct_id(productEntityFake.getProduct_id());
                m_user_qrcode_entity.setProduct_batch(productEntityFake.getRelate_batch());
                //表示是否缓存
                m_user_qrcode_entity.setCache_flag(cacheFlag.toCharArray()[0]);
                //表示是否中奖
                if(lottery_desc == null || lottery_desc.length()==0)
                    m_user_qrcode_entity.setLottery_flag('N');
                else {
                    m_user_qrcode_entity.setLottery_flag('Y');
                    m_user_qrcode_entity.setLottery_desc(lottery_desc);
                }
                m_user_qrcode_entity.setGet_lottery_flag('N');

                String uniqueKey = null;
                if(sequenceNo!=null && sequenceNo.length()>0) {
                    uniqueKey = randomStringGenerator(regex, currentStep, df, formattedUserId);
                }
                currentStep++;

                String qrcodeQueryString = generateQRCodeString(userType, productEntityFake.getAdvice_temp(), formattedUserId, formattedDate, uniqueKey);
                m_user_qrcode_entity.setQr_query_string(qrcodeQueryString);
                m_user_qrcode_entity.setQuery_match(qrcodeQueryString.split("\\?")[1]);
                m_user_qrcode_entity.setIp_check_flag("N");
                m_user_qrcode_entity.setQuery_times(0);
                m_user_qrcode_entity.setActive_flag("Y");
                m_user_qrcode_entity.setLottery_check_flag('N');
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                m_user_qrcode_entity.setCreate_date(sdf.format(new Date()));
                m_user_qrcode_entity.setDelete_flag('N');
                session.save(m_user_qrcode_entity);
                if( i % 50 == 0 ) { // Same as the JDBC batch size
                    //flush a batch of inserts and release memory:
                    session.flush();
                    session.clear();
                }
            }

            tran.commit();
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        finally {
            if(session!=null){
                session.close();
            }
        }
        return true;
    }

    public boolean updateProductAndBatchQrTotalAccount(Integer userId, String productId, String batchId, Integer qrAccount){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tran=session.beginTransaction();
        UserProductEntity userProductEntity = null;
        try {
            //更新二维码生成到批次数
            String hql = "from UserProductEntity as UserProductEntity where UserProductEntity.user_id=:userId and UserProductEntity.product_id=:productId and UserProductEntity.relate_batch=:batchId";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            query.setString("batchId", batchId);
            userProductEntity = (UserProductEntity)query.list().get(0);
            userProductEntity.setQrcode_total_no(userProductEntity.getQrcode_total_no() + qrAccount);
            session.update(userProductEntity);
            //更新二维码总数
            hql = "from UserProductMetaEntity as UserProductMetaEntity where UserProductMetaEntity.user_id=:userId and UserProductMetaEntity.product_id=:productId";//使用命名参数，推荐使用，易读。
            query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            UserProductMetaEntity meta = null;
            meta = (UserProductMetaEntity)query.list().get(0);
            meta.setQrcode_total_no(meta.getQrcode_total_no() + qrAccount);
            session.update(meta);
            tran.commit();
        }catch (Exception e){
            tran.rollback();
            return false;
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return true;
    }

    //检查产品名称是否存在
    public boolean checkProductNameExist(Integer userId, String productName){
        Session session = HibernateUtil.getSessionFactory().openSession();
        boolean flag = false;
        try {
            String hql = "from UserProductMetaEntity as UserProductMetaEntity where UserProductMetaEntity.user_id=:userId and UserProductMetaEntity.product_name=:productName";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productName", productName);
            if(query.list().size()>0){
                flag =  true;
            }else{
                flag = false;
            }
        }catch (Exception e){
            log.warn(e.getMessage());
        } finally{
            if(session!=null){
                session.close();
            }
        }
        return  flag;
    }

    //检查产品批次是否存在
    public boolean checkProductBatchExist(Integer userId, String productId, String batchNo){
        Session session = HibernateUtil.getSessionFactory().openSession();
        boolean flag = false;
        try {
            String hql = "from UserProductEntity as UserProductEntity where UserProductEntity.user_id=:userId and UserProductEntity.product_id=:productId and UserProductEntity.relate_batch=:batchNo";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            query.setString("batchNo", batchNo);
            if(query.list().size()>0){
                flag =  true;
            }else{
                flag = false;
            }
        }catch (Exception e){
            log.warn(e.getMessage());
        } finally{
            if(session!=null){
                session.close();
            }
        }
        return  flag;
    }

    //
    //获得用户的参数
    public List<UserParamsEntity> loadUserParams(Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UserParamsEntity> list = null;
        try {
            String hql = "from UserParamsEntity as UserParamsEntity where UserParamsEntity.user_id=:userId";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            list = query.list();

        }catch (Exception e){
            log.warn(e.getMessage());
        } finally{
            if(session!=null){
                session.close();
            }
        }
        return  list;
    }

    /*
        @see: user表中的user_type
        unqiueCode可从外部获取
     */
    private String generateQRCodeString(String userType, String batchTemplateName,
                                        String formattedUserId, String formattedDate, String uniqueCode){

        /*
            sequenceNo:代表生成的二维码是否要增加序列，用来区分不同批次的二维码顺序
         */
        //urlPrefix定义在User表中，用来代表用户的访问路径
        String urlPrefix = userType.split(":")[1];
        //urlTemplate定义在User表中，用来代表用户的访问的模版路径
        /*
                User 表中的user_type 字段
                A类:a:y
                第一标示用户级别是，第二个表示生成的URL以字母a开始，如a.315kc.com
                第三个表示，用户的访问模版:y代表柚子，j代表酒，t代表桃子
                未来要拓展
         */
        if(uniqueCode!=null && uniqueCode.length()>0 && !uniqueCode.equalsIgnoreCase("null")){
            return "http://"+urlPrefix+".315kc.com/m/r/"+batchTemplateName+"/i.htm?"+uniqueCode;
        }else{
            return "http://"+urlPrefix+".315kc.com/m/r/"+batchTemplateName+"/i.htm?"+ UniqueKeyGenerator.getFixLenthString(10);

        }

    }

    private String randomStringGenerator(String regex, int currentStep, DecimalFormat df, String userId) {
        String[] groups = getRegexGroup(regex);
        StringBuilder sb = new StringBuilder();
        for (String subRegex : groups) {
            //移除掉第一
            generateStringBaseOnRegesSubGroup(subRegex, sb, currentStep, df, userId);
        }
        return sb.toString();
    }


    /**
     * 核心处理类
     * @param subRegex
     * @param stringBuilder
     * @param currentStep
     * @param df
     * @return
     */
    public StringBuilder generateStringBaseOnRegesSubGroup(String subRegex, final StringBuilder stringBuilder, int currentStep, DecimalFormat df, String userId) {
        char[] chars = subRegex.toCharArray();
        // 纯的任意随机数
        if ('X' == subRegex.charAt(0)) {
            String length = subRegex.substring(1, subRegex.length());
            stringBuilder.append(getRandomCharAndNumr(Integer.valueOf(length)));
        }
        //添加日期
        if ("TIME".equalsIgnoreCase(subRegex)) {
            stringBuilder.append(getCurrentDateTime());
        }
        //添加用户ID
        if ("UIQ".equalsIgnoreCase(subRegex)) {
            stringBuilder.append(userId);
        }

        //数字序号
        if ('D' == subRegex.charAt(0)) {
            stringBuilder.append(df.format(currentStep));
        }
        return stringBuilder;
    }

    /**
     * 获取随机字母数字组合
     *
     * @param length 字符串长度
     * @return
     */
    private String getRandomCharAndNumr(Integer length) {
        String str = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            boolean b = random.nextBoolean();
            if (b) { // 字符串
                int choice = random.nextBoolean() ? 65 : 97; //取得65大写字母还是97小写字母
                str += (char) (choice + random.nextInt(26));// 取得随机大小写字母
            }
            else { // 数字
                str += String.valueOf(random.nextInt(10));
            }
        }
        return str;
    }


    private String getCurrentDateTime() {
        long l = System.currentTimeMillis();
        //new日期对象
        Date date = new Date(l);
        //转换提日期输出格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return (dateFormat.format(date));
    }

    private boolean isSequence(String regex) {
        return (regex.indexOf("D") > 0 || regex.indexOf("d") > 0) ? true : false;
    }

    private int getStartStep(String regex) throws Exception {

        //获得数字信息
        String dataInfo = getStepDatInfo(regex);
        //[7]0000001
        String length = dataInfo.split("]")[0].replace("[", "");
        String startNum = dataInfo.split("]")[1];
        if (startNum.length() != Integer.valueOf(length)) {
            throw new Exception("数字长度不匹配");
        }
        return Integer.valueOf(startNum);

    }

    private DecimalFormat getDecimalFormat(String regex) throws Exception {
        //获得数字信息
        String dataInfo = getStepDatInfo(regex);
        //[7]0000001
        String length = dataInfo.split("]")[0].replace("[", "");
        String type = "";
        for (int i = 0; i < Integer.valueOf(length); i++) {
            type += "0";
        }
        return new DecimalFormat(type);

    }

    private String[] getRegexGroup(String regex) {
        String regexAfterMove = regex.substring(1, regex.length() - 1); //移除掉第一个和最后一个{}花括号
        String[] groups = regexAfterMove.split("\\}\\{");
        return groups;
    }

    private String getStepDatInfo(String regex) {
        String[] groups = getRegexGroup(regex);
        for (String subRegex : groups) {
            if (subRegex.charAt(0) == 'D') {
                //获得数字信息
                String dataInfo = subRegex.substring(1, subRegex.length());
                return dataInfo;
            }
        }
        return null;
    }
}
