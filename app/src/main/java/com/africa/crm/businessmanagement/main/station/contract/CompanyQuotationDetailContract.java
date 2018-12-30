package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyQuotationInfo;
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
 * Date：2018/12/17 0017 9:27
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyQuotationDetailContract {

    public interface View extends BaseView {
        void getAllContact(List<DicInfo2> dicInfoList);

        void getAllCustomers(List<DicInfo2> dicInfoList);

        void getAllProduct(List<DicInfo2> dicInfoList);

        void getCompanyQuotationDetail(CompanyQuotationInfo companyQuotationInfo);

        void saveCompanyQuotation(BaseEntity baseEntity);

    }

    public interface Presenter extends IBasePresenter<CompanyQuotationDetailContract.View> {
        void getAllContact(String companyId);

        void getAllCustomers(String companyId);

        void getAllProduct(String companyId);

        void getCompanyQuotationDetail(String id);

        void saveCompanyQuotation(String id, String companyId, String userId, String name, String customerName, String contactName, String termOfValidity,String price, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark);
    }
}
