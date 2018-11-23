package com.africa.crm.businessmanagementproject.main.presenter;

import com.africa.crm.businessmanagementproject.main.bean.LoginInfoBean;
import com.africa.crm.businessmanagementproject.main.contract.LoginContract;
import com.africa.crm.businessmanagementproject.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagementproject.network.error.ComConsumer;
import com.africa.crm.businessmanagementproject.network.util.RxUtils;

import io.reactivex.functions.Consumer;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/23 0023 10:00
 * Modification  History:
 * Why & What is modified:
 */
public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {


    @Override
    public void getLoginInfo(String userName, String passWord) {
        addDisposable(mDataManager.getLoginInfo(userName, passWord)
                .compose(RxUtils.<LoginInfoBean>ioToMain(mView))
                .subscribe(new Consumer<LoginInfoBean>() {
                    @Override
                    public void accept(LoginInfoBean loginInfoBean) throws Exception {
                        mView.getLoginInfo(loginInfoBean);
                    }
                }, new ComConsumer(mView)));
    }
}
