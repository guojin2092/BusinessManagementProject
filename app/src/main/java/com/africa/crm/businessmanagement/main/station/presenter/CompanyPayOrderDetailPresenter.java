package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPayOrderInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPayOrderDetailContract;
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
public class CompanyPayOrderDetailPresenter extends RxPresenter<CompanyPayOrderDetailContract.View> implements CompanyPayOrderDetailContract.Presenter {

    @Override
    public void getAllSaleOrders(String companyId, String userId) {
        addDisposable(mDataManager.getAllSaleOrders(companyId, userId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfo2List) throws Exception {
                        mView.getAllSaleOrders(dicInfo2List);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void getAllTradingOrders(String companyId, String userId) {
        addDisposable(mDataManager.getAllTradingOrders(companyId, userId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfo2List) throws Exception {
                        mView.getAllTradingOrders(dicInfo2List);
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
    public void getCompanyPayOrderDetail(String id) {
        addDisposable(mDataManager.getCompanyPayOrderDetail(id)
                .compose(RxUtils.<CompanyPayOrderInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanyPayOrderInfo>() {
                    @Override
                    public void accept(CompanyPayOrderInfo companyPayOrderInfo) throws Exception {
                        mView.getCompanyPayOrderDetail(companyPayOrderInfo);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void saveCompanyPayOrder(String id, String companyId, String userId, String name, String salesOrderId, String tradingOrderId, String customerName, String price, String payTime, String hasInvoice, String hasPrint, String invoiceFiles, String remark) {
        addDisposable(mDataManager.saveCompanyPayOrder(id, companyId, userId, name, salesOrderId, tradingOrderId, customerName, price, payTime, hasInvoice, hasPrint, invoiceFiles, remark)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveCompanyPayOrder(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }

}
