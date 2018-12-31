package com.africa.crm.businessmanagement.network.api;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.LoginInfoBean;

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
public interface LoginApi {

    @FormUrlEncoded
    @POST("login/doLogin")
    Observable<BaseEntity<LoginInfoBean>> getLoginInfo(@Field("userName") String userName, @Field("passWord") String passWord);

    @FormUrlEncoded
    @POST("login/doLogin")
    Observable<BaseEntity> changePassword(@Field("id") String id, @Field("oldPassWord") String oldPassWord, @Field("newPassWord") String newPassWord);

    @FormUrlEncoded
    @POST("dic/getDicByCode")
    Observable<BaseEntity<List<DicInfo>>> getDicByCode(@Field("code") String code);

}
