package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.CompanyInventoryInfoBean;
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
 * Date：2018/12/12 0012 9:13
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyInventoryContract {

    public interface View extends BaseView {
        void getProductList(List<DicInfo2> dicInfoList);

        void getType(List<DicInfo> dicInfoList);

        void getInventoryList(CompanyInventoryInfoBean companyInventoryInfoBean);

    }

    public interface Presenter extends IBasePresenter<CompanyInventoryContract.View> {
        void getProductList(String companyId);

        void getType(String code);

        void getInventoryList(int page, int rows, String companyId, String productId, String type, String createTimes, String createTimee);

    }
}
