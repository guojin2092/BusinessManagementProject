package com.africa.crm.businessmanagement.station.bean;

import java.io.Serializable;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/25 0025 18:30
 * Modification  History:
 * Why & What is modified:
 */
public class AuthInfoBean implements Serializable {
    private String authType;

    public String getAuthType() {
        return authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }
}
