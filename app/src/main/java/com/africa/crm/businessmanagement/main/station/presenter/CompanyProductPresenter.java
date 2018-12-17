package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyProductInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.station.contract.CompanyProductManagementContract;
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
 * Date：2018/12/12 0012 9:16
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyProductPresenter extends RxPresenter<CompanyProductManagementContract.View> implements CompanyProductManagementContract.Presenter {

    @Override
    public void getProductType(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getProductType(dicInfoList);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getCompanyProductList(int page, int rows, String companyId, String name, String type) {
        addDisposable(mDataManager.getCompanyProductList(page, rows, companyId, name, type)
                .compose(RxUtils.<CompanyProductInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyProductInfoBean>() {
                    @Override
                    public void accept(CompanyProductInfoBean companyProductInfoBean) throws Exception {
                        mView.getCompanyProductList(companyProductInfoBean);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void deleteCompanyProduct(String id) {
        addDisposable(mDataManager.deleteCompanyProduct(id)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteCompanyProduct(baseEntity);
                    }
                }, new ComConsumer(mView)));

    }
}
