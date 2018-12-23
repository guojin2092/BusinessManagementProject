package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyTaskInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.station.contract.CompanyTaskDetailContract;
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
                }, new ComConsumer(mView)));
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
                }, new ComConsumer(mView)));
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
                }, new ComConsumer(mView)));

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
                }, new ComConsumer(mView)));
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
                }, new ComConsumer(mView)));
    }

    @Override
    public void saveCompanyTask(String id, String companyId, String userId, String name, String remindTime, String customerName, String contactName, String level, String state, String remark) {
        addDisposable(mDataManager.saveCompanyTask(id, companyId, userId, name, remindTime, customerName, contactName, level, state, remark)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveCompanyTask(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }

}
