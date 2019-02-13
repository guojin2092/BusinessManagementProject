package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.mvp.presenter.IBasePresenter;
import com.africa.crm.businessmanagement.network.base.BaseView;

import java.util.List;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/7 0007 10:35
 * Modification  History:
 * Why & What is modified:
 */
public class UserManagementContract {

    public interface View extends BaseView {
        void getUserType(List<DicInfo> dicInfoList);

        void getStateType(List<DicInfo> dicInfoList);

        void getUserList(UserManagementInfoBean userManagementInfoBean);

        void deleteUser(BaseEntity baseEntity, boolean isLocal);

    }

    public interface Presenter extends IBasePresenter<UserManagementContract.View> {
        void getUserType(String code);

        void getStateType(String code);

        void getUserList(int page, int rows, String userName, String type, String companyId, String state, String name);

        void deleteUser(String id);
    }
}
