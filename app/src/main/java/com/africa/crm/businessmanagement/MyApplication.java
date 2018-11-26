package com.africa.crm.businessmanagement;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 14:55
 * Modification  History:
 * Why & What is modified:
 */

import android.support.multidex.MultiDexApplication;

import com.africa.crm.businessmanagement.network.DataManager;

public class MyApplication extends MultiDexApplication {
    private static MyApplication instance;
    private DataManager mDataManager;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mDataManager = DataManager.newInstance();

    }

    public static MyApplication getInstance() {
        if (null == instance) {
            instance = new MyApplication();
        }
        return instance;
    }

    public DataManager getDataManager() {
        return mDataManager;
    }
}
