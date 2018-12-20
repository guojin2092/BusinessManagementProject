package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyProductInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.station.contract.CompanyProductDetailContract;
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
public class CompanyProductDetailPresenter extends RxPresenter<CompanyProductDetailContract.View> implements CompanyProductDetailContract.Presenter {
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
    public void getProductType(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getProductType(dicInfoList);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getCompanyProductDetail(String id) {
        addDisposable(mDataManager.getCompanyProductDetail(id)
                .compose(RxUtils.<CompanyProductInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanyProductInfo>() {
                    @Override
                    public void accept(CompanyProductInfo companyProductInfo) throws Exception {
                        mView.getCompanyProductDetail(companyProductInfo);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void saveCompanyProduct(String id, String companyId, String name, String code, String supplierName, String makerName, String type, String unitPrice, String unit, String stockNum, String warnNum, String remark) {
        addDisposable(mDataManager.saveCompanyProduct(id, companyId, name, code, supplierName, makerName, type, unitPrice, unit, stockNum, warnNum, remark)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveCompanyProduct(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }
}
