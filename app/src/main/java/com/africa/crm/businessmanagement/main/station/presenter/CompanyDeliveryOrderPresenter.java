package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyDeliveryOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyDeliveryOrderContract;
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
public class CompanyDeliveryOrderPresenter extends RxPresenter<CompanyDeliveryOrderContract.View> implements CompanyDeliveryOrderContract.Presenter {
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
    public void getCompanyDeliveryOrderList(int page, int rows, String companyId, String userId, String name, String createTimes, String createTimee) {
        addDisposable(mDataManager.getCompanyDeliveryOrderList(page, rows, companyId, userId, name, createTimes, createTimee)
                .compose(RxUtils.<CompanyDeliveryOrderInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyDeliveryOrderInfoBean>() {
                    @Override
                    public void accept(CompanyDeliveryOrderInfoBean companyDeliveryOrderInfoBean) throws Exception {
                        mView.getCompanyDeliveryOrderList(companyDeliveryOrderInfoBean);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void deleteCompanyDeliveryOrder(String id) {
        addDisposable(mDataManager.deleteCompanyDeliveryOrder(id)
                .compose(RxUtils.<BaseEntity>ioToMain())
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteCompanyDeliveryOrder(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }

}
