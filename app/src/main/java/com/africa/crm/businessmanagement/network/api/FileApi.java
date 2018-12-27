package com.africa.crm.businessmanagement.network.api;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.LoginInfoBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 14:39
 * Modification  History:
 * Why & What is modified:
 */
public interface FileApi {

    @FormUrlEncoded
    @POST("defile/fileupload")
    Observable<BaseEntity<LoginInfoBean>> uploadFiles(@Part MultipartBody.Part part);

    @FormUrlEncoded
    @POST("defile/file")
    Observable<BaseEntity<DicInfo>> downloadFiles(@Field("code") String code);

}
