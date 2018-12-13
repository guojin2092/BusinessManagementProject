package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.station.contract.ContactManagementContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

public class ContactManagementPresenter extends RxPresenter<ContactManagementContract.View> implements ContactManagementContract.Presenter {
    @Override
    public void getFromType(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getFromType(dicInfoList);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void getCompanyContactList(int page, int rows, String companyId, String userId, String name, String fromType) {
        addDisposable(mDataManager.getCompanyContactList(page, rows, companyId, userId, name, fromType)
                .compose(RxUtils.<CompanyContactInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyContactInfoBean>() {
                    @Override
                    public void accept(CompanyContactInfoBean companyContactInfoBean) throws Exception {
                        mView.getCompanyContactList(companyContactInfoBean);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void deleteCompanyContact(String id) {
        addDisposable(mDataManager.deleteCompanyAccount(id)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteCompanyContact(baseEntity);
                    }
                }, new ComConsumer(mView)));

    }
}
