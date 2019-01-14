package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.CompanyUserInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserInfo;
import com.africa.crm.businessmanagement.main.station.contract.UserDetailContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_STATE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_GET_ALL_COMPANY;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_GET_USER_INFO;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_QUERY_ALL_ROLES;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_USER;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_UPLOAD_IMAGE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_USER_TYPE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/9 0009 12:44
 * Modification  History:
 * Why & What is modified:
 */
public class UserDetailPresenter extends RxPresenter<UserDetailContract.View> implements UserDetailContract.Presenter {
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
    public void getUserType(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getUserType(dicInfoList);
                    }
                }, new ComConsumer(mView, REQUEST_USER_TYPE)));

    }

    @Override
    public void getStateType(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getStateType(dicInfoList);
                    }
                }, new ComConsumer(mView, REQUEST_COMPANY_STATE)));
    }

    @Override
    public void getUserInfo(String id) {
        addDisposable(mDataManager.getUserInfo(id)
                .compose(RxUtils.<CompanyUserInfoBean>ioToMain(mView))
                .subscribe(new Consumer<CompanyUserInfoBean>() {
                    @Override
                    public void accept(CompanyUserInfoBean companyUserInfoBean) throws Exception {
                        mView.getUserInfo(companyUserInfoBean);
                    }
                }, new ComConsumer(mView, REQUEST_GET_USER_INFO)));

    }

    @Override
    public void getAllCompany(String name) {
        addDisposable(mDataManager.getAllCompany(name)
                .compose(RxUtils.<List<DicInfo2>>ioToMain(mView))
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfoList) throws Exception {
                        mView.getAllCompany(dicInfoList);
                    }
                }, new ComConsumer(mView, REQUEST_GET_ALL_COMPANY)));
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
    public void saveOrcreateUser(String id, String userName, String type, String roleIds, String passWord, String name, String phone, String address, String email, String state, String companyId, String head) {
        addDisposable(mDataManager.saveOrcreateUser(id, userName, type, roleIds, passWord, name, phone, address, email, state, companyId, head)
                .compose(RxUtils.<UploadInfoBean>ioToMain(mView))
                .subscribe(new Consumer<UploadInfoBean>() {
                    @Override
                    public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                        mView.saveOrcreateUser(uploadInfoBean, false);
                    }
                }, new ComConsumer(mView, REQUEST_SAVE_COMPANY_USER)));
    }
}
