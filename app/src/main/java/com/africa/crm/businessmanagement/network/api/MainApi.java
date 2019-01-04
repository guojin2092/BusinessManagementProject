package com.africa.crm.businessmanagement.network.api;

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
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.MainStationInfoBean;
import com.africa.crm.businessmanagement.main.bean.PayRecordInfo;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleLimitInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleManagementInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserInfo;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 14:39
 * Modification  History:
 * Why & What is modified:
 */
public interface MainApi {

    @FormUrlEncoded
    @POST("user/getUserResource")
    Observable<BaseEntity<List<MainStationInfoBean>>> getMainStationInfo(@Field("id") String id);

    @FormUrlEncoded
    @POST("user/grid")
    Observable<BaseEntity<UserManagementInfoBean>> getUserList(@Field("page") int page, @Field("rows") int rows, @Field("userName") String userName, @Field("type") String type, @Field("companyId") String companyId, @Field("state") String state, @Field("name") String name);

    @FormUrlEncoded
    @POST("user/deleteById")
    Observable<BaseEntity> deleteUser(@Field("id") String id);

    @FormUrlEncoded
    @POST("user/getInfo")
    Observable<BaseEntity<UserInfo>> getUserInfo(@Field("id") String id);

    @FormUrlEncoded
    @POST("user/save")
    Observable<BaseEntity> saveOrcreateUser(@Field("id") String id, @Field("userName") String userName, @Field("type") String type, @Field("roleIds") String roleIds, @Field("passWord") String passWord, @Field("name") String name, @Field("phone") String phone, @Field("address") String address, @Field("email") String email, @Field("state") String state, @Field("companyId") String companyId, @Field("head") String head);

    @FormUrlEncoded
    @POST("company/queryAll")
    Observable<BaseEntity<List<DicInfo2>>> getAllCompany(@Field("name") String name);

    @FormUrlEncoded
    @POST("role/queryAll")
    Observable<BaseEntity<List<RoleInfoBean>>> getAllRoles(@Field("name") String name);

    @FormUrlEncoded
    @POST("role/grid")
    Observable<BaseEntity<RoleManagementInfoBean>> getRoleList(@Field("page") int page, @Field("rows") int rows, @Field("roleName") String roleName, @Field("roleCode") String roleCode, @Field("typeName") String typeName);

    @FormUrlEncoded
    @POST("role/getInfo")
    Observable<BaseEntity<RoleInfoBean>> getRoleInfo(@Field("id") String id);

    @FormUrlEncoded
    @POST("role/save")
    Observable<BaseEntity> saveRoleInfo(@Field("userId") String userId, @Field("id") String id, @Field("roleName") String roleName, @Field("roleCode") String roleCode, @Field("typeName") String typeName, @Field("orderNum") String orderNum);

    @FormUrlEncoded
    @POST("role/getRoleResourceAndBtn")
    Observable<BaseEntity<List<RoleLimitInfoBean>>> getRoleLimit(@Field("id") String id);

    @FormUrlEncoded
    @POST("role/saveRoleResource")
    Observable<BaseEntity> saveRoleLimit(@Field("id") String id, @Field("resourceIds") String resourceIds, @Field("btnIds") String btnIds);

    @FormUrlEncoded
    @POST("company/grid")
    Observable<BaseEntity<CompanyInfoBean>> getCompanyInfoList(@Field("page") int page, @Field("rows") int rows, @Field("name") String name);

    @FormUrlEncoded
    @POST("company/deleteById")
    Observable<BaseEntity> deleteCompanyInfo(@Field("id") String id);

    @FormUrlEncoded
    @POST("company/getInfo")
    Observable<BaseEntity<CompanyInfo>> getCompanyInfoDetail(@Field("id") String id);

    @FormUrlEncoded
    @POST("company/save")
    Observable<BaseEntity> saveCompanyInfo(@Field("id") String id, @Field("head") String head, @Field("name") String name, @Field("code") String code, @Field("type") String type, @Field("address") String address, @Field("phone") String phone, @Field("email") String email, @Field("mid") String mid, @Field("area") String area, @Field("profession") String profession, @Field("numA") String numA, @Field("state") String state);

    @FormUrlEncoded
    @POST("companyUser/grid")
    Observable<BaseEntity<CompanyAccountInfoBean>> getCompanyAccounList(@Field("page") int page, @Field("rows") int rows, @Field("companyId") String companyId, @Field("userName") String userName, @Field("name") String name);

