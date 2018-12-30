package com.africa.crm.businessmanagement.main.station.contract;

import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPackagingDataInfo;
import com.africa.crm.businessmanagement.main.bean.PreviewInfo;
import com.africa.crm.businessmanagement.mvp.presenter.IBasePresenter;
import com.africa.crm.businessmanagement.network.base.BaseView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/10 0010 14:42
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyPackagingDataDetailContract {

    public interface View extends BaseView {

        void getPreviewInfo(String previewInfo);

        void getPackagingDataDetail(CompanyPackagingDataInfo companyPackagingDataInfo);

        void savePackagingData(BaseEntity baseEntity);

    }

    public interface Presenter extends IBasePresenter<CompanyPackagingDataDetailContract.View> {

        void getPreviewInfo(String companyId, String startDate, String endDate);

        void getPackagingDataDetail(String id);

        void savePackagingData(String companyId, String userId, String startDate, String endDate, String num, String previewInfo, String remark);
    }
}
