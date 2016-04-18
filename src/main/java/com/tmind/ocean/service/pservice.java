package com.tmind.ocean.service;

import com.tmind.ocean.entity.UserCategoryEntity;
import com.tmind.ocean.entity.UserProductEntity;
import com.tmind.ocean.entity.UserProductMetaEntity;
import com.tmind.ocean.entity.UserQrcodeEntity;
import com.tmind.ocean.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lijunying on 15/12/20.
 *
 * @DESC: 用来做性能测试的页面
 */
@Service("pservice")
public class pservice {

    //查询全产品时间
    public long getProductMetaQueryTime() {
        long startMils = System.currentTimeMillis();
        long endMils = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UserProductMetaEntity> list = null;
        try {
            String hql = "from UserProductMetaEntity as UserProductMetaEntity";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            list = query.list();
            endMils = System.currentTimeMillis();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return endMils-startMils;
    }

    //查询全批次时间
    public long getProductBatchQueryTime() {
        long startMils = System.currentTimeMillis();
        long endMils = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UserProductEntity> list = null;
        try {
            String hql = "from UserProductEntity as UserProductEntity";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            list = query.list();
            endMils = System.currentTimeMillis();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return endMils-startMils;
    }

    //查询全类别时间
    public long getCategoryQueryTime() {
        long startMils = System.currentTimeMillis();
        long endMils = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UserCategoryEntity> list = null;
        try {
            String hql = "from UserCategoryEntity as UserCategoryEntity";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            list = query.list();
            endMils = System.currentTimeMillis();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return endMils-startMils;
    }

    //二维码扫码表查询时间
    public long getQrcodeQueryTime() {
        long startMils = System.currentTimeMillis();
        long endMils = 0;
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<UserQrcodeEntity> list = null;
        try {
            String hql = "from UserQrcodeEntity as UserQrcodeEntity";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            list = query.list();
            endMils = System.currentTimeMillis();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return endMils-startMils;
    }
}