    @FormUrlEncoded
    @POST("companyUser/deleteById")
    Observable<BaseEntity> deleteCompanyAccount(@Field("id") String id);

    @FormUrlEncoded
    @POST("companyUser/queryAll")
    Observable<BaseEntity<List<DicInfo2>>> getAllCompanyUser(@Field("companyId") String companyId);

    @FormUrlEncoded
    @POST("companyUser/getInfo")
    Observable<BaseEntity<CompanyAccountInfo>> getCompanyAccountDetail(@Field("id") String id);

    @FormUrlEncoded
    @POST("companyUser/save")
    Observable<BaseEntity> saveCompanyAccount(@Field("id") String id, @Field("userName") String userName, @Field("type") String type, @Field("roleIds") String roleId, @Field("passWord") String passWord, @Field("name") String name, @Field("phone") String phone, @Field("address") String address, @Field("email") String email, @Field("state") String state, @Field("companyId") String companyId, @Field("head") String head);

    @FormUrlEncoded
    @POST("supplier/grid")
    Observable<BaseEntity<CompanySupplierInfoBean>> getCompanySupplierList(@Field("page") int page, @Field("rows") int rows, @Field("companyId") String companyId, @Field("name") String name, @Field("type") String type);

    @FormUrlEncoded
    @POST("supplier/deleteById")
    Observable<BaseEntity> deleteCompanySupplier(@Field("id") String id);

    @FormUrlEncoded
    @POST("supplier/getInfo")
    Observable<BaseEntity<CompanySupplierInfo>> getCompanySupplierDetail(@Field("id") String id);

    @FormUrlEncoded
    @POST("supplier/save")
    Observable<BaseEntity> saveCompanySupplier(@Field("id") String id, @Field("companyId") String companyId, @Field("head") String head, @Field("name") String name, @Field("type") String type, @Field("address") String address, @Field("phone") String phone, @Field("email") String email, @Field("zipCode") String zipCode, @Field("area") String area, @Field("remark") String remark);

    @FormUrlEncoded
    @POST("supplier/queryAll")
    Observable<BaseEntity<List<DicInfo2>>> getAllSuppliers(@Field("companyId") String companyId);

    @FormUrlEncoded
    @POST("contact/grid")
    Observable<BaseEntity<CompanyContactInfoBean>> getCompanyContactList(@Field("page") int page, @Field("rows") int rows, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name, @Field("fromType") String fromType);

    @FormUrlEncoded
    @POST("contact/deleteById")
    Observable<BaseEntity> deleteCompanyContact(@Field("id") String id);

    @FormUrlEncoded
    @POST("contact/getInfo")
    Observable<BaseEntity<CompanyContactInfo>> getContactDetail(@Field("id") String id);

    @FormUrlEncoded
    @POST("contact/save")
    Observable<BaseEntity> saveCompanyContact(@Field("id") String id, @Field("companyId") String companyId, @Field("userId") String userId, @Field("head") String head, @Field("name") String name, @Field("fromType") String fromType, @Field("address") String address, @Field("mailAddress") String mailAddress, @Field("phone") String phone, @Field("tel") String tel, @Field("email") String email, @Field("job") String job, @Field("remark") String remark);

    @FormUrlEncoded
    @POST("contact/queryAll")
    Observable<BaseEntity<List<DicInfo2>>> getAllContact(@Field("companyId") String companyId);

    @FormUrlEncoded
    @POST("customer/grid")
    Observable<BaseEntity<CompanyClientInfoBean>> getCompanyClientList(@Field("page") int page, @Field("rows") int rows, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name, @Field("industry") String industry);

    @FormUrlEncoded
    @POST("customer/deleteById")
    Observable<BaseEntity> deleteCompanyClient(@Field("id") String id);

    @FormUrlEncoded
    @POST("customer/getInfo")
    Observable<BaseEntity<CompanyClientInfo>> getCompanyClientDetail(@Field("id") String id);

    @FormUrlEncoded
    @POST("customer/save")
    Observable<BaseEntity> saveCompanyClient(@Field("id") String id, @Field("companyId") String companyId, @Field("userId") String userId, @Field("head") String head, @Field("name") String name, @Field("industry") String industry, @Field("address") String address, @Field("workerNum") String workerNum, @Field("tel") String tel, @Field("yearIncome") String yearIncome, @Field("remark") String remark);

