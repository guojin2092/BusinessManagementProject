package com.africa.crm.businessmanagementproject.network;

import com.africa.crm.businessmanagementproject.main.bean.LoginInfoBean;
import com.africa.crm.businessmanagementproject.network.api.LoginApi;
import com.africa.crm.businessmanagementproject.network.retrofit.RetrofitHelper;
import com.africa.crm.businessmanagementproject.network.util.RxUtils;

import io.reactivex.Observable;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 14:48
 * Modification  History:
 * Why & What is modified:
 */
public class HttpHelperImpl implements HttpHelper {
    private LoginApi loginApi;

    private static final HttpHelperImpl getInstance = new HttpHelperImpl();

    public static HttpHelperImpl getInstance() {
        return getInstance;
    }

    private HttpHelperImpl() {
        loginApi = RetrofitHelper.provideApi(LoginApi.class);
    }


    @Override
    public Observable<LoginInfoBean> getLoginInfo(String userName, String passWord) {
        return loginApi.getLoginInfo(userName, passWord).compose(RxUtils.<LoginInfoBean>handleResult());
    }
}
