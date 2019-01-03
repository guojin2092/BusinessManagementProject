package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.mvp.presenter.IBasePresenter;
import com.africa.crm.businessmanagement.network.base.BaseView;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/10 0010 14:42
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyInfoContract {

    public interface View extends BaseView {

        void getCompanyInfoDetail(CompanyInfo companyInfo);

        void getCompanyType(List<DicInfo> dicInfoList);

        void getState(List<DicInfo> dicInfoList);

        void uploadImages(FileInfoBean fileInfoBean);

        void saveCompanyInfo(BaseEntity baseEntity);

    }

    public interface Presenter extends IBasePresenter<CompanyInfoContract.View> {

        void getCompanyInfoDetail(String id);

        void getCompanyType(String code);

        void getState(String code);

        void uploadImages(String filePath);

        void saveCompanyInfo(String id, String head, String name, String code, String type, String address, String phone, String email, String mid, String area, String profession, String numA, String state);
    }
}
