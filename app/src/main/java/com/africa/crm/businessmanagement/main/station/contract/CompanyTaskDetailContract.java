package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyTaskInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.mvp.presenter.IBasePresenter;
import com.africa.crm.businessmanagement.network.base.BaseView;

import java.util.List;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/17 0017 9:27
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyTaskDetailContract {

    public interface View extends BaseView {

        void getAllCustomers(List<DicInfo2> dicInfoList);

        void getAllContact(List<DicInfo2> dicInfoList);

        void getLevels(List<DicInfo> dicInfoList);

        void getStates(List<DicInfo> dicInfoList);

        void getCompanyTaskDetail(CompanyTaskInfo companyTaskInfo);

        void saveCompanyTask(BaseEntity baseEntity);

    }

    public interface Presenter extends IBasePresenter<CompanyTaskDetailContract.View> {

        void getAllCustomers(String companyId);

        void getAllContact(String companyId);

        void getLevels(String code);

        void getStates(String code);

        void getCompanyTaskDetail(String id);

        void saveCompanyTask(String id, String companyId, String userId, String name, String remindTime, String customerName, String contactName, String level, String state, String remark);
    }
}
