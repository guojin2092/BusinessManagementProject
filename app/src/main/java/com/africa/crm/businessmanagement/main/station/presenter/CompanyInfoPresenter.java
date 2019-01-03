package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyInfoContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/10 0010 14:43
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyInfoPresenter extends RxPresenter<CompanyInfoContract.View> implements CompanyInfoContract.Presenter {

    @Override
    public void getCompanyType(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getCompanyType(dicInfoList);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getState(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getState(dicInfoList);
                    }
                }, new ComConsumer(mView)));
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
                }, new ComConsumer(mView)));
    }

    @Override
    public void getCompanyInfoDetail(String id) {
        addDisposable(mDataManager.getCompanyInfoDetail(id)
                .compose(RxUtils.<CompanyInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanyInfo>() {
                    @Override
                    public void accept(CompanyInfo companyInfo) throws Exception {
                        mView.getCompanyInfoDetail(companyInfo);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void saveCompanyInfo(String id, String head, String name, String code, String type, String address, String phone, String email, String mid, String area, String profession, String numA, String state) {
        addDisposable(mDataManager.saveCompanyInfo(id, head, name, code, type, address, phone, email, mid, area, profession, numA, state)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveCompanyInfo(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }
}
