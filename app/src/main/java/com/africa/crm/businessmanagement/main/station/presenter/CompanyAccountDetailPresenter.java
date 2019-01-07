package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyAccountDetailContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_ACCOUNT_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_STATE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_QUERY_ALL_ROLES;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_ACCOUNT;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_UPLOAD_IMAGE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/12 0012 20:21
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyAccountDetailPresenter extends RxPresenter<CompanyAccountDetailContract.View> implements CompanyAccountDetailContract.Presenter {

    @Override
    public void getState(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getState(dicInfoList);
                    }
                }, new ComConsumer(mView, REQUEST_COMPANY_STATE)));
    }

    @Override
    public void getAllRoles() {
        addDisposable(mDataManager.getAllRoles()
                .compose(RxUtils.<List<RoleInfoBean>>ioToMain(mView))
                .subscribe(new Consumer<List<RoleInfoBean>>() {
                    @Override
                    public void accept(List<RoleInfoBean> roleInfoBeanList) throws Exception {
                        mView.getAllRoles(roleInfoBeanList);
                    }
                }, new ComConsumer(mView, REQUEST_QUERY_ALL_ROLES)));

    }

    @Override
    public void getCompanyAccountDetail(String id) {
        addDisposable(mDataManager.getCompanyAccountDetail(id)
                .compose(RxUtils.<CompanyAccountInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanyAccountInfo>() {
                    @Override
                    public void accept(CompanyAccountInfo companyAccountInfo) throws Exception {
                        mView.getCompanyAccountDetail(companyAccountInfo);
                    }
                }, new ComConsumer(mView, REQUEST_COMPANY_ACCOUNT_DETAIL)));

    }


    @Override
    public void uploadImages(String filePath) {
        addDisposable(mDataManager.uploadFiles(filePath)
                .compose(RxUtils.<FileInfoBean>ioToMain(mView))
                .subscribe(new Consumer<FileInfoBean>() {
                    @Override
                    public void accept(FileInfoBean fileInfoBean) throws Exception {
                        mView.uploadImages(fileInfoBean,false);
                    }
                }, new ComConsumer(mView, REQUEST_UPLOAD_IMAGE)));
    }

    @Override
    public void saveCompanyAccount(String id, String userName, String type, String roleId, String passWord, String name, String phone, String address, String email, String state, String companyId, String head) {
        addDisposable(mDataManager.saveCompanyAccount(id, userName, type, roleId, passWord, name, phone, address, email, state, companyId, head)
                .compose(RxUtils.<UploadInfoBean>ioToMain(mView))
                .subscribe(new Consumer<UploadInfoBean>() {
                    @Override
                    public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                        mView.saveCompanyAccount(uploadInfoBean,false);
                    }
                }, new ComConsumer(mView, REQUEST_SAVE_COMPANY_ACCOUNT)));
    }
}
