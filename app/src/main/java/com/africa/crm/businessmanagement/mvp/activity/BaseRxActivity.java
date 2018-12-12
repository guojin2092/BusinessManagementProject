package com.africa.crm.businessmanagement.mvp.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.network.DataManager;
import com.africa.crm.businessmanagement.network.base.BaseView;
import com.africa.crm.businessmanagement.network.error.ComException;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.LoadingDialog;

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

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    protected DataManager mDataManager;

    /**
     * 管理所有添加进来的Disposable；
     */
    protected CompositeDisposable mCompositeDisposable;

    private LoadingDialog mDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
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

    public LoadingDialog getDialog() {
        if (mDialog == null) {
            mDialog = new LoadingDialog(this);
        }
        return mDialog;
    }

    @Override
    public void onDialogShow() {
        getDialog().show();
    }

    @Override
    public void onDialogHide() {
        if (mDialog != null && getDialog().isShowing()) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onTakeException(@NonNull ComException error) {
        toastMsg(error);
    }

    public final void toastMsg(final ComException error) {
        Snackbar.make(findViewById(R.id.titlebar) == null ? getWindow().getDecorView() : findViewById(R.id.titlebar)
                , ErrorMsg.showErrorMsg(error.getMessage())
                , Snackbar.LENGTH_LONG)
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

    public final void toastMsg(String msg, String actionName, ComException.OnErrorListener onErrorListener) {
        toastMsg(new ComException(msg, actionName, onErrorListener));
    }

    @Override
    public Context getBVContext() {
        return this;
    }

    @Override
    public FragmentActivity getBVActivity() {
        return this;
    }

    @Override
    public void reLogin() {

    }

}
