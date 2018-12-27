package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPdfInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPdfReportManagementContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import io.reactivex.functions.Consumer;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/12 0012 9:16
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyPdfManagementPresenter extends RxPresenter<CompanyPdfReportManagementContract.View> implements CompanyPdfReportManagementContract.Presenter {

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
    public void getCompanyPdfList(int page, int rows, String companyId, String userId, String name) {
        addDisposable(mDataManager.getCompanyPdfList(page, rows, companyId, userId, name)
                .compose(RxUtils.<CompanyPdfInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyPdfInfoBean>() {
                    @Override
                    public void accept(CompanyPdfInfoBean companyPdfInfoBean) throws Exception {
                        mView.getCompanyPdfList(companyPdfInfoBean);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void deleteCompanyPdf(String id) {
        addDisposable(mDataManager.deleteCompanyPdf(id)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteCompanyPdf(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }
}
