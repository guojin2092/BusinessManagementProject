package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPayOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPayOrderContract;
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
public class CompanyPayOrderPresenter extends RxPresenter<CompanyPayOrderContract.View> implements CompanyPayOrderContract.Presenter {
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
    public void getCompanyPayOrderList(int page, int rows, String companyId, String userId, String name, String code, String createTimes, String createTimee) {
        addDisposable(mDataManager.getCompanyPayOrderList(page, rows, companyId, userId, name, code, createTimes, createTimee)
                .compose(RxUtils.<CompanyPayOrderInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyPayOrderInfoBean>() {
                    @Override
                    public void accept(CompanyPayOrderInfoBean companyPayOrderInfoBean) throws Exception {
                        mView.getCompanyPayOrderList(companyPayOrderInfoBean);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void deleteCompanyPayOrder(String id) {
        addDisposable(mDataManager.deleteCompanyPayOrder(id)
                .compose(RxUtils.<BaseEntity>ioToMain())
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteCompanyPayOrder(baseEntity);
                    }
                }, new ComConsumer(mView)));

    }

}
