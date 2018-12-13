package com.africa.crm.businessmanagement.network;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInfoBean;
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

import java.util.List;

import io.reactivex.Observable;

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

    Observable<List<DicInfo>> getDicByCode(String code);

    Observable<List<MainStationInfoBean>> getMainStationInfo(String id);

    Observable<UserManagementInfoBean> getUserList(int page, int rows, String userName, String type, String companyId, String state, String name);

    Observable<BaseEntity> deleteUser(String id);

    Observable<UserInfo> getUserInfo(String id);

    Observable<BaseEntity> saveOrcreateUser(String id, String userName, String type, String roleIds, String passWord, String name, String phone, String address, String email, String state, String companyId, String head);

    Observable<List<DicInfo2>> getAllCompany(String name);

    Observable<List<RoleInfoBean>> getAllRoles(String name);

    Observable<RoleManagementInfoBean> getRoleList(int page, int rows, String roleName, String roleCode, String typeName);

    Observable<RoleInfoBean> getRoleInfo(String id);

    Observable<BaseEntity> saveRoleInfo(String userId, String id, String roleName, String roleCode, String typeName, String orderNum);

    Observable<List<RoleLimitInfoBean>> getRoleLimit(String id);

    Observable<BaseEntity> saveRoleLimit(String id, String resourceIds, String btnIds);

    Observable<CompanyInfoBean> getCompanyInfoList(int page, int rows, String name);

    Observable<BaseEntity> deleteCompanyInfo(String id);

    Observable<CompanyInfo> getCompanyInfoDetail(String id);

    Observable<BaseEntity> saveCompanyInfo(String id, String head, String name, String code, String type, String address, String phone, String email, String mid, String area, String profession, String numA, String state);

    Observable<CompanyInfoBean> getCompanyAccounList(int page, int rows, String companyId, String userName, String name);

    Observable<BaseEntity> deleteCompanyAccount(String id);

    Observable<CompanyAccountInfo> getCompanyAccountDetail(String id);

    Observable<BaseEntity> saveCompanyAccount(String id, String userName, String type, String roleId, String passWord, String name, String phone, String address, String email, String state, String companyId, String head);

    Observable<CompanySupplierInfoBean> getCompanySupplierList(int page, int rows, String companyId, String name, String type);

    Observable<BaseEntity> deleteCompanySupplier(String id);

    Observable<CompanySupplierInfo> getCompanySupplierDetail(String id);

    Observable<BaseEntity> saveCompanySupplier(String id, String companyId, String head, String name, String type, String address, String phone, String email, String zipCode, String area, String remark);

    Observable<CompanyContactInfoBean> getCompanyContactList(int page, int rows, String companyId, String userId, String name, String fromType);

    Observable<BaseEntity> deleteCompanyContact(String id);

}
