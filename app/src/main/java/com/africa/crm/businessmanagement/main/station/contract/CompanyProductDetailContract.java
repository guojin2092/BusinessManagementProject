package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyProductInfo;
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
 * Date：2018/12/12 0012 20:19
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyProductDetailContract {
    public interface View extends BaseView {
        void getAllSuppliers(List<DicInfo2> dicInfoList);

        void getProductType(List<DicInfo> dicInfoList);

        void getCompanyProductDetail(CompanyProductInfo companyProductInfo);

        void saveCompanyProduct(BaseEntity baseEntity);
    }

    public interface Presenter extends IBasePresenter<CompanyProductDetailContract.View> {
        void getAllSuppliers(String companyId);

        void getProductType(String code);

        void getCompanyProductDetail(String id);

        void saveCompanyProduct(String id, String companyId, String name, String code, String supplierName, String makerName, String type, String unitPrice, String unit, String stockNum, String warnNum, String remark);
    }
}
