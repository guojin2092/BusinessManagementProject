package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPayOrderInfo;
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
public class CompanyPayOrderDetailContract {

    public interface View extends BaseView {

        void getAllSaleOrders(List<DicInfo2> dicInfoList);

        void getAllTradingOrders(List<DicInfo2> dicInfoList);

        void getAllCustomers(List<DicInfo2> dicInfoList);

        void getCompanyPayOrderDetail(CompanyPayOrderInfo companyPayOrderInfo);

        void saveCompanyPayOrder(BaseEntity baseEntity);

    }

    public interface Presenter extends IBasePresenter<CompanyPayOrderDetailContract.View> {

        void getAllSaleOrders(String companyId, String userId);

        void getAllTradingOrders(String companyId, String userId);

        void getAllCustomers(String companyId);

        void getCompanyPayOrderDetail(String id);

        void saveCompanyPayOrder(String id, String companyId, String userId, String name, String salesOrderId, String tradingOrderId, String customerName, String price, String payTime, String hasInvoice, String hasPrint, String invoiceFiles, String remark);
    }
}
