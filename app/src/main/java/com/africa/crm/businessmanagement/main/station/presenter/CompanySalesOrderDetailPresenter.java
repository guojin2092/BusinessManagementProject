package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanySalesOrderInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.station.contract.CompanySalesOrderDetailContract;
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
public class CompanySalesOrderDetailPresenter extends RxPresenter<CompanySalesOrderDetailContract.View> implements CompanySalesOrderDetailContract.Presenter {

    @Override
    public void getOrderState(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getOrderState(dicInfoList);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void getCompanySalesOrderDetail(String id) {
        addDisposable(mDataManager.getCompanySalesOrderDetail(id)
                .compose(RxUtils.<CompanySalesOrderInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanySalesOrderInfo>() {
                    @Override
                    public void accept(CompanySalesOrderInfo companySalesOrderInfo) throws Exception {
                        mView.getCompanySalesOrderDetail(companySalesOrderInfo);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void saveCompanySalesOrder(String id, String companyId, String userId, String name, String customerName, String contactName, String saleCommission, String state, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark) {
        addDisposable(mDataManager.saveCompanySalesOrder(id, companyId, userId, name, customerName, contactName, saleCommission, state, sendAddress, sendAddressZipCode, destinationAddress, destinationAddressZipCode, products, clause, remark)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveCompanySalesOrder(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }
}
