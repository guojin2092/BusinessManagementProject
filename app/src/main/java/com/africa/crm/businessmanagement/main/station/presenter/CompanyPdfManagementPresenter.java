package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyInfoBean;
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
    public void getCompanyPdfList(int page, int rows, String companyId, String userId, String name) {
/*
        addDisposable(mDataManager.getCompanyAccounList(page, rows, companyId, userName, name)
                .compose(RxUtils.<CompanyInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyInfoBean>() {
                    @Override
                    public void accept(CompanyInfoBean companyInfoBean) throws Exception {
                        mView.getCompanyAccounList(companyInfoBean);
                    }
                }, new ComConsumer(mView)));
*/

    }

    @Override
    public void deleteCompanyPdf(String id) {
/*
        addDisposable(mDataManager.deleteCompanyAccount(id)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteCompanyAccount(baseEntity);
                    }
                }, new ComConsumer(mView)));
*/

    }
}
