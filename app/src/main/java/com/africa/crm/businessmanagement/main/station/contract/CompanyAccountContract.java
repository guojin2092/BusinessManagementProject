package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfoBean;
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
public class CompanyAccountContract {

    public interface View extends BaseView {

        void getCompanyAccounList(CompanyAccountInfoBean companyAccountInfoBean);

        void deleteCompanyAccount(BaseEntity baseEntity, boolean isLocal);
    }

    public interface Presenter extends IBasePresenter<CompanyAccountContract.View> {

        void getCompanyAccounList(int page, int rows, String companyId, String userName, String name);

        void deleteCompanyAccount(String id);
    }
}
