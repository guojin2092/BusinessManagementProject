package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.CompanyInventoryInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyInventoryDetailContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_PRODUCTS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_INVENTORY_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_INVENTORY;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_STOCK_TYPE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/17 0017 20:21
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyInventoryDetailPresenter extends RxPresenter<CompanyInventoryDetailContract.View> implements CompanyInventoryDetailContract.Presenter {

    @Override
    public void getAllProduct(String companyId) {
        addDisposable(mDataManager.getAllProducts(companyId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain())
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfo2List) throws Exception {
                        mView.getAllProduct(dicInfo2List);
                    }
                }, new ComConsumer(mView, REQUEST_ALL_PRODUCTS_LIST)));
    }

    @Override
    public void getOperationType(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getOperationType(dicInfoList);
                    }
                }, new ComConsumer(mView, REQUEST_STOCK_TYPE)));

    }

    @Override
    public void getInventoryDetail(String id) {
        addDisposable(mDataManager.getInventoryDetail(id)
                .compose(RxUtils.<CompanyInventoryInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanyInventoryInfo>() {
                    @Override
                    public void accept(CompanyInventoryInfo companyInventoryInfo) throws Exception {
                        mView.getInventoryDetail(companyInventoryInfo);
                    }
                }, new ComConsumer(mView, REQUEST_COMPANY_INVENTORY_DETAIL)));
    }

    @Override
    public void saveInventory(String companyId, String productId, String type, String num, String remark) {
        addDisposable(mDataManager.saveInventory(companyId, productId, type, num, remark)
                .compose(RxUtils.<UploadInfoBean>ioToMain(mView))
                .subscribe(new Consumer<UploadInfoBean>() {
                    @Override
                    public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                        mView.saveInventory(uploadInfoBean, false);
                    }
                }, new ComConsumer(mView, REQUEST_SAVE_COMPANY_INVENTORY)));
    }
}
