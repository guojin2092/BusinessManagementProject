package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPayOrderInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPayOrderDetailContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_CUSTOMER_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_SALES_ORDER;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_TRADING_ORDER;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_PAY_ORDER_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_PAY_ORDER;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_UPLOAD_IMAGE;

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
    public void uploadImages(String filePath) {
        addDisposable(mDataManager.uploadFiles(filePath)
                .compose(RxUtils.<FileInfoBean>ioToMain(mView))
                .subscribe(new Consumer<FileInfoBean>() {
                    @Override
                    public void accept(FileInfoBean fileInfoBean) throws Exception {
                        mView.uploadImages(fileInfoBean);
                    }
                }, new ComConsumer(mView, REQUEST_UPLOAD_IMAGE)));
    }

    @Override
    public void getAllSaleOrders(String companyId, String userId) {
        addDisposable(mDataManager.getAllSaleOrders(companyId, userId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfo2List) throws Exception {
                        mView.getAllSaleOrders(dicInfo2List);
                    }
                }, new ComConsumer(mView, REQUEST_ALL_SALES_ORDER)));

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
                }, new ComConsumer(mView, REQUEST_ALL_TRADING_ORDER)));
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
                }, new ComConsumer(mView, REQUEST_ALL_CUSTOMER_LIST)));

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
                }, new ComConsumer(mView, REQUEST_COMPANY_PAY_ORDER_DETAIL)));

    }

    @Override
    public void saveCompanyPayOrder(String id, String companyId, String userId, String name, String salesOrderId, String tradingOrderId, String customerName, String price, String payTime, String hasInvoice, String hasPrint, String invoiceFiles, String remark) {
        addDisposable(mDataManager.saveCompanyPayOrder(id, companyId, userId, name, salesOrderId, tradingOrderId, customerName, price, payTime, hasInvoice, hasPrint, invoiceFiles, remark)
                .compose(RxUtils.<UploadInfoBean>ioToMain(mView))
                .subscribe(new Consumer<UploadInfoBean>() {
                    @Override
                    public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                        mView.saveCompanyPayOrder(uploadInfoBean, false);
                    }
                }, new ComConsumer(mView, REQUEST_SAVE_COMPANY_PAY_ORDER)));
    }

}
