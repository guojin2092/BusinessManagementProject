package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyInfoBean;
import com.africa.crm.businessmanagement.mvp.presenter.IBasePresenter;
import com.africa.crm.businessmanagement.network.base.BaseView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/10 0010 12:41
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyInfoManagementContract {

    public interface View extends BaseView {

        void getCompanyInfoList(CompanyInfoBean companyInfoBean);

        void deleteCompanyInfo(BaseEntity baseEntity, boolean isLocal);
    }

    public interface Presenter extends IBasePresenter<CompanyInfoManagementContract.View> {

        void getCompanyInfoList(int page, int rows, String name);

        void deleteCompanyInfo(String id);
    }
}