package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyClientInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.station.contract.CompanyClientContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_CLIENT_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_COMPANY_CLIENT;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_INDUSTRY_TYPE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/12 0012 9:16
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyClientPresenter extends RxPresenter<CompanyClientContract.View> implements CompanyClientContract.Presenter {

    @Override
    public void getIndustryType(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getIndustryType(dicInfoList);
                    }
                }, new ComConsumer(mView, REQUEST_INDUSTRY_TYPE)));

    }

    @Override
    public void getCompanyClientList(int page, int rows, String companyId, String userId, String name, String industry) {
        addDisposable(mDataManager.getCompanyClientList(page, rows, companyId, userId, name, industry)
                .compose(RxUtils.<CompanyClientInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyClientInfoBean>() {
                    @Override
                    public void accept(CompanyClientInfoBean companyClientInfoBean) throws Exception {
                        mView.getCompanyClientList(companyClientInfoBean);
                    }
                }, new ComConsumer(mView, REQUEST_COMPANY_CLIENT_LIST)));
    }

    @Override
    public void deleteCompanyClient(String id) {
        addDisposable(mDataManager.deleteCompanyClient(id)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteCompanyClient(baseEntity, false);
                    }
                }, new ComConsumer(mView, REQUEST_DELETE_COMPANY_CLIENT)));
    }

}
