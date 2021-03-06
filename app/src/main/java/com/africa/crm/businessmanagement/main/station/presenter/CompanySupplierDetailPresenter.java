package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanySupplierDetailContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_SUPPLIER_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_SUPPLIER;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SUPPLIER_TYPE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_UPLOAD_IMAGE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/10 0010 14:43
 * Modification  History:
 * Why & What is modified:
 */
public class CompanySupplierDetailPresenter extends RxPresenter<CompanySupplierDetailContract.View> implements CompanySupplierDetailContract.Presenter {

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
    public void uploadImages(String filePath) {
        addDisposable(mDataManager.uploadFiles(filePath)
                .compose(RxUtils.<FileInfoBean>ioToMain(mView))
                .subscribe(new Consumer<FileInfoBean>() {
                    @Override
                    public void accept(FileInfoBean fileInfoBean) throws Exception {
                        mView.uploadImages(fileInfoBean);
                    }
                }, new ComConsumer(mView, REQUEST_UPLOAD_IMAGE)));
    }

    @Override
    public void getCompanySupplierDetail(String id) {
        addDisposable(mDataManager.getCompanySupplierDetail(id)
                .compose(RxUtils.<CompanySupplierInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanySupplierInfo>() {
                    @Override
                    public void accept(CompanySupplierInfo companySupplierInfo) throws Exception {
                        mView.getCompanySupplierDetail(companySupplierInfo);
                    }
                }, new ComConsumer(mView, REQUEST_COMPANY_SUPPLIER_DETAIL)));

    }

    @Override
    public void saveCompanySupplier(String id, String companyId, String head, String name, String type, String address, String phone, String email, String zipCode, String area, String remark) {
        addDisposable(mDataManager.saveCompanySupplier(id, companyId, head, name, type, address, phone, email, zipCode, area, remark)
                .compose(RxUtils.<UploadInfoBean>ioToMain(mView))
                .subscribe(new Consumer<UploadInfoBean>() {
                    @Override
                    public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                        mView.saveCompanySupplier(uploadInfoBean, false);
                    }
                }, new ComConsumer(mView, REQUEST_SAVE_COMPANY_SUPPLIER)));
    }
}
