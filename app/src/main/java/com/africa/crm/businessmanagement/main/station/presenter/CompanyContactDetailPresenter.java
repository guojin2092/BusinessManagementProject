package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.station.contract.CompanyContactDetailContract;
import com.africa.crm.businessmanagement.mvp.presenter.RxPresenter;
import com.africa.crm.businessmanagement.network.error.ComConsumer;
import com.africa.crm.businessmanagement.network.util.RxUtils;

import java.util.List;

import io.reactivex.functions.Consumer;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_USERS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_CONTACT_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_CONTACT_FROM_TYPE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_CONTACT;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_UPLOAD_IMAGE;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/14 0014 9:48
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyContactDetailPresenter extends RxPresenter<CompanyContactDetailContract.View> implements CompanyContactDetailContract.Presenter {

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
    public void getAllCompanyUsers(String companyId) {
        addDisposable(mDataManager.getAllCompanyUser(companyId)
                .compose(RxUtils.<List<DicInfo2>>ioToMain(mView))
                .subscribe(new Consumer<List<DicInfo2>>() {
                    @Override
                    public void accept(List<DicInfo2> dicInfoList) throws Exception {
                        mView.getAllCompanyUsers(dicInfoList);
                    }
                }, new ComConsumer(mView,REQUEST_ALL_USERS_LIST)));

    }

    @Override
    public void getFromType(String code) {
        addDisposable(mDataManager.getDicByCode(code)
                .compose(RxUtils.<List<DicInfo>>ioToMain())
                .subscribe(new Consumer<List<DicInfo>>() {
                    @Override
                    public void accept(List<DicInfo> dicInfoList) throws Exception {
                        mView.getFromType(dicInfoList);
                    }
                }, new ComConsumer(mView,REQUEST_CONTACT_FROM_TYPE)));
    }

    @Override
    public void getContactDetail(String id) {
        addDisposable(mDataManager.getContactDetail(id)
                .compose(RxUtils.<CompanyContactInfo>ioToMain(mView))
                .subscribe(new Consumer<CompanyContactInfo>() {
                    @Override
                    public void accept(CompanyContactInfo companyContactInfo) throws Exception {
                        mView.getContactDetail(companyContactInfo);
                    }
                }, new ComConsumer(mView,REQUEST_COMPANY_CONTACT_DETAIL)));
    }

    @Override
    public void saveCompanyContact(String id, String companyId, String userId, String head, String name, String fromType, String address, String mailAddress, String phone, String tel, String email, String job, String remark) {
        addDisposable(mDataManager.saveCompanyContact(id, companyId, userId, head, name, fromType, address, mailAddress, phone, tel, email, job, remark)
                .compose(RxUtils.<UploadInfoBean>ioToMain(mView))
                .subscribe(new Consumer<UploadInfoBean>() {
                    @Override
                    public void accept(UploadInfoBean uploadInfoBean) throws Exception {
                        mView.saveCompanyContact(uploadInfoBean, false);
                    }
                }, new ComConsumer(mView,REQUEST_SAVE_COMPANY_CONTACT)));
    }

}
