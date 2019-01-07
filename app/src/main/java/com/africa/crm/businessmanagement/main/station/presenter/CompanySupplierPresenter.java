package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.station.contract.CompanySupplierContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_SUPPLIER_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_DELETE_COMPANY_SUPPLIER;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SUPPLIER_TYPE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/12 0012 9:16
 * Modification  History:
 * Why & What is modified:
 */
public class CompanySupplierPresenter extends RxPresenter<CompanySupplierContract.View> implements CompanySupplierContract.Presenter {

    @Override
    public void getSupplierType(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getSupplierType(dicInfoList);
                    }
                }, new ComConsumer(mView, REQUEST_SUPPLIER_TYPE)));
    }

    @Override
    public void getCompanySupplierList(int page, int rows, String companyId, String name, String type) {
        addDisposable(mDataManager.getCompanySupplierList(page, rows, companyId, name, type)
                .compose(RxUtils.<CompanySupplierInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanySupplierInfoBean>() {
                    @Override
                    public void accept(CompanySupplierInfoBean companySupplierInfoBean) throws Exception {
                        mView.getCompanySupplierList(companySupplierInfoBean);
                    }
                }, new ComConsumer(mView, REQUEST_COMPANY_SUPPLIER_LIST)));

    }

    @Override
    public void deleteCompanySupplier(String id) {
        addDisposable(mDataManager.deleteCompanySupplier(id)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.deleteCompanySupplier(baseEntity, false);
                    }
                }, new ComConsumer(mView, REQUEST_DELETE_COMPANY_SUPPLIER)));

    }
}
