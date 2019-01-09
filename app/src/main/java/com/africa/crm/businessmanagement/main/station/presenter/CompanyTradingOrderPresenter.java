package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyTradingOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.station.contract.CompanyTradingOrderContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_USERS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_TRADING_ORDER_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_COMPANY_TRADING_ORDER;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/19 0019 9:15
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyTradingOrderPresenter extends RxPresenter<CompanyTradingOrderContract.View> implements CompanyTradingOrderContract.Presenter {

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
    public void getCompanyTradingOrderList(int page, int rows, String companyId, String userId, String name, String createTimes, String createTimee) {
        addDisposable(mDataManager.getCompanyTradingOrderList(page, rows, companyId, userId, name, createTimes, createTimee)
                .compose(RxUtils.<CompanyTradingOrderInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyTradingOrderInfoBean>() {
                    @Override
                    public void accept(CompanyTradingOrderInfoBean companyTradingOrderInfoBean) throws Exception {
                        mView.getCompanyTradingOrderList(companyTradingOrderInfoBean);
                    }
                }, new ComConsumer(mView, REQUEST_COMPANY_TRADING_ORDER_LIST)));

    }

    @Override
    public void deleteCompanyTradingOrder(String id) {
        addDisposable(mDataManager.deleteCompanyTradingOrder(id)
                .compose(RxUtils.<BaseEntity>ioToMain())
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteCompanyTradingOrder(baseEntity, false);
                    }
                }, new ComConsumer(mView, REQUEST_DELETE_COMPANY_TRADING_ORDER)));

    }

}
