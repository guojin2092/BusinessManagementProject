package com.africa.crm.businessmanagement.main.station.presenter;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserInfo;
import com.africa.crm.businessmanagement.main.station.contract.UploadPictureContract;
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
public class UploadPicturePresenter extends RxPresenter<UploadPictureContract.View> implements UploadPictureContract.Presenter {

    @Override
    public void getUserInfo(String userId) {
        addDisposable(mDataManager.getUserInfo(userId)
                .compose(RxUtils.<UserInfo>ioToMain(mView))
                .subscribe(new Consumer<UserInfo>() {
                    @Override
                    public void accept(UserInfo userInfo) throws Exception {
                        mView.getUserInfo(userInfo);
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
    public void saveUserInfo(String id, String userName, String type, String roleIds, String passWord, String name, String phone, String address, String email, String state, String companyId, String head) {
        addDisposable(mDataManager.saveOrcreateUser(id, userName, type, roleIds, passWord, name, phone, address, email, state, companyId, head)
                .compose(RxUtils.<BaseEntity>ioToMain(mView))
                .subscribe(new Consumer<BaseEntity>() {
                    @Override
                    public void accept(BaseEntity baseEntity) throws Exception {
                        mView.saveUserInfo(baseEntity);
                    }
                }, new ComConsumer(mView)));
    }

}
