package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanySalesOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.station.contract.CompanySalesOrderContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_USERS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_SALE_ORDER;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SALE_ORDER_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SALE_ORDER_STATE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/19 0019 9:15
 * Modification  History:
 * Why & What is modified:
 */
public class CompanySalesOrderPresenter extends RxPresenter<CompanySalesOrderContract.View> implements CompanySalesOrderContract.Presenter {

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
    public void getStateType(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getStateType(dicInfoList);
                    }
                }, new ComConsumer(mView, REQUEST_SALE_ORDER_STATE)));
    }

    @Override
    public void getCompanySalesOrderList(int page, int rows, String companyId, String userId, String name, String state, String createTimes, String createTimee) {
        addDisposable(mDataManager.getCompanySalesOrderList(page, rows, companyId, userId, name, state, createTimes, createTimee)
                .compose(RxUtils.<CompanySalesOrderInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanySalesOrderInfoBean>() {
                    @Override
                    public void accept(CompanySalesOrderInfoBean companyQuotationInfoBean) throws Exception {
                        mView.getCompanySalesOrderList(companyQuotationInfoBean);
                    }
                }, new ComConsumer(mView, REQUEST_SALE_ORDER_LIST)));
    }

    @Override
    public void deleteCompanySalesOrder(String id) {
        addDisposable(mDataManager.deleteCompanySalesOrder(id)
                .compose(RxUtils.<BaseEntity>ioToMain())
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteCompanySalesOrder(baseEntity, false);
                    }
                }, new ComConsumer(mView, REQUEST_DELETE_SALE_ORDER)));
    }

}
