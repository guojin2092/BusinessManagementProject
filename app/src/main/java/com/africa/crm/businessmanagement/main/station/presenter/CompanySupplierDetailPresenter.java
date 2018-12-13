package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.station.contract.CompanySupplierDetailContract;
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
 * Date：2018/12/10 0010 14:43
 * Modification  History:
 * Why & What is modified:
 */
public class CompanySupplierDetailPresenter extends RxPresenter<CompanySupplierDetailContract.View> implements CompanySupplierDetailContract.Presenter {

    @Override
    public void getSupplierType(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getSupplierType(dicInfoList);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getCompanySupplierDetail(String id) {
        addDisposable(mDataManager.getCompanySupplierDetail(id)
                .compose(RxUtils.<CompanySupplierInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanySupplierInfo>() {
                    @Override
                    public void accept(CompanySupplierInfo companySupplierInfo) throws Exception {
                        mView.getCompanySupplierDetail(companySupplierInfo);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void saveCompanySupplier(String id, String companyId, String head, String name, String type, String address, String phone, String email, String zipCode, String area, String remark) {
        addDisposable(mDataManager.saveCompanySupplier(id, companyId, head, name, type, address, phone, email, zipCode, area, remark)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveCompanySupplier(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }


}
