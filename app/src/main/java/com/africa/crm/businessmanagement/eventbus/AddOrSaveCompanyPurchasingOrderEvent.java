package com.africa.crm.businessmanagement.eventbus;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/10 0010 16:59
 * Modification  History:
 * Why & What is modified:
 */
public class AddOrSaveCompanyPurchasingOrderEvent {
    private String msg;

    public AddOrSaveCompanyPurchasingOrderEvent(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
