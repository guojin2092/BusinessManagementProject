package com.africa.crm.businessmanagement.network;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyClientInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyClientInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyDeliveryOrderInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyDeliveryOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyExpenditureInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyExpenditureInfoB;
import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyInventoryInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInventoryInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyPackagingDataInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyPackagingDataInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyPayOrderInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyPayOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyPdfInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyPdfInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyProductInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyProductInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyPurchasingOrderInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyPurchasingOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyQuotationInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyQuotationInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanySalesOrderInfo;
import com.africa.crm.businessmanagement.main.bean.CompanySalesOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyServiceRecordInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyServiceRecordInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfo;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyTaskInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyTaskInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyTradingOrderInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyTradingOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyUserInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyeExpenditureInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyeExpenditureInfoBeanB;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.LoginInfoBean;
import com.africa.crm.businessmanagement.main.bean.MainStationInfoBean;
import com.africa.crm.businessmanagement.main.bean.PayRecordInfo;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleLimitInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleManagementInfoBean;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.main.bean.delete.WebInfoBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 15:26
 * Modification  History:
 * Why & What is modified:
 */
public class DataManager implements HttpHelper {
    private static final DataManager INSTANCE = new DataManager();
    private HttpHelperImpl mHttpHelper;

    public static DataManager newInstance() {
        return INSTANCE;
    }

    private DataManager() {
        mHttpHelper = HttpHelperImpl.getInstance();
    }

    @Override
    public Observable<LoginInfoBean> getLoginInfo(String userName, String passWord) {
        return mHttpHelper.getLoginInfo(userName, passWord);
    }

    @Override
    public Observable<BaseEntity> changePassword(String id, String oldPassWord, String newPassWord) {
        return mHttpHelper.changePassword(id, oldPassWord, newPassWord);
    }

    @Override
    public Observable<List<DicInfo>> getDicByCode(String code) {
        return mHttpHelper.getDicByCode(code);
    }

    @Override
    public Observable<List<MainStationInfoBean>> getMainStationInfo(String id) {
        return mHttpHelper.getMainStationInfo(id);
    }

    @Override
    public Observable<UserManagementInfoBean> getUserList(int page, int rows, String userName, String type, String companyId, String state, String name) {
        return mHttpHelper.getUserList(page, rows, userName, type, companyId, state, name);
    }

    @Override
    public Observable<BaseEntity> deleteUser(String id) {
        return mHttpHelper.deleteUser(id);
    }

    @Override
    public Observable<CompanyUserInfoBean> getUserInfo(String id) {
        return mHttpHelper.getUserInfo(id);
    }

    @Override
    public Observable<UploadInfoBean> saveOrcreateUser(String id, String userName, String type, String roleIds, String passWord, String name, String phone, String address, String email, String state, String companyId, String head) {
        return mHttpHelper.saveOrcreateUser(id, userName, type, roleIds, passWord, name, phone, address, email, state, companyId, head);
    }

    @Override
    public Observable<List<DicInfo2>> getAllCompanyUser(String companyId) {
        return mHttpHelper.getAllCompanyUser(companyId);
    }

    @Override
    public Observable<List<DicInfo2>> getAllCompany(String name) {
        return mHttpHelper.getAllCompany(name);
    }

    @Override
    public Observable<List<RoleInfoBean>> getAllRoles() {
        return mHttpHelper.getAllRoles();
    }

    @Override
    public Observable<RoleManagementInfoBean> getRoleList(int page, int rows, String roleName, String roleCode, String typeName) {
        return mHttpHelper.getRoleList(page, rows, roleName, roleCode, typeName);
    }

    @Override
    public Observable<RoleInfoBean> getRoleInfo(String id) {
        return mHttpHelper.getRoleInfo(id);
    }

    @Override
    public Observable<BaseEntity> saveRoleInfo(String userId, String id, String roleName, String roleCode, String typeName, String orderNum) {
        return mHttpHelper.saveRoleInfo(userId, id, roleName, roleCode, typeName, orderNum);
    }

    @Override
    public Observable<List<RoleLimitInfoBean>> getRoleLimit(String id) {
        return mHttpHelper.getRoleLimit(id);
    }

