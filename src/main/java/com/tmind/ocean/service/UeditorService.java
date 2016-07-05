package com.tmind.ocean.service;

import com.tmind.ocean.entity.CustTileShTable;
import com.tmind.ocean.entity.UserEntity;
import com.tmind.ocean.entity.UserProductMetaEntity;
import com.tmind.ocean.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lijunying on 16/7/5.
 */
@Service("ueditorService")
public class UeditorService {

    public String queryValue(String key){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String hql = "from CustTileShTable as CustTileShTable where CustTileShTable.key_t=:key";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setString("key", key);
            return ((CustTileShTable)query.list().get(0)).getValue_t();
        }finally {
            if(session!=null){
                session.close();
            }
        }
    }

    //更新
    public boolean updateProductById(String key, String content){
        Session session =null;
        try
        {
            session= HibernateUtil.getSessionFactory().openSession();
            String hql = "from CustTileShTable as CustTileShTable where CustTileShTable.key_t=:key";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setString("key", key);
            CustTileShTable custTileShTable = (CustTileShTable)query.list().get(0);
            custTileShTable.setValue_t(content);
            //开启事务.
            Transaction tran = session.beginTransaction();
            session.update(custTileShTable);
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
}
