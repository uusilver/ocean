package com.tmind.ocean.service;

import com.tmind.ocean.entity.UserCategoryEntity;
import com.tmind.ocean.util.HibernateUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lijunying on 15/11/22.
 */
@SuppressWarnings("ALL")
@Service("categoryService")
public class CategoryService {

    Log log = LogFactory.getLog(CategoryService.class);

    public boolean createCategory(UserCategoryEntity category){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction trans = session.beginTransaction();
            session.save(category);
            trans.commit();
            System.out.println("分类创建成功");
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return true;
    }

    public List<UserCategoryEntity> queryCategory(Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UserCategoryEntity> list = null;
        try{
            String hql = "from UserCategoryEntity as UserCategoryEntity where UserCategoryEntity.user_id=:userId";
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

    public boolean deleteCategory(Integer categoryId, Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            Transaction trans = session.beginTransaction();
            String hql = "delete UserCategoryEntity as UserCategoryEntity where UserCategoryEntity.Id=:Id and UserCategoryEntity.user_id=:userId";
            Query query = session.createQuery(hql);
            query.setInteger("Id",categoryId);
            query.setInteger("userId",userId);
            query.executeUpdate();
            trans.commit();
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return true;
    }

    public boolean checkCategoryUsedOrNot(Integer categoryId, Integer userId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        UserCategoryEntity categoryEntity = null;
        try{
            String hql = "from UserCategoryEntity as UserCategoryEntity where UserCategoryEntity.user_id=:userId and UserCategoryEntity.Id=:id";
            Query query = session.createQuery(hql);
            query.setInteger("userId",userId);
            query.setInteger("id",categoryId);
            categoryEntity = (UserCategoryEntity)query.list().get(0);
            hql = "from UserProductMetaEntity as UserProductMetaEntity where UserProductMetaEntity.user_id=:userId and UserProductMetaEntity.product_category=:product_category";
            query = session.createQuery(hql);
            query.setInteger("userId",userId);
            query.setString("product_category",categoryEntity.getCategory_name());
            if(query.list().size()>0)
                return false;
            else
                return true;
        }catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }finally {
            if(session!=null){
                session.close();
            }
        }
    }

    //检测分类是否存在
    public boolean checkCategoryNameExist(Integer userId, String categoryName){
        boolean flag = false;
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UserCategoryEntity> list = null;
        try{
            String hql = "from UserCategoryEntity as UserCategoryEntity where UserCategoryEntity.user_id=:userId and UserCategoryEntity.category_name=:categoryName";
            Query query = session.createQuery(hql);
            query.setInteger("userId",userId);
            query.setString("categoryName", categoryName);
            if(query.list().size()>0){
                flag = true;
            }else{
                flag = flag;
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }finally {
            if(session!=null){
                session.close();
            }
        }
        return flag;

    }
}
