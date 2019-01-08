package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyClientInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
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
public class CompanyClientContract {

    public interface View extends BaseView {
        void getIndustryType(List<DicInfo> dicInfoList);

        void getCompanyClientList(CompanyClientInfoBean companyClientInfoBean);

        void deleteCompanyClient(BaseEntity baseEntity, boolean isLocal);
    }

    public interface Presenter extends IBasePresenter<CompanyClientContract.View> {
        void getIndustryType(String code);

        void getCompanyClientList(int page, int rows, String companyId, String userId, String name, String industry);

        void deleteCompanyClient(String id);
    }
}
