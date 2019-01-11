package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.CompanyTaskInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyTaskDetailContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_CONTACT_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_CUSTOMER_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_TASK_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_TASK;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_TASK_LEVEL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_TASK_STATE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/12 0012 20:21
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyTaskDetailPresenter extends RxPresenter<CompanyTaskDetailContract.View> implements CompanyTaskDetailContract.Presenter {
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
    public void getLevels(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getLevels(dicInfoList);
                    }
                }, new ComConsumer(mView, REQUEST_TASK_LEVEL)));

    }

    @Override
    public void getStates(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getStates(dicInfoList);
                    }
                }, new ComConsumer(mView, REQUEST_TASK_STATE)));
    }

    @Override
    public void getCompanyTaskDetail(String id) {
        addDisposable(mDataManager.getCompanyTaskDetail(id)
                .compose(RxUtils.<CompanyTaskInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanyTaskInfo>() {
                    @Override
                    public void accept(CompanyTaskInfo companyTaskInfo) throws Exception {
                        mView.getCompanyTaskDetail(companyTaskInfo);
                    }
                }, new ComConsumer(mView, REQUEST_COMPANY_TASK_DETAIL)));
    }

    @Override
    public void saveCompanyTask(String id, String companyId, String userId, String name, String remindTime, String customerName, String contactName, String level, String state, String remark) {
        addDisposable(mDataManager.saveCompanyTask(id, companyId, userId, name, remindTime, customerName, contactName, level, state, remark)
                .compose(RxUtils.<UploadInfoBean>ioToMain(mView))
                .subscribe(new Consumer<UploadInfoBean>() {
                    @Override
                    public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                        mView.saveCompanyTask(uploadInfoBean, false);
                    }
                }, new ComConsumer(mView, REQUEST_SAVE_COMPANY_TASK)));
    }

}
