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

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDexApplication;
import android.support.v7.app.AppCompatDelegate;

import com.africa.crm.businessmanagement.network.DataManager;
import com.africa.crm.businessmanagement.widget.LogUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreater;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.simplesoft.baselibrary.utils.DynamicTimeFormat;
import com.tencent.smtt.sdk.QbSdk;

import java.util.LinkedList;
import java.util.List;

public class MyApplication extends MultiDexApplication {
    private static MyApplication instance;
    private DataManager mDataManager;

    /**
     * Activity集合
     */
    private List<Activity> mActivitys;

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
        mActivitys = new LinkedList<>();
        registerActivityListener();
        QbSdk.initX5Environment(this,cb);
//        setDatabase();
    }

    /**
     * 搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
     */
    QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

        @Override
        public void onViewInitFinished(boolean arg0) {
            //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
            LogUtil.e("MyApplication", " onViewInitFinished is " + arg0);
        }

        @Override
        public void onCoreInitFinished() {
            LogUtil.e("MyApplication", " onCoreInitFinished");
        }
    };

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


    public List<Activity> getAllActivitys() {
        return mActivitys;
    }

    /**
     * 添加Activity到容器中
     */
    public void pushActivity(Activity activity) {
        mActivitys.add(activity);
    }

    /**
     * 移除当前的activity
     */
    public void popActivity(Activity activity) {
        if (mActivitys.contains(activity)) {
            mActivitys.remove(activity);
        }
    }

    /**
     * 清除当前Activity以外的Activities
     */
    public void finishOthersActivities() {
        finishTopActivitiesByNum(mActivitys.size() - 1);
    }

    /**
     * 遍历所有Activity并finish
     */
    public void finishAllActivities() {
        //退出
        for (Activity activity : mActivitys) {
            activity.finish();
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishTopActivitiesByNum(Class<?> cls) {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return;
        }
        for (Activity activity : mActivitys) {
            if (activity.getClass().equals(cls)) {
                finishTopActivitiesByNum(activity);
            }
        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishTopActivitiesByNum(Activity activity) {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return;
        }
        if (activity != null) {
            activity.finish();
            popActivity(activity);
        }
    }

    public void finishActivitiesOnActivity(Class<?> cls) {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return;
        }
        Activity targetActivity = null;
        for (Activity activity : mActivitys) {
            if (activity.getClass().equals(cls)) {
                targetActivity = activity;
            }
        }

        _finishActivitiesOnActivity(targetActivity);
    }

    public void finishActivitiesOnActivity(Activity activity) {
        if (mActivitys == null || mActivitys.isEmpty()) {
            return;
        }
        _finishActivitiesOnActivity(activity);
    }

    private void _finishActivitiesOnActivity(Activity targetActivity) {
        if (targetActivity != null && mActivitys.contains(targetActivity)) {
            int index = mActivitys.indexOf(targetActivity);
            int size = mActivitys.size();

            for (int i = ++index; i < size; i++) {
                mActivitys.get(i).finish();
                mActivitys.remove(i);
            }
        }
    }

    /**
     * 同时出栈多个Activity
     */
    public void finishTopActivitiesByNum(int num) {
        if (num > mActivitys.size()) {
            throw new ArrayIndexOutOfBoundsException();
        }
        for (int i = 0; i < num; i++) {
            mActivitys.get(mActivitys.size() - 1).finish();
            mActivitys.remove(mActivitys.size() - 1);
        }
    }


    /**
     * 遍历所有Activity并finish
     */
    public void exit() {
        //退出
        for (Activity activity : mActivitys) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }


    private void registerActivityListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                /**
                 *  监听到 Activity创建事件 将该 Activity 加入list
                 */
                pushActivity(activity);

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (null == mActivitys || mActivitys.isEmpty()) {
                    return;
                }
                popActivity(activity);
            }
        });

    }
}
