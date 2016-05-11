package com.tmind.ocean.service;


import com.tmind.ocean.entity.UserAdviceTemplateEntity;
import com.tmind.ocean.entity.UserProductEntity;
import com.tmind.ocean.entity.UserProductMetaEntity;
import com.tmind.ocean.model.BatchQueryTo;
import com.tmind.ocean.util.HibernateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lijunying on 15/11/28.
 */
@SuppressWarnings("ALL")
@Service("batchService")
public class BatchService {

    Log log = LogFactory.getLog(BatchService.class);

    @Resource(name="productService")
    private  ProductService productService;

    public List<UserAdviceTemplateEntity> queryBatch(Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UserAdviceTemplateEntity> list = null;
        try{
            String hql = "from UserAdviceTemplateEntity as UserAdviceTemplateEntity where UserAdviceTemplateEntity.user_id=:userId";
            Query query = session.createQuery(hql);
            query.setInteger("userId",userId);
            list = query.list();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return list;
    }

    //查询产品信息

    public List<BatchQueryTo> queryProductInfo(Integer userId, String searchType, String searchContent, Integer firstRecord, Integer maxResult){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<BatchQueryTo> list = new ArrayList<BatchQueryTo>();
        List<UserProductEntity> batchList = null;
        try {
            String hql = "";
            Query query = null;
            if(searchType.equalsIgnoreCase("productId")&&searchContent.length()>0){
                hql = "from UserProductEntity as UserProductEntity where UserProductEntity.user_id=:userId and UserProductEntity.product_id like :product_id";//使用命名参数，推荐使用，易读。
                query = session.createQuery(hql);
                query.setInteger("userId", userId);
                query.setString("product_id","%"+searchContent+"%");

            }else if(searchType.equalsIgnoreCase("batchNo")&&searchContent.length()>0){
                hql = "from UserProductEntity as UserProductEntity where UserProductEntity.user_id=:userId and UserProductEntity.relate_batch like :batchNo";//使用命名参数，推荐使用，易读。
                query = session.createQuery(hql);
                query.setInteger("userId", userId);
                query.setString("batchNo","%"+searchContent+"%");
            }else if(searchContent.length()==0){
                hql = "from UserProductEntity as UserProductEntity where UserProductEntity.user_id=:userId order by UserProductEntity.product_id";//使用命名参数，推荐使用，易读。
                query = session.createQuery(hql);
                query.setInteger("userId", userId);
            }

            query.setFirstResult(firstRecord);
            query.setMaxResults(maxResult);
            batchList = query.list();
            for(UserProductEntity m:batchList){
                BatchQueryTo b = new BatchQueryTo();
                UserProductMetaEntity meta = productService.queryProductInfoById(userId,m.getProduct_id());
                b.setId(m.getId());
                b.setProductId(m.getProduct_id());
                b.setProductName(meta.getProduct_name());
                b.setAdviceTemplate(m.getAdvice_temp());
                b.setBatchNo(m.getRelate_batch());
                b.setQrTotalNo(m.getQrcode_total_no());
                b.setUpdateTime(m.getUpdate_time());
                b.setSellArthor(m.getSellArthor());
                b.setSellPrice(m.getSellPrice());
                b.setProductAddress(m.getProductAddress());
                //去掉json的包装符号
                b.setParams(m.getBatch_params().replaceAll("\\[","").replaceAll("\\]", "").replaceAll("\"",""));
                list.add(b);
            }
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return list;
    }

    public Integer getProductBatchTotalNo(Integer userId, String searchType, String searchContent){
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            String hql = "";
            Query query = null;
            if(searchType.equalsIgnoreCase("productId")&&searchContent.length()>0){
                hql = "from UserProductEntity as UserProductEntity where UserProductEntity.user_id=:userId and UserProductEntity.product_id like :product_id";//使用命名参数，推荐使用，易读。
                query = session.createQuery(hql);
                query.setInteger("userId", userId);
                query.setString("product_id","%"+searchContent+"%");

            }else if(searchType.equalsIgnoreCase("batchNo")&&searchContent.length()>0){
                hql = "from UserProductEntity as UserProductEntity where UserProductEntity.user_id=:userId and UserProductEntity.relate_batch like :batchNo";//使用命名参数，推荐使用，易读。
                query = session.createQuery(hql);
                query.setInteger("userId", userId);
                query.setString("batchNo","%"+searchContent+"%");
            }else if(searchContent.length()==0){
                hql = "from UserProductEntity as UserProductEntity where UserProductEntity.user_id=:userId order by UserProductEntity.product_id";//使用命名参数，推荐使用，易读。
                query = session.createQuery(hql);
                query.setInteger("userId", userId);
            }

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

    //删除
    public boolean deleteQrCodes(Integer userId, String productId, String batchNo){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction trans = session.beginTransaction();
            //update  UserQrcodeEntity m set m.active_flag='N' where m.user_id=:userId and m.product_id=:productId
            String hql = "update UserQrcodeEntity m set m.delete_flag='Y' where m.user_id=:userId and m.product_id=:productId and m.product_batch=:batchNo";
            Query query = session.createQuery(hql);
            query.setInteger("userId", userId);
            query.setString("productId", productId);
            query.setString("batchNo",batchNo);
            query.executeUpdate();
            trans.commit();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return true;
    }

    //根据ID查产品批次

    public boolean queryBatchInfoByProductId(Integer userId, String productId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        boolean flag = false;
        try {
            String hql = "";
            Query query = null;
                hql = "from UserProductEntity as UserProductEntity where UserProductEntity.user_id=:userId and UserProductEntity.product_id =:productId";//使用命名参数，推荐使用，易读。
                query = session.createQuery(hql);
                query.setInteger("userId", userId);
                query.setString("productId",productId);
            if(query.list().size()>0){
                flag = true;
            }
        }catch (Exception e){
            log.warn(e.getMessage());
        }
        finally {
            if(session!=null){
                session.close();
            }
        }
        return flag;
    }
}
