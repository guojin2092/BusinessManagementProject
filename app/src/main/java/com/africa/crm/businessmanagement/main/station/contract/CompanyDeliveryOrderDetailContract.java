package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyDeliveryOrderInfo;
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
 * Date：2018/12/17 0017 9:27
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyDeliveryOrderDetailContract {

    public interface View extends BaseView {
        void getState(List<DicInfo> dicInfoList);

        void getAllProduct(List<DicInfo2> dicInfoList);

        void getAllSaleOrders(List<DicInfo2> dicInfoList);

        void getCompanyDeliveryOrderDetail(CompanyDeliveryOrderInfo companyDeliveryOrderInfo);

        void saveCompanyDeliveryOrder(UploadInfoBean uploadInfoBean, boolean isLocal);

    }

    public interface Presenter extends IBasePresenter<CompanyDeliveryOrderDetailContract.View> {
        void getState(String code);

        void getAllProduct(String companyId);

        void getAllSaleOrders(String companyId, String userId);

        void getCompanyDeliveryOrderDetail(String id);

        void saveCompanyDeliveryOrder(String id, String companyId, String userId, String name, String salesOrderId, String logisticsCode, String state, String arriveDate, String sendAddress, String sendAddressZipCode, String destinationAddress, String destinationAddressZipCode, String products, String clause, String remark);
    }
}
