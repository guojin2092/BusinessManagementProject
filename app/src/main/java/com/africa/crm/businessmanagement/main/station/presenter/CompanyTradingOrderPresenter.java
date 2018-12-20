package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyQuotationInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyTradingOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyQuotationContract;
import com.africa.crm.businessmanagement.main.station.contract.CompanyTradingOrderContract;
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
public class CompanyTradingOrderPresenter extends RxPresenter<CompanyTradingOrderContract.View> implements CompanyTradingOrderContract.Presenter {
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
    public void getCompanyTradingOrderList(int page, int rows, String companyId, String userId, String name, String createTimes, String createTimee) {
        addDisposable(mDataManager.getCompanyTradingOrderList(page, rows, companyId, userId, name, createTimes, createTimee)
                .compose(RxUtils.<CompanyTradingOrderInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyTradingOrderInfoBean>() {
                    @Override
                    public void accept(CompanyTradingOrderInfoBean companyTradingOrderInfoBean) throws Exception {
                        mView.getCompanyTradingOrderList(companyTradingOrderInfoBean);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void deleteCompanyTradingOrder(String id) {
        addDisposable(mDataManager.deleteCompanyTradingOrder(id)
                .compose(RxUtils.<BaseEntity>ioToMain())
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteCompanyTradingOrder(baseEntity);
                    }
                }, new ComConsumer(mView)));

    }

}
