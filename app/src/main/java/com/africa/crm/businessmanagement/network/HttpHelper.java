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

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 14:37
 * Modification  History:
 * Why & What is modified:
 */
public interface HttpHelper {

    Observable<LoginInfoBean> getLoginInfo(String userName, String passWord);

    Observable<BaseEntity> changePassword(String id, String oldPassWord,String newPassWord);

    Observable<List<DicInfo>> getDicByCode(String code);

    Observable<List<MainStationInfoBean>> getMainStationInfo(String id);

    Observable<UserManagementInfoBean> getUserList(int page, int rows, String userName, String type, String companyId, String state, String name);

    Observable<BaseEntity> deleteUser(String id);

    Observable<UserInfo> getUserInfo(String id);

    Observable<BaseEntity> saveOrcreateUser(String id, String userName, String type, String roleIds, String passWord, String name, String phone, String address, String email, String state, String companyId, String head);

    Observable<List<DicInfo2>> getAllCompanyUser(String companyId);

    Observable<List<DicInfo2>> getAllCompany(String name);

    Observable<List<RoleInfoBean>> getAllRoles();

    Observable<RoleManagementInfoBean> getRoleList(int page, int rows, String roleName, String roleCode, String typeName);

    Observable<RoleInfoBean> getRoleInfo(String id);

    Observable<BaseEntity> saveRoleInfo(String userId, String id, String roleName, String roleCode, String typeName, String orderNum);

    Observable<List<RoleLimitInfoBean>> getRoleLimit(String id);

    Observable<BaseEntity> saveRoleLimit(String id, String resourceIds, String btnIds);

    Observable<CompanyInfoBean> getCompanyInfoList(int page, int rows, String name);

    Observable<BaseEntity> deleteCompanyInfo(String id);

    Observable<CompanyInfo> getCompanyInfoDetail(String id);

    Observable<UploadInfoBean> saveCompanyInfo(String id, String head, String name, String code, String type, String address, String phone, String email, String mid, String area, String profession, String numA, String state);

    Observable<CompanyAccountInfoBean> getCompanyAccounList(int page, int rows, String companyId, String userName, String name);

    Observable<BaseEntity> deleteCompanyAccount(String id);

    Observable<CompanyAccountInfo> getCompanyAccountDetail(String id);

    Observable<UploadInfoBean> saveCompanyAccount(String id, String userName, String type, String roleId, String passWord, String name, String phone, String address, String email, String state, String companyId, String head);

    Observable<CompanySupplierInfoBean> getCompanySupplierList(int page, int rows, String companyId, String name, String type);

    Observable<BaseEntity> deleteCompanySupplier(String id);

    Observable<CompanySupplierInfo> getCompanySupplierDetail(String id);

    Observable<UploadInfoBean> saveCompanySupplier(String id, String companyId, String head, String name, String type, String address, String phone, String email, String zipCode, String area, String remark);

    Observable<List<DicInfo2>> getAllSuppliers(String companyId);

    Observable<CompanyContactInfoBean> getCompanyContactList(int page, int rows, String companyId, String userId, String name, String fromType);

    Observable<BaseEntity> deleteCompanyContact(String id);

    Observable<CompanyContactInfo> getContactDetail(String id);

    Observable<BaseEntity> saveCompanyContact(String id, String companyId, String userId, String head, String name, String fromType, String address, String mailAddress, String phone, String tel, String email, String job, String remark);

    Observable<List<DicInfo2>> getAllContact(String companyId);

    Observable<CompanyClientInfoBean> getCompanyClientList(int page, int rows, String companyId, String userId, String name, String industry);

    Observable<BaseEntity> deleteCompanyClient(String id);

    Observable<CompanyClientInfo> getCompanyClientDetail(String id);

    Observable<BaseEntity> saveCompanyClient(String id, String companyId, String userId, String head, String name, String industry, String address, String workerNum, String tel, String yearIncome, String remark);

    Observable<List<DicInfo2>> getAllCustomers(String companyId);

    Observable<CompanyProductInfoBean> getCompanyProductList(int page, int rows, String companyId, String name, String type);

    Observable<BaseEntity> deleteCompanyProduct(String id);

    Observable<CompanyProductInfo> getCompanyProductDetail(String id);

    Observable<UploadInfoBean> saveCompanyProduct(String id, String companyId, String name, String code, String supplierName, String makerName, String type, String unitPrice, String unit, String stockNum, String warnNum, String remark);

    Observable<List<DicInfo2>> getAllProducts(String companyId);

    Observable<CompanyQuotationInfoBean> getCompanyQuotationList(int page, int rows, String companyId, String userId, String name, String createTimes, String createTimee);

    Observable<BaseEntity> deleteCompanyQuotation(String id);

    Observable<CompanyQuotationInfo> getCompanyQuotationDetail(String id);

    Observable<BaseEntity> saveCompanyQuotation(String id, String companyId, String userId, String name, String customerName, String contactName, String termOfValidity, String price, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark);

    Observable<CompanySalesOrderInfoBean> getCompanySalesOrderList(int page, int rows, String companyId, String userId, String name, String state, String createTimes, String createTimee);

    Observable<BaseEntity> deleteCompanySalesOrder(String id);

    Observable<CompanySalesOrderInfo> getCompanySalesOrderDetail(String id);

    Observable<BaseEntity> saveCompanySalesOrder(String id, String companyId, String userId, String name, String customerName, String contactName, String saleCommission, String state, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark);

    Observable<List<DicInfo2>> getAllSaleOrders(String companyId, String userId);

    Observable<CompanyTradingOrderInfoBean> getCompanyTradingOrderList(int page, int rows, String companyId, String userId, String name, String createTimes, String createTimee);

