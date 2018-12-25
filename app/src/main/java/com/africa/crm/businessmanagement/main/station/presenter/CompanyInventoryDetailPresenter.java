package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyInventoryInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.station.contract.CompanyInventoryDetailContract;
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
                }, new ComConsumer(mView)));
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
                }, new ComConsumer(mView)));

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
                }, new ComConsumer(mView)));
    }

    @Override
    public void saveInventory(String id, String companyId, String productId, String type, String num, String remark) {
        addDisposable(mDataManager.saveInventory(id, companyId, productId, type, num, remark)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveInventory(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }
}
