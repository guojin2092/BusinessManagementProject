package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyQuotationInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyQuotationDetailContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_CONTACT_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_CUSTOMER_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_PRODUCTS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_QUOTATION_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_QUOTATION;

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
    public void getAllContact(String companyId) {
        addDisposable(mDataManager.getAllContact(companyId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfo2List) throws Exception {
                        mView.getAllContact(dicInfo2List);
                    }
                }, new ComConsumer(mView, REQUEST_ALL_CONTACT_LIST)));
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
    public void getAllProduct(String companyId) {
        addDisposable(mDataManager.getAllProducts(companyId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfo2List) throws Exception {
                        mView.getAllProduct(dicInfo2List);
                    }
                }, new ComConsumer(mView, REQUEST_ALL_PRODUCTS_LIST)));
    }

    @Override
    public void getCompanyQuotationDetail(String id) {
        addDisposable(mDataManager.getCompanyQuotationDetail(id)
                .compose(RxUtils.<CompanyQuotationInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanyQuotationInfo>() {
                    @Override
                    public void accept(CompanyQuotationInfo companyQuotationInfo) throws Exception {
                        mView.getCompanyQuotationDetail(companyQuotationInfo);
                    }
                }, new ComConsumer(mView, REQUEST_COMPANY_QUOTATION_DETAIL)));

    }

    @Override
    public void saveCompanyQuotation(String id, String companyId, String userId, String name, String customerName, String contactName, String termOfValidity, String price, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark) {
        addDisposable(mDataManager.saveCompanyQuotation(id, companyId, userId, name, customerName, contactName, termOfValidity, price, sendAddress, sendAddressZipCode, destinationAddress, destinationAddressZipCode, products, clause, remark)
                .compose(RxUtils.<UploadInfoBean>ioToMain())
                .subscribe(new Consumer<UploadInfoBean>() {
                    @Override
                    public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                        mView.saveCompanyQuotation(uploadInfoBean, false);
                    }
                }, new ComConsumer(mView, REQUEST_SAVE_COMPANY_QUOTATION)));
    }
}
