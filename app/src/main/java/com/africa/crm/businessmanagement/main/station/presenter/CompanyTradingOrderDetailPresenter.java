package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyTradingOrderInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.station.contract.CompanyTradingOrderDetailContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/12 0012 20:21
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyTradingOrderDetailPresenter extends RxPresenter<CompanyTradingOrderDetailContract.View> implements CompanyTradingOrderDetailContract.Presenter {

    @Override
    public void getAllContact(String companyId) {
        addDisposable(mDataManager.getAllContact(companyId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfo2List) throws Exception {
                        mView.getAllContact(dicInfo2List);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getAllCustomers(String companyId) {
        addDisposable(mDataManager.getAllCustomers(companyId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfo2List) throws Exception {
                        mView.getAllCustomers(dicInfo2List);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getCompanyTradingOrderDetail(String id) {
        addDisposable(mDataManager.getCompanyTradingOrderDetail(id)
                .compose(RxUtils.<CompanyTradingOrderInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanyTradingOrderInfo>() {
                    @Override
                    public void accept(CompanyTradingOrderInfo companyTradingOrderInfo) throws Exception {
                        mView.getCompanyTradingOrderDetail(companyTradingOrderInfo);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void saveCompanyTradingOrder(String id, String companyId, String userId, String name, String customerName, String price, String estimateProfit, String contactName, String possibility, String clueSource, String remark) {
        addDisposable(mDataManager.saveCompanyTradingOrder(id, companyId, userId, name, customerName, price, estimateProfit, contactName, possibility, clueSource, remark)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveCompanyTradingOrder(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }
}
