package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.mvp.presenter.IBasePresenter;
import com.africa.crm.businessmanagement.network.base.BaseView;

import java.util.List;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/13 0013 12:58
 * Modification  History:
 * Why & What is modified:
 */
public class CompanySupplierContract {

    public interface View extends BaseView {
        void getSupplierType(List<DicInfo> dicInfoList);

        void getCompanySupplierList(CompanySupplierInfoBean companySupplierInfoBean);

        void deleteCompanySupplier(BaseEntity baseEntity);
    }

    public interface Presenter extends IBasePresenter<CompanySupplierContract.View> {
        void getSupplierType(String code);

        void getCompanySupplierList(int page, int rows, String companyId, String name, String type);

        void deleteCompanySupplier(String id);
    }
}
