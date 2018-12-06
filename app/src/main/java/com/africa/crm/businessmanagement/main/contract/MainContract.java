package com.africa.crm.businessmanagement.main.contract;

import com.africa.crm.businessmanagement.main.bean.MainStationInfoBean;
import com.africa.crm.businessmanagement.mvp.presenter.IBasePresenter;
import com.africa.crm.businessmanagement.network.base.BaseView;

import java.util.List;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/6 0006 13:35
 * Modification  History:
 * Why & What is modified:
 */
public class MainContract {

    public interface View extends BaseView {

        void getMainStationInfo(List<MainStationInfoBean> mainStationInfoBeanList);
    }

    public interface Presenter extends IBasePresenter<View> {
        void getMainStationInfo(String id);
    }
}
