package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyClientInfoBean;
import com.africa.crm.businessmanagement.main.bean.CompanyProductInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
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
public class CompanyProductManagementContract {

    public interface View extends BaseView {
        void getProductType(List<DicInfo> dicInfoList);

        void getCompanyProductList(CompanyProductInfoBean companyProductInfoBean);

        void deleteCompanyProduct(BaseEntity baseEntity);
    }

    public interface Presenter extends IBasePresenter<CompanyProductManagementContract.View> {
        void getProductType(String code);

        void getCompanyProductList(int page, int rows, String companyId,String name, String type);

        void deleteCompanyProduct(String id);
    }
}
