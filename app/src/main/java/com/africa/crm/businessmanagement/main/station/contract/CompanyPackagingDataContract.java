package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPackagingDataInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyPayOrderInfoBean;
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
public class CompanyPackagingDataContract {

    public interface View extends BaseView {

        void getAllCompanyUsers(List<DicInfo2> dicInfo2List);

        void getCompanyPackagingDataList(CompanyPackagingDataInfoBean companyPackagingDataInfoBean);

    }

    public interface Presenter extends IBasePresenter<CompanyPackagingDataContract.View> {

        void getAllCompanyUsers(String companyId);

        void getCompanyPackagingDataList(int page, int rows, String companyId, String userId, String createTimes, String createTimee);

    }
}
