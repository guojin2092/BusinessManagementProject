package com.africa.crm.businessmanagement.main.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteAccountInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteSupplierInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;

import com.africa.crm.businessmanagement.main.dao.CompanyAccountInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanySupplierInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteAccountInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteSupplierInfoDao;
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
    private final DaoConfig companySupplierInfoDaoConfig;
    private final DaoConfig companyDeleteAccountInfoDaoConfig;
    private final DaoConfig companyDeleteInfoDaoConfig;
    private final DaoConfig companyDeleteSupplierInfoDaoConfig;
    private final DaoConfig dicInfoDaoConfig;

    private final CompanyAccountInfoDao companyAccountInfoDao;
    private final CompanyInfoDao companyInfoDao;
    private final CompanySupplierInfoDao companySupplierInfoDao;
    private final CompanyDeleteAccountInfoDao companyDeleteAccountInfoDao;
    private final CompanyDeleteInfoDao companyDeleteInfoDao;
    private final CompanyDeleteSupplierInfoDao companyDeleteSupplierInfoDao;
    private final DicInfoDao dicInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        companyAccountInfoDaoConfig = daoConfigMap.get(CompanyAccountInfoDao.class).clone();
        companyAccountInfoDaoConfig.initIdentityScope(type);

        companyInfoDaoConfig = daoConfigMap.get(CompanyInfoDao.class).clone();
        companyInfoDaoConfig.initIdentityScope(type);

        companySupplierInfoDaoConfig = daoConfigMap.get(CompanySupplierInfoDao.class).clone();
        companySupplierInfoDaoConfig.initIdentityScope(type);

        companyDeleteAccountInfoDaoConfig = daoConfigMap.get(CompanyDeleteAccountInfoDao.class).clone();
        companyDeleteAccountInfoDaoConfig.initIdentityScope(type);

        companyDeleteInfoDaoConfig = daoConfigMap.get(CompanyDeleteInfoDao.class).clone();
        companyDeleteInfoDaoConfig.initIdentityScope(type);

        companyDeleteSupplierInfoDaoConfig = daoConfigMap.get(CompanyDeleteSupplierInfoDao.class).clone();
        companyDeleteSupplierInfoDaoConfig.initIdentityScope(type);

        dicInfoDaoConfig = daoConfigMap.get(DicInfoDao.class).clone();
        dicInfoDaoConfig.initIdentityScope(type);

        companyAccountInfoDao = new CompanyAccountInfoDao(companyAccountInfoDaoConfig, this);
        companyInfoDao = new CompanyInfoDao(companyInfoDaoConfig, this);
        companySupplierInfoDao = new CompanySupplierInfoDao(companySupplierInfoDaoConfig, this);
        companyDeleteAccountInfoDao = new CompanyDeleteAccountInfoDao(companyDeleteAccountInfoDaoConfig, this);
        companyDeleteInfoDao = new CompanyDeleteInfoDao(companyDeleteInfoDaoConfig, this);
        companyDeleteSupplierInfoDao = new CompanyDeleteSupplierInfoDao(companyDeleteSupplierInfoDaoConfig, this);
        dicInfoDao = new DicInfoDao(dicInfoDaoConfig, this);

        registerDao(CompanyAccountInfo.class, companyAccountInfoDao);
        registerDao(CompanyInfo.class, companyInfoDao);
        registerDao(CompanySupplierInfo.class, companySupplierInfoDao);
        registerDao(CompanyDeleteAccountInfo.class, companyDeleteAccountInfoDao);
        registerDao(CompanyDeleteInfo.class, companyDeleteInfoDao);
        registerDao(CompanyDeleteSupplierInfo.class, companyDeleteSupplierInfoDao);
        registerDao(DicInfo.class, dicInfoDao);
    }
    
    public void clear() {
        companyAccountInfoDaoConfig.clearIdentityScope();
        companyInfoDaoConfig.clearIdentityScope();
        companySupplierInfoDaoConfig.clearIdentityScope();
        companyDeleteAccountInfoDaoConfig.clearIdentityScope();
        companyDeleteInfoDaoConfig.clearIdentityScope();
        companyDeleteSupplierInfoDaoConfig.clearIdentityScope();
        dicInfoDaoConfig.clearIdentityScope();
    }

    public CompanyAccountInfoDao getCompanyAccountInfoDao() {
        return companyAccountInfoDao;
    }

    public CompanyInfoDao getCompanyInfoDao() {
        return companyInfoDao;
    }

    public CompanySupplierInfoDao getCompanySupplierInfoDao() {
        return companySupplierInfoDao;
    }

    public CompanyDeleteAccountInfoDao getCompanyDeleteAccountInfoDao() {
        return companyDeleteAccountInfoDao;
    }

    public CompanyDeleteInfoDao getCompanyDeleteInfoDao() {
        return companyDeleteInfoDao;
    }

    public CompanyDeleteSupplierInfoDao getCompanyDeleteSupplierInfoDao() {
        return companyDeleteSupplierInfoDao;
    }

    public DicInfoDao getDicInfoDao() {
        return dicInfoDao;
    }

}
