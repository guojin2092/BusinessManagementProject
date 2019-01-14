package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.CompanyExpenditureInfoB;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyExpenditureDetailContractB;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_EXPENDITURE_B_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_EXPENDITURE_B;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/12 0012 9:16
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyExpenditureDetailPresenterB extends RxPresenter<CompanyExpenditureDetailContractB.View> implements CompanyExpenditureDetailContractB.Presenter {

    @Override
    public void getExpenditureDetailB(String id) {
        addDisposable(mDataManager.getExpenditureDetailB(id)
                .compose(RxUtils.<CompanyExpenditureInfoB>ioToMain(mView))
                .subscribe(new Consumer<CompanyExpenditureInfoB>() {
                    @Override
                    public void accept(CompanyExpenditureInfoB companyExpenditureInfoB) throws Exception {
                        mView.getExpenditureDetailB(companyExpenditureInfoB);
                    }
                }, new ComConsumer(mView, REQUEST_COMPANY_EXPENDITURE_B_DETAIL)));
    }

    @Override
    public void saveExpenditureB(String companyId, String userId, String payDate, String price, String remark) {
        addDisposable(mDataManager.saveExpenditureB(companyId, userId, payDate, price, remark)
                .compose(RxUtils.<UploadInfoBean>ioToMain(mView))
                .subscribe(new Consumer<UploadInfoBean>() {
                    @Override
                    public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                        mView.saveExpenditureB(uploadInfoBean, false);
                    }
                }, new ComConsumer(mView, REQUEST_SAVE_COMPANY_EXPENDITURE_B)));
    }
}
