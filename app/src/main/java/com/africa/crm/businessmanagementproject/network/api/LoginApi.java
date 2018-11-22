package com.africa.crm.businessmanagementproject.network.api;

import com.africa.crm.businessmanagementproject.bean.GoodAlertBean;
import com.africa.crm.businessmanagementproject.network.base.BaseEntity;

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
    @POST("index.php?r=client/get-goods-alert")
    Observable<BaseEntity<GoodAlertBean>> getGoodsAlert(@Field("ak") String ak, @Field("sn") String sn, @Field("gid") String gid);
}
