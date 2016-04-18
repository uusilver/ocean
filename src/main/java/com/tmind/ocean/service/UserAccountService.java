package com.tmind.ocean.service;

import com.tmind.ocean.entity.UserAccountEntity;
import com.tmind.ocean.entity.UserAccountOptEntity;
import com.tmind.ocean.entity.UserProductEntity;
import com.tmind.ocean.entity.UserEntity;
import com.tmind.ocean.util.HibernateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lijunying on 15/11/24.
 */
@SuppressWarnings("ALL")
@Service("userAccountService")
public class UserAccountService {

    Log log = LogFactory.getLog(UserAccountService.class);
    //查询用户余额是否能够满足打印标签的需求
    //创建产品信息
    public boolean judgeCanPrintQrCodeOrNot(Integer printQrcodeNo, Integer userId){
        boolean flag = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UserProductEntity> list = null;
        try {
            String hql = "from UserAccountEntity as UserAccountEntity where UserAccountEntity.user_id=:userId";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            Integer qrCodeInAccount = ((UserAccountEntity)query.list().get(0)).getAccount();
            if(qrCodeInAccount<printQrcodeNo)
                flag =  false;
        }catch(Exception e){
            log.error(e.getMessage());
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return flag;
    }

    //更新用户账户
    public boolean updateUserAccountForConsuming(Integer printQrcodeNo, Integer userId){
        boolean flag = true;
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UserProductEntity> list = null;
        try {
            Transaction tran = session.beginTransaction();
            String hql = "from UserAccountEntity as UserAccountEntity where UserAccountEntity.user_id=:userId";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            UserAccountEntity userAccount = ((UserAccountEntity)query.list().get(0));
            userAccount.setAccount(userAccount.getAccount()-printQrcodeNo);
            session.update(userAccount);
            tran.commit();
        }catch(Exception e){
            log.error(e.getMessage());
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return flag;
    }

    public UserAccountEntity queryAccountForDisplay(Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        UserAccountEntity mUserAccount = null;
        try {
            String hql = "from UserAccountEntity as UserAccountEntity where UserAccountEntity.user_id=:userId";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            mUserAccount = (UserAccountEntity)query.list().get(0);
        }catch(Exception e){
            e.printStackTrace();
            log.error(e.getMessage());
        } finally{
            if(session!=null){
                session.close();
            }
        }
        return mUserAccount;
    }

    //为充值二维码更新
    public boolean purchaseQrAmountAndKeepRecordIntoOptTable(UserAccountEntity account, UserAccountOptEntity opt){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tran = session.beginTransaction();
        try{
            session.update(account);
            session.save(opt);
            tran.commit();
            return true;
        }catch (Exception e ){
            tran.rollback();
            System.out.println(e.getMessage());
            return false;
        }finally {
            if(session!=null){
                session.close();

            }
        }
    }

    //单独为了消费二维码更新
    public boolean purchaseQrAmountAndKeepRecordIntoOptTable(UserAccountOptEntity opt){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tran = session.beginTransaction();
        try{
            session.save(opt);
            tran.commit();
            return true;
        }catch (Exception e ){
            tran.rollback();
            System.out.println(e.getMessage());
            return false;
        }finally {
            if(session!=null){
                session.close();

            }
        }
    }

    public List<UserAccountOptEntity> queryUserOpt(Integer userId, Integer firstRecord, Integer maxResult){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UserAccountOptEntity> list = null;
        try {
            String hql = "from UserAccountOptEntity as UserAccountOptEntity where UserAccountOptEntity.user_id=:userId";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setFirstResult(firstRecord);
            query.setMaxResults(maxResult);
            list = query.list();
        }catch(Exception e){
            log.error(e.getMessage());
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return list;
    }

    //获得总行数
    public Integer getUserOptTotalNo(Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "select count(UserAccountOptEntity.id) from UserAccountOptEntity as UserAccountOptEntity where UserAccountOptEntity.user_id=:userId";//使用命名参数，推荐使用，易读。
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

    public UserEntity queryUserInfo(Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        UserEntity user = null;
        try {
            String hql = "from UserEntity as UserEntity where UserEntity.Id=:userId";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            user = (UserEntity)query.list().get(0);
        }catch(Exception e){
            log.error(e.getMessage());
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return user;
    }

    //为用户更新
    public boolean updateUserInfo(UserEntity userEntity){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tran = session.beginTransaction();
        try{
            session.update(userEntity);
            tran.commit();
            return true;
        }catch (Exception e ){
            tran.rollback();
            System.out.println(e.getMessage());
            return false;
        }finally {
            if(session!=null){
                session.close();

            }
        }
    }
}
