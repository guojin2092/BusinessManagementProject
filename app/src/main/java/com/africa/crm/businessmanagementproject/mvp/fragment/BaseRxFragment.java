package com.africa.crm.businessmanagementproject.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

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
 * Date：2018/11/22 0022 16:46
 * Modification  History:
 * Why & What is modified:
 */
public class BaseRxFragment extends Fragment implements BaseView {
    protected DataManager mDataManager;
    /**
     * 管理所有添加进来的Disposable；
     */
    protected CompositeDisposable mCompositeDisposable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
    }

    /**
     * 管理所有添加进来的Disposable；
     */

    protected void addDisposable(Disposable disposable) {

        mCompositeDisposable.add(disposable);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
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
}
