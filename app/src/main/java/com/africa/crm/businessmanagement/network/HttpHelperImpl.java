package com.africa.crm.businessmanagement.network;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyClientInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyClientInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyProductInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyProductInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyQuotationInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyQuotationInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanySalesOrderInfo;
import com.africa.crm.businessmanagement.main.bean.CompanySalesOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfo;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.LoginInfoBean;
import com.africa.crm.businessmanagement.main.bean.MainStationInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleLimitInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleManagementInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserInfo;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.network.api.LoginApi;
import com.africa.crm.businessmanagement.network.api.MainApi;
import com.africa.crm.businessmanagement.network.retrofit.RetrofitHelper;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.Observable;

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

    private static final HttpHelperImpl getInstance = new HttpHelperImpl();

    public static HttpHelperImpl getInstance() {
        return getInstance;
    }

    private HttpHelperImpl() {
        loginApi = RetrofitHelper.provideApi(LoginApi.class);
        mainApi = RetrofitHelper.provideApi(MainApi.class);
    }


    @Override
    public Observable<LoginInfoBean> getLoginInfo(String userName, String passWord) {
        return loginApi.getLoginInfo(userName, passWord).compose(RxUtils.<LoginInfoBean>handleResult());
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
    public Observable<List<RoleInfoBean>> getAllRoles(String name) {
        return mainApi.getAllRoles(name).compose(RxUtils.<List<RoleInfoBean>>handleResult());
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
    public Observable<BaseEntity> saveCompanyInfo(String id, String head, String name, String code, String type, String address, String phone, String email, String mid, String area, String profession, String numA, String state) {
        return mainApi.saveCompanyInfo(id, head, name, code, type, address, phone, email, mid, area, profession, numA, state);
    }

    @Override
    public Observable<CompanyInfoBean> getCompanyAccounList(int page, int rows, String companyId, String userName, String name) {
        return mainApi.getCompanyAccounList(page, rows, companyId, userName, name).compose(RxUtils.<CompanyInfoBean>handleResult());
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
    public Observable<BaseEntity> saveCompanyAccount(String id, String userName, String type, String roleId, String passWord, String name, String phone, String address, String email, String state, String companyId, String head) {
        return mainApi.saveCompanyAccount(id, userName, type, roleId, passWord, name, phone, address, email, state, companyId, head);
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
    public Observable<BaseEntity> saveCompanySupplier(String id, String companyId, String head, String name, String type, String address, String phone, String email, String zipCode, String area, String remark) {
        return mainApi.saveCompanySupplier(id, companyId, head, name, type, address, phone, email, zipCode, area, remark);
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
    public Observable<BaseEntity> saveCompanyContact(String id, String companyId, String userId, String head, String name, String fromType, String address, String mailAddress, String phone, String tel, String email, String job, String remark) {
        return mainApi.saveCompanyContact(id, companyId, userId, head, name, fromType, address, mailAddress, phone, tel, email, job, remark);
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
    public Observable<BaseEntity> saveCompanyClient(String id, String companyId, String userId, String head, String name, String industry, String address, String workerNum, String tel, String yearIncome, String remark) {
        return mainApi.saveCompanyClient(id, companyId, userId, head, name, industry, address, workerNum, tel, yearIncome, remark);
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
    public Observable<BaseEntity> saveCompanyProduct(String id, String companyId, String name, String code, String supplierName, String makerName, String type, String unitPrice, String unit, String stockNum, String warnNum, String remark) {
        return mainApi.saveCompanyProduct(id, companyId, name, code, supplierName, makerName, type, unitPrice, unit, stockNum, warnNum, remark);
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
    public Observable<BaseEntity> saveCompanyQuotation(String id, String companyId, String userId, String name, String customerName, String contactName, String termOfValidity, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark) {
        return mainApi.saveCompanyQuotation(id, companyId, userId, name, customerName, contactName, termOfValidity, sendAddress, sendAddressZipCode, destinationAddress, destinationAddressZipCode, products, clause, remark);
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
    public Observable<BaseEntity> saveCompanySalesOrder(String id, String companyId, String userId, String name, String customerName, String contactName, String saleCommission, String state, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark) {
        return mainApi.saveCompanySalesOrder(id, companyId, userId, name, customerName, contactName, saleCommission, state, sendAddress, sendAddressZipCode, destinationAddress, destinationAddressZipCode, products, clause, remark);
    }

}
