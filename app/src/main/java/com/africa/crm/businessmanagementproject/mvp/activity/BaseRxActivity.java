package com.africa.crm.businessmanagementproject.mvp.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.africa.crm.businessmanagementproject.MyApplication;
import com.africa.crm.businessmanagementproject.network.DataManager;
import com.africa.crm.businessmanagementproject.network.base.BaseView;
import com.africa.crm.businessmanagementproject.network.error.ComException;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 15:57
 * Modification  History:
 * Why & What is modified:
 */
public abstract class BaseRxActivity extends AppCompatActivity implements BaseView {
    protected DataManager mDataManager;
    /**
     * 管理所有添加进来的Disposable；
     */
    protected CompositeDisposable mCompositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataManager = MyApplication.getInstance().getDataManager();
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
    }

    protected void addDisposable(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
    }

    @Override
    public void showLoad() {

    }

    @Override
    public void showActionLoad() {

    }

    @Override
    public void dismissLoad() {

    }

    @Override
    public void dismissActionLoad() {

    }

    @Override
    public void onTakeException(@NonNull ComException e) {
        onTakeException(false, e);
    }

    @Override
    public void onTakeException(boolean action, @NonNull ComException e) {

    }

    @Override
    public void reLogin() {

    }

    @Override
    public void dismissLoad(boolean isSuccess, String showWord) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public Activity getActivity() {
        return this;
    }


}