    @FormUrlEncoded
    @POST("customer/queryAll")
    Observable<BaseEntity<List<DicInfo2>>> getAllCustomers(@Field("companyId") String companyId);

    @FormUrlEncoded
    @POST("product/grid")
    Observable<BaseEntity<CompanyProductInfoBean>> getCompanyProductList(@Field("page") int page, @Field("rows") int rows, @Field("companyId") String companyId, @Field("name") String name, @Field("type") String type);

    @FormUrlEncoded
    @POST("product/deleteById")
    Observable<BaseEntity> deleteCompanyProduct(@Field("id") String id);

    @FormUrlEncoded
    @POST("product/getInfo")
    Observable<BaseEntity<CompanyProductInfo>> getCompanyProductDetail(@Field("id") String id);

    @FormUrlEncoded
    @POST("product/save")
    Observable<BaseEntity> saveCompanyProduct(@Field("id") String id, @Field("companyId") String companyId, @Field("name") String name, @Field("code") String code, @Field("supplierName") String supplierName, @Field("makerName") String makerName, @Field("type") String type, @Field("unitPrice") String unitPrice, @Field("unit") String unit, @Field("stockNum") String stockNum, @Field("warnNum") String warnNum, @Field("remark") String remark);

    @FormUrlEncoded
    @POST("product/queryAll")
    Observable<BaseEntity<List<DicInfo2>>> getAllProducts(@Field("companyId") String companyId);

    @FormUrlEncoded
    @POST("quotationOrder/grid")
    Observable<BaseEntity<CompanyQuotationInfoBean>> getCompanyQuotationList(@Field("page") int page, @Field("rows") int rows, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name, @Field("createTimes") String createTimes, @Field("createTimee") String createTimee);

    @FormUrlEncoded
    @POST("quotationOrder/deleteById")
    Observable<BaseEntity> deleteCompanyQuotation(@Field("id") String id);

    @FormUrlEncoded
    @POST("quotationOrder/getInfo")
    Observable<BaseEntity<CompanyQuotationInfo>> getCompanyQuotationDetail(@Field("id") String id);

    @FormUrlEncoded
    @POST("quotationOrder/save")
    Observable<BaseEntity> saveCompanyQuotation(@Field("id") String id, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name, @Field("customerName") String customerName, @Field("contactName") String contactName, @Field("termOfValidity") String termOfValidity, @Field("price") String price, @Field("sendAddress") String sendAddress, @Field("sendAddressZipCode") String sendAddressZipCode, @Field("destinationAddress") String destinationAddress, @Field("destinationAddressZipCode") String destinationAddressZipCode, @Field("products") String products, @Field("clause") String clause, @Field("remark") String remark);

    @FormUrlEncoded
    @POST("salesOrder/grid")
    Observable<BaseEntity<CompanySalesOrderInfoBean>> getCompanySalesOrderList(@Field("page") int page, @Field("rows") int rows, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name, @Field("state") String state, @Field("createTimes") String createTimes, @Field("createTimee") String createTimee);

    @FormUrlEncoded
    @POST("salesOrder/deleteById")
    Observable<BaseEntity> deleteCompanySalesOrder(@Field("id") String id);

    @FormUrlEncoded
    @POST("salesOrder/getInfo")
    Observable<BaseEntity<CompanySalesOrderInfo>> getCompanySalesOrderDetail(@Field("id") String id);

    @FormUrlEncoded
    @POST("salesOrder/save")
    Observable<BaseEntity> saveCompanySalesOrder(@Field("id") String id, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name, @Field("customerName") String customerName, @Field("contactName") String contactName, @Field("saleCommission") String saleCommission, @Field("state") String state, @Field("sendAddress") String sendAddress, @Field("sendAddressZipCode") String sendAddressZipCode, @Field("destinationAddress") String destinationAddress, @Field("destinationAddressZipCode") String destinationAddressZipCode, @Field("products") String products, @Field("clause") String clause, @Field("remark") String remark);

    @FormUrlEncoded
    @POST("salesOrder/queryAll")
    Observable<BaseEntity<List<DicInfo2>>> getAllSaleOrders(@Field("companyId") String companyId, @Field("userId") String userId);

