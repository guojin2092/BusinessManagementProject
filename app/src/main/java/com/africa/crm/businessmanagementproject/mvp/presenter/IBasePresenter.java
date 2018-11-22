package com.africa.crm.businessmanagementproject.mvp.presenter;

import com.africa.crm.businessmanagementproject.network.base.BaseView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 16:41
 * Modification  History:
 * Why & What is modified:
 */
public interface IBasePresenter<V extends BaseView> {

    /**
     * 粘贴
     * @param view
     */
    void attach(V view);

    /**
     *
     * 取消粘贴
     */
    void detach();
}
