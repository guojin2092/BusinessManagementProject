package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.CompanyPackagingDataInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPackagingDataContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import io.reactivex.functions.Consumer;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/19 0019 9:15
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyPackagingDataPresenter extends RxPresenter<CompanyPackagingDataContract.View> implements CompanyPackagingDataContract.Presenter {
    @Override
    public void getCompanyUserList(int page, int rows, String userName, String type, String companyId, String state, String name) {
        addDisposable(mDataManager.getUserList(page, rows, userName, type, companyId, state, name)
                .compose(RxUtils.<UserManagementInfoBean>ioToMain(mView))
                .subscribe(new Consumer<UserManagementInfoBean>() {
                    @Override
                    public void accept(UserManagementInfoBean userManagementInfoBean) throws Exception {
                        mView.getCompanyUserList(userManagementInfoBean);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getCompanyPackagingDataList(int page, int rows, String companyId, String userId, String createTimes, String createTimee) {
        addDisposable(mDataManager.getCompanyPackagingDataList(page, rows, companyId, userId, createTimes, createTimee)
                .compose(RxUtils.<CompanyPackagingDataInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyPackagingDataInfoBean>() {
                    @Override
                    public void accept(CompanyPackagingDataInfoBean companyPayOrderInfoBean) throws Exception {
                        mView.getCompanyPackagingDataList(companyPayOrderInfoBean);
                    }
                }, new ComConsumer(mView)));

    }

}
