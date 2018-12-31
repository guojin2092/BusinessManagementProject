package com.africa.crm.businessmanagement.main.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.LoginInfoBean;
import com.africa.crm.businessmanagement.mvp.presenter.IBasePresenter;
import com.africa.crm.businessmanagement.network.base.BaseView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/23 0023 10:00
 * Modification  History:
 * Why & What is modified:
 */
public class ChangePasswordContract {

    public interface View extends BaseView {

        void changePassword(BaseEntity baseEntity);
    }

    public interface Presenter extends IBasePresenter<View> {

        void changePassword(String id, String oldPassWord, String newPassWord);
    }
}
