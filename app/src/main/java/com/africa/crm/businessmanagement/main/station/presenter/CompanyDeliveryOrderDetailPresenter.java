package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyDeliveryOrderInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.station.contract.CompanyDeliveryOrderDetailContract;
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
public class CompanyDeliveryOrderDetailPresenter extends RxPresenter<CompanyDeliveryOrderDetailContract.View> implements CompanyDeliveryOrderDetailContract.Presenter {

    @Override
    public void getState(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getState(dicInfoList);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void getAllProduct(String companyId) {
        addDisposable(mDataManager.getAllProducts(companyId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfo2List) throws Exception {
                        mView.getAllProduct(dicInfo2List);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getAllDeliveryOrders(String companyId, String userId) {
        addDisposable(mDataManager.getAllDeliveryOrders(companyId, userId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfo2List) throws Exception {
                        mView.getAllDeliveryOrders(dicInfo2List);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void getCompanyDeliveryOrderDetail(String id) {
        addDisposable(mDataManager.getCompanyDeliveryOrderDetail(id)
                .compose(RxUtils.<CompanyDeliveryOrderInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanyDeliveryOrderInfo>() {
                    @Override
                    public void accept(CompanyDeliveryOrderInfo companyDeliveryOrderInfo) throws Exception {
                        mView.getCompanyDeliveryOrderDetail(companyDeliveryOrderInfo);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void saveCompanyDeliveryOrder(String id, String companyId, String userId, String name, String salesOrderId, String logisticsCode, String state, String arriveDate, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark) {
        addDisposable(mDataManager.saveCompanyDeliveryOrder(id, companyId, userId, name, salesOrderId, logisticsCode, state, arriveDate, sendAddress, sendAddressZipCode, destinationAddress, destinationAddressZipCode, products, clause, remark)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveCompanyDeliveryOrder(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }

}
