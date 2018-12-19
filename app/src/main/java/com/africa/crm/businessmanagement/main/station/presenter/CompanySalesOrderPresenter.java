package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanySalesOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanySalesOrderContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

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
public class CompanySalesOrderPresenter extends RxPresenter<CompanySalesOrderContract.View> implements CompanySalesOrderContract.Presenter {
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
    public void getStateType(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getStateType(dicInfoList);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getCompanySalesOrderList(int page, int rows, String companyId, String userId, String name, String state, String createTimes, String createTimee) {
        addDisposable(mDataManager.getCompanySalesOrderList(page, rows, companyId, userId, name, state, createTimes, createTimee)
                .compose(RxUtils.<CompanySalesOrderInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanySalesOrderInfoBean>() {
                    @Override
                    public void accept(CompanySalesOrderInfoBean companyQuotationInfoBean) throws Exception {
                        mView.getCompanySalesOrderList(companyQuotationInfoBean);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void deleteCompanySalesOrder(String id) {
        addDisposable(mDataManager.deleteCompanySalesOrder(id)
                .compose(RxUtils.<BaseEntity>ioToMain())
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteCompanySalesOrder(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }

}
