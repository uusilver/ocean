package com.tmind.ocean.service;

import com.tmind.ocean.entity.ProductShowInfoEntity;
import com.tmind.ocean.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

/**
 * Created by lijunying on 15/12/7.
 */

@SuppressWarnings("ALL")
@Service("productInfoService")
public class ProductInfoService {

    public boolean createProductInfo(ProductShowInfoEntity infoEntity){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try{
            Transaction tran=session.beginTransaction();
            String hql = "delete ProductShowInfoEntity as ProductShowInfoEntity where ProductShowInfoEntity.user_id=:userId and ProductShowInfoEntity.product_id=:productId and ProductShowInfoEntity.batch_no=:batchNo";
            Query query = session.createQuery(hql);
            query.setInteger("userId", infoEntity.getUser_id());
            query.setString("productId",infoEntity.getProduct_id());
            query.setString("batchNo",infoEntity.getBatch_no());
            query.executeUpdate();
            session.save(infoEntity);
            tran.commit();
        }catch (Exception e){
            System.out.println(e.getMessage());
        } finally{
            if(session!=null){
                session.close();
            }
        }
        return true;
    }

    public ProductShowInfoEntity queryInfoBySpecificInfo(String productId, String batchNo, Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        ProductShowInfoEntity info = null;
        try{
            String hql = "from ProductShowInfoEntity as ProductShowInfoEntity where ProductShowInfoEntity.user_id=:userId and ProductShowInfoEntity.product_id=:productId and ProductShowInfoEntity.batch_no=:batchNo";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId",productId);
            query.setString("batchNo",batchNo);
            if (query.list().size()>0)
                    info = (ProductShowInfoEntity)query.list().get(0);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        finally
        {
            if(session!=null){
                session.close();
            }
        }
        return info;
    }
}
