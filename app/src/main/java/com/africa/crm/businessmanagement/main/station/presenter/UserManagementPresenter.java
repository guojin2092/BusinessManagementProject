package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.UserManagementContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import io.reactivex.functions.Consumer;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/7 0007 10:38
 * Modification  History:
 * Why & What is modified:
 */
public class UserManagementPresenter extends RxPresenter<UserManagementContract.View> implements UserManagementContract.Presenter {
    @Override
    public void getUserList(int page, int rows, String userName, String type, String companyId, String state) {
        addDisposable(mDataManager.getUserList(page, rows, userName, type, companyId, state)
                .compose(RxUtils.<UserManagementInfoBean>ioToMain(mView))
                .subscribe(new Consumer<UserManagementInfoBean>() {
                    @Override
                    public void accept(UserManagementInfoBean userManagementInfoBean) throws Exception {
                        mView.getUserList(userManagementInfoBean);
                    }
                }, new ComConsumer(mView)));
    }
}
