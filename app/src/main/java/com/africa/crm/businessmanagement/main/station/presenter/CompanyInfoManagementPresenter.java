package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyInfoManagementContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_INFO_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_COMPANY_INFO;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/10 0010 12:46
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyInfoManagementPresenter extends RxPresenter<CompanyInfoManagementContract.View> implements CompanyInfoManagementContract.Presenter {
    @Override
    public void getCompanyInfoList(int page, int rows, String name) {
        addDisposable(mDataManager.getCompanyInfoList(page, rows, name)
                .compose(RxUtils.<CompanyInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyInfoBean>() {
                    @Override
                    public void accept(CompanyInfoBean companyInfoBean) throws Exception {
                        mView.getCompanyInfoList(companyInfoBean);
                    }
                }, new ComConsumer(mView, REQUEST_COMPANY_INFO_LIST)));

    }

    @Override
    public void deleteCompanyInfo(String id) {
        addDisposable(mDataManager.deleteCompanyInfo(id)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteCompanyInfo(baseEntity, false);
                    }
                }, new ComConsumer(mView, REQUEST_DELETE_COMPANY_INFO)));
    }
}
