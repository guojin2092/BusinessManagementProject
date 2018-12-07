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

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.africa.crm.businessmanagement.network.DataManager;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.simplesoft.baselibrary.utils.DynamicTimeFormat;

public class MyApplication extends MultiDexApplication {
    private static MyApplication instance;
    private DataManager mDataManager;


/*    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;*/

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        SmartRefreshLayout.setDefaultRefreshHeaderCreater(new DefaultRefreshHeaderCreater() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.F2F2F2, R.color.a999999);//全局设置主题颜色
                return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
            }
        });
    }


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mDataManager = DataManager.newInstance();
//        setDatabase();
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

//    /**
//     *      * 设置greenDao
//     */
//    private void setDatabase() {
//        //创建数据库crm-db"
//        mHelper = new DaoMaster.DevOpenHelper(this, "crm-db", null);
//        //获取可写数据库
//        db = mHelper.getWritableDatabase();
//        //获取数据库对象
//        mDaoMaster = new DaoMaster(db);
//        //获取Dao对象管理者
//        mDaoSession = mDaoMaster.newSession();
//    }
//
//    public DaoSession getDaoSession() {
//        return mDaoSession;
//    }
//
//    public SQLiteDatabase getDb() {
//        return db;
//    }

}
