package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyExpenditureInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyExpenditureInfoB;
import com.africa.crm.businessmanagement.main.bean.PayRecordInfo;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
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
public class CompanyExpenditureDetailContractB {

    public interface View extends BaseView {

        void getExpenditureDetailB(CompanyExpenditureInfoB companyExpenditureInfoB);

        void saveExpenditureB(UploadInfoBean uploadInfoBean, boolean isLocal);

    }

    public interface Presenter extends IBasePresenter<CompanyExpenditureDetailContractB.View> {

        void getExpenditureDetailB(String id);

        void saveExpenditureB(String companyId, String userId, String payDate, String price, String remark);

    }
}
