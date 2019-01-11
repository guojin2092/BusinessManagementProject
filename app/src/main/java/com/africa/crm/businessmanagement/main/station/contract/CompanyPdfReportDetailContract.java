package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPdfInfo;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.mvp.presenter.IBasePresenter;
import com.africa.crm.businessmanagement.network.base.BaseView;

import okhttp3.ResponseBody;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/12 0012 9:13
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyPdfReportDetailContract {

    public interface View extends BaseView {
        void uploadFile(FileInfoBean fileInfoBean);

        void downLoadFile(ResponseBody responseBody,boolean isLocal);

        void getCompanyPdfDetail(CompanyPdfInfo companyPdfInfo);

        void saveCompanyPdfDetail(UploadInfoBean uploadInfoBean, boolean isLocal);

    }

    public interface Presenter extends IBasePresenter<CompanyPdfReportDetailContract.View> {
        void uploadFile(String filePath);

        void downLoadFile(String code);

        void getCompanyPdfDetail(String id);

        void saveCompanyPdfDetail(String id, String companyId, String userId, String name, String code, String remark);
    }
}