    @FormUrlEncoded
    @POST("tradingOrder/grid")
    Observable<BaseEntity<CompanyTradingOrderInfoBean>> getCompanyTradingOrderList(@Field("page") int page, @Field("rows") int rows, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name, @Field("createTimes") String createTimes, @Field("createTimee") String createTimee);

    @FormUrlEncoded
    @POST("tradingOrder/deleteById")
    Observable<BaseEntity> deleteCompanyTradingOrder(@Field("id") String id);

    @FormUrlEncoded
    @POST("tradingOrder/getInfo")
    Observable<BaseEntity<CompanyTradingOrderInfo>> getCompanyTradingOrderDetail(@Field("id") String id);

    @FormUrlEncoded
    @POST("tradingOrder/save")
    Observable<BaseEntity> saveCompanyTradingOrder(@Field("id") String id, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name, @Field("customerName") String customerName, @Field("price") String price, @Field("estimateProfit") String estimateProfit, @Field("contactName") String contactName, @Field("possibility") String possibility, @Field("clueSource") String clueSource, @Field("remark") String remark);

    @FormUrlEncoded
    @POST("tradingOrder/queryAll")
    Observable<BaseEntity<List<DicInfo2>>> getAllTradingOrders(@Field("companyId") String companyId, @Field("userId") String userId);

    @FormUrlEncoded
    @POST("invoiceOrder/grid")
    Observable<BaseEntity<CompanyDeliveryOrderInfoBean>> getCompanyDeliveryOrderList(@Field("page") int page, @Field("rows") int rows, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name, @Field("code") String code, @Field("createTimes") String createTimes, @Field("createTimee") String createTimee);

    @FormUrlEncoded
    @POST("invoiceOrder/deleteById")
    Observable<BaseEntity> deleteCompanyDeliveryOrder(@Field("id") String id);

    @FormUrlEncoded
    @POST("invoiceOrder/getInfo")
    Observable<BaseEntity<CompanyDeliveryOrderInfo>> getCompanyDeliveryOrderDetail(@Field("id") String id);

    @FormUrlEncoded
    @POST("invoiceOrder/save")
    Observable<BaseEntity> saveCompanyDeliveryOrder(@Field("id") String id, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name, @Field("salesOrderId") String salesOrderId, @Field("logisticsCode") String logisticsCode, @Field("state") String state, @Field("arriveDate") String arriveDate, @Field("sendAddress") String sendAddress, @Field("sendAddressZipCode") String sendAddressZipCode, @Field("destinationAddress") String destinationAddress, @Field("destinationAddressZipCode") String destinationAddressZipCode, @Field("products") String products, @Field("clause") String clause, @Field("remark") String remark);

    @FormUrlEncoded
    @POST("paymentOrder/grid")
    Observable<BaseEntity<CompanyPayOrderInfoBean>> getCompanyPayOrderList(@Field("page") int page, @Field("rows") int rows, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name, @Field("code") String code, @Field("createTimes") String createTimes, @Field("createTimee") String createTimee);

    @FormUrlEncoded
    @POST("paymentOrder/deleteById")
    Observable<BaseEntity> deleteCompanyPayOrder(@Field("id") String id);

    @FormUrlEncoded
    @POST("paymentOrder/getInfo")
    Observable<BaseEntity<CompanyPayOrderInfo>> getCompanyPayOrderDetail(@Field("id") String id);

    @FormUrlEncoded
    @POST("paymentOrder/save")
    Observable<BaseEntity> saveCompanyPayOrder(@Field("id") String id, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name, @Field("salesOrderId") String salesOrderId, @Field("tradingOrderId") String tradingOrderId, @Field("customerName") String customerName, @Field("price") String price, @Field("payTime") String payTime, @Field("hasInvoice") String hasInvoice, @Field("hasPrint") String hasPrint, @Field("invoiceFiles") String invoiceFiles, @Field("remark") String remark);

    @FormUrlEncoded
    @POST("purchaseOrder/grid")
    Observable<BaseEntity<CompanyPurchasingOrderInfoBean>> getCompanyPurchasingOrderList(@Field("page") int page, @Field("rows") int rows, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name, @Field("code") String code, @Field("createTimes") String createTimes, @Field("createTimee") String createTimee);

    @FormUrlEncoded
    @POST("purchaseOrder/deleteById")
    Observable<BaseEntity> deleteCompanyPurchasingOrder(@Field("id") String id);

