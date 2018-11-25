package com.africa.crm.businessmanagementproject.network.retrofit;

import com.africa.crm.businessmanagementproject.network.encryption.Coder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/24 0024 18:09
 * Modification  History:
 * Why & What is modified:
 */
public class HeaderInterceptor implements Interceptor {
    String authKey = "sycrm:sycrmad";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder().addHeader("Authorization", authKey).build();
        return chain.proceed(request);
    }
}