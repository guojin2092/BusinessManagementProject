package com.africa.crm.businessmanagement.network.api;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.MainStationInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
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
    Observable<BaseEntity<List<DicInfo>>> getAllCompany(@Field("name") String name);

    @FormUrlEncoded
    @POST("role/queryAll")
    Observable<BaseEntity<List<RoleInfoBean>>> getAllRoles(@Field("name") String name);
}
