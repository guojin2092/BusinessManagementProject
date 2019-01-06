package com.africa.crm.businessmanagement.main.bean;

import java.io.Serializable;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2019/1/2 0002 9:53
 * Modification  History:
 * Why & What is modified:
 */
public class FileInfoBean implements Serializable {
    private String code;

    public FileInfoBean(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
