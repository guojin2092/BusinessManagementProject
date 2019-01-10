package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyServiceRecordInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.station.contract.CompanyServiceRecordOrderContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_USERS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_SERVICE_RECORD;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SERVICE_RECORD_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SERVICE_STATE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SERVICE_TYPE;

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
    public void getAllCompanyUsers(String companyId) {
        addDisposable(mDataManager.getAllCompanyUser(companyId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain(mView))
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfoList) throws Exception {
                        mView.getAllCompanyUsers(dicInfoList);
                    }
                }, new ComConsumer(mView, REQUEST_ALL_USERS_LIST)));
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
                }, new ComConsumer(mView, REQUEST_SERVICE_RECORD_LIST)));
    }

    @Override
    public void deleteServiceRecord(String id) {
        addDisposable(mDataManager.deleteServiceRecord(id)
                .compose(RxUtils.<BaseEntity>ioToMain())
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteServiceRecord(baseEntity, false);
                    }
                }, new ComConsumer(mView, REQUEST_DELETE_SERVICE_RECORD)));
    }

}
