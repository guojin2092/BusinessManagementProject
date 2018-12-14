package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.station.contract.CompanyContactDetailContract;
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
 * Date：2018/12/14 0014 9:48
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyContactPresenter extends RxPresenter<CompanyContactDetailContract.View> implements CompanyContactDetailContract.Presenter {

    @Override
    public void getAllCompanyUsers(String companyId) {
        addDisposable(mDataManager.getAllCompanyUser(companyId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain(mView))
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfoList) throws Exception {
                        mView.getAllCompanyUsers(dicInfoList);
                    }
                }, new ComConsumer(mView)));

    }

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
    public void getContactDetail(String id) {
        addDisposable(mDataManager.getContactDetail(id)
                .compose(RxUtils.<CompanyContactInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanyContactInfo>() {
                    @Override
                    public void accept(CompanyContactInfo companyContactInfo) throws Exception {
                        mView.getContactDetail(companyContactInfo);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void saveCompanyContact(String id, String companyId, String userId, String head, String name, String fromType, String address, String mailAddress, String phone, String tel, String email, String job, String remark) {
        addDisposable(mDataManager.saveCompanyContact(id, companyId, userId, head, name, fromType, address, mailAddress, phone, tel, email, job, remark)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveCompanyContact(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }

}
