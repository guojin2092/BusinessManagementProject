package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanySalesOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
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
public class CompanySalesOrderContract {

    public interface View extends BaseView {
        void getAllCompanyUsers(List<DicInfo2> dicInfo2List);

        void getStateType(List<DicInfo> dicInfoList);

        void getCompanySalesOrderList(CompanySalesOrderInfoBean companySalesOrderInfoBean);

        void deleteCompanySalesOrder(BaseEntity baseEntity, boolean isLocal);
    }

    public interface Presenter extends IBasePresenter<CompanySalesOrderContract.View> {
        void getAllCompanyUsers(String companyId);

        void getStateType(String code);

        void getCompanySalesOrderList(int page, int rows, String companyId, String userId, String name, String state, String createTimes, String createTimee);

        void deleteCompanySalesOrder(String id);
    }
}
