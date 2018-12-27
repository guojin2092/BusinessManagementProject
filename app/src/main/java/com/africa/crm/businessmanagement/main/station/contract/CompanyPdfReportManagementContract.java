package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyPdfInfoBean;
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
public class CompanyPdfReportManagementContract {

    public interface View extends BaseView {
        void getCompanyUserList(UserManagementInfoBean userManagementInfoBean);

        void getCompanyPdfList(CompanyPdfInfoBean companyPdfInfoBean);

        void deleteCompanyPdf(BaseEntity baseEntity);
    }

    public interface Presenter extends IBasePresenter<CompanyPdfReportManagementContract.View> {
        void getCompanyUserList(int page, int rows, String userName, String type, String companyId, String state, String name);

        void getCompanyPdfList(int page, int rows, String companyId, String userId, String name);

        void deleteCompanyPdf(String id);
    }
}
