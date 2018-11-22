package com.africa.crm.businessmanagementproject.network;

import com.africa.crm.businessmanagementproject.bean.GoodAlertBean;

import io.reactivex.Observable;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 15:26
 * Modification  History:
 * Why & What is modified:
 */
public class DataManager implements HttpHelper {
    private static final DataManager INSTANCE = new DataManager();
    private HttpHelperImpl mHttpHelper;

    public static DataManager newInstance() {
        return INSTANCE;
    }

    private DataManager() {
        mHttpHelper = HttpHelperImpl.getInstance();
    }

    @Override
    public Observable<GoodAlertBean> getGoodsAlert(String gId) {
        return mHttpHelper.getGoodsAlert(gId);
    }
}
