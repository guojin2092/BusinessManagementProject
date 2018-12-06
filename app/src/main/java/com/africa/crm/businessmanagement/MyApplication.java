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


/*    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;*/

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
