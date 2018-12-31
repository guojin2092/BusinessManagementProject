package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyExpenditureInfoB;
import com.africa.crm.businessmanagement.main.station.contract.CompanyExpenditureDetailContractB;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.error.ComException;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

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
                }, new ComConsumer(mView)));
    }

    @Override
    public void saveExpenditureB(String companyId, String userId, String payDate, String price, String remark) {
        addDisposable(mDataManager.saveExpenditureB(companyId, userId, payDate, price, remark)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveExpenditureB(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }
}
