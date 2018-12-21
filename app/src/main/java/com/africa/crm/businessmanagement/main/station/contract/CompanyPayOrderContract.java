package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyDeliveryOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyPayOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.mvp.presenter.IBasePresenter;
import com.africa.crm.businessmanagement.network.base.BaseView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/12 0012 9:13
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyPayOrderContract {

    public interface View extends BaseView {
        void getCompanyUserList(UserManagementInfoBean userManagementInfoBean);

        void getCompanyPayOrderList(CompanyPayOrderInfoBean companyPayOrderInfoBean);

        void deleteCompanyPayOrder(BaseEntity baseEntity);
    }

    public interface Presenter extends IBasePresenter<CompanyPayOrderContract.View> {
        void getCompanyUserList(int page, int rows, String userName, String type, String companyId, String state, String name);

        void getCompanyPayOrderList(int page, int rows, String companyId, String userId, String name, String code, String createTimes, String createTimee);

        void deleteCompanyPayOrder(String id);
    }
}
