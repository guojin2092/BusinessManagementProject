package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyExpenditureInfo;
import com.africa.crm.businessmanagement.main.bean.PayRecordInfo;
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
public class CompanyExpenditureDetailContractA {

    public interface View extends BaseView {

        void getExpenditureDetail(CompanyExpenditureInfo companyExpenditureInfo);

        void getPayRecord(List<PayRecordInfo> payRecordInfoList);

        void saveExpenditureA(BaseEntity baseEntity);

    }

    public interface Presenter extends IBasePresenter<CompanyExpenditureDetailContractA.View> {

        void getExpenditureDetail(String id);

        void getPayRecord(String estimateId);

        void saveExpenditureA(String companyId, String userId, String title, String startDate, String endDate, String estimatePrice, String remark);

    }
}
