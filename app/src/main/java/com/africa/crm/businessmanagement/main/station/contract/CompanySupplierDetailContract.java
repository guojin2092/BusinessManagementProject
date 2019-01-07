package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
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
 * Date：2018/12/12 0012 20:19
 * Modification  History:
 * Why & What is modified:
 */
public class CompanySupplierDetailContract {
    public interface View extends BaseView {

        void getSupplierType(List<DicInfo> dicInfoList);

        void uploadImages(FileInfoBean fileInfoBean);

        void getCompanySupplierDetail(CompanySupplierInfo companySupplierInfo);

        void saveCompanySupplier(UploadInfoBean uploadInfoBean, boolean isLocal);

    }

    public interface Presenter extends IBasePresenter<CompanySupplierDetailContract.View> {

        void getSupplierType(String code);

        void uploadImages(String filePath);

        void getCompanySupplierDetail(String id);

        void saveCompanySupplier(String id, String companyId, String head, String name, String type, String address, String phone, String email, String zipCode, String area, String remark);
    }
}
