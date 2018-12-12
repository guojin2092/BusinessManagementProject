package com.africa.crm.businessmanagement.network;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInfoBean;
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
    public Observable<UserInfo> getUserInfo(String id) {
        return mHttpHelper.getUserInfo(id);
    }

    @Override
    public Observable<BaseEntity> saveOrcreateUser(String id, String userName, String type, String roleIds, String passWord, String name, String phone, String address, String email, String state, String companyId, String head) {
        return mHttpHelper.saveOrcreateUser(id, userName, type, roleIds, passWord, name, phone, address, email, state, companyId, head);
    }

    @Override
    public Observable<List<DicInfo2>> getAllCompany(String name) {
        return mHttpHelper.getAllCompany(name);
    }

    @Override
    public Observable<List<RoleInfoBean>> getAllRoles(String name) {
        return mHttpHelper.getAllRoles(name);
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
    public Observable<BaseEntity> saveCompanyInfo(String id, String head, String name, String code, String type, String address, String phone, String email, String mid, String area, String profession, String numA, String state) {
        return mHttpHelper.saveCompanyInfo(id, head, name, code, type, address, phone, email, mid, area, profession, numA, state);
    }

    @Override
    public Observable<CompanyInfoBean> getCompanyAccounList(int page, int rows, String companyId, String userName, String name) {
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
    public Observable<BaseEntity> saveCompanyAccount(String id, String userName, String type, String roleId, String passWord, String name, String phone, String address, String email, String state, String companyId, String head) {
        return mHttpHelper.saveCompanyAccount(id, userName, type, roleId, passWord, name, phone, address, email, state, companyId, head);
    }

}
