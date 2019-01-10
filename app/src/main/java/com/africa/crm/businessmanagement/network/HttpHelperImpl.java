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
import com.africa.crm.businessmanagement.main.bean.UserInfo;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.network.api.FileApi;
import com.africa.crm.businessmanagement.network.api.LoginApi;
import com.africa.crm.businessmanagement.network.api.MainApi;
import com.africa.crm.businessmanagement.network.retrofit.FileRetrofitHelper;
import com.africa.crm.businessmanagement.network.retrofit.RetrofitHelper;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.io.File;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 14:48
 * Modification  History:
 * Why & What is modified:
 */
public class HttpHelperImpl implements HttpHelper {
    private LoginApi loginApi;
    private MainApi mainApi;
    private FileApi fileApi;

    private static final HttpHelperImpl getInstance = new HttpHelperImpl();

    public static HttpHelperImpl getInstance() {
        return getInstance;
    }

    private HttpHelperImpl() {
        loginApi = RetrofitHelper.provideApi(LoginApi.class);
        mainApi = RetrofitHelper.provideApi(MainApi.class);
        fileApi = FileRetrofitHelper.provideApi(FileApi.class);
    }


    @Override
    public Observable<LoginInfoBean> getLoginInfo(String userName, String passWord) {
        return loginApi.getLoginInfo(userName, passWord).compose(RxUtils.<LoginInfoBean>handleResult());
    }

    @Override
    public Observable<BaseEntity> changePassword(String id, String oldPassWord, String newPassWord) {
        return loginApi.changePassword(id, oldPassWord, newPassWord);
    }

    @Override
    public Observable<List<DicInfo>> getDicByCode(String code) {
        return loginApi.getDicByCode(code).compose(RxUtils.<List<DicInfo>>handleResult());
    }

    @Override
    public Observable<List<MainStationInfoBean>> getMainStationInfo(String id) {
        return mainApi.getMainStationInfo(id).compose(RxUtils.<List<MainStationInfoBean>>handleResult());
    }

    @Override
    public Observable<UserManagementInfoBean> getUserList(int page, int rows, String userName, String type, String companyId, String state, String name) {
        return mainApi.getUserList(page, rows, userName, type, companyId, state, name).compose(RxUtils.<UserManagementInfoBean>handleResult());
    }

    @Override
    public Observable<BaseEntity> deleteUser(String id) {
        return mainApi.deleteUser(id);
    }

    @Override
    public Observable<UserInfo> getUserInfo(String id) {
        return mainApi.getUserInfo(id).compose(RxUtils.<UserInfo>handleResult());
    }

    @Override
    public Observable<BaseEntity> saveOrcreateUser(String id, String userName, String type, String roleIds, String passWord, String name, String phone, String address, String email, String state, String companyId, String head) {
        return mainApi.saveOrcreateUser(id, userName, type, roleIds, passWord, name, phone, address, email, state, companyId, head);
    }

    @Override
    public Observable<List<DicInfo2>> getAllCompanyUser(String companyId) {
        return mainApi.getAllCompanyUser(companyId).compose(RxUtils.<List<DicInfo2>>handleResult());
    }

    @Override
    public Observable<List<DicInfo2>> getAllCompany(String name) {
        return mainApi.getAllCompany(name).compose(RxUtils.<List<DicInfo2>>handleResult());
    }

    @Override
    public Observable<List<RoleInfoBean>> getAllRoles() {
        return mainApi.getAllRoles("").compose(RxUtils.<List<RoleInfoBean>>handleResult());
    }

    @Override
    public Observable<RoleManagementInfoBean> getRoleList(int page, int rows, String roleName, String roleCode, String typeName) {
        return mainApi.getRoleList(page, rows, roleName, roleCode, typeName).compose(RxUtils.<RoleManagementInfoBean>handleResult());
    }

    @Override
    public Observable<RoleInfoBean> getRoleInfo(String id) {
        return mainApi.getRoleInfo(id).compose(RxUtils.<RoleInfoBean>handleResult());
    }

