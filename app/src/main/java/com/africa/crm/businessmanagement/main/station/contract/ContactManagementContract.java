package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.mvp.presenter.IBasePresenter;
import com.africa.crm.businessmanagement.network.base.BaseView;

import java.util.List;

public class ContactManagementContract {

    public interface View extends BaseView {
        void getFromType(List<DicInfo> dicInfoList);

        void getCompanyContactList(CompanyContactInfoBean companyContactInfoBean);

        void deleteCompanyContact(BaseEntity baseEntity, boolean isLocal);
    }

    public interface Presenter extends IBasePresenter<ContactManagementContract.View> {
        void getFromType(String code);

        void getCompanyContactList(int page, int rows, String companyId, String userId, String name, String fromType);

        void deleteCompanyContact(String id);
    }
}
