package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyTaskInfoBean;
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
public class CompanyTaskManagementContract {

    public interface View extends BaseView {

        void getStates(List<DicInfo> dicInfoList);

        void getLevels(List<DicInfo> dicInfoList);

        void getCompanyTaskList(CompanyTaskInfoBean companyTaskInfoBean);

        void deleteCompanyTask(BaseEntity baseEntity);
    }

    public interface Presenter extends IBasePresenter<CompanyTaskManagementContract.View> {

        void getStates(String code);

        void getLevels(String code);

        void getCompanyTaskList(int page, int rows, String companyId, String userId, String name, String customerName, String state, String level, String remindTimes, String remindTimee);

        void deleteCompanyTask(String id);
    }
}
