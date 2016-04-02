package com.tmind.ocean.service;

import com.tmind.ocean.entity.M_USER_PARAMS_ENTITY;
import com.tmind.ocean.entity.M_USER_PRODUCT_ENTITY;
import com.tmind.ocean.entity.M_USER_PRODUCT_META;
import com.tmind.ocean.entity.M_USER_QRCODE_ENTITY;
import com.tmind.ocean.util.HibernateUtil;
import com.tmind.ocean.util.UniqueKeyGenerator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by lijunying on 15/11/21.
 */
@SuppressWarnings("ALL")
@Service("productService")
public class ProductService {

    Log log = LogFactory.getLog(ProductService.class);
    //创建产品信息
    public boolean createUserProducet(M_USER_PRODUCT_META productMeta){
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

    public List<M_USER_PRODUCT_META> queryProductInfo(Integer userId, Integer firstRecord, Integer maxResult){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<M_USER_PRODUCT_META> list = null;
        try {
            String hql = "from M_USER_PRODUCT_META as M_USER_PRODUCT_META where M_USER_PRODUCT_META.user_id=:userId order by M_USER_PRODUCT_META.update_time desc";//使用命名参数，推荐使用，易读。
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
            String hql = "select count(M_USER_PRODUCT_META.id) from M_USER_PRODUCT_META as M_USER_PRODUCT_META where M_USER_PRODUCT_META.user_id=:userId";//使用命名参数，推荐使用，易读。
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
            String hql = "delete M_USER_PRODUCT_META as M_USER_PRODUCT_META where M_USER_PRODUCT_META.user_id=:userId and M_USER_PRODUCT_META.product_id=:productId and M_USER_PRODUCT_META.relate_batch=:batchNo";
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
            String hql = "delete M_USER_PRODUCT_META as M_USER_PRODUCT_META where M_USER_PRODUCT_META.user_id=:userId and M_USER_PRODUCT_META.product_id=:productId";
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            int result  = query.executeUpdate();
            hql = "delete M_USER_PRODUCT_ENTITY as M_USER_PRODUCT_ENTITY where M_USER_PRODUCT_ENTITY.user_id=:userId and M_USER_PRODUCT_ENTITY.product_id=:productId";
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
    public M_USER_PRODUCT_META queryProductInfoById(Integer userId, String productId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        M_USER_PRODUCT_META productMeta = null;
        try {
            String hql = "from M_USER_PRODUCT_META as M_USER_PRODUCT_META where M_USER_PRODUCT_META.user_id=:userId and M_USER_PRODUCT_META.product_id=:productId";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            productMeta = (M_USER_PRODUCT_META)query.list().get(0);
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return productMeta;
    }

    //为当前的产品创建一个新的批次-->插入到m_user_product表中
    public boolean createNewProductBatch(M_USER_PRODUCT_ENTITY productEntity){
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
    public boolean updateProductById(M_USER_PRODUCT_META meta){
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

    public boolean createQrcode(M_USER_PRODUCT_ENTITY productEntityFake, Integer userId, String userType){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Transaction tran=session.beginTransaction();
            for(int i=0;i<Integer.valueOf(productEntityFake.getQrcode_total_no());i++){
                M_USER_QRCODE_ENTITY m_user_qrcode_entity = new M_USER_QRCODE_ENTITY();
                m_user_qrcode_entity.setUser_id(userId);
                m_user_qrcode_entity.setProduct_id(productEntityFake.getProduct_id());
                m_user_qrcode_entity.setProduct_batch(productEntityFake.getRelate_batch());
                //绑定唯一码
                String qrcodeQueryString = generateQRCodeString(userType);
                m_user_qrcode_entity.setQr_query_string(qrcodeQueryString);
                m_user_qrcode_entity.setQuery_match(qrcodeQueryString.split("\\?")[1]);
                m_user_qrcode_entity.setIp_check_flag("N");
                m_user_qrcode_entity.setQuery_times(0);
                m_user_qrcode_entity.setActive_flag("Y");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                m_user_qrcode_entity.setCreate_date(sdf.format(new Date()));
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
        M_USER_PRODUCT_ENTITY m_user_product_entity = null;
        try {
            //更新二维码生成到批次数
            String hql = "from M_USER_PRODUCT_ENTITY as M_USER_PRODUCT_ENTITY where M_USER_PRODUCT_ENTITY.user_id=:userId and M_USER_PRODUCT_ENTITY.product_id=:productId and M_USER_PRODUCT_ENTITY.relate_batch=:batchId";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            query.setString("batchId", batchId);
            m_user_product_entity = (M_USER_PRODUCT_ENTITY)query.list().get(0);
            m_user_product_entity.setQrcode_total_no(m_user_product_entity.getQrcode_total_no() + qrAccount);
            session.update(m_user_product_entity);
            //更新二维码总数
            hql = "from M_USER_PRODUCT_META as M_USER_PRODUCT_META where M_USER_PRODUCT_META.user_id=:userId and M_USER_PRODUCT_META.product_id=:productId";//使用命名参数，推荐使用，易读。
            query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            M_USER_PRODUCT_META meta = null;
            meta = (M_USER_PRODUCT_META)query.list().get(0);
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
            String hql = "from M_USER_PRODUCT_META as M_USER_PRODUCT_META where M_USER_PRODUCT_META.user_id=:userId and M_USER_PRODUCT_META.product_name=:productName";//使用命名参数，推荐使用，易读。
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
            String hql = "from M_USER_PRODUCT_ENTITY as M_USER_PRODUCT_ENTITY where M_USER_PRODUCT_ENTITY.user_id=:userId and M_USER_PRODUCT_ENTITY.product_id=:productId and M_USER_PRODUCT_ENTITY.relate_batch=:batchNo";//使用命名参数，推荐使用，易读。
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
    public List<M_USER_PARAMS_ENTITY> loadUserParams(Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<M_USER_PARAMS_ENTITY> list = null;
        try {
            String hql = "from M_USER_PARAMS_ENTITY as M_USER_PARAMS_ENTITY where M_USER_PARAMS_ENTITY.user_id=:userId";//使用命名参数，推荐使用，易读。
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
     */
    private String generateQRCodeString(String userType){
        //http://localhost:8080/iSearch/index.html?queryid=6ebe7af5-437c-4999-9a1c-84181089889b&uniqueCode=2059467068
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
        String userTemplatePrefix = userType.split(":")[2];
        return "http://"+urlPrefix+".315kc.com/m/r/"+userTemplatePrefix+"/i.htm?"+System.currentTimeMillis()+ UniqueKeyGenerator.generateShortUuid();

    }
}