    Observable<BaseEntity> deleteCompanyTradingOrder(String id);

    Observable<CompanyTradingOrderInfo> getCompanyTradingOrderDetail(String id);

    Observable<BaseEntity> saveCompanyTradingOrder(String id, String companyId, String userId, String name, String customerName, String price, String estimateProfit, String contactName, String possibility, String clueSource, String remark);

    Observable<List<DicInfo2>> getAllTradingOrders(String companyId, String userId);

    Observable<CompanyDeliveryOrderInfoBean> getCompanyDeliveryOrderList(int page, int rows, String companyId, String userId, String name, String code, String createTimes, String createTimee);

    Observable<BaseEntity> deleteCompanyDeliveryOrder(String id);

    Observable<CompanyDeliveryOrderInfo> getCompanyDeliveryOrderDetail(String id);

    Observable<BaseEntity> saveCompanyDeliveryOrder(String id, String companyId, String userId, String name, String salesOrderId, String logisticsCode, String state, String arriveDate, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark);

    Observable<CompanyPayOrderInfoBean> getCompanyPayOrderList(int page, int rows, String companyId, String userId, String name, String code, String createTimes, String createTimee);

    Observable<BaseEntity> deleteCompanyPayOrder(String id);

    Observable<CompanyPayOrderInfo> getCompanyPayOrderDetail(String id);

    Observable<BaseEntity> saveCompanyPayOrder(String id, String companyId, String userId, String name, String salesOrderId, String tradingOrderId, String customerName, String price, String payTime, String hasInvoice, String hasPrint, String invoiceFiles, String remark);

    Observable<CompanyPurchasingOrderInfoBean> getCompanyPurchasingOrderList(int page, int rows, String companyId, String userId, String name, String code, String createTimes, String createTimee);

    Observable<BaseEntity> deleteCompanyPurchasingOrder(String id);

    Observable<CompanyPurchasingOrderInfo> getCompanyPurchasingDetail(String id);

    Observable<BaseEntity> saveCompanyPurchasingOrder(String id, String companyId, String userId, String name, String supplierName, String state, String orderDate, String arriveDate, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark);

    Observable<CompanyServiceRecordInfoBean> getServiceRecordList(int page, int rows, String companyId, String userId, String name, String state, String type, String createTimes, String createTimee);

    Observable<BaseEntity> deleteServiceRecord(String id);

    Observable<CompanyServiceRecordInfo> getCompanyServiceRecordDetail(String id);

    Observable<BaseEntity> saveCompanyServiceRecord(String id, String companyId, String userId, String name, String state, String type, String productId, String customerName, String level, String phone, String email, String reason, String remark, String solution, String track);

    Observable<CompanyTaskInfoBean> getCompanyTaskList(int page, int rows, String companyId, String userId, String name, String customerName, String state, String level, String remindTimes, String remindTimee);

    Observable<BaseEntity> deleteCompanyTask(String id);

    Observable<CompanyTaskInfo> getCompanyTaskDetail(String id);

    Observable<BaseEntity> saveCompanyTask(String id, String companyId, String userId, String name, String remindTime, String customerName, String contactName, String level, String state, String remark);

    Observable<CompanyTaskInfo> getRecentTask(String userId);

    Observable<BaseEntity> setTaskRead(String id);

    Observable<CompanyInventoryInfoBean> getInventoryList(int page, int rows, String companyId, String productId, String type, String createTimes, String createTimee);

    Observable<CompanyInventoryInfo> getInventoryDetail(String id);

    Observable<BaseEntity> saveInventory(String companyId, String productId, String type, String num, String remark);

    Observable<CompanyPdfInfoBean> getCompanyPdfList(int page, int rows, String companyId, String userId, String name);

    Observable<BaseEntity> deleteCompanyPdf(String id);

    Observable<BaseEntity> saveCompanyPdfDetail(String id,String companyId, String userId, String name, String code, String remark);

    Observable<CompanyPdfInfo> getCompanyPdfDetail(String id);

    Observable<FileInfoBean> uploadFiles(String filePath);

    Observable<FileInfoBean> uploadImages(String filePath);

    Observable<ResponseBody> downloadFiles(String code);

    Observable<CompanyPackagingDataInfoBean> getCompanyPackagingDataList(int page, int rows, String companyId, String userId, String createTimes, String createTimee);

    Observable<BaseEntity> checkDate(String companyId, String startDate, String endDate);

    Observable<CompanyPackagingDataInfo> getPackagingDataDetail(String id);

    Observable<BaseEntity> savePackagingData(String companyId, String userId, String startDate, String endDate, String num, String previewInfo, String remark);

    Observable<String> getPreviewInfo(String companyId, String startDate, String endDate);

    Observable<CompanyeExpenditureInfoBean> getExpenditureList(int page, int rows, String companyId, String title, String createTimes, String createTimee);

    Observable<CompanyExpenditureInfo> getExpenditureDetail(String id);

    Observable<List<PayRecordInfo>> getPayRecord(String estimateId);

    Observable<BaseEntity> checkYsDate(String companyId, String startDate, String endDate);

    Observable<BaseEntity> saveExpenditureA(String companyId, String userId, String title, String startDate, String endDate, String estimatePrice, String remark);

    Observable<CompanyeExpenditureInfoBeanB> getExpenditureListB(int page, int rows, String companyId, String userId, String payDates, String payDatee);

    Observable<CompanyExpenditureInfoB> getExpenditureDetailB(String id);

    Observable<BaseEntity> saveExpenditureB(String companyId, String userId, String payDate, String price, String remark);

}

