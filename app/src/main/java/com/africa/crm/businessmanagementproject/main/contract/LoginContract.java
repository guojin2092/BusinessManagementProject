package com.africa.crm.businessmanagementproject.main.contract;

import com.africa.crm.businessmanagementproject.main.bean.LoginInfoBean;
import com.africa.crm.businessmanagementproject.mvp.presenter.IBasePresenter;
import com.africa.crm.businessmanagementproject.network.base.BaseView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/23 0023 10:00
 * Modification  History:
 * Why & What is modified:
 */
public class LoginContract {

    public interface View extends BaseView {

        void getLoginInfo(LoginInfoBean loginInfoBean);
    }

    public interface Presenter extends IBasePresenter<View> {
        void getLoginInfo(String userName,String passWord);
    }
}
