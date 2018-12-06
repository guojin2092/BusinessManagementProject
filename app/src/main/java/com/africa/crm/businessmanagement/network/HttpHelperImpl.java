package com.africa.crm.businessmanagement.network;

import com.africa.crm.businessmanagement.main.bean.LoginInfoBean;
import com.africa.crm.businessmanagement.main.bean.MainStationInfoBean;
import com.africa.crm.businessmanagement.network.api.LoginApi;
import com.africa.crm.businessmanagement.network.api.MainApi;
import com.africa.crm.businessmanagement.network.retrofit.RetrofitHelper;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

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
    private MainApi mainApi;

    private static final HttpHelperImpl getInstance = new HttpHelperImpl();

    public static HttpHelperImpl getInstance() {
        return getInstance;
    }

    private HttpHelperImpl() {
        loginApi = RetrofitHelper.provideApi(LoginApi.class);
        mainApi = RetrofitHelper.provideApi(MainApi.class);
    }


    @Override
    public Observable<LoginInfoBean> getLoginInfo(String userName, String passWord) {
        return loginApi.getLoginInfo(userName, passWord).compose(RxUtils.<LoginInfoBean>handleResult());
    }

    @Override
    public Observable<List<MainStationInfoBean>> getMainStationInfo(String id) {
        return mainApi.getMainStationInfo(id).compose(RxUtils.<List<MainStationInfoBean>>handleResult());
    }

}
