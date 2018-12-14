package com.africa.crm.businessmanagement.network.api;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfo;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.MainStationInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleLimitInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleManagementInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserInfo;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;

import java.util.List;

import io.reactivex.Observable;
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
    Observable<BaseEntity<CompanyInfoBean>> getCompanyAccounList(@Field("page") int page, @Field("rows") int rows, @Field("companyId") String companyId, @Field("userName") String userName, @Field("name") String name);

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
}
