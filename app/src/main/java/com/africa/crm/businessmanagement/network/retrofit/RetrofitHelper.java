package com.africa.crm.businessmanagement.network.retrofit;

import com.africa.crm.businessmanagement.BuildConfig;
import com.africa.crm.businessmanagement.network.api.ApiConfig;
import com.africa.crm.businessmanagement.network.util.FileUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 14:51
 * Modification  History:
 * Why & What is modified:
 */

public class RetrofitHelper {
    private static OkHttpClient okHttpClient;
    private static Retrofit retrofit;

    public static <T> T provideApi(Class<T> clazz) {
        if (retrofit == null) {
            init();
        }
        return retrofit.create(clazz);
    }

    public static void init() {
        if (okHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (okHttpClient == null) {
                    okHttpClient = provideOkHttpClient();
                }
            }
        }
        if (retrofit == null) {
            synchronized (RetrofitHelper.class) {
                if (retrofit == null) {
                    retrofit = provideRetrofit(okHttpClient);
                }
            }
        }
    }

    public static OkHttpClient provideOkHttpClient() {
        //设置Http缓存
        Cache cache = new Cache(FileUtils.createFileDir(FileUtils.CACHE_HTTP), 1024 * 1024 * 10);

        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(new HeaderInterceptor())
                .addInterceptor(new RequestEncryptInterceptor())
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS);
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY));
        }
        return builder.build();
    }

    public static Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(ApiConfig.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

}
