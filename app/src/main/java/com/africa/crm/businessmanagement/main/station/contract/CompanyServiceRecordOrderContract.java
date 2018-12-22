package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyDeliveryOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyServiceRecordInfoBean;
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
 * Date：2018/12/12 0012 9:13
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyServiceRecordOrderContract {

    public interface View extends BaseView {
        void getState(List<DicInfo> dicInfoList);

        void getType(List<DicInfo> dicInfoList);

        void getCompanyUserList(UserManagementInfoBean userManagementInfoBean);

        void getServiceRecordList(CompanyServiceRecordInfoBean companyServiceRecordInfoBean);

        void deleteServiceRecord(BaseEntity baseEntity);
    }

    public interface Presenter extends IBasePresenter<CompanyServiceRecordOrderContract.View> {
        void getState(String code);

        void getType(String code);

        void getCompanyUserList(int page, int rows, String userName, String type, String companyId, String state, String name);

        void getServiceRecordList(int page, int rows, String companyId, String userId, String name, String state, String type, String createTimes, String createTimee);

        void deleteServiceRecord(String id);
    }
}
