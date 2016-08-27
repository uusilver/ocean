package com.tmind.ocean.service;

import com.tmind.ocean.entity.SystemInfoEntity;
import com.tmind.ocean.util.HibernateUtil;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lijunying on 15/11/20.
 */
@SuppressWarnings("ALL")
@Service("loadSysInfo")
public class LoadSystemInfoService {

    Logger log = Logger.getLogger(LoadSystemInfoService.class);

    public String loadSysInfo(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query q = session.createQuery("from SystemInfoEntity");
        List<SystemInfoEntity> list = q.list();
        log.info("读取系统信息成功");
        session.close();
        return list.get(0).getSystem_message();
    }

    public String updateSysInfo(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<SystemInfoEntity> list = null;
        try {
            String hql = "from SystemInfoEntity as SystemInfoEntity where SystemInfoEntity.id=:id";//使用命名参数，推荐使用，易读。
            Query query = session.createQuery(hql);
            query.setInteger("id", 99);
            list = query.list();
            //共12万100个物品正在使用315快查 <br/>今天监控问题产品1万1次 累计安全监控30万9420次
            String message = list.get(0).getSystem_message();

        }catch (Exception e){
            log.warn(e.getMessage());
        } finally{
            if(session!=null){
                session.close();
            }
        }
        return null;
    }
}
