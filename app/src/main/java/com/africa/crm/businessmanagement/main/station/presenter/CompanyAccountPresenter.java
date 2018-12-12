package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyAccountContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import io.reactivex.functions.Consumer;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/12 0012 9:16
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyAccountPresenter extends RxPresenter<CompanyAccountContract.View> implements CompanyAccountContract.Presenter {
    @Override
    public void getCompanyAccounList(int page, int rows, String companyId, String userName, String name) {
        addDisposable(mDataManager.getCompanyAccounList(page, rows, companyId, userName, name)
                .compose(RxUtils.<CompanyInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyInfoBean>() {
                    @Override
                    public void accept(CompanyInfoBean companyInfoBean) throws Exception {
                        mView.getCompanyAccounList(companyInfoBean);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void deleteCompanyAccount(String id) {
        addDisposable(mDataManager.deleteCompanyAccount(id)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteCompanyAccount(baseEntity);
                    }
                }, new ComConsumer(mView)));

    }
}
