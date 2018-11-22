package com.africa.crm.businessmanagementproject.network;

import com.africa.crm.businessmanagementproject.bean.GoodAlertBean;
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

    /**
     * Text Value
     */
    private String mGId = "1487";
    private String mAk = "IXuNz/MuG7VUJJ1Eg4octg==";
    private String mSn = "HIiaau8Hg6C7t7dQPAe+KcCLv5c0QxCZ3GtaZyF165gMEk5Iymznn023tGKjLuO1";

    private static final HttpHelperImpl getInstance = new HttpHelperImpl();

    public static HttpHelperImpl getInstance() {
        return getInstance;
    }

    private HttpHelperImpl() {
        loginApi = RetrofitHelper.provideApi(LoginApi.class);
    }

    @Override
    public Observable<GoodAlertBean> getGoodsAlert(String gId) {
        return loginApi.getGoodsAlert(mAk, mSn, mGId).compose(RxUtils.<GoodAlertBean>handleResult());
    }
}
