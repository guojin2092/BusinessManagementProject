package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyQuotationInfo;
import com.africa.crm.businessmanagement.main.station.contract.CompanyQuotationDetailContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

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
public class CompanyQuotationDetailPresenter extends RxPresenter<CompanyQuotationDetailContract.View> implements CompanyQuotationDetailContract.Presenter {

    @Override
    public void getCompanyQuotationDetail(String id) {
        addDisposable(mDataManager.getCompanyQuotationDetail(id)
                .compose(RxUtils.<CompanyQuotationInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanyQuotationInfo>() {
                    @Override
                    public void accept(CompanyQuotationInfo companyQuotationInfo) throws Exception {
                        mView.getCompanyQuotationDetail(companyQuotationInfo);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void saveCompanyQuotation(String id, String companyId, String userId, String name, String customerName, String contactName, String termOfValidity, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark) {
        addDisposable(mDataManager.saveCompanyQuotation(id, companyId, userId, name, customerName, contactName, termOfValidity, sendAddress, sendAddressZipCode, destinationAddress, destinationAddressZipCode, products, clause, remark)
                .compose(RxUtils.<BaseEntity>ioToMain())
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveCompanyQuotation(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }
}
