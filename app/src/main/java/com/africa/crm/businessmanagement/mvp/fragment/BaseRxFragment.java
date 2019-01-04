package com.africa.crm.businessmanagement.mvp.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.mvp.activity.BaseRxActivity;
import com.africa.crm.businessmanagement.network.DataManager;
import com.africa.crm.businessmanagement.network.base.BaseView;
import com.africa.crm.businessmanagement.network.error.ComException;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;

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
    public void onDialogShow() {
        ((BaseRxActivity) getActivity()).onDialogShow();
    }

    @Override
    public void onDialogHide() {
        ((BaseRxActivity) getActivity()).onDialogHide();
    }

    @Override
    public void onTakeException(@NonNull ComException error) {
        toastMsg(error);
    }

    @Override
    public void loadLocalData(String port) {
        onDialogHide();
    }

    public final void toastMsg(final ComException error) {
        Snackbar.make(getView() == null ? getActivity().getWindow().getDecorView() : getView(), ErrorMsg.showErrorMsg(error.getMessage()), Snackbar.LENGTH_LONG)
                .setAction(error.getActionName() == null ? getString(R.string.i_know) : error.getActionName()
                        , new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (error.getListener() != null) {
                                    error.getListener().errorAction();
                                }
                            }
                        })
                .show();
    }

    public final void toastMsg(String msg) {
        toastMsg(new ComException(msg));
    }

    @Override
    public void reLogin() {

    }

    @Override
    public Context getBVContext() {
        return getContext();
    }

    @Override
    public FragmentActivity getBVActivity() {
        if (getActivity() instanceof BaseRxActivity) {
            return getActivity();
        } else {
            throw new RuntimeException("fragment 需要放到BaseRxActivity上");
        }
    }
}
