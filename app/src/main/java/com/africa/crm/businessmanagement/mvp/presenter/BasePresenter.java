package com.africa.crm.businessmanagement.mvp.presenter;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.network.DataManager;
import com.africa.crm.businessmanagement.network.base.BaseView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 16:41
 * Modification  History:
 * Why & What is modified:
 */
public class BasePresenter<V extends BaseView> implements IBasePresenter<V> {
    protected DataManager mDataManager;

    protected V mView;


    @Override
    public void attach(V view) {
        this.mView = view;
        mDataManager = MyApplication.getInstance().getDataManager();
    }

    @Override
    public void detach() {
        this.mView = null;
    }

    public V getView() {
        return mView;
    }
}