    @Override
    public Observable<BaseEntity> saveRoleInfo(String userId, String id, String roleName, String roleCode, String typeName, String orderNum) {
        return mainApi.saveRoleInfo(userId, id, roleName, roleCode, typeName, orderNum);
    }

    @Override
    public Observable<List<RoleLimitInfoBean>> getRoleLimit(String id) {
        return mainApi.getRoleLimit(id).compose(RxUtils.<List<RoleLimitInfoBean>>handleResult());
    }

    @Override
    public Observable<BaseEntity> saveRoleLimit(String id, String resourceIds, String btnIds) {
        return mainApi.saveRoleLimit(id, resourceIds, btnIds);
    }

    @Override
    public Observable<CompanyInfoBean> getCompanyInfoList(int page, int rows, String name) {
        return mainApi.getCompanyInfoList(page, rows, name).compose(RxUtils.<CompanyInfoBean>handleResult());
    }

    @Override
    public Observable<BaseEntity> deleteCompanyInfo(String id) {
        return mainApi.deleteCompanyInfo(id);
    }

    @Override
    public Observable<CompanyInfo> getCompanyInfoDetail(String id) {
        return mainApi.getCompanyInfoDetail(id).compose(RxUtils.<CompanyInfo>handleResult());
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyInfo(String id, String head, String name, String code, String type, String address, String phone, String email, String mid, String area, String profession, String numA, String state) {
        return mainApi.saveCompanyInfo(id, head, name, code, type, address, phone, email, mid, area, profession, numA, state).compose(RxUtils.<UploadInfoBean>handleResult());
    }

    @Override
    public Observable<CompanyAccountInfoBean> getCompanyAccounList(int page, int rows, String companyId, String userName, String name) {
        return mainApi.getCompanyAccounList(page, rows, companyId, userName, name).compose(RxUtils.<CompanyAccountInfoBean>handleResult());
    }

    @Override
    public Observable<BaseEntity> deleteCompanyAccount(String id) {
        return mainApi.deleteCompanyAccount(id);
    }

    @Override
    public Observable<CompanyAccountInfo> getCompanyAccountDetail(String id) {
        return mainApi.getCompanyAccountDetail(id).compose(RxUtils.<CompanyAccountInfo>handleResult());
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyAccount(String id, String userName, String type, String roleId, String passWord, String name, String phone, String address, String email, String state, String companyId, String head) {
        return mainApi.saveCompanyAccount(id, userName, type, roleId, passWord, name, phone, address, email, state, companyId, head).compose(RxUtils.<UploadInfoBean>handleResult());
    }

    @Override
    public Observable<CompanySupplierInfoBean> getCompanySupplierList(int page, int rows, String companyId, String name, String type) {
        return mainApi.getCompanySupplierList(page, rows, companyId, name, type).compose(RxUtils.<CompanySupplierInfoBean>handleResult());
    }

    @Override
    public Observable<BaseEntity> deleteCompanySupplier(String id) {
        return mainApi.deleteCompanySupplier(id);
    }

    @Override
    public Observable<CompanySupplierInfo> getCompanySupplierDetail(String id) {
        return mainApi.getCompanySupplierDetail(id).compose(RxUtils.<CompanySupplierInfo>handleResult());
    }

    @Override
    public Observable<UploadInfoBean> saveCompanySupplier(String id, String companyId, String head, String name, String type, String address, String phone, String email, String zipCode, String area, String remark) {
        return mainApi.saveCompanySupplier(id, companyId, head, name, type, address, phone, email, zipCode, area, remark).compose(RxUtils.<UploadInfoBean>handleResult());
    }

    @Override
    public Observable<List<DicInfo2>> getAllSuppliers(String companyId) {
        return mainApi.getAllSuppliers(companyId).compose(RxUtils.<List<DicInfo2>>handleResult());
    }

    @Override
    public Observable<CompanyContactInfoBean> getCompanyContactList(int page, int rows, String companyId, String userId, String name, String fromType) {
        return mainApi.getCompanyContactList(page, rows, companyId, userId, name, fromType).compose(RxUtils.<CompanyContactInfoBean>handleResult());
    }

    @Override
    public Observable<BaseEntity> deleteCompanyContact(String id) {
        return mainApi.deleteCompanyContact(id);
    }

    @Override
    public Observable<CompanyContactInfo> getContactDetail(String id) {
        return mainApi.getContactDetail(id).compose(RxUtils.<CompanyContactInfo>handleResult());
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyContact(String id, String companyId, String userId, String head, String name, String fromType, String address, String mailAddress, String phone, String tel, String email, String job, String remark) {
        return mainApi.saveCompanyContact(id, companyId, userId, head, name, fromType, address, mailAddress, phone, tel, email, job, remark).compose(RxUtils.<UploadInfoBean>handleResult());
    }

    @Override
    public Observable<List<DicInfo2>> getAllContact(String companyId) {
        return mainApi.getAllContact(companyId).compose(RxUtils.<List<DicInfo2>>handleResult());
    }

    @Override
    public Observable<CompanyClientInfoBean> getCompanyClientList(int page, int rows, String companyId, String userId, String name, String industry) {
        return mainApi.getCompanyClientList(page, rows, companyId, userId, name, industry).compose(RxUtils.<CompanyClientInfoBean>handleResult());
    }

    @Override
    public Observable<BaseEntity> deleteCompanyClient(String id) {
        return mainApi.deleteCompanyClient(id);
    }

    @Override
    public Observable<CompanyClientInfo> getCompanyClientDetail(String id) {
        return mainApi.getCompanyClientDetail(id).compose(RxUtils.<CompanyClientInfo>handleResult());
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyClient(String id, String companyId, String userId, String head, String name, String industry, String address, String workerNum, String tel, String yearIncome, String remark) {
        return mainApi.saveCompanyClient(id, companyId, userId, head, name, industry, address, workerNum, tel, yearIncome, remark).compose(RxUtils.<UploadInfoBean>handleResult());
    }

    @Override
    public Observable<List<DicInfo2>> getAllCustomers(String companyId) {
        return mainApi.getAllCustomers(companyId).compose(RxUtils.<List<DicInfo2>>handleResult());
    }

    @Override
    public Observable<CompanyProductInfoBean> getCompanyProductList(int page, int rows, String companyId, String name, String type) {
        return mainApi.getCompanyProductList(page, rows, companyId, name, type).compose(RxUtils.<CompanyProductInfoBean>handleResult());
    }

    @Override
    public Observable<BaseEntity> deleteCompanyProduct(String id) {
        return mainApi.deleteCompanyProduct(id);
    }

    @Override
    public Observable<CompanyProductInfo> getCompanyProductDetail(String id) {
        return mainApi.getCompanyProductDetail(id).compose(RxUtils.<CompanyProductInfo>handleResult());
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyProduct(String id, String companyId, String name, String code, String supplierName, String makerName, String type, String unitPrice, String unit, String stockNum, String warnNum, String remark) {
        return mainApi.saveCompanyProduct(id, companyId, name, code, supplierName, makerName, type, unitPrice, unit, stockNum, warnNum, remark).compose(RxUtils.<UploadInfoBean>handleResult());
    }

    @Override
    public Observable<List<DicInfo2>> getAllProducts(String companyId) {
        return mainApi.getAllProducts(companyId).compose(RxUtils.<List<DicInfo2>>handleResult());
    }

    @Override
    public Observable<CompanyQuotationInfoBean> getCompanyQuotationList(int page, int rows, String companyId, String userId, String name, String createTimes, String createTimee) {
        return mainApi.getCompanyQuotationList(page, rows, companyId, userId, name, createTimes, createTimee).compose(RxUtils.<CompanyQuotationInfoBean>handleResult());
    }

    @Override
    public Observable<BaseEntity> deleteCompanyQuotation(String id) {
        return mainApi.deleteCompanyQuotation(id);
    }

    @Override
    public Observable<CompanyQuotationInfo> getCompanyQuotationDetail(String id) {
        return mainApi.getCompanyQuotationDetail(id).compose(RxUtils.<CompanyQuotationInfo>handleResult());
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyQuotation(String id, String companyId, String userId, String name, String customerName, String contactName, String termOfValidity, String price, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark) {
        return mainApi.saveCompanyQuotation(id, companyId, userId, name, customerName, contactName, termOfValidity, price, sendAddress, sendAddressZipCode, destinationAddress, destinationAddressZipCode, products, clause, remark).compose(RxUtils.<UploadInfoBean>handleResult());
    }

    @Override
    public Observable<CompanySalesOrderInfoBean> getCompanySalesOrderList(int page, int rows, String companyId, String userId, String name, String state, String createTimes, String createTimee) {
        return mainApi.getCompanySalesOrderList(page, rows, companyId, userId, name, state, createTimes, createTimee).compose(RxUtils.<CompanySalesOrderInfoBean>handleResult());
    }

    @Override
    public Observable<BaseEntity> deleteCompanySalesOrder(String id) {
        return mainApi.deleteCompanySalesOrder(id);
    }

    @Override
    public Observable<CompanySalesOrderInfo> getCompanySalesOrderDetail(String id) {
        return mainApi.getCompanySalesOrderDetail(id).compose(RxUtils.<CompanySalesOrderInfo>handleResult());
    }

    @Override
    public Observable<UploadInfoBean> saveCompanySalesOrder(String id, String companyId, String userId, String name, String customerName, String contactName, String saleCommission, String state, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark) {
        return mainApi.saveCompanySalesOrder(id, companyId, userId, name, customerName, contactName, saleCommission, state, sendAddress, sendAddressZipCode, destinationAddress, destinationAddressZipCode, products, clause, remark).compose(RxUtils.<UploadInfoBean>handleResult());
    }

    @Override
    public Observable<List<DicInfo2>> getAllSaleOrders(String companyId, String userId) {
        return mainApi.getAllSaleOrders(companyId, userId).compose(RxUtils.<List<DicInfo2>>handleResult());
    }

    @Override
    public Observable<CompanyTradingOrderInfoBean> getCompanyTradingOrderList(int page, int rows, String companyId, String userId, String name, String createTimes, String createTimee) {
        return mainApi.getCompanyTradingOrderList(page, rows, companyId, userId, name, createTimes, createTimee).compose(RxUtils.<CompanyTradingOrderInfoBean>handleResult());
    }

    @Override
    public Observable<BaseEntity> deleteCompanyTradingOrder(String id) {
        return mainApi.deleteCompanyTradingOrder(id);
    }

    @Override
    public Observable<CompanyTradingOrderInfo> getCompanyTradingOrderDetail(String id) {
        return mainApi.getCompanyTradingOrderDetail(id).compose(RxUtils.<CompanyTradingOrderInfo>handleResult());
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyTradingOrder(String id, String companyId, String userId, String name, String customerName, String price, String estimateProfit, String contactName, String possibility, String clueSource, String remark) {
        return mainApi.saveCompanyTradingOrder(id, companyId, userId, name, customerName, price, estimateProfit, contactName, possibility, clueSource, remark).compose(RxUtils.<UploadInfoBean>handleResult());
    }

    @Override
    public Observable<List<DicInfo2>> getAllTradingOrders(String companyId, String userId) {
        return mainApi.getAllTradingOrders(companyId, userId).compose(RxUtils.<List<DicInfo2>>handleResult());
    }

    @Override
    public Observable<CompanyDeliveryOrderInfoBean> getCompanyDeliveryOrderList(int page, int rows, String companyId, String userId, String name, String code, String createTimes, String createTimee) {
        return mainApi.getCompanyDeliveryOrderList(page, rows, companyId, userId, name, code, createTimes, createTimee).compose(RxUtils.<CompanyDeliveryOrderInfoBean>handleResult());
    }


    @Override
    public Observable<BaseEntity> deleteCompanyDeliveryOrder(String id) {
        return mainApi.deleteCompanyDeliveryOrder(id);
    }

    @Override
    public Observable<CompanyDeliveryOrderInfo> getCompanyDeliveryOrderDetail(String id) {
        return mainApi.getCompanyDeliveryOrderDetail(id).compose(RxUtils.<CompanyDeliveryOrderInfo>handleResult());
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyDeliveryOrder(String id, String companyId, String userId, String name, String salesOrderId, String logisticsCode, String state, String arriveDate, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark) {
        return mainApi.saveCompanyDeliveryOrder(id, companyId, userId, name, salesOrderId, logisticsCode, state, arriveDate, sendAddress, sendAddressZipCode, destinationAddress, destinationAddressZipCode, products, clause, remark).compose(RxUtils.<UploadInfoBean>handleResult());
    }

    @Override
    public Observable<CompanyPayOrderInfoBean> getCompanyPayOrderList(int page, int rows, String companyId, String userId, String name, String code, String createTimes, String createTimee) {
        return mainApi.getCompanyPayOrderList(page, rows, companyId, userId, name, code, createTimes, createTimee).compose(RxUtils.<CompanyPayOrderInfoBean>handleResult());
    }

    @Override
    public Observable<BaseEntity> deleteCompanyPayOrder(String id) {
        return mainApi.deleteCompanyPayOrder(id);
    }

    @Override
    public Observable<CompanyPayOrderInfo> getCompanyPayOrderDetail(String id) {
        return mainApi.getCompanyPayOrderDetail(id).compose(RxUtils.<CompanyPayOrderInfo>handleResult());
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyPayOrder(String id, String companyId, String userId, String name, String salesOrderId, String tradingOrderId, String customerName, String price, String payTime, String hasInvoice, String hasPrint, String invoiceFiles, String remark) {
        return mainApi.saveCompanyPayOrder(id, companyId, userId, name, salesOrderId, tradingOrderId, customerName, price, payTime, hasInvoice, hasPrint, invoiceFiles, remark).compose(RxUtils.<UploadInfoBean>handleResult());
    }

    @Override
    public Observable<CompanyPurchasingOrderInfoBean> getCompanyPurchasingOrderList(int page, int rows, String companyId, String userId, String name, String code, String createTimes, String createTimee) {
        return mainApi.getCompanyPurchasingOrderList(page, rows, companyId, userId, name, code, createTimes, createTimee).compose(RxUtils.<CompanyPurchasingOrderInfoBean>handleResult());
    }

    @Override
    public Observable<BaseEntity> deleteCompanyPurchasingOrder(String id) {
        return mainApi.deleteCompanyPurchasingOrder(id);
    }

    @Override
    public Observable<CompanyPurchasingOrderInfo> getCompanyPurchasingDetail(String id) {
        return mainApi.getCompanyPurchasingDetail(id).compose(RxUtils.<CompanyPurchasingOrderInfo>handleResult());
    }

    @Override
    public Observable<BaseEntity> saveCompanyPurchasingOrder(String id, String companyId, String userId, String name, String supplierName, String state, String orderDate, String arriveDate, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark) {
        return mainApi.saveCompanyPurchasingOrder(id, companyId, userId, name, supplierName, state, orderDate, arriveDate, sendAddress, sendAddressZipCode, destinationAddress, destinationAddressZipCode, products, clause, remark);
    }

    @Override
    public Observable<CompanyServiceRecordInfoBean> getServiceRecordList(int page, int rows, String companyId, String userId, String name, String state, String type, String createTimes, String createTimee) {
        return mainApi.getServiceRecordList(page, rows, companyId, userId, name, state, type, createTimes, createTimee).compose(RxUtils.<CompanyServiceRecordInfoBean>handleResult());
    }

    @Override
    public Observable<BaseEntity> deleteServiceRecord(String id) {
        return mainApi.deleteServiceRecord(id);
    }

    @Override
    public Observable<CompanyServiceRecordInfo> getCompanyServiceRecordDetail(String id) {
        return mainApi.getCompanyServiceRecordDetail(id).compose(RxUtils.<CompanyServiceRecordInfo>handleResult());
    }

    @Override
    public Observable<UploadInfoBean> saveCompanyServiceRecord(String id, String companyId, String userId, String name, String state, String type, String productId, String customerName, String level, String phone, String email, String reason, String remark, String solution, String track) {
        return mainApi.saveCompanyServiceRecord(id, companyId, userId, name, state, type, productId, customerName, level, phone, email, reason, remark, solution, track).compose(RxUtils.<UploadInfoBean>handleResult());
    }

    @Override
    public Observable<CompanyTaskInfoBean> getCompanyTaskList(int page, int rows, String companyId, String userId, String name, String customerName, String state, String level, String remindTimes, String remindTimee) {
        return mainApi.getCompanyTaskList(page, rows, companyId, userId, name, customerName, state, level, remindTimes, remindTimee).compose(RxUtils.<CompanyTaskInfoBean>handleResult());
    }

    @Override
    public Observable<BaseEntity> deleteCompanyTask(String id) {
        return mainApi.deleteCompanyTask(id);
    }

    @Override
    public Observable<CompanyTaskInfo> getCompanyTaskDetail(String id) {
        return mainApi.getCompanyTaskDetail(id).compose(RxUtils.<CompanyTaskInfo>handleResult());
    }

    @Override
    public Observable<BaseEntity> saveCompanyTask(String id, String companyId, String userId, String name, String remindTime, String customerName, String contactName, String level, String state, String remark) {
        return mainApi.saveCompanyTask(id, companyId, userId, name, remindTime, customerName, contactName, level, state, remark);
    }

    @Override
    public Observable<CompanyTaskInfo> getRecentTask(String userId) {
        return mainApi.getRecentTask(userId).compose(RxUtils.<CompanyTaskInfo>handleResult());
    }

    @Override
    public Observable<BaseEntity> setTaskRead(String id) {
        return mainApi.setTaskRead(id);
    }

    @Override
    public Observable<CompanyInventoryInfoBean> getInventoryList(int page, int rows, String companyId, String productId, String type, String createTimes, String createTimee) {
        return mainApi.getInventoryList(page, rows, companyId, productId, type, createTimes, createTimee).compose(RxUtils.<CompanyInventoryInfoBean>handleResult());
    }

    @Override
    public Observable<CompanyInventoryInfo> getInventoryDetail(String id) {
        return mainApi.getInventoryDetail(id).compose(RxUtils.<CompanyInventoryInfo>handleResult());
    }

    @Override
    public Observable<BaseEntity> saveInventory(String companyId, String productId, String type, String num, String remark) {
        return mainApi.saveInventory(companyId, productId, type, num, remark);
    }

    @Override
    public Observable<CompanyPdfInfoBean> getCompanyPdfList(int page, int rows, String companyId, String userId, String name) {
        return mainApi.getCompanyPdfList(page, rows, companyId, userId, name).compose(RxUtils.<CompanyPdfInfoBean>handleResult());
    }

    @Override
    public Observable<BaseEntity> deleteCompanyPdf(String id) {
        return mainApi.deleteCompanyPdf(id);
    }

    @Override
    public Observable<BaseEntity> saveCompanyPdfDetail(String id, String companyId, String userId, String name, String code, String remark) {
        return mainApi.saveCompanyPdfDetail(id, companyId, userId, name, code, remark);
    }

    @Override
    public Observable<CompanyPdfInfo> getCompanyPdfDetail(String id) {
        return mainApi.getCompanyPdfDetail(id).compose(RxUtils.<CompanyPdfInfo>handleResult());
    }

    @Override
    public Observable<FileInfoBean> uploadFiles(String filePath) {
        File file = new File(filePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        return fileApi.uploadFiles(part).compose(RxUtils.<FileInfoBean>handleResult());
    }

    @Override
    public Observable<FileInfoBean> uploadImages(String filePath) {
        File file = new File(filePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        return fileApi.uploadImages(part).compose(RxUtils.<FileInfoBean>handleResult());
    }

    @Override
    public Observable<ResponseBody> downloadFiles(String code) {
        return mainApi.downloadFiles(code);
    }

    @Override
    public Observable<CompanyPackagingDataInfoBean> getCompanyPackagingDataList(int page, int rows, String companyId, String userId, String createTimes, String createTimee) {
        return mainApi.getCompanyPackagingDataList(page, rows, companyId, userId, createTimes, createTimee).compose(RxUtils.<CompanyPackagingDataInfoBean>handleResult());
    }

    @Override
    public Observable<BaseEntity> checkDate(String companyId, String startDate, String endDate) {
        return mainApi.checkDate(companyId, startDate, endDate);
    }

    @Override
    public Observable<CompanyPackagingDataInfo> getPackagingDataDetail(String id) {
        return mainApi.getPackagingDataDetail(id).compose(RxUtils.<CompanyPackagingDataInfo>handleResult());
    }

    @Override
    public Observable<BaseEntity> savePackagingData(String companyId, String userId, String startDate, String endDate, String num, String previewInfo, String remark) {
        return mainApi.savePackagingData(companyId, userId, startDate, endDate, num, previewInfo, remark);
    }

    @Override
    public Observable<String> getPreviewInfo(String companyId, String startDate, String endDate) {
        return mainApi.getPreviewInfo(companyId, startDate, endDate).compose(RxUtils.<String>handleResult());
    }

    @Override
    public Observable<CompanyeExpenditureInfoBean> getExpenditureList(int page, int rows, String companyId, String title, String createTimes, String createTimee) {
        return mainApi.getExpenditureList(page, rows, companyId, title, createTimes, createTimee).compose(RxUtils.<CompanyeExpenditureInfoBean>handleResult());
    }

    @Override
    public Observable<CompanyExpenditureInfo> getExpenditureDetail(String id) {
        return mainApi.getExpenditureDetail(id).compose(RxUtils.<CompanyExpenditureInfo>handleResult());
    }

    @Override
    public Observable<List<PayRecordInfo>> getPayRecord(String estimateId) {
        return mainApi.getPayRecord(estimateId).compose(RxUtils.<List<PayRecordInfo>>handleResult());
    }

    @Override
    public Observable<BaseEntity> checkYsDate(String companyId, String startDate, String endDate) {
        return mainApi.checkYsDate(companyId, startDate, endDate);
    }

    @Override
    public Observable<BaseEntity> saveExpenditureA(String companyId, String userId, String title, String startDate, String endDate, String estimatePrice, String remark) {
        return mainApi.saveExpenditureA(companyId, userId, title, startDate, endDate, estimatePrice, remark);
    }

    @Override
    public Observable<CompanyeExpenditureInfoBeanB> getExpenditureListB(int page, int rows, String companyId, String userId, String payDates, String payDatee) {
        return mainApi.getExpenditureListB(page, rows, companyId, userId, payDates, payDatee).compose(RxUtils.<CompanyeExpenditureInfoBeanB>handleResult());
    }

    @Override
    public Observable<CompanyExpenditureInfoB> getExpenditureDetailB(String id) {
        return mainApi.getExpenditureDetailB(id).compose(RxUtils.<CompanyExpenditureInfoB>handleResult());
    }

    @Override
    public Observable<BaseEntity> saveExpenditureB(String companyId, String userId, String payDate, String price, String remark) {
        return mainApi.saveExpenditureB(companyId, userId, payDate, price, remark);
    }

}
