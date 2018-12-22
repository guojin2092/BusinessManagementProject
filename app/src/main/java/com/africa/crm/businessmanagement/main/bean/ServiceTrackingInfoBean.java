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
    private String step;
    private String time;

    public ServiceTrackingInfoBean(String step, String time) {
        this.step = step;
        this.time = time;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
