package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyInventoryInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyeExpenditureInfoBean;
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
 * Date：2018/12/12 0012 9:13
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyExpenditureManagementContractA {

    public interface View extends BaseView {

        void getExpenditureList(CompanyeExpenditureInfoBean companyeExpenditureInfoBean);

    }

    public interface Presenter extends IBasePresenter<CompanyExpenditureManagementContractA.View> {

        void getExpenditureList(int page, int rows, String companyId, String title, String createTimes, String createTimee);

    }
}
