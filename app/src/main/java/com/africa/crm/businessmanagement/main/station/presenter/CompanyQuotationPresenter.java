package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyQuotationInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyQuotationContract;
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
public class CompanyQuotationPresenter extends RxPresenter<CompanyQuotationContract.View> implements CompanyQuotationContract.Presenter {
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
    public void getCompanyQuotationList(int page, int rows, String companyId, String userId, String name, String createTimes, String createTimee) {
        addDisposable(mDataManager.getCompanyQuotationList(page, rows, companyId, userId, name, createTimes, createTimee)
                .compose(RxUtils.<CompanyQuotationInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyQuotationInfoBean>() {
                    @Override
                    public void accept(CompanyQuotationInfoBean companyQuotationInfoBean) throws Exception {
                        mView.getCompanyQuotationList(companyQuotationInfoBean);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void deleteCompanyQuotation(String id) {
        addDisposable(mDataManager.deleteCompanyQuotation(id)
                .compose(RxUtils.<BaseEntity>ioToMain())
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteCompanyQuotation(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }
}
