package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPurchasingOrderInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
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
public class CompanyPurchasingDetailContract {
    public interface View extends BaseView {
        void getAllSuppliers(List<DicInfo2> dicInfoList);

        void getStateType(List<DicInfo> dicInfoList);

        void getAllProduct(List<DicInfo2> dicInfoList);

        void getCompanyPurchasingDetail(CompanyPurchasingOrderInfo companyPurchasingOrderInfo);

        void saveCompanyPurchasing(UploadInfoBean uploadInfoBean, boolean isLocal);
    }

    public interface Presenter extends IBasePresenter<CompanyPurchasingDetailContract.View> {
        void getAllSuppliers(String companyId);

        void getStateType(String code);

        void getAllProduct(String companyId);

        void getCompanyPurchasingDetail(String id);

        void saveCompanyPurchasing(String id, String companyId, String userId, String name, String supplierName, String state, String orderDate, String arriveDate, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark);
    }
}
