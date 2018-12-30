package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPackagingDataInfo;
import com.africa.crm.businessmanagement.main.bean.PreviewInfo;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPackagingDataDetailContract;
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
 * Date：2018/12/17 0017 20:21
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyPackagingDataDetailPresenter extends RxPresenter<CompanyPackagingDataDetailContract.View> implements CompanyPackagingDataDetailContract.Presenter {

    @Override
    public void getPreviewInfo(final String companyId, final String startDate, final String endDate) {
        addDisposable(mDataManager.checkDate(companyId, startDate, endDate)
                .flatMap(new Function<BaseEntity, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(BaseEntity baseEntity) throws Exception {
                        if (baseEntity.isSuccess()) {
                            return mDataManager.getPreviewInfo(companyId, startDate, endDate);
                        } else {
                            return Observable.error(new ComException(baseEntity.getReturnMsg()));
                        }
                    }
                })
                .compose(RxUtils.<String>ioToMain(mView))
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String previewInfo) throws Exception {
                        mView.getPreviewInfo(previewInfo);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getPackagingDataDetail(String id) {
        addDisposable(mDataManager.getPackagingDataDetail(id)
                .compose(RxUtils.<CompanyPackagingDataInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanyPackagingDataInfo>() {
                    @Override
                    public void accept(CompanyPackagingDataInfo companyPackagingDataInfo) throws Exception {
                        mView.getPackagingDataDetail(companyPackagingDataInfo);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void savePackagingData(final String companyId, final String userId, final String startDate, final String endDate, final String num, final String previewInfo, final String remark) {
        addDisposable(mDataManager.checkDate(companyId, startDate, endDate)
                .flatMap(new Function<BaseEntity, ObservableSource<BaseEntity>>() {
                    @Override
                    public ObservableSource<BaseEntity> apply(BaseEntity baseEntity) throws Exception {
                        if (baseEntity.isSuccess()) {
                            return mDataManager.savePackagingData(companyId, userId, startDate, endDate, num, previewInfo, remark);
                        } else {
                            return Observable.error(new ComException(baseEntity.getReturnMsg()));
                        }
                    }
                })
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.savePackagingData(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }

}
