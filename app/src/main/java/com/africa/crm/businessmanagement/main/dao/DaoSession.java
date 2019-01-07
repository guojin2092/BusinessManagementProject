package com.africa.crm.businessmanagement.main.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteAccountInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;

import com.africa.crm.businessmanagement.main.dao.CompanyAccountInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteAccountInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig companyAccountInfoDaoConfig;
    private final DaoConfig companyInfoDaoConfig;
    private final DaoConfig companyDeleteAccountInfoDaoConfig;
    private final DaoConfig companyDeleteInfoDaoConfig;
    private final DaoConfig dicInfoDaoConfig;

    private final CompanyAccountInfoDao companyAccountInfoDao;
    private final CompanyInfoDao companyInfoDao;
    private final CompanyDeleteAccountInfoDao companyDeleteAccountInfoDao;
    private final CompanyDeleteInfoDao companyDeleteInfoDao;
    private final DicInfoDao dicInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        companyAccountInfoDaoConfig = daoConfigMap.get(CompanyAccountInfoDao.class).clone();
        companyAccountInfoDaoConfig.initIdentityScope(type);

        companyInfoDaoConfig = daoConfigMap.get(CompanyInfoDao.class).clone();
        companyInfoDaoConfig.initIdentityScope(type);

        companyDeleteAccountInfoDaoConfig = daoConfigMap.get(CompanyDeleteAccountInfoDao.class).clone();
        companyDeleteAccountInfoDaoConfig.initIdentityScope(type);

        companyDeleteInfoDaoConfig = daoConfigMap.get(CompanyDeleteInfoDao.class).clone();
        companyDeleteInfoDaoConfig.initIdentityScope(type);

        dicInfoDaoConfig = daoConfigMap.get(DicInfoDao.class).clone();
        dicInfoDaoConfig.initIdentityScope(type);

        companyAccountInfoDao = new CompanyAccountInfoDao(companyAccountInfoDaoConfig, this);
        companyInfoDao = new CompanyInfoDao(companyInfoDaoConfig, this);
        companyDeleteAccountInfoDao = new CompanyDeleteAccountInfoDao(companyDeleteAccountInfoDaoConfig, this);
        companyDeleteInfoDao = new CompanyDeleteInfoDao(companyDeleteInfoDaoConfig, this);
        dicInfoDao = new DicInfoDao(dicInfoDaoConfig, this);

        registerDao(CompanyAccountInfo.class, companyAccountInfoDao);
        registerDao(CompanyInfo.class, companyInfoDao);
        registerDao(CompanyDeleteAccountInfo.class, companyDeleteAccountInfoDao);
        registerDao(CompanyDeleteInfo.class, companyDeleteInfoDao);
        registerDao(DicInfo.class, dicInfoDao);
    }
    
    public void clear() {
        companyAccountInfoDaoConfig.clearIdentityScope();
        companyInfoDaoConfig.clearIdentityScope();
        companyDeleteAccountInfoDaoConfig.clearIdentityScope();
        companyDeleteInfoDaoConfig.clearIdentityScope();
        dicInfoDaoConfig.clearIdentityScope();
    }

    public CompanyAccountInfoDao getCompanyAccountInfoDao() {
        return companyAccountInfoDao;
    }

    public CompanyInfoDao getCompanyInfoDao() {
        return companyInfoDao;
    }

    public CompanyDeleteAccountInfoDao getCompanyDeleteAccountInfoDao() {
        return companyDeleteAccountInfoDao;
    }

    public CompanyDeleteInfoDao getCompanyDeleteInfoDao() {
        return companyDeleteInfoDao;
    }

    public DicInfoDao getDicInfoDao() {
        return dicInfoDao;
    }

}
