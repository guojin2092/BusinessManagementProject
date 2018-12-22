package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPurchasingOrderInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPurchasingDetailContract;
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
 * Date：2018/12/17 0017 20:21
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyPurchasingDetailPresenter extends RxPresenter<CompanyPurchasingDetailContract.View> implements CompanyPurchasingDetailContract.Presenter {
    @Override
    public void getAllSuppliers(String companyId) {
        addDisposable(mDataManager.getAllSuppliers(companyId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfoList) throws Exception {
                        mView.getAllSuppliers(dicInfoList);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getStateType(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getStateType(dicInfoList);
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
    public void getCompanyPurchasingDetail(String id) {
        addDisposable(mDataManager.getCompanyPurchasingDetail(id)
                .compose(RxUtils.<CompanyPurchasingOrderInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanyPurchasingOrderInfo>() {
                    @Override
                    public void accept(CompanyPurchasingOrderInfo companyPurchasingOrderInfo) throws Exception {
                        mView.getCompanyPurchasingDetail(companyPurchasingOrderInfo);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void saveCompanyPurchasing(String id, String companyId, String userId, String name, String supplierName, String state, String orderDate, String arriveDate, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark) {
        addDisposable(mDataManager.saveCompanyPurchasingOrder(id, companyId, userId, name, supplierName, state, orderDate, arriveDate, sendAddress, sendAddressZipCode, destinationAddress, destinationAddressZipCode, products, clause, remark)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveCompanyPurchasing(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }

}
