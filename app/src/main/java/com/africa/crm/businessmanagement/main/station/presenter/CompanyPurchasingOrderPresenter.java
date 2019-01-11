package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPurchasingOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPurchasingOrderContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_USERS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_PURCHASING_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_COMPANY_PURCHASING;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/19 0019 9:15
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyPurchasingOrderPresenter extends RxPresenter<CompanyPurchasingOrderContract.View> implements CompanyPurchasingOrderContract.Presenter {

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
    public void getCompanyPurchasingOrderList(int page, int rows, String companyId, String userId, String name, String code, String createTimes, String createTimee) {
        addDisposable(mDataManager.getCompanyPurchasingOrderList(page, rows, companyId, userId, name, code, createTimes, createTimee)
                .compose(RxUtils.<CompanyPurchasingOrderInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyPurchasingOrderInfoBean>() {
                    @Override
                    public void accept(CompanyPurchasingOrderInfoBean companyPurchasingOrderInfoBean) throws Exception {
                        mView.getCompanyPurchasingOrderList(companyPurchasingOrderInfoBean);
                    }
                }, new ComConsumer(mView, REQUEST_COMPANY_PURCHASING_LIST)));

    }

    @Override
    public void deleteCompanyPurchasingOrder(String id) {
        addDisposable(mDataManager.deleteCompanyPurchasingOrder(id)
                .compose(RxUtils.<BaseEntity>ioToMain())
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteCompanyPurchasingOrder(baseEntity, false);
                    }
                }, new ComConsumer(mView, REQUEST_DELETE_COMPANY_PURCHASING)));

    }

}
