package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyClientInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
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
public class CompanyClientDetailContract {
    public interface View extends BaseView {
        void uploadImages(FileInfoBean fileInfoBean);

        void getAllCompanyUsers(List<DicInfo2> dicInfo2List);

        void getIndustry(List<DicInfo> dicInfoList);

        void getCompanyClientDetail(CompanyClientInfo companyClientInfo);

        void saveCompanyClient(UploadInfoBean uploadInfoBean, boolean isLocal);

    }

    public interface Presenter extends IBasePresenter<CompanyClientDetailContract.View> {
        void uploadImages(String filePath);

        void getAllCompanyUsers(String companyId);

        void getIndustry(String code);

        void getCompanyClientDetail(String id);

        void saveCompanyClient(String id, String companyId, String userId, String head, String name, String industry, String address, String workerNum, String tel, String yearIncome, String remark);
    }
}