    @FormUrlEncoded
    @POST("purchaseOrder/getInfo")
    Observable<BaseEntity<CompanyPurchasingOrderInfo>> getCompanyPurchasingDetail(@Field("id") String id);

    @FormUrlEncoded
    @POST("purchaseOrder/save")
    Observable<BaseEntity> saveCompanyPurchasingOrder(@Field("id") String id, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name, @Field("supplierName") String supplierName, @Field("state") String state, @Field("orderDate") String orderDate, @Field("arriveDate") String arriveDate, @Field("sendAddress") String sendAddress, @Field("sendAddressZipCode") String sendAddressZipCode, @Field("destinationAddress") String destinationAddress, @Field("destinationAddressZipCode") String destinationAddressZipCode, @Field("products") String products, @Field("clause") String clause, @Field("remark") String remark);

    @FormUrlEncoded
    @POST("serviceRecord/grid")
    Observable<BaseEntity<CompanyServiceRecordInfoBean>> getServiceRecordList(@Field("page") int page, @Field("rows") int rows, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name, @Field("state") String state, @Field("type") String type, @Field("createTimes") String createTimes, @Field("createTimee") String createTimee);

    @FormUrlEncoded
    @POST("serviceRecord/deleteById")
    Observable<BaseEntity> deleteServiceRecord(@Field("id") String id);

    @FormUrlEncoded
    @POST("serviceRecord/getInfo")
    Observable<BaseEntity<CompanyServiceRecordInfo>> getCompanyServiceRecordDetail(@Field("id") String id);

    @FormUrlEncoded
    @POST("serviceRecord/save")
    Observable<BaseEntity> saveCompanyServiceRecord(@Field("id") String id, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name, @Field("state") String state, @Field("type") String type, @Field("productId") String productId, @Field("customerName") String customerName, @Field("level") String level, @Field("phone") String phone, @Field("email") String email, @Field("reason") String reason, @Field("remark") String remark, @Field("solution") String solution, @Field("track") String track);

    @FormUrlEncoded
    @POST("taskRecord/grid")
    Observable<BaseEntity<CompanyTaskInfoBean>> getCompanyTaskList(@Field("page") int page, @Field("rows") int rows, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name, @Field("customerName") String customerName, @Field("state") String state, @Field("level") String level, @Field("remindTimes") String remindTimes, @Field("remindTimee") String remindTimee);

    @FormUrlEncoded
    @POST("taskRecord/deleteById")
    Observable<BaseEntity> deleteCompanyTask(@Field("id") String id);

    @FormUrlEncoded
    @POST("taskRecord/getInfo")
    Observable<BaseEntity<CompanyTaskInfo>> getCompanyTaskDetail(@Field("id") String id);

    @FormUrlEncoded
    @POST("taskRecord/save")
    Observable<BaseEntity> saveCompanyTask(@Field("id") String id, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name, @Field("remindTime") String remindTime, @Field("customerName") String customerName, @Field("contactName") String contactName, @Field("level") String level, @Field("state") String state, @Field("remark") String remark);

    @FormUrlEncoded
    @POST("taskRecord/selectFirstNoRemind")
    Observable<BaseEntity<CompanyTaskInfo>> getRecentTask(@Field("userId") String userId);

    @FormUrlEncoded
    @POST("taskRecord/updateHasRemind")
    Observable<BaseEntity> setTaskRead(@Field("id") String id);

    @FormUrlEncoded
    @POST("stockRecord/grid")
    Observable<BaseEntity<CompanyInventoryInfoBean>> getInventoryList(@Field("page") int page, @Field("rows") int rows, @Field("companyId") String companyId, @Field("productId") String productId, @Field("type") String type, @Field("createTimes") String createTimes, @Field("createTimee") String createTimee);

    @FormUrlEncoded
    @POST("stockRecord/getInfo")
    Observable<BaseEntity<CompanyInventoryInfo>> getInventoryDetail(@Field("id") String id);

    @FormUrlEncoded
    @POST("stockRecord/save")
    Observable<BaseEntity> saveInventory(@Field("companyId") String companyId, @Field("productId") String productId, @Field("type") String type, @Field("num") String num, @Field("remark") String remark);

    @FormUrlEncoded
    @POST("pdfFiles/grid")
    Observable<BaseEntity<CompanyPdfInfoBean>> getCompanyPdfList(@Field("page") int page, @Field("rows") int rows, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name);

