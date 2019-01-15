package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.delete.WebInfoBean;
import com.africa.crm.businessmanagement.mvp.presenter.IBasePresenter;
import com.africa.crm.businessmanagement.network.base.BaseView;

import java.util.List;

import okhttp3.ResponseBody;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/12 0012 9:13
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyStatisticalFormWebContract {

    public interface View extends BaseView {

        void getAllCompany(List<DicInfo2> dicInfoList);

        void getAllCompanyUsers(List<DicInfo2> dicInfo2List);

        void getOrderAmountStatisticsReport(WebInfoBean webInfoBean);

        void getOrderAmountStatisticsExport(ResponseBody responseBody);

        void getOrderNumStatisticsReport(WebInfoBean webInfoBean);

        void getOrderNumStatisticsExport(ResponseBody responseBody);

        void getPayRecordStatisticsReport(WebInfoBean webInfoBean);

        void getPayRecordStatisticsExport(ResponseBody responseBody);

        void getServiceStatisticsReport(WebInfoBean webInfoBean);

        void getServiceStatisticsExport(ResponseBody responseBody);

        void getStockStatisticsReport(WebInfoBean webInfoBean);

        void getStockStatisticsExport(ResponseBody responseBody);
    }

    public interface Presenter extends IBasePresenter<CompanyStatisticalFormWebContract.View> {

        void getAllCompany(String name);

        void getAllCompanyUsers(String companyId);

        void getOrderAmountStatisticsReport(String startDate, String endDate, String sfxsysc, String userId, String companyId);

        void getOrderAmountStatisticsExport(String startDate, String endDate, String sfxsysc, String userId, String companyId);

        void getOrderNumStatisticsReport(String startDate, String endDate, String sfxsysc, String userId, String companyId);

        void getOrderNumStatisticsExport(String startDate, String endDate, String sfxsysc, String userId, String companyId);

        void getPayRecordStatisticsReport(String startDate, String endDate, String sfxsysc, String userId, String companyId);

        void getPayRecordStatisticsExport(String startDate, String endDate, String sfxsysc, String userId, String companyId);

        void getServiceStatisticsReport(String startDate, String endDate, String sfxsysc, String userId, String companyId);

        void getServiceStatisticsExport(String startDate, String endDate, String sfxsysc, String userId, String companyId);

        void getStockStatisticsReport(String startDate, String endDate, String sfxsysc, String userId, String companyId);

        void getStockStatisticsExport(String startDate, String endDate, String sfxsysc, String userId, String companyId);
    }
}
