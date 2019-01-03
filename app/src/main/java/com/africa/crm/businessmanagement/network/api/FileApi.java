package com.africa.crm.businessmanagement.network.api;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;
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

    @POST("defile/fileupload")
    @Multipart
    Observable<BaseEntity<FileInfoBean>> uploadFiles(@Part MultipartBody.Part part);

    @POST("defile/fileupload")
    @Multipart
    Observable<BaseEntity<FileInfoBean>> uploadImages(@Part MultipartBody.Part part);
}
