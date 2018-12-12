package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyAccountDetailContract;
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
public class CompanyAccountDetailPresenter extends RxPresenter<CompanyAccountDetailContract.View> implements CompanyAccountDetailContract.Presenter {

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
    public void getAllRoles(String name) {
        addDisposable(mDataManager.getAllRoles(name)
                .compose(RxUtils.<List<RoleInfoBean>>ioToMain(mView))
                .subscribe(new Consumer<List<RoleInfoBean>>() {
                    @Override
                    public void accept(List<RoleInfoBean> roleInfoBeanList) throws Exception {
                        mView.getAllRoles(roleInfoBeanList);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void getCompanyAccountDetail(String id) {
        addDisposable(mDataManager.getCompanyAccountDetail(id)
                .compose(RxUtils.<CompanyAccountInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanyAccountInfo>() {
                    @Override
                    public void accept(CompanyAccountInfo companyAccountInfo) throws Exception {
                        mView.getCompanyAccountDetail(companyAccountInfo);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void saveCompanyAccount(String id, String userName, String type, String roleId, String passWord, String name, String phone, String address, String email, String state, String companyId, String head) {
        addDisposable(mDataManager.saveCompanyAccount(id, userName, type, roleId, passWord, name, phone, address, email, state, companyId, head)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveCompanyAccount(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }
}
