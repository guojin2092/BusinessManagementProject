package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyAccountContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_ACCOUNT_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_COMPANY_ACCOUNT;

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
                .compose(RxUtils.<CompanyAccountInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyAccountInfoBean>() {
                    @Override
                    public void accept(CompanyAccountInfoBean companyInfoBean) throws Exception {
                        mView.getCompanyAccounList(companyInfoBean);
                    }
                }, new ComConsumer(mView, REQUEST_COMPANY_ACCOUNT_LIST)));

    }

    @Override
    public void deleteCompanyAccount(String id) {
        addDisposable(mDataManager.deleteCompanyAccount(id)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteCompanyAccount(baseEntity, false);
                    }
                }, new ComConsumer(mView, REQUEST_DELETE_COMPANY_ACCOUNT)));

    }
}
