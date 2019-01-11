package com.africa.crm.businessmanagement.main.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyClientInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyDeliveryOrderInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInventoryInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyPayOrderInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyPdfInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyProductInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyPurchasingOrderInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyQuotationInfo;
import com.africa.crm.businessmanagement.main.bean.CompanySalesOrderInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyServiceRecordInfo;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyTaskInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyTradingOrderInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteAccountInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteClientInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteContactInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteDeliveryOrderInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeletePayOrderInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeletePdfInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteProductInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeletePurchasingOrderInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteQuotationInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteSalesOrderInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteServiceRecordInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteSupplierInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteTaskInfo;
import com.africa.crm.businessmanagement.main.bean.delete.CompanyDeleteTradingOrderInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;

import com.africa.crm.businessmanagement.main.dao.CompanyAccountInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyClientInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyContactInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeliveryOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyInventoryInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyPayOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyPdfInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyProductInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyPurchasingOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyQuotationInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanySalesOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyServiceRecordInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanySupplierInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyTaskInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyTradingOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteAccountInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteClientInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteContactInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteDeliveryOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeletePayOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeletePdfInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteProductInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeletePurchasingOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteQuotationInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteSalesOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteServiceRecordInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteSupplierInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteTaskInfoDao;
import com.africa.crm.businessmanagement.main.dao.CompanyDeleteTradingOrderInfoDao;
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
    private final DaoConfig companyContactInfoDaoConfig;
    private final DaoConfig companyDeliveryOrderInfoDaoConfig;
    private final DaoConfig companyInfoDaoConfig;
    private final DaoConfig companyInventoryInfoDaoConfig;
    private final DaoConfig companyPayOrderInfoDaoConfig;
    private final DaoConfig companyPdfInfoDaoConfig;
    private final DaoConfig companyProductInfoDaoConfig;
    private final DaoConfig companyPurchasingOrderInfoDaoConfig;
    private final DaoConfig companyQuotationInfoDaoConfig;
    private final DaoConfig companySalesOrderInfoDaoConfig;
    private final DaoConfig companyServiceRecordInfoDaoConfig;
    private final DaoConfig companySupplierInfoDaoConfig;
    private final DaoConfig companyTaskInfoDaoConfig;
    private final DaoConfig companyTradingOrderInfoDaoConfig;
    private final DaoConfig companyDeleteAccountInfoDaoConfig;
    private final DaoConfig companyDeleteClientInfoDaoConfig;
    private final DaoConfig companyDeleteContactInfoDaoConfig;
    private final DaoConfig companyDeleteDeliveryOrderInfoDaoConfig;
    private final DaoConfig companyDeleteInfoDaoConfig;
    private final DaoConfig companyDeletePayOrderInfoDaoConfig;
    private final DaoConfig companyDeletePdfInfoDaoConfig;
    private final DaoConfig companyDeleteProductInfoDaoConfig;
    private final DaoConfig companyDeletePurchasingOrderInfoDaoConfig;
    private final DaoConfig companyDeleteQuotationInfoDaoConfig;
    private final DaoConfig companyDeleteSalesOrderInfoDaoConfig;
    private final DaoConfig companyDeleteServiceRecordInfoDaoConfig;
    private final DaoConfig companyDeleteSupplierInfoDaoConfig;
    private final DaoConfig companyDeleteTaskInfoDaoConfig;
    private final DaoConfig companyDeleteTradingOrderInfoDaoConfig;
    private final DaoConfig dicInfoDaoConfig;

    private final CompanyAccountInfoDao companyAccountInfoDao;
    private final CompanyClientInfoDao companyClientInfoDao;
    private final CompanyContactInfoDao companyContactInfoDao;
    private final CompanyDeliveryOrderInfoDao companyDeliveryOrderInfoDao;
    private final CompanyInfoDao companyInfoDao;
    private final CompanyInventoryInfoDao companyInventoryInfoDao;
    private final CompanyPayOrderInfoDao companyPayOrderInfoDao;
    private final CompanyPdfInfoDao companyPdfInfoDao;
    private final CompanyProductInfoDao companyProductInfoDao;
    private final CompanyPurchasingOrderInfoDao companyPurchasingOrderInfoDao;
    private final CompanyQuotationInfoDao companyQuotationInfoDao;
    private final CompanySalesOrderInfoDao companySalesOrderInfoDao;
    private final CompanyServiceRecordInfoDao companyServiceRecordInfoDao;
    private final CompanySupplierInfoDao companySupplierInfoDao;
    private final CompanyTaskInfoDao companyTaskInfoDao;
    private final CompanyTradingOrderInfoDao companyTradingOrderInfoDao;
    private final CompanyDeleteAccountInfoDao companyDeleteAccountInfoDao;
    private final CompanyDeleteClientInfoDao companyDeleteClientInfoDao;
    private final CompanyDeleteContactInfoDao companyDeleteContactInfoDao;
    private final CompanyDeleteDeliveryOrderInfoDao companyDeleteDeliveryOrderInfoDao;
    private final CompanyDeleteInfoDao companyDeleteInfoDao;
    private final CompanyDeletePayOrderInfoDao companyDeletePayOrderInfoDao;
    private final CompanyDeletePdfInfoDao companyDeletePdfInfoDao;
    private final CompanyDeleteProductInfoDao companyDeleteProductInfoDao;
    private final CompanyDeletePurchasingOrderInfoDao companyDeletePurchasingOrderInfoDao;
    private final CompanyDeleteQuotationInfoDao companyDeleteQuotationInfoDao;
    private final CompanyDeleteSalesOrderInfoDao companyDeleteSalesOrderInfoDao;
    private final CompanyDeleteServiceRecordInfoDao companyDeleteServiceRecordInfoDao;
    private final CompanyDeleteSupplierInfoDao companyDeleteSupplierInfoDao;
    private final CompanyDeleteTaskInfoDao companyDeleteTaskInfoDao;
    private final CompanyDeleteTradingOrderInfoDao companyDeleteTradingOrderInfoDao;
    private final DicInfoDao dicInfoDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        companyAccountInfoDaoConfig = daoConfigMap.get(CompanyAccountInfoDao.class).clone();
        companyAccountInfoDaoConfig.initIdentityScope(type);

        companyClientInfoDaoConfig = daoConfigMap.get(CompanyClientInfoDao.class).clone();
        companyClientInfoDaoConfig.initIdentityScope(type);

        companyContactInfoDaoConfig = daoConfigMap.get(CompanyContactInfoDao.class).clone();
        companyContactInfoDaoConfig.initIdentityScope(type);

        companyDeliveryOrderInfoDaoConfig = daoConfigMap.get(CompanyDeliveryOrderInfoDao.class).clone();
        companyDeliveryOrderInfoDaoConfig.initIdentityScope(type);

        companyInfoDaoConfig = daoConfigMap.get(CompanyInfoDao.class).clone();
        companyInfoDaoConfig.initIdentityScope(type);

        companyInventoryInfoDaoConfig = daoConfigMap.get(CompanyInventoryInfoDao.class).clone();
        companyInventoryInfoDaoConfig.initIdentityScope(type);

        companyPayOrderInfoDaoConfig = daoConfigMap.get(CompanyPayOrderInfoDao.class).clone();
        companyPayOrderInfoDaoConfig.initIdentityScope(type);

        companyPdfInfoDaoConfig = daoConfigMap.get(CompanyPdfInfoDao.class).clone();
        companyPdfInfoDaoConfig.initIdentityScope(type);

        companyProductInfoDaoConfig = daoConfigMap.get(CompanyProductInfoDao.class).clone();
        companyProductInfoDaoConfig.initIdentityScope(type);

        companyPurchasingOrderInfoDaoConfig = daoConfigMap.get(CompanyPurchasingOrderInfoDao.class).clone();
        companyPurchasingOrderInfoDaoConfig.initIdentityScope(type);

        companyQuotationInfoDaoConfig = daoConfigMap.get(CompanyQuotationInfoDao.class).clone();
        companyQuotationInfoDaoConfig.initIdentityScope(type);

        companySalesOrderInfoDaoConfig = daoConfigMap.get(CompanySalesOrderInfoDao.class).clone();
        companySalesOrderInfoDaoConfig.initIdentityScope(type);

        companyServiceRecordInfoDaoConfig = daoConfigMap.get(CompanyServiceRecordInfoDao.class).clone();
        companyServiceRecordInfoDaoConfig.initIdentityScope(type);

        companySupplierInfoDaoConfig = daoConfigMap.get(CompanySupplierInfoDao.class).clone();
        companySupplierInfoDaoConfig.initIdentityScope(type);

        companyTaskInfoDaoConfig = daoConfigMap.get(CompanyTaskInfoDao.class).clone();
        companyTaskInfoDaoConfig.initIdentityScope(type);

        companyTradingOrderInfoDaoConfig = daoConfigMap.get(CompanyTradingOrderInfoDao.class).clone();
        companyTradingOrderInfoDaoConfig.initIdentityScope(type);

        companyDeleteAccountInfoDaoConfig = daoConfigMap.get(CompanyDeleteAccountInfoDao.class).clone();
        companyDeleteAccountInfoDaoConfig.initIdentityScope(type);

        companyDeleteClientInfoDaoConfig = daoConfigMap.get(CompanyDeleteClientInfoDao.class).clone();
        companyDeleteClientInfoDaoConfig.initIdentityScope(type);

        companyDeleteContactInfoDaoConfig = daoConfigMap.get(CompanyDeleteContactInfoDao.class).clone();
        companyDeleteContactInfoDaoConfig.initIdentityScope(type);

        companyDeleteDeliveryOrderInfoDaoConfig = daoConfigMap.get(CompanyDeleteDeliveryOrderInfoDao.class).clone();
        companyDeleteDeliveryOrderInfoDaoConfig.initIdentityScope(type);

        companyDeleteInfoDaoConfig = daoConfigMap.get(CompanyDeleteInfoDao.class).clone();
        companyDeleteInfoDaoConfig.initIdentityScope(type);

        companyDeletePayOrderInfoDaoConfig = daoConfigMap.get(CompanyDeletePayOrderInfoDao.class).clone();
        companyDeletePayOrderInfoDaoConfig.initIdentityScope(type);

        companyDeletePdfInfoDaoConfig = daoConfigMap.get(CompanyDeletePdfInfoDao.class).clone();
        companyDeletePdfInfoDaoConfig.initIdentityScope(type);

        companyDeleteProductInfoDaoConfig = daoConfigMap.get(CompanyDeleteProductInfoDao.class).clone();
        companyDeleteProductInfoDaoConfig.initIdentityScope(type);

        companyDeletePurchasingOrderInfoDaoConfig = daoConfigMap.get(CompanyDeletePurchasingOrderInfoDao.class).clone();
        companyDeletePurchasingOrderInfoDaoConfig.initIdentityScope(type);

        companyDeleteQuotationInfoDaoConfig = daoConfigMap.get(CompanyDeleteQuotationInfoDao.class).clone();
        companyDeleteQuotationInfoDaoConfig.initIdentityScope(type);

        companyDeleteSalesOrderInfoDaoConfig = daoConfigMap.get(CompanyDeleteSalesOrderInfoDao.class).clone();
        companyDeleteSalesOrderInfoDaoConfig.initIdentityScope(type);

        companyDeleteServiceRecordInfoDaoConfig = daoConfigMap.get(CompanyDeleteServiceRecordInfoDao.class).clone();
        companyDeleteServiceRecordInfoDaoConfig.initIdentityScope(type);

        companyDeleteSupplierInfoDaoConfig = daoConfigMap.get(CompanyDeleteSupplierInfoDao.class).clone();
        companyDeleteSupplierInfoDaoConfig.initIdentityScope(type);

        companyDeleteTaskInfoDaoConfig = daoConfigMap.get(CompanyDeleteTaskInfoDao.class).clone();
        companyDeleteTaskInfoDaoConfig.initIdentityScope(type);

        companyDeleteTradingOrderInfoDaoConfig = daoConfigMap.get(CompanyDeleteTradingOrderInfoDao.class).clone();
        companyDeleteTradingOrderInfoDaoConfig.initIdentityScope(type);

        dicInfoDaoConfig = daoConfigMap.get(DicInfoDao.class).clone();
        dicInfoDaoConfig.initIdentityScope(type);

        companyAccountInfoDao = new CompanyAccountInfoDao(companyAccountInfoDaoConfig, this);
        companyClientInfoDao = new CompanyClientInfoDao(companyClientInfoDaoConfig, this);
        companyContactInfoDao = new CompanyContactInfoDao(companyContactInfoDaoConfig, this);
        companyDeliveryOrderInfoDao = new CompanyDeliveryOrderInfoDao(companyDeliveryOrderInfoDaoConfig, this);
        companyInfoDao = new CompanyInfoDao(companyInfoDaoConfig, this);
        companyInventoryInfoDao = new CompanyInventoryInfoDao(companyInventoryInfoDaoConfig, this);
        companyPayOrderInfoDao = new CompanyPayOrderInfoDao(companyPayOrderInfoDaoConfig, this);
        companyPdfInfoDao = new CompanyPdfInfoDao(companyPdfInfoDaoConfig, this);
        companyProductInfoDao = new CompanyProductInfoDao(companyProductInfoDaoConfig, this);
        companyPurchasingOrderInfoDao = new CompanyPurchasingOrderInfoDao(companyPurchasingOrderInfoDaoConfig, this);
        companyQuotationInfoDao = new CompanyQuotationInfoDao(companyQuotationInfoDaoConfig, this);
        companySalesOrderInfoDao = new CompanySalesOrderInfoDao(companySalesOrderInfoDaoConfig, this);
        companyServiceRecordInfoDao = new CompanyServiceRecordInfoDao(companyServiceRecordInfoDaoConfig, this);
        companySupplierInfoDao = new CompanySupplierInfoDao(companySupplierInfoDaoConfig, this);
        companyTaskInfoDao = new CompanyTaskInfoDao(companyTaskInfoDaoConfig, this);
        companyTradingOrderInfoDao = new CompanyTradingOrderInfoDao(companyTradingOrderInfoDaoConfig, this);
        companyDeleteAccountInfoDao = new CompanyDeleteAccountInfoDao(companyDeleteAccountInfoDaoConfig, this);
        companyDeleteClientInfoDao = new CompanyDeleteClientInfoDao(companyDeleteClientInfoDaoConfig, this);
        companyDeleteContactInfoDao = new CompanyDeleteContactInfoDao(companyDeleteContactInfoDaoConfig, this);
        companyDeleteDeliveryOrderInfoDao = new CompanyDeleteDeliveryOrderInfoDao(companyDeleteDeliveryOrderInfoDaoConfig, this);
        companyDeleteInfoDao = new CompanyDeleteInfoDao(companyDeleteInfoDaoConfig, this);
        companyDeletePayOrderInfoDao = new CompanyDeletePayOrderInfoDao(companyDeletePayOrderInfoDaoConfig, this);
        companyDeletePdfInfoDao = new CompanyDeletePdfInfoDao(companyDeletePdfInfoDaoConfig, this);
        companyDeleteProductInfoDao = new CompanyDeleteProductInfoDao(companyDeleteProductInfoDaoConfig, this);
        companyDeletePurchasingOrderInfoDao = new CompanyDeletePurchasingOrderInfoDao(companyDeletePurchasingOrderInfoDaoConfig, this);
        companyDeleteQuotationInfoDao = new CompanyDeleteQuotationInfoDao(companyDeleteQuotationInfoDaoConfig, this);
        companyDeleteSalesOrderInfoDao = new CompanyDeleteSalesOrderInfoDao(companyDeleteSalesOrderInfoDaoConfig, this);
        companyDeleteServiceRecordInfoDao = new CompanyDeleteServiceRecordInfoDao(companyDeleteServiceRecordInfoDaoConfig, this);
        companyDeleteSupplierInfoDao = new CompanyDeleteSupplierInfoDao(companyDeleteSupplierInfoDaoConfig, this);
        companyDeleteTaskInfoDao = new CompanyDeleteTaskInfoDao(companyDeleteTaskInfoDaoConfig, this);
        companyDeleteTradingOrderInfoDao = new CompanyDeleteTradingOrderInfoDao(companyDeleteTradingOrderInfoDaoConfig, this);
        dicInfoDao = new DicInfoDao(dicInfoDaoConfig, this);

        registerDao(CompanyAccountInfo.class, companyAccountInfoDao);
        registerDao(CompanyClientInfo.class, companyClientInfoDao);
        registerDao(CompanyContactInfo.class, companyContactInfoDao);
        registerDao(CompanyDeliveryOrderInfo.class, companyDeliveryOrderInfoDao);
        registerDao(CompanyInfo.class, companyInfoDao);
        registerDao(CompanyInventoryInfo.class, companyInventoryInfoDao);
        registerDao(CompanyPayOrderInfo.class, companyPayOrderInfoDao);
        registerDao(CompanyPdfInfo.class, companyPdfInfoDao);
        registerDao(CompanyProductInfo.class, companyProductInfoDao);
        registerDao(CompanyPurchasingOrderInfo.class, companyPurchasingOrderInfoDao);
        registerDao(CompanyQuotationInfo.class, companyQuotationInfoDao);
        registerDao(CompanySalesOrderInfo.class, companySalesOrderInfoDao);
        registerDao(CompanyServiceRecordInfo.class, companyServiceRecordInfoDao);
        registerDao(CompanySupplierInfo.class, companySupplierInfoDao);
        registerDao(CompanyTaskInfo.class, companyTaskInfoDao);
        registerDao(CompanyTradingOrderInfo.class, companyTradingOrderInfoDao);
        registerDao(CompanyDeleteAccountInfo.class, companyDeleteAccountInfoDao);
        registerDao(CompanyDeleteClientInfo.class, companyDeleteClientInfoDao);
        registerDao(CompanyDeleteContactInfo.class, companyDeleteContactInfoDao);
        registerDao(CompanyDeleteDeliveryOrderInfo.class, companyDeleteDeliveryOrderInfoDao);
        registerDao(CompanyDeleteInfo.class, companyDeleteInfoDao);
        registerDao(CompanyDeletePayOrderInfo.class, companyDeletePayOrderInfoDao);
        registerDao(CompanyDeletePdfInfo.class, companyDeletePdfInfoDao);
        registerDao(CompanyDeleteProductInfo.class, companyDeleteProductInfoDao);
        registerDao(CompanyDeletePurchasingOrderInfo.class, companyDeletePurchasingOrderInfoDao);
        registerDao(CompanyDeleteQuotationInfo.class, companyDeleteQuotationInfoDao);
        registerDao(CompanyDeleteSalesOrderInfo.class, companyDeleteSalesOrderInfoDao);
        registerDao(CompanyDeleteServiceRecordInfo.class, companyDeleteServiceRecordInfoDao);
        registerDao(CompanyDeleteSupplierInfo.class, companyDeleteSupplierInfoDao);
        registerDao(CompanyDeleteTaskInfo.class, companyDeleteTaskInfoDao);
        registerDao(CompanyDeleteTradingOrderInfo.class, companyDeleteTradingOrderInfoDao);
        registerDao(DicInfo.class, dicInfoDao);
    }
    
    public void clear() {
        companyAccountInfoDaoConfig.clearIdentityScope();
        companyClientInfoDaoConfig.clearIdentityScope();
        companyContactInfoDaoConfig.clearIdentityScope();
        companyDeliveryOrderInfoDaoConfig.clearIdentityScope();
        companyInfoDaoConfig.clearIdentityScope();
        companyInventoryInfoDaoConfig.clearIdentityScope();
        companyPayOrderInfoDaoConfig.clearIdentityScope();
        companyPdfInfoDaoConfig.clearIdentityScope();
        companyProductInfoDaoConfig.clearIdentityScope();
        companyPurchasingOrderInfoDaoConfig.clearIdentityScope();
        companyQuotationInfoDaoConfig.clearIdentityScope();
        companySalesOrderInfoDaoConfig.clearIdentityScope();
        companyServiceRecordInfoDaoConfig.clearIdentityScope();
        companySupplierInfoDaoConfig.clearIdentityScope();
        companyTaskInfoDaoConfig.clearIdentityScope();
        companyTradingOrderInfoDaoConfig.clearIdentityScope();
        companyDeleteAccountInfoDaoConfig.clearIdentityScope();
        companyDeleteClientInfoDaoConfig.clearIdentityScope();
        companyDeleteContactInfoDaoConfig.clearIdentityScope();
        companyDeleteDeliveryOrderInfoDaoConfig.clearIdentityScope();
        companyDeleteInfoDaoConfig.clearIdentityScope();
        companyDeletePayOrderInfoDaoConfig.clearIdentityScope();
        companyDeletePdfInfoDaoConfig.clearIdentityScope();
        companyDeleteProductInfoDaoConfig.clearIdentityScope();
        companyDeletePurchasingOrderInfoDaoConfig.clearIdentityScope();
        companyDeleteQuotationInfoDaoConfig.clearIdentityScope();
        companyDeleteSalesOrderInfoDaoConfig.clearIdentityScope();
        companyDeleteServiceRecordInfoDaoConfig.clearIdentityScope();
        companyDeleteSupplierInfoDaoConfig.clearIdentityScope();
        companyDeleteTaskInfoDaoConfig.clearIdentityScope();
        companyDeleteTradingOrderInfoDaoConfig.clearIdentityScope();
        dicInfoDaoConfig.clearIdentityScope();
    }

    public CompanyAccountInfoDao getCompanyAccountInfoDao() {
        return companyAccountInfoDao;
    }

    public CompanyClientInfoDao getCompanyClientInfoDao() {
        return companyClientInfoDao;
    }

    public CompanyContactInfoDao getCompanyContactInfoDao() {
        return companyContactInfoDao;
    }

    public CompanyDeliveryOrderInfoDao getCompanyDeliveryOrderInfoDao() {
        return companyDeliveryOrderInfoDao;
    }

    public CompanyInfoDao getCompanyInfoDao() {
        return companyInfoDao;
    }

    public CompanyInventoryInfoDao getCompanyInventoryInfoDao() {
        return companyInventoryInfoDao;
    }

    public CompanyPayOrderInfoDao getCompanyPayOrderInfoDao() {
        return companyPayOrderInfoDao;
    }

    public CompanyPdfInfoDao getCompanyPdfInfoDao() {
        return companyPdfInfoDao;
    }

    public CompanyProductInfoDao getCompanyProductInfoDao() {
        return companyProductInfoDao;
    }

    public CompanyPurchasingOrderInfoDao getCompanyPurchasingOrderInfoDao() {
        return companyPurchasingOrderInfoDao;
    }

    public CompanyQuotationInfoDao getCompanyQuotationInfoDao() {
        return companyQuotationInfoDao;
    }

    public CompanySalesOrderInfoDao getCompanySalesOrderInfoDao() {
        return companySalesOrderInfoDao;
    }

    public CompanyServiceRecordInfoDao getCompanyServiceRecordInfoDao() {
        return companyServiceRecordInfoDao;
    }

    public CompanySupplierInfoDao getCompanySupplierInfoDao() {
        return companySupplierInfoDao;
    }

    public CompanyTaskInfoDao getCompanyTaskInfoDao() {
        return companyTaskInfoDao;
    }

    public CompanyTradingOrderInfoDao getCompanyTradingOrderInfoDao() {
        return companyTradingOrderInfoDao;
    }

    public CompanyDeleteAccountInfoDao getCompanyDeleteAccountInfoDao() {
        return companyDeleteAccountInfoDao;
    }

    public CompanyDeleteClientInfoDao getCompanyDeleteClientInfoDao() {
        return companyDeleteClientInfoDao;
    }

    public CompanyDeleteContactInfoDao getCompanyDeleteContactInfoDao() {
        return companyDeleteContactInfoDao;
    }

    public CompanyDeleteDeliveryOrderInfoDao getCompanyDeleteDeliveryOrderInfoDao() {
        return companyDeleteDeliveryOrderInfoDao;
    }

    public CompanyDeleteInfoDao getCompanyDeleteInfoDao() {
        return companyDeleteInfoDao;
    }

    public CompanyDeletePayOrderInfoDao getCompanyDeletePayOrderInfoDao() {
        return companyDeletePayOrderInfoDao;
    }

    public CompanyDeletePdfInfoDao getCompanyDeletePdfInfoDao() {
        return companyDeletePdfInfoDao;
    }

    public CompanyDeleteProductInfoDao getCompanyDeleteProductInfoDao() {
        return companyDeleteProductInfoDao;
    }

    public CompanyDeletePurchasingOrderInfoDao getCompanyDeletePurchasingOrderInfoDao() {
        return companyDeletePurchasingOrderInfoDao;
    }

    public CompanyDeleteQuotationInfoDao getCompanyDeleteQuotationInfoDao() {
        return companyDeleteQuotationInfoDao;
    }

    public CompanyDeleteSalesOrderInfoDao getCompanyDeleteSalesOrderInfoDao() {
        return companyDeleteSalesOrderInfoDao;
    }

    public CompanyDeleteServiceRecordInfoDao getCompanyDeleteServiceRecordInfoDao() {
        return companyDeleteServiceRecordInfoDao;
    }

    public CompanyDeleteSupplierInfoDao getCompanyDeleteSupplierInfoDao() {
        return companyDeleteSupplierInfoDao;
    }

    public CompanyDeleteTaskInfoDao getCompanyDeleteTaskInfoDao() {
        return companyDeleteTaskInfoDao;
    }

    public CompanyDeleteTradingOrderInfoDao getCompanyDeleteTradingOrderInfoDao() {
        return companyDeleteTradingOrderInfoDao;
    }

    public DicInfoDao getDicInfoDao() {
        return dicInfoDao;
    }

}
