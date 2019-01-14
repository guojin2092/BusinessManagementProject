package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.CompanyPackagingDataInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPackagingDataContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_USERS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_PACKAGING_DATA_LIST;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/19 0019 9:15
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyPackagingDataPresenter extends RxPresenter<CompanyPackagingDataContract.View> implements CompanyPackagingDataContract.Presenter {


    @Override
    public void getAllCompanyUsers(String companyId) {
        addDisposable(mDataManager.getAllCompanyUser(companyId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain(mView))
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfoList) throws Exception {
                        mView.getAllCompanyUsers(dicInfoList);
                    }
                }, new ComConsumer(mView, REQUEST_ALL_USERS_LIST)));
    }

    @Override
    public void getCompanyPackagingDataList(int page, int rows, String companyId, String userId, String createTimes, String createTimee) {
        addDisposable(mDataManager.getCompanyPackagingDataList(page, rows, companyId, userId, createTimes, createTimee)
                .compose(RxUtils.<CompanyPackagingDataInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyPackagingDataInfoBean>() {
                    @Override
                    public void accept(CompanyPackagingDataInfoBean companyPayOrderInfoBean) throws Exception {
                        mView.getCompanyPackagingDataList(companyPayOrderInfoBean);
                    }
                }, new ComConsumer(mView, REQUEST_COMPANY_PACKAGING_DATA_LIST)));

    }

}
