package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.CompanyeExpenditureInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyeExpenditureInfoBeanB;
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
public class CompanyExpenditureManagementContractB {

    public interface View extends BaseView {

        void getExpenditureListB(CompanyeExpenditureInfoBeanB companyeExpenditureInfoBeanB);

    }

    public interface Presenter extends IBasePresenter<CompanyExpenditureManagementContractB.View> {

        void getExpenditureListB(int page, int rows, String companyId, String userId, String payDates, String payDatee);

    }
}
