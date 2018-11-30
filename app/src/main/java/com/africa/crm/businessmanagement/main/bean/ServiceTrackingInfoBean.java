package com.africa.crm.businessmanagement.main.bean;

import java.io.Serializable;

/**
 * Project：android-heshui
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/13 0013 9:47
 * Modification  History:
 * Why & What is modified:
 */
public class ServiceTrackingInfoBean implements Serializable {
    private String AcceptStation;
    private String AcceptTime;

    public String getAcceptStation() {
        return AcceptStation;
    }

    public void setAcceptStation(String acceptStation) {
        AcceptStation = acceptStation;
    }

    public String getAcceptTime() {
        return AcceptTime;
    }

    public void setAcceptTime(String acceptTime) {
        AcceptTime = acceptTime;
    }
}
