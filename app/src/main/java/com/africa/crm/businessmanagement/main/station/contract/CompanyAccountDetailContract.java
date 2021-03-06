package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
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
public class CompanyAccountDetailContract {
    public interface View extends BaseView {

        void getState(List<DicInfo> dicInfoList);

        void getAllRoles(List<RoleInfoBean> roleInfoBeanList);

        void uploadImages(FileInfoBean fileInfoBean);

        void getCompanyAccountDetail(CompanyAccountInfo companyAccountInfo);

        void saveCompanyAccount(UploadInfoBean uploadInfoBean, boolean isLocal);

    }

    public interface Presenter extends IBasePresenter<CompanyAccountDetailContract.View> {

        void getState(String code);

        void getAllRoles();

        void getCompanyAccountDetail(String id);

        void uploadImages(String filePath);

        void saveCompanyAccount(String id, String userName, String type, String roleId, String passWord, String name, String phone, String address, String email, String state, String companyId, String head);
    }
}
