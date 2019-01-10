package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.CompanyServiceRecordInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyServiceRecordrDetailContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_CUSTOMER_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_PRODUCTS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_SERVICE_RECORD_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_SERVICE_RECORD;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SERVICE_LEVEL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SERVICE_STATE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SERVICE_TYPE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/12 0012 20:21
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyServiceRecordDetailPresenter extends RxPresenter<CompanyServiceRecordrDetailContract.View> implements CompanyServiceRecordrDetailContract.Presenter {

    @Override
    public void getState(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getState(dicInfoList);
                    }
                }, new ComConsumer(mView, REQUEST_SERVICE_STATE)));

    }

    @Override
    public void getType(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getType(dicInfoList);
                    }
                }, new ComConsumer(mView, REQUEST_SERVICE_TYPE)));

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
    public void getLevels(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getLevels(dicInfoList);
                    }
                }, new ComConsumer(mView, REQUEST_SERVICE_LEVEL)));
    }

    @Override
    public void getCompanyServiceRecordDetail(String id) {
        addDisposable(mDataManager.getCompanyServiceRecordDetail(id)
                .compose(RxUtils.<CompanyServiceRecordInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanyServiceRecordInfo>() {
                    @Override
                    public void accept(CompanyServiceRecordInfo companyServiceRecordInfo) throws Exception {
                        mView.getCompanyServiceRecordDetail(companyServiceRecordInfo);
                    }
                }, new ComConsumer(mView, REQUEST_COMPANY_SERVICE_RECORD_DETAIL)));
    }

    @Override
    public void saveCompanyServiceRecord(String id, String companyId, String userId, String name, String state, String type, String productId, String customerName, String level, String phone, String email, String reason, String remark, String solution, String track) {
        addDisposable(mDataManager.saveCompanyServiceRecord(id, companyId, userId, name, state, type, productId, customerName, level, phone, email, reason, remark, solution, track)
                .compose(RxUtils.<UploadInfoBean>ioToMain(mView))
                .subscribe(new Consumer<UploadInfoBean>() {
                    @Override
                    public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                        mView.saveCompanyServiceRecord(uploadInfoBean, false);
                    }
                }, new ComConsumer(mView, REQUEST_SAVE_COMPANY_SERVICE_RECORD)));
    }
}
