package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyInventoryInfo;
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
 * Date：2018/12/10 0010 14:42
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyInventoryDetailContract {

    public interface View extends BaseView {
        void getAllProduct(List<DicInfo2> dicInfoList);

        void getOperationType(List<DicInfo> dicInfoList);

        void getInventoryDetail(CompanyInventoryInfo companyInventoryInfo);

        void saveInventory(BaseEntity baseEntity);

    }

    public interface Presenter extends IBasePresenter<CompanyInventoryDetailContract.View> {

        void getAllProduct(String companyId);

        void getOperationType(String code);

        void getInventoryDetail(String id);

        void saveInventory(String id, String companyId, String productId, String type, String num, String remark);
    }
}
