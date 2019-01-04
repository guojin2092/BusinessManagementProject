package com.africa.crm.businessmanagement.main.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;

import com.africa.crm.businessmanagement.main.dao.CompanyInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig companyInfoDaoConfig;
    private final DaoConfig dicInfoDaoConfig;

    private final CompanyInfoDao companyInfoDao;
    private final DicInfoDao dicInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        companyInfoDaoConfig = daoConfigMap.get(CompanyInfoDao.class).clone();
        companyInfoDaoConfig.initIdentityScope(type);

        dicInfoDaoConfig = daoConfigMap.get(DicInfoDao.class).clone();
        dicInfoDaoConfig.initIdentityScope(type);

        companyInfoDao = new CompanyInfoDao(companyInfoDaoConfig, this);
        dicInfoDao = new DicInfoDao(dicInfoDaoConfig, this);

        registerDao(CompanyInfo.class, companyInfoDao);
        registerDao(DicInfo.class, dicInfoDao);
    }
    
    public void clear() {
        companyInfoDaoConfig.clearIdentityScope();
        dicInfoDaoConfig.clearIdentityScope();
    }

    public CompanyInfoDao getCompanyInfoDao() {
        return companyInfoDao;
    }

    public DicInfoDao getDicInfoDao() {
        return dicInfoDao;
    }

}
