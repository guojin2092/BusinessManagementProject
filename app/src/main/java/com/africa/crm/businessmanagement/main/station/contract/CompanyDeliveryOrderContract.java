package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyDeliveryOrderInfoBean;
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
public class CompanyDeliveryOrderContract {

    public interface View extends BaseView {
        void getCompanyUserList(UserManagementInfoBean userManagementInfoBean);

        void getCompanyDeliveryOrderList(CompanyDeliveryOrderInfoBean companyDeliveryOrderInfoBean);

        void deleteCompanyDeliveryOrder(BaseEntity baseEntity);
    }

    public interface Presenter extends IBasePresenter<CompanyDeliveryOrderContract.View> {
        void getCompanyUserList(int page, int rows, String userName, String type, String companyId, String state, String name);

        void getCompanyDeliveryOrderList(int page, int rows, String companyId, String userId, String name, String createTimes, String createTimee);

        void deleteCompanyDeliveryOrder(String id);
    }
}
