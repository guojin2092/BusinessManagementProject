package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.CompanyInventoryInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.station.contract.CompanyInventoryContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_PRODUCTS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_INVENTORY_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_STOCK_TYPE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/12 0012 9:16
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyInventoryPresenter extends RxPresenter<CompanyInventoryContract.View> implements CompanyInventoryContract.Presenter {

    @Override
    public void getProductList(String companyId) {
        addDisposable(mDataManager.getAllProducts(companyId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfo2List) throws Exception {
                        mView.getProductList(dicInfo2List);
                    }
                }, new ComConsumer(mView, REQUEST_ALL_PRODUCTS_LIST)));
    }

    @Override
    public void getType(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getType(dicInfoList);
                    }
                }, new ComConsumer(mView, REQUEST_STOCK_TYPE)));

    }

    @Override
    public void getInventoryList(int page, int rows, String companyId, String productId, String type, String createTimes, String createTimee) {
        addDisposable(mDataManager.getInventoryList(page, rows, companyId, productId, type, createTimes, createTimee)
                .compose(RxUtils.<CompanyInventoryInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyInventoryInfoBean>() {
                    @Override
                    public void accept(CompanyInventoryInfoBean companyInventoryInfoBean) throws Exception {
                        mView.getInventoryList(companyInventoryInfoBean);
                    }
                }, new ComConsumer(mView, REQUEST_INVENTORY_LIST)));

    }
}
