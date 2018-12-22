package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanySalesOrderInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyServiceRecordInfo;
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
public class CompanyServiceRecordrDetailContract {

    public interface View extends BaseView {
        void getState(List<DicInfo> dicInfoList);

        void getType(List<DicInfo> dicInfoList);

        void getAllProduct(List<DicInfo2> dicInfoList);

        void getAllCustomers(List<DicInfo2> dicInfoList);

        void getLevels(List<DicInfo> dicInfoList);

        void getCompanyServiceRecordDetail(CompanyServiceRecordInfo companyServiceRecordInfo);

        void saveCompanyServiceRecord(BaseEntity baseEntity);

    }

    public interface Presenter extends IBasePresenter<CompanyServiceRecordrDetailContract.View> {
        void getState(String code);

        void getType(String code);

        void getAllProduct(String companyId);

        void getAllCustomers(String companyId);

        void getLevels(String code);

        void getCompanyServiceRecordDetail(String id);

        void saveCompanyServiceRecord(String id, String companyId, String userId, String name, String state, String type, String productId, String customerName, String level, String phone, String email, String reason, String remark, String solution, String track);
    }
}
