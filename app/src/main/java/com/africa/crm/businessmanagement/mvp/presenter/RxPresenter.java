package com.africa.crm.businessmanagement.mvp.presenter;

import com.africa.crm.businessmanagement.network.base.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 16:40
 * Modification  History:
 * Why & What is modified:
 */
public class RxPresenter<V extends BaseView> extends BasePresenter<V> {

    /**
     * 管理所有添加进来的Disposable；
     */
    protected CompositeDisposable mCompositeDisposable;

    public RxPresenter() {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
    }

    @Override
    public void attach(V view) {
        super.attach(view);
    }

    protected void addDisposable(Disposable disposable) {
        mCompositeDisposable.add(disposable);
    }


    @Override
    public void detach() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.clear();
        }
        super.detach();
    }
}
