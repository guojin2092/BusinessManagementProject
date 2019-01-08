package com.africa.crm.businessmanagement.main.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyClientInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyProductInfo;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteAccountInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteClientInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteProductInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteSupplierInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;

import com.africa.crm.businessmanagement.main.dao.CompanyAccountInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyClientInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyProductInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanySupplierInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteAccountInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteClientInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteProductInfoDao;
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
    private final DaoConfig companyClientInfoDaoConfig;
    private final DaoConfig companyInfoDaoConfig;
    private final DaoConfig companyProductInfoDaoConfig;
    private final DaoConfig companySupplierInfoDaoConfig;
    private final DaoConfig companyDeleteAccountInfoDaoConfig;
    private final DaoConfig companyDeleteClientInfoDaoConfig;
    private final DaoConfig companyDeleteInfoDaoConfig;
    private final DaoConfig companyDeleteProductInfoDaoConfig;
    private final DaoConfig companyDeleteSupplierInfoDaoConfig;
    private final DaoConfig dicInfoDaoConfig;

    private final CompanyAccountInfoDao companyAccountInfoDao;
    private final CompanyClientInfoDao companyClientInfoDao;
    private final CompanyInfoDao companyInfoDao;
    private final CompanyProductInfoDao companyProductInfoDao;
    private final CompanySupplierInfoDao companySupplierInfoDao;
    private final CompanyDeleteAccountInfoDao companyDeleteAccountInfoDao;
    private final CompanyDeleteClientInfoDao companyDeleteClientInfoDao;
    private final CompanyDeleteInfoDao companyDeleteInfoDao;
    private final CompanyDeleteProductInfoDao companyDeleteProductInfoDao;
    private final CompanyDeleteSupplierInfoDao companyDeleteSupplierInfoDao;
    private final DicInfoDao dicInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        companyAccountInfoDaoConfig = daoConfigMap.get(CompanyAccountInfoDao.class).clone();
        companyAccountInfoDaoConfig.initIdentityScope(type);

        companyClientInfoDaoConfig = daoConfigMap.get(CompanyClientInfoDao.class).clone();
        companyClientInfoDaoConfig.initIdentityScope(type);

        companyInfoDaoConfig = daoConfigMap.get(CompanyInfoDao.class).clone();
        companyInfoDaoConfig.initIdentityScope(type);

        companyProductInfoDaoConfig = daoConfigMap.get(CompanyProductInfoDao.class).clone();
        companyProductInfoDaoConfig.initIdentityScope(type);

        companySupplierInfoDaoConfig = daoConfigMap.get(CompanySupplierInfoDao.class).clone();
        companySupplierInfoDaoConfig.initIdentityScope(type);

        companyDeleteAccountInfoDaoConfig = daoConfigMap.get(CompanyDeleteAccountInfoDao.class).clone();
        companyDeleteAccountInfoDaoConfig.initIdentityScope(type);

        companyDeleteClientInfoDaoConfig = daoConfigMap.get(CompanyDeleteClientInfoDao.class).clone();
        companyDeleteClientInfoDaoConfig.initIdentityScope(type);

        companyDeleteInfoDaoConfig = daoConfigMap.get(CompanyDeleteInfoDao.class).clone();
        companyDeleteInfoDaoConfig.initIdentityScope(type);

        companyDeleteProductInfoDaoConfig = daoConfigMap.get(CompanyDeleteProductInfoDao.class).clone();
        companyDeleteProductInfoDaoConfig.initIdentityScope(type);

        companyDeleteSupplierInfoDaoConfig = daoConfigMap.get(CompanyDeleteSupplierInfoDao.class).clone();
        companyDeleteSupplierInfoDaoConfig.initIdentityScope(type);

        dicInfoDaoConfig = daoConfigMap.get(DicInfoDao.class).clone();
        dicInfoDaoConfig.initIdentityScope(type);

        companyAccountInfoDao = new CompanyAccountInfoDao(companyAccountInfoDaoConfig, this);
        companyClientInfoDao = new CompanyClientInfoDao(companyClientInfoDaoConfig, this);
        companyInfoDao = new CompanyInfoDao(companyInfoDaoConfig, this);
        companyProductInfoDao = new CompanyProductInfoDao(companyProductInfoDaoConfig, this);
        companySupplierInfoDao = new CompanySupplierInfoDao(companySupplierInfoDaoConfig, this);
        companyDeleteAccountInfoDao = new CompanyDeleteAccountInfoDao(companyDeleteAccountInfoDaoConfig, this);
        companyDeleteClientInfoDao = new CompanyDeleteClientInfoDao(companyDeleteClientInfoDaoConfig, this);
        companyDeleteInfoDao = new CompanyDeleteInfoDao(companyDeleteInfoDaoConfig, this);
        companyDeleteProductInfoDao = new CompanyDeleteProductInfoDao(companyDeleteProductInfoDaoConfig, this);
        companyDeleteSupplierInfoDao = new CompanyDeleteSupplierInfoDao(companyDeleteSupplierInfoDaoConfig, this);
        dicInfoDao = new DicInfoDao(dicInfoDaoConfig, this);

        registerDao(CompanyAccountInfo.class, companyAccountInfoDao);
        registerDao(CompanyClientInfo.class, companyClientInfoDao);
        registerDao(CompanyInfo.class, companyInfoDao);
        registerDao(CompanyProductInfo.class, companyProductInfoDao);
        registerDao(CompanySupplierInfo.class, companySupplierInfoDao);
        registerDao(CompanyDeleteAccountInfo.class, companyDeleteAccountInfoDao);
        registerDao(CompanyDeleteClientInfo.class, companyDeleteClientInfoDao);
        registerDao(CompanyDeleteInfo.class, companyDeleteInfoDao);
        registerDao(CompanyDeleteProductInfo.class, companyDeleteProductInfoDao);
        registerDao(CompanyDeleteSupplierInfo.class, companyDeleteSupplierInfoDao);
        registerDao(DicInfo.class, dicInfoDao);
    }
    
    public void clear() {
        companyAccountInfoDaoConfig.clearIdentityScope();
        companyClientInfoDaoConfig.clearIdentityScope();
        companyInfoDaoConfig.clearIdentityScope();
        companyProductInfoDaoConfig.clearIdentityScope();
        companySupplierInfoDaoConfig.clearIdentityScope();
        companyDeleteAccountInfoDaoConfig.clearIdentityScope();
        companyDeleteClientInfoDaoConfig.clearIdentityScope();
        companyDeleteInfoDaoConfig.clearIdentityScope();
        companyDeleteProductInfoDaoConfig.clearIdentityScope();
        companyDeleteSupplierInfoDaoConfig.clearIdentityScope();
        dicInfoDaoConfig.clearIdentityScope();
    }

    public CompanyAccountInfoDao getCompanyAccountInfoDao() {
        return companyAccountInfoDao;
    }

    public CompanyClientInfoDao getCompanyClientInfoDao() {
        return companyClientInfoDao;
    }

    public CompanyInfoDao getCompanyInfoDao() {
        return companyInfoDao;
    }

    public CompanyProductInfoDao getCompanyProductInfoDao() {
        return companyProductInfoDao;
    }

    public CompanySupplierInfoDao getCompanySupplierInfoDao() {
        return companySupplierInfoDao;
    }

    public CompanyDeleteAccountInfoDao getCompanyDeleteAccountInfoDao() {
        return companyDeleteAccountInfoDao;
    }

    public CompanyDeleteClientInfoDao getCompanyDeleteClientInfoDao() {
        return companyDeleteClientInfoDao;
    }

    public CompanyDeleteInfoDao getCompanyDeleteInfoDao() {
        return companyDeleteInfoDao;
    }

    public CompanyDeleteProductInfoDao getCompanyDeleteProductInfoDao() {
        return companyDeleteProductInfoDao;
    }

    public CompanyDeleteSupplierInfoDao getCompanyDeleteSupplierInfoDao() {
        return companyDeleteSupplierInfoDao;
    }

    public DicInfoDao getDicInfoDao() {
        return dicInfoDao;
    }

}
