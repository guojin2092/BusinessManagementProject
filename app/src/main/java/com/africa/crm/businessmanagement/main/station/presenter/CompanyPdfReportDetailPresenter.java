package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPdfInfo;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPdfReportDetailContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/12 0012 9:16
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyPdfReportDetailPresenter extends RxPresenter<CompanyPdfReportDetailContract.View> implements CompanyPdfReportDetailContract.Presenter {

    @Override
    public void uploadFile(String filePath) {
        addDisposable(mDataManager.uploadFiles(filePath)
                .compose(RxUtils.<FileInfoBean>ioToMain(mView))
                .subscribe(new Consumer<FileInfoBean>() {
                    @Override
                    public void accept(FileInfoBean fileInfoBean) throws Exception {
                        mView.uploadFile(fileInfoBean);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void downLoadFile(String code) {
        addDisposable(mDataManager.downloadFiles(code)
                .compose(RxUtils.<ResponseBody>ioToMain(mView))
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        mView.downLoadFile(responseBody);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getCompanyPdfDetail(String id) {
        addDisposable(mDataManager.getCompanyPdfDetail(id)
                .compose(RxUtils.<CompanyPdfInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanyPdfInfo>() {
                    @Override
                    public void accept(CompanyPdfInfo companyPdfInfo) throws Exception {
                        mView.getCompanyPdfDetail(companyPdfInfo);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void saveCompanyPdfDetail(String id, String companyId, String userId, String name, String code, String remark) {
        addDisposable(mDataManager.saveCompanyPdfDetail(id, companyId, userId, name, code, remark)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveCompanyPdfDetail(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }

}