    @Override
    public Observable<BaseEntity> saveRoleLimit(String id, String resourceIds, String btnIds) {
        return mHttpHelper.saveRoleLimit(id, resourceIds, btnIds);
    }

    @Override
    public Observable<CompanyInfoBean> getCompanyInfoList(int page, int rows, String name) {
        return mHttpHelper.getCompanyInfoList(page, rows, name);
    }

    @Override
    public Observable<BaseEntity> deleteCompanyInfo(String id) {
        return mHttpHelper.deleteCompanyInfo(id);
    }

    @Override
    public Observable<CompanyInfo> getCompanyInfoDetail(String id) {
        return mHttpHelper.getCompanyInfoDetail(id);
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyInfo(String id, String head, String name, String code, String type, String address, String phone, String email, String mid, String area, String profession, String numA, String state) {
        return mHttpHelper.saveCompanyInfo(id, head, name, code, type, address, phone, email, mid, area, profession, numA, state);
    }

    @Override
    public Observable<CompanyAccountInfoBean> getCompanyAccounList(int page, int rows, String companyId, String userName, String name) {
        return mHttpHelper.getCompanyAccounList(page, rows, companyId, userName, name);
    }

    @Override
    public Observable<BaseEntity> deleteCompanyAccount(String id) {
        return mHttpHelper.deleteCompanyAccount(id);
    }

    @Override
    public Observable<CompanyAccountInfo> getCompanyAccountDetail(String id) {
        return mHttpHelper.getCompanyAccountDetail(id);
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyAccount(String id, String userName, String type, String roleId, String passWord, String name, String phone, String address, String email, String state, String companyId, String head) {
        return mHttpHelper.saveCompanyAccount(id, userName, type, roleId, passWord, name, phone, address, email, state, companyId, head);
    }

    @Override
    public Observable<CompanySupplierInfoBean> getCompanySupplierList(int page, int rows, String companyId, String name, String type) {
        return mHttpHelper.getCompanySupplierList(page, rows, companyId, name, type);
    }

    @Override
    public Observable<BaseEntity> deleteCompanySupplier(String id) {
        return mHttpHelper.deleteCompanySupplier(id);
    }

    @Override
    public Observable<CompanySupplierInfo> getCompanySupplierDetail(String id) {
        return mHttpHelper.getCompanySupplierDetail(id);
    }

    @Override
    public Observable<UploadInfoBean> saveCompanySupplier(String id, String companyId, String head, String name, String type, String address, String phone, String email, String zipCode, String area, String remark) {
        return mHttpHelper.saveCompanySupplier(id, companyId, head, name, type, address, phone, email, zipCode, area, remark);
    }

    @Override
    public Observable<List<DicInfo2>> getAllSuppliers(String companyId) {
        return mHttpHelper.getAllSuppliers(companyId);
    }

    @Override
    public Observable<CompanyContactInfoBean> getCompanyContactList(int page, int rows, String companyId, String userId, String name, String fromType) {
        return mHttpHelper.getCompanyContactList(page, rows, companyId, userId, name, fromType);
    }

    @Override
    public Observable<BaseEntity> deleteCompanyContact(String id) {
        return mHttpHelper.deleteCompanyContact(id);
    }

    @Override
    public Observable<CompanyContactInfo> getContactDetail(String id) {
        return mHttpHelper.getContactDetail(id);
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyContact(String id, String companyId, String userId, String head, String name, String fromType, String address, String mailAddress, String phone, String tel, String email, String job, String remark) {
        return mHttpHelper.saveCompanyContact(id, companyId, userId, head, name, fromType, address, mailAddress, phone, tel, email, job, remark);
    }

    @Override
    public Observable<List<DicInfo2>> getAllContact(String companyId) {
        return mHttpHelper.getAllContact(companyId);
    }

    @Override
    public Observable<CompanyClientInfoBean> getCompanyClientList(int page, int rows, String companyId, String userId, String name, String industry) {
        return mHttpHelper.getCompanyClientList(page, rows, companyId, userId, name, industry);
    }

    @Override
    public Observable<BaseEntity> deleteCompanyClient(String id) {
        return mHttpHelper.deleteCompanyClient(id);
    }

    @Override
    public Observable<CompanyClientInfo> getCompanyClientDetail(String id) {
        return mHttpHelper.getCompanyClientDetail(id);
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyClient(String id, String companyId, String userId, String head, String name, String industry, String address, String workerNum, String tel, String yearIncome, String remark) {
        return mHttpHelper.saveCompanyClient(id, companyId, userId, head, name, industry, address, workerNum, tel, yearIncome, remark);
    }

    @Override
    public Observable<List<DicInfo2>> getAllCustomers(String companyId) {
        return mHttpHelper.getAllCustomers(companyId);
    }

    @Override
    public Observable<CompanyProductInfoBean> getCompanyProductList(int page, int rows, String companyId, String name, String type) {
        return mHttpHelper.getCompanyProductList(page, rows, companyId, name, type);
    }

    @Override
    public Observable<BaseEntity> deleteCompanyProduct(String id) {
        return mHttpHelper.deleteCompanyProduct(id);
    }

    @Override
    public Observable<CompanyProductInfo> getCompanyProductDetail(String id) {
        return mHttpHelper.getCompanyProductDetail(id);
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyProduct(String id, String companyId, String name, String code, String supplierName, String makerName, String type, String unitPrice, String unit, String stockNum, String warnNum, String remark) {
        return mHttpHelper.saveCompanyProduct(id, companyId, name, code, supplierName, makerName, type, unitPrice, unit, stockNum, warnNum, remark);
    }

    @Override
    public Observable<List<DicInfo2>> getAllProducts(String companyId) {
        return mHttpHelper.getAllProducts(companyId);
    }

    @Override
    public Observable<CompanyQuotationInfoBean> getCompanyQuotationList(int page, int rows, String companyId, String userId, String name, String createTimes, String createTimee) {
        return mHttpHelper.getCompanyQuotationList(page, rows, companyId, userId, name, createTimes, createTimee);
    }

    @Override
    public Observable<BaseEntity> deleteCompanyQuotation(String id) {
        return mHttpHelper.deleteCompanyQuotation(id);
    }

    @Override
    public Observable<CompanyQuotationInfo> getCompanyQuotationDetail(String id) {
        return mHttpHelper.getCompanyQuotationDetail(id);
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyQuotation(String id, String companyId, String userId, String name, String customerName, String contactName, String termOfValidity, String price, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark) {
        return mHttpHelper.saveCompanyQuotation(id, companyId, userId, name, customerName, contactName, termOfValidity, price, sendAddress, sendAddressZipCode, destinationAddress, destinationAddressZipCode, products, clause, remark);
    }

    @Override
    public Observable<CompanySalesOrderInfoBean> getCompanySalesOrderList(int page, int rows, String companyId, String userId, String name, String state, String createTimes, String createTimee) {
        return mHttpHelper.getCompanySalesOrderList(page, rows, companyId, userId, name, state, createTimes, createTimee);
    }

    @Override
    public Observable<BaseEntity> deleteCompanySalesOrder(String id) {
        return mHttpHelper.deleteCompanySalesOrder(id);
    }

    @Override
    public Observable<CompanySalesOrderInfo> getCompanySalesOrderDetail(String id) {
        return mHttpHelper.getCompanySalesOrderDetail(id);
    }

    @Override
    public Observable<UploadInfoBean> saveCompanySalesOrder(String id, String companyId, String userId, String name, String customerName, String contactName, String saleCommission, String state, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark) {
        return mHttpHelper.saveCompanySalesOrder(id, companyId, userId, name, customerName, contactName, saleCommission, state, sendAddress, sendAddressZipCode, destinationAddress, destinationAddressZipCode, products, clause, remark);
    }

    @Override
    public Observable<List<DicInfo2>> getAllSaleOrders(String companyId, String userId) {
        return mHttpHelper.getAllSaleOrders(companyId, userId);
    }

    @Override
    public Observable<CompanyTradingOrderInfoBean> getCompanyTradingOrderList(int page, int rows, String companyId, String userId, String name, String createTimes, String createTimee) {
        return mHttpHelper.getCompanyTradingOrderList(page, rows, companyId, userId, name, createTimes, createTimee);
    }

    @Override
    public Observable<BaseEntity> deleteCompanyTradingOrder(String id) {
        return mHttpHelper.deleteCompanyTradingOrder(id);
    }

    @Override
    public Observable<CompanyTradingOrderInfo> getCompanyTradingOrderDetail(String id) {
        return mHttpHelper.getCompanyTradingOrderDetail(id);
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyTradingOrder(String id, String companyId, String userId, String name, String customerName, String price, String estimateProfit, String contactName, String possibility, String clueSource, String remark) {
        return mHttpHelper.saveCompanyTradingOrder(id, companyId, userId, name, customerName, price, estimateProfit, contactName, possibility, clueSource, remark);
    }

    @Override
    public Observable<List<DicInfo2>> getAllTradingOrders(String companyId, String userId) {
        return mHttpHelper.getAllTradingOrders(companyId, userId);
    }

    @Override
    public Observable<CompanyDeliveryOrderInfoBean> getCompanyDeliveryOrderList(int page, int rows, String companyId, String userId, String name, String code, String createTimes, String createTimee) {
        return mHttpHelper.getCompanyDeliveryOrderList(page, rows, companyId, userId, name, code, createTimes, createTimee);
    }

    @Override
    public Observable<BaseEntity> deleteCompanyDeliveryOrder(String id) {
        return mHttpHelper.deleteCompanyDeliveryOrder(id);
    }

    @Override
    public Observable<CompanyDeliveryOrderInfo> getCompanyDeliveryOrderDetail(String id) {
        return mHttpHelper.getCompanyDeliveryOrderDetail(id);
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyDeliveryOrder(String id, String companyId, String userId, String name, String salesOrderId, String logisticsCode, String state, String arriveDate, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark) {
        return mHttpHelper.saveCompanyDeliveryOrder(id, companyId, userId, name, salesOrderId, logisticsCode, state, arriveDate, sendAddress, sendAddressZipCode, destinationAddress, destinationAddressZipCode, products, clause, remark);
    }

    @Override
    public Observable<CompanyPayOrderInfoBean> getCompanyPayOrderList(int page, int rows, String companyId, String userId, String name, String code, String createTimes, String createTimee) {
        return mHttpHelper.getCompanyPayOrderList(page, rows, companyId, userId, name, code, createTimes, createTimee);
    }

    @Override
    public Observable<BaseEntity> deleteCompanyPayOrder(String id) {
        return mHttpHelper.deleteCompanyPayOrder(id);
    }

    @Override
    public Observable<CompanyPayOrderInfo> getCompanyPayOrderDetail(String id) {
        return mHttpHelper.getCompanyPayOrderDetail(id);
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyPayOrder(String id, String companyId, String userId, String name, String salesOrderId, String tradingOrderId, String customerName, String price, String payTime, String hasInvoice, String hasPrint, String invoiceFiles, String remark) {
        return mHttpHelper.saveCompanyPayOrder(id, companyId, userId, name, salesOrderId, tradingOrderId, customerName, price, payTime, hasInvoice, hasPrint, invoiceFiles, remark);
    }

    @Override
    public Observable<CompanyPurchasingOrderInfoBean> getCompanyPurchasingOrderList(int page, int rows, String companyId, String userId, String name, String code, String createTimes, String createTimee) {
        return mHttpHelper.getCompanyPurchasingOrderList(page, rows, companyId, userId, name, code, createTimes, createTimee);
    }

    @Override
    public Observable<BaseEntity> deleteCompanyPurchasingOrder(String id) {
        return mHttpHelper.deleteCompanyPurchasingOrder(id);
    }

    @Override
    public Observable<CompanyPurchasingOrderInfo> getCompanyPurchasingDetail(String id) {
        return mHttpHelper.getCompanyPurchasingDetail(id);
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyPurchasingOrder(String id, String companyId, String userId, String name, String supplierName, String state, String orderDate, String arriveDate, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark) {
        return mHttpHelper.saveCompanyPurchasingOrder(id, companyId, userId, name, supplierName, state, orderDate, arriveDate, sendAddress, sendAddressZipCode, destinationAddress, destinationAddressZipCode, products, clause, remark);
    }

    @Override
    public Observable<CompanyServiceRecordInfoBean> getServiceRecordList(int page, int rows, String companyId, String userId, String name, String state, String type, String createTimes, String createTimee) {
        return mHttpHelper.getServiceRecordList(page, rows, companyId, userId, name, state, type, createTimes, createTimee);
    }

    @Override
    public Observable<BaseEntity> deleteServiceRecord(String id) {
        return mHttpHelper.deleteServiceRecord(id);
    }

    @Override
    public Observable<CompanyServiceRecordInfo> getCompanyServiceRecordDetail(String id) {
        return mHttpHelper.getCompanyServiceRecordDetail(id);
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyServiceRecord(String id, String companyId, String userId, String name, String state, String type, String productId, String customerName, String level, String phone, String email, String reason, String remark, String solution, String track) {
        return mHttpHelper.saveCompanyServiceRecord(id, companyId, userId, name, state, type, productId, customerName, level, phone, email, reason, remark, solution, track);
    }

    @Override
    public Observable<CompanyTaskInfoBean> getCompanyTaskList(int page, int rows, String companyId, String userId, String name, String customerName, String state, String level, String remindTimes, String remindTimee) {
        return mHttpHelper.getCompanyTaskList(page, rows, companyId, userId, name, customerName, state, level, remindTimes, remindTimee);
    }

    @Override
    public Observable<BaseEntity> deleteCompanyTask(String id) {
        return mHttpHelper.deleteCompanyTask(id);
    }

    @Override
    public Observable<CompanyTaskInfo> getCompanyTaskDetail(String id) {
        return mHttpHelper.getCompanyTaskDetail(id);
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyTask(String id, String companyId, String userId, String name, String remindTime, String customerName, String contactName, String level, String state, String remark) {
        return mHttpHelper.saveCompanyTask(id, companyId, userId, name, remindTime, customerName, contactName, level, state, remark);
    }

    @Override
    public Observable<CompanyTaskInfo> getRecentTask(String userId) {
        return mHttpHelper.getRecentTask(userId);
    }

    @Override
    public Observable<BaseEntity> setTaskRead(String id) {
        return mHttpHelper.setTaskRead(id);
    }

    @Override
    public Observable<CompanyInventoryInfoBean> getInventoryList(int page, int rows, String companyId, String productId, String type, String createTimes, String createTimee) {
        return mHttpHelper.getInventoryList(page, rows, companyId, productId, type, createTimes, createTimee);
    }

    @Override
    public Observable<CompanyInventoryInfo> getInventoryDetail(String id) {
        return mHttpHelper.getInventoryDetail(id);
    }

    @Override
    public Observable<UploadInfoBean> saveInventory(String companyId, String productId, String type, String num, String remark) {
        return mHttpHelper.saveInventory(companyId, productId, type, num, remark);
    }

    @Override
    public Observable<CompanyPdfInfoBean> getCompanyPdfList(int page, int rows, String companyId, String userId, String name) {
        return mHttpHelper.getCompanyPdfList(page, rows, companyId, userId, name);
    }

    @Override
    public Observable<BaseEntity> deleteCompanyPdf(String id) {
        return mHttpHelper.deleteCompanyPdf(id);
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyPdfDetail(String id, String companyId, String userId, String name, String code, String remark) {
        return mHttpHelper.saveCompanyPdfDetail(id, companyId, userId, name, code, remark);
    }

    @Override
    public Observable<CompanyPdfInfo> getCompanyPdfDetail(String id) {
        return mHttpHelper.getCompanyPdfDetail(id);
    }

    @Override
    public Observable<FileInfoBean> uploadFiles(String filePath) {
        return mHttpHelper.uploadFiles(filePath);
    }

    @Override
    public Observable<FileInfoBean> uploadImages(String filePath) {
        return mHttpHelper.uploadImages(filePath);
    }

    @Override
    public Observable<ResponseBody> downloadFiles(String code) {
        return mHttpHelper.downloadFiles(code);
    }

    @Override
    public Observable<CompanyPackagingDataInfoBean> getCompanyPackagingDataList(int page, int rows, String companyId, String userId, String createTimes, String createTimee) {
        return mHttpHelper.getCompanyPackagingDataList(page, rows, companyId, userId, createTimes, createTimee);
    }

    @Override
    public Observable<BaseEntity> checkDate(String companyId, String startDate, String endDate) {
        return mHttpHelper.checkDate(companyId, startDate, endDate);
    }

    @Override
    public Observable<CompanyPackagingDataInfo> getPackagingDataDetail(String id) {
        return mHttpHelper.getPackagingDataDetail(id);
    }

    @Override
    public Observable<BaseEntity> savePackagingData(String companyId, String userId, String startDate, String endDate, String num, String previewInfo, String remark) {
        return mHttpHelper.savePackagingData(companyId, userId, startDate, endDate, num, previewInfo, remark);
    }

    @Override
    public Observable<String> getPreviewInfo(String companyId, String startDate, String endDate) {
        return mHttpHelper.getPreviewInfo(companyId, startDate, endDate);
    }

    @Override
    public Observable<CompanyeExpenditureInfoBean> getExpenditureList(int page, int rows, String companyId, String title, String createTimes, String createTimee) {
        return mHttpHelper.getExpenditureList(page, rows, companyId, title, createTimes, createTimee);
    }

    @Override
    public Observable<CompanyExpenditureInfo> getExpenditureDetail(String id) {
        return mHttpHelper.getExpenditureDetail(id);
    }

    @Override
    public Observable<List<PayRecordInfo>> getPayRecord(String estimateId) {
        return mHttpHelper.getPayRecord(estimateId);
    }

    @Override
    public Observable<BaseEntity> checkYsDate(String companyId, String startDate, String endDate) {
        return mHttpHelper.checkYsDate(companyId, startDate, endDate);
    }

    @Override
    public Observable<BaseEntity> saveExpenditureA(String companyId, String userId, String title, String startDate, String endDate, String estimatePrice, String remark) {
        return mHttpHelper.saveExpenditureA(companyId, userId, title, startDate, endDate, estimatePrice, remark);
    }

    @Override
    public Observable<CompanyeExpenditureInfoBeanB> getExpenditureListB(int page, int rows, String companyId, String userId, String payDates, String payDatee) {
        return mHttpHelper.getExpenditureListB(page, rows, companyId, userId, payDates, payDatee);
    }

    @Override
    public Observable<CompanyExpenditureInfoB> getExpenditureDetailB(String id) {
        return mHttpHelper.getExpenditureDetailB(id);
    }

    @Override
    public Observable<UploadInfoBean> saveExpenditureB(String companyId, String userId, String payDate, String price, String remark) {
        return mHttpHelper.saveExpenditureB(companyId, userId, payDate, price, remark);
    }

    @Override
    public Observable<WebInfoBean> getOrderAmountStatisticsReport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        return mHttpHelper.getOrderAmountStatisticsReport(startDate, endDate, sfxsysc, userId, companyId);
    }

    @Override
    public Observable<ResponseBody> getOrderAmountStatisticsExport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        return mHttpHelper.getOrderAmountStatisticsExport(startDate, endDate, sfxsysc, userId, companyId);
    }

    @Override
    public Observable<WebInfoBean> getOrderNumStatisticsReport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        return mHttpHelper.getOrderNumStatisticsReport(startDate, endDate, sfxsysc, userId, companyId);
    }

    @Override
    public Observable<ResponseBody> getOrderNumStatisticsExport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        return mHttpHelper.getOrderNumStatisticsExport(startDate, endDate, sfxsysc, userId, companyId);
    }

    @Override
    public Observable<WebInfoBean> getPayRecordStatisticsReport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        return mHttpHelper.getPayRecordStatisticsReport(startDate, endDate, sfxsysc, userId, companyId);
    }

    @Override
    public Observable<ResponseBody> getPayRecordStatisticsExport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        return mHttpHelper.getPayRecordStatisticsExport(startDate, endDate, sfxsysc, userId, companyId);
    }

    @Override
    public Observable<WebInfoBean> getServiceStatisticsReport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        return mHttpHelper.getServiceStatisticsReport(startDate, endDate, sfxsysc, userId, companyId);
    }

    @Override
    public Observable<ResponseBody> getServiceStatisticsExport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        return mHttpHelper.getServiceStatisticsExport(startDate, endDate, sfxsysc, userId, companyId);
    }

    @Override
    public Observable<WebInfoBean> getStockStatisticsReport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        return mHttpHelper.getStockStatisticsReport(startDate, endDate, sfxsysc, userId, companyId);
    }

    @Override
    public Observable<ResponseBody> getStockStatisticsExport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        return mHttpHelper.getStockStatisticsExport(startDate, endDate, sfxsysc, userId, companyId);
    }

}
