package com.africa.crm.businessmanagement.network.api;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.MainStationInfoBean;
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
    Observable<BaseEntity<UserManagementInfoBean>> getUserList(@Field("page") int page, @Field("rows") int rows, @Field("userName") String userName, @Field("type") String type, @Field("companyId") String companyId, @Field("state") String state);
}
