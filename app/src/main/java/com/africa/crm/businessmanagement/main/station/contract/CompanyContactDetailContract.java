package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfo;
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
 * Date：2018/12/14 0014 9:16
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyContactDetailContract {
    public interface View extends BaseView {

        void getAllCompanyUsers(List<DicInfo2> dicInfo2List);

        void getFromType(List<DicInfo> dicInfoList);

        void getContactDetail(CompanyContactInfo companyContactInfo);

        void saveCompanyContact(BaseEntity baseEntity);

    }

    public interface Presenter extends IBasePresenter<CompanyContactDetailContract.View> {

        void getAllCompanyUsers(String companyId);

        void getFromType(String code);

        void getContactDetail(String id);

        void saveCompanyContact(String id, String companyId, String userId, String head, String name, String fromType, String address, String mailAddress, String phone, String tel, String email, String job, String remark);
    }
}
