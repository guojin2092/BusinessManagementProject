package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserInfo;
import com.africa.crm.businessmanagement.mvp.presenter.IBasePresenter;
import com.africa.crm.businessmanagement.network.base.BaseView;

import java.util.List;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/9 0009 12:40
 * Modification  History:
 * Why & What is modified:
 */
public class UserDetailContract {
    public interface View extends BaseView {
        void uploadImages(FileInfoBean fileInfoBean);

        void getUserType(List<DicInfo> dicInfoList);

        void getStateType(List<DicInfo> dicInfoList);

        void getUserInfo(UserInfo userInfo);

        void saveOrcreateUser(BaseEntity baseEntity);

        void getAllCompany(List<DicInfo2> dicInfoList);

        void getAllRoles(List<RoleInfoBean> roleInfoBeanList);
    }

    public interface Presenter extends IBasePresenter<UserDetailContract.View> {
        void uploadImages(String filePath);

        void getUserType(String code);

        void getStateType(String code);

        void getUserInfo(String id);

        void saveOrcreateUser(String id, String userName, String type, String roleIds, String passWord, String name, String phone, String address, String email, String state, String companyId, String head);

        void getAllCompany(String name);

        void getAllRoles(String name);
    }
}
