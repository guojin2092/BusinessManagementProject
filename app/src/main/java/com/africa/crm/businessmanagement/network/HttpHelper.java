package com.africa.crm.businessmanagement.network;

import com.africa.crm.businessmanagement.main.bean.LoginInfoBean;

import io.reactivex.Observable;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 14:37
 * Modification  History:
 * Why & What is modified:
 */
public interface HttpHelper {

    Observable<LoginInfoBean> getLoginInfo(String userName, String passWord);

}