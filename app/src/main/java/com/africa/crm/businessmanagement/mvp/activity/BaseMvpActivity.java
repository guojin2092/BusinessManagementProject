package com.africa.crm.businessmanagement.mvp.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.africa.crm.businessmanagement.mvp.presenter.BasePresenter;

import baseutil.library.base.BaseActivity;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 16:43
 * Modification  History:
 * Why & What is modified:
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {


    protected P mPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = injectPresenter();
        mPresenter.attach(this);
    }

    protected abstract P injectPresenter();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }
}
