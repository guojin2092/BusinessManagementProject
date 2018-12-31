package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyExpenditureInfo;
import com.africa.crm.businessmanagement.main.bean.PayRecordInfo;
import com.africa.crm.businessmanagement.main.station.contract.CompanyExpenditureDetailContractA;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.error.ComException;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

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
public class CompanyExpenditureDetailPresenterA extends RxPresenter<CompanyExpenditureDetailContractA.View> implements CompanyExpenditureDetailContractA.Presenter {

    @Override
    public void getExpenditureDetail(String id) {
        addDisposable(mDataManager.getExpenditureDetail(id)
                .compose(RxUtils.<CompanyExpenditureInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanyExpenditureInfo>() {
                    @Override
                    public void accept(CompanyExpenditureInfo companyExpenditureInfo) throws Exception {
                        mView.getExpenditureDetail(companyExpenditureInfo);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getPayRecord(String estimateId) {
        addDisposable(mDataManager.getPayRecord(estimateId)
                .compose(RxUtils.<List<PayRecordInfo>>ioToMain(mView))
                .subscribe(new Consumer<List<PayRecordInfo>>() {
                    @Override
                    public void accept(List<PayRecordInfo> payRecordInfoList) throws Exception {
                        mView.getPayRecord(payRecordInfoList);
                    }
                }, new ComConsumer(mView)));
    }


    @Override
    public void saveExpenditureA(final String companyId, final String userId, final String title, final String startDate, final String endDate, final String estimatePrice, final String remark) {
        addDisposable(mDataManager.checkYsDate(companyId, startDate, endDate)
                .flatMap(new Function<BaseEntity, ObservableSource<BaseEntity>>() {
                    @Override
                    public ObservableSource<BaseEntity> apply(BaseEntity baseEntity) throws Exception {
                        if (baseEntity.isSuccess()) {
                            return mDataManager.saveExpenditureA(companyId, userId, title, startDate, endDate, estimatePrice, remark);
                        } else {
                            return Observable.error(new ComException(baseEntity.getReturnMsg()));
                        }
                    }
                })
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveExpenditureA(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }

}
