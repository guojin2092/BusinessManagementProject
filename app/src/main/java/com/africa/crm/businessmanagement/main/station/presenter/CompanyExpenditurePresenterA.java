package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyeExpenditureInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyExpenditureManagementContractA;
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
public class CompanyExpenditurePresenterA extends RxPresenter<CompanyExpenditureManagementContractA.View> implements CompanyExpenditureManagementContractA.Presenter {

    @Override
    public void getExpenditureList(int page, int rows, String companyId, String title, String createTimes, String createTimee) {
        addDisposable(mDataManager.getExpenditureList(page, rows, companyId, title, createTimes, createTimee)
                .compose(RxUtils.<CompanyeExpenditureInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyeExpenditureInfoBean>() {
                    @Override
                    public void accept(CompanyeExpenditureInfoBean companyeExpenditureInfoBean) throws Exception {
                        mView.getExpenditureList(companyeExpenditureInfoBean);
                    }
                }, new ComConsumer(mView)));
    }

}
