package com.africa.crm.businessmanagement.bean;

import java.io.Serializable;

/**
 * Project：android-heshui
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/21 0021 15:09
 * Modification  History:
 * Why & What is modified:
 */
public class GoodAlertBean implements Serializable {
    private boolean alert;
    private String count;

    public boolean isAlert() {
        return alert;
    }

    public void setAlert(boolean alert) {
        this.alert = alert;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
