package com.africa.crm.businessmanagement.mvp.activity;

import android.os.Bundle;

import com.africa.crm.businessmanagement.baseutil.library.base.BaseActivity;
import com.africa.crm.businessmanagement.mvp.presenter.BasePresenter;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = injectPresenter();
        mPresenter.attach(this);
        requestData();
    }

    protected abstract P injectPresenter();

    /**
     * 请求初始化数据
     */
    protected abstract void requestData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }
}
