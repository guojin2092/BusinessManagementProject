package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.delete.WebInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyStatisticalFormWebContract;
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
 * Date：2018/12/14 0014 9:48
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyStatisticalFormWebPresenter extends RxPresenter<CompanyStatisticalFormWebContract.View> implements CompanyStatisticalFormWebContract.Presenter {

    @Override
    public void getAllCompany(String name) {
        addDisposable(mDataManager.getAllCompany(name)
                .compose(RxUtils.<List<DicInfo2>>ioToMain(mView))
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfoList) throws Exception {
                        mView.getAllCompany(dicInfoList);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getAllCompanyUsers(String companyId) {
        addDisposable(mDataManager.getAllCompanyUser(companyId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain(mView))
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfoList) throws Exception {
                        mView.getAllCompanyUsers(dicInfoList);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void getOrderAmountStatisticsReport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        addDisposable(mDataManager.getOrderAmountStatisticsReport(startDate, endDate, sfxsysc, userId, companyId)
                .compose(RxUtils.<WebInfoBean>ioToMain(mView))
                .subscribe(new Consumer<WebInfoBean>() {
                    @Override
                    public void accept(WebInfoBean webInfoBean) throws Exception {
                        mView.getOrderAmountStatisticsReport(webInfoBean);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void getOrderAmountStatisticsExport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        addDisposable(mDataManager.getOrderAmountStatisticsExport(startDate, endDate, sfxsysc, userId, companyId)
                .compose(RxUtils.<ResponseBody>ioToMain(mView))
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        mView.getOrderAmountStatisticsExport(responseBody);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getOrderNumStatisticsReport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        addDisposable(mDataManager.getOrderNumStatisticsReport(startDate, endDate, sfxsysc, userId, companyId)
                .compose(RxUtils.<WebInfoBean>ioToMain(mView))
                .subscribe(new Consumer<WebInfoBean>() {
                    @Override
                    public void accept(WebInfoBean webInfoBean) throws Exception {
                        mView.getOrderNumStatisticsReport(webInfoBean);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getOrderNumStatisticsExport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        addDisposable(mDataManager.getOrderNumStatisticsExport(startDate, endDate, sfxsysc, userId, companyId)
                .compose(RxUtils.<ResponseBody>ioToMain(mView))
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        mView.getOrderNumStatisticsExport(responseBody);
                    }
                }, new ComConsumer(mView)));

    }

    @Override
    public void getPayRecordStatisticsReport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        addDisposable(mDataManager.getPayRecordStatisticsReport(startDate, endDate, sfxsysc, userId, companyId)
                .compose(RxUtils.<WebInfoBean>ioToMain(mView))
                .subscribe(new Consumer<WebInfoBean>() {
                    @Override
                    public void accept(WebInfoBean webInfoBean) throws Exception {
                        mView.getPayRecordStatisticsReport(webInfoBean);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getPayRecordStatisticsExport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        addDisposable(mDataManager.getPayRecordStatisticsExport(startDate, endDate, sfxsysc, userId, companyId)
                .compose(RxUtils.<ResponseBody>ioToMain(mView))
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        mView.getPayRecordStatisticsExport(responseBody);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getServiceStatisticsReport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        addDisposable(mDataManager.getServiceStatisticsReport(startDate, endDate, sfxsysc, userId, companyId)
                .compose(RxUtils.<WebInfoBean>ioToMain(mView))
                .subscribe(new Consumer<WebInfoBean>() {
                    @Override
                    public void accept(WebInfoBean webInfoBean) throws Exception {
                        mView.getServiceStatisticsReport(webInfoBean);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getServiceStatisticsExport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        addDisposable(mDataManager.getServiceStatisticsExport(startDate, endDate, sfxsysc, userId, companyId)
                .compose(RxUtils.<ResponseBody>ioToMain(mView))
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        mView.getServiceStatisticsExport(responseBody);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getStockStatisticsReport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        addDisposable(mDataManager.getStockStatisticsReport(startDate, endDate, sfxsysc, userId, companyId)
                .compose(RxUtils.<WebInfoBean>ioToMain(mView))
                .subscribe(new Consumer<WebInfoBean>() {
                    @Override
                    public void accept(WebInfoBean webInfoBean) throws Exception {
                        mView.getStockStatisticsReport(webInfoBean);
                    }
                }, new ComConsumer(mView)));
    }

    @Override
    public void getStockStatisticsExport(String startDate, String endDate, String sfxsysc, String userId, String companyId) {
        addDisposable(mDataManager.getStockStatisticsExport(startDate, endDate, sfxsysc, userId, companyId)
                .compose(RxUtils.<ResponseBody>ioToMain(mView))
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        mView.getStockStatisticsExport(responseBody);
                    }
                }, new ComConsumer(mView)));
    }

}
