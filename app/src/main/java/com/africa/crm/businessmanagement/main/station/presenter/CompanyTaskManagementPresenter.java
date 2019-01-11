package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyTaskInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.station.contract.CompanyTaskManagementContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_TASK_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_COMPANY_TASK;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_TASK_LEVEL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_TASK_STATE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/19 0019 9:15
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyTaskManagementPresenter extends RxPresenter<CompanyTaskManagementContract.View> implements CompanyTaskManagementContract.Presenter {

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
    public void getCompanyTaskList(int page, int rows, String companyId, String userId, String name, String customerName, String state, String level, String remindTimes, String remindTimee) {
        addDisposable(mDataManager.getCompanyTaskList(page, rows, companyId, userId, name, customerName, state, level, remindTimes, remindTimee)
                .compose(RxUtils.<CompanyTaskInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyTaskInfoBean>() {
                    @Override
                    public void accept(CompanyTaskInfoBean companyTaskInfoBean) throws Exception {
                        mView.getCompanyTaskList(companyTaskInfoBean);
                    }
                }, new ComConsumer(mView, REQUEST_COMPANY_TASK_LIST)));

    }

    @Override
    public void deleteCompanyTask(String id) {
        addDisposable(mDataManager.deleteServiceRecord(id)
                .compose(RxUtils.<BaseEntity>ioToMain())
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteCompanyTask(baseEntity, false);
                    }
                }, new ComConsumer(mView, REQUEST_DELETE_COMPANY_TASK)));
    }

}