    @FormUrlEncoded
    @POST("pdfFiles/deleteById")
    Observable<BaseEntity> deleteCompanyPdf(@Field("id") String id);

    @FormUrlEncoded
    @POST("pdfFiles/save")
    Observable<BaseEntity> saveCompanyPdfDetail(@Field("id") String id, @Field("companyId") String companyId, @Field("userId") String userId, @Field("name") String name, @Field("code") String code, @Field("remark") String remark);

    @FormUrlEncoded
    @POST("pdfFiles/getInfo")
    Observable<BaseEntity<CompanyPdfInfo>> getCompanyPdfDetail(@Field("id") String id);

    @FormUrlEncoded
    @POST("packageRecord/grid")
    Observable<BaseEntity<CompanyPackagingDataInfoBean>> getCompanyPackagingDataList(@Field("page") int page, @Field("rows") int rows, @Field("companyId") String companyId, @Field("userId") String userId, @Field("createTimes") String createTimes, @Field("createTimee") String createTimee);

    @FormUrlEncoded
    @POST("packageRecord/checkDate")
    Observable<BaseEntity> checkDate(@Field("companyId") String companyId, @Field("startDate") String startDate, @Field("endDate") String endDate);

    @FormUrlEncoded
    @POST("packageRecord/getInfo")
    Observable<BaseEntity<CompanyPackagingDataInfo>> getPackagingDataDetail(@Field("id") String id);

    @FormUrlEncoded
    @POST("packageRecord/save")
    Observable<BaseEntity> savePackagingData(@Field("companyId") String companyId, @Field("userId") String userId, @Field("startDate") String startDate, @Field("endDate") String endDate, @Field("num") String num, @Field("previewInfo") String previewInfo, @Field("remark") String remark);

    @FormUrlEncoded
    @POST("packageRecord/getPreviewInfo")
    Observable<BaseEntity<String>> getPreviewInfo(@Field("companyId") String companyId, @Field("startDate") String startDate, @Field("endDate") String endDate);

    @FormUrlEncoded
    @POST("payEstimate/grid")
    Observable<BaseEntity<CompanyeExpenditureInfoBean>> getExpenditureList(@Field("page") int page, @Field("rows") int rows, @Field("companyId") String companyId, @Field("title") String title, @Field("createTimes") String createTimes, @Field("createTimee") String createTimee);

    @FormUrlEncoded
    @POST("payEstimate/getInfo")
    Observable<BaseEntity<CompanyExpenditureInfo>> getExpenditureDetail(@Field("id") String id);

    @FormUrlEncoded
    @POST("payRecord/queryByEstimateId")
    Observable<BaseEntity<List<PayRecordInfo>>> getPayRecord(@Field("estimateId") String estimateId);

    @FormUrlEncoded
    @POST("payEstimate/checkDate")
    Observable<BaseEntity> checkYsDate(@Field("companyId") String companyId, @Field("startDate") String startDate, @Field("endDate") String endDate);

    @FormUrlEncoded
    @POST("payEstimate/save")
    Observable<BaseEntity> saveExpenditureA(@Field("companyId") String companyId, @Field("userId") String userId, @Field("title") String title, @Field("startDate") String startDate, @Field("endDate") String endDate, @Field("estimatePrice") String estimatePrice, @Field("remark") String remark);

    @FormUrlEncoded
    @POST("payRecord/grid")
    Observable<BaseEntity<CompanyeExpenditureInfoBeanB>> getExpenditureListB(@Field("page") int page, @Field("rows") int rows, @Field("companyId") String companyId, @Field("userId") String userId, @Field("payDates") String payDates, @Field("payDatee") String payDatee);

    @FormUrlEncoded
    @POST("payRecord/getInfo")
    Observable<BaseEntity<CompanyExpenditureInfoB>> getExpenditureDetailB(@Field("id") String id);

    @FormUrlEncoded
    @POST("payRecord/save")
    Observable<BaseEntity> saveExpenditureB(@Field("companyId") String companyId, @Field("userId") String userId, @Field("payDate") String payDate, @Field("price") String price, @Field("remark") String remark);

    @FormUrlEncoded
    @POST("defile/file/code")
    Observable<ResponseBody> downloadFiles(@Field("code") String code);
}
