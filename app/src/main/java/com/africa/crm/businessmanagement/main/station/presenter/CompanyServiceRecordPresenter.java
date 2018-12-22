package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyServiceRecordInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyServiceRecordOrderContract;
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
 * Date：2018/12/19 0019 9:15
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyServiceRecordPresenter extends RxPresenter<CompanyServiceRecordOrderContract.View> implements CompanyServiceRecordOrderContract.Presenter {
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
    public void getType(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getType(dicInfoList);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getCompanyUserList(int page, int rows, String userName, String type, String companyId, String state, String name) {
        addDisposable(mDataManager.getUserList(page, rows, userName, type, companyId, state, name)
                .compose(RxUtils.<UserManagementInfoBean>ioToMain(mView))
                .subscribe(new Consumer<UserManagementInfoBean>() {
                    @Override
                    public void accept(UserManagementInfoBean userManagementInfoBean) throws Exception {
                        mView.getCompanyUserList(userManagementInfoBean);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getServiceRecordList(int page, int rows, String companyId, String userId, String name, String state, String type, String createTimes, String createTimee) {
        addDisposable(mDataManager.getServiceRecordList(page, rows, companyId, userId, name, state, type, createTimes, createTimee)
                .compose(RxUtils.<CompanyServiceRecordInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyServiceRecordInfoBean>() {
                    @Override
                    public void accept(CompanyServiceRecordInfoBean companyServiceRecordInfoBean) throws Exception {
                        mView.getServiceRecordList(companyServiceRecordInfoBean);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void deleteServiceRecord(String id) {
        addDisposable(mDataManager.deleteServiceRecord(id)
                .compose(RxUtils.<BaseEntity>ioToMain())
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteServiceRecord(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }

}
