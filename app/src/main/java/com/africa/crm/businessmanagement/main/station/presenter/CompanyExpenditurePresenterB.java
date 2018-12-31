package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.CompanyeExpenditureInfoBeanB;
import com.africa.crm.businessmanagement.main.station.contract.CompanyExpenditureManagementContractB;
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
public class CompanyExpenditurePresenterB extends RxPresenter<CompanyExpenditureManagementContractB.View> implements CompanyExpenditureManagementContractB.Presenter {
    @Override
    public void getExpenditureListB(int page, int rows, String companyId, String userId, String payDates, String payDatee) {
        addDisposable(mDataManager.getExpenditureListB(page, rows, companyId, userId, payDates, payDatee)
                .compose(RxUtils.<CompanyeExpenditureInfoBeanB>ioToMain(mView))
                .subscribe(new Consumer<CompanyeExpenditureInfoBeanB>() {
                    @Override
                    public void accept(CompanyeExpenditureInfoBeanB companyeExpenditureInfoBeanB) throws Exception {
                        mView.getExpenditureListB(companyeExpenditureInfoBeanB);
                    }
                }, new ComConsumer(mView)));
    }
}
