package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserInfo;
import com.africa.crm.businessmanagement.mvp.presenter.IBasePresenter;
import com.africa.crm.businessmanagement.network.base.BaseView;

import okhttp3.ResponseBody;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/12 0012 9:13
 * Modification  History:
 * Why & What is modified:
 */
public class UploadPictureContract {

    public interface View extends BaseView {
        void getUserInfo(UserInfo userInfo);

        void uploadImages(FileInfoBean fileInfoBean);

        void downLoadFile(ResponseBody responseBody);

        void saveUserInfo(BaseEntity baseEntity);

    }

    public interface Presenter extends IBasePresenter<UploadPictureContract.View> {

        void getUserInfo(String userId);

        void uploadImages(String filePath);

        void downLoadFile(String code);

        void saveUserInfo(String id, String userName, String type, String roleIds, String passWord, String name, String phone, String address, String email, String state, String companyId, String head);
    }
}
