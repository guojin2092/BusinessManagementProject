package com.africa.crm.businessmanagement.mvp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.africa.crm.businessmanagement.mvp.presenter.IBasePresenter;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 16:51
 * Modification  History:
 * Why & What is modified:
 */
public abstract class BaseMvpFragment<P extends IBasePresenter> extends BaseRxFragment {

    protected P mPresenter;

    protected abstract P initPresenter();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        mPresenter.attach(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.detach();
    }
}
