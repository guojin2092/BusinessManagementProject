package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyTradingOrderInfo;
import com.africa.crm.businessmanagement.mvp.presenter.IBasePresenter;
import com.africa.crm.businessmanagement.network.base.BaseView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/17 0017 9:27
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyTradingOrderDetailContract {

    public interface View extends BaseView {

        void getCompanyTradingOrderDetail(CompanyTradingOrderInfo companyTradingOrderInfo);

        void saveCompanyTradingOrder(BaseEntity baseEntity);

    }

    public interface Presenter extends IBasePresenter<CompanyTradingOrderDetailContract.View> {

        void getCompanyTradingOrderDetail(String id);

        void saveCompanyTradingOrder(String id, String companyId, String userId, String name, String customerName, String price, String estimateProfit, String contactName, String possibility, String clueSource, String remark);
    }
}
