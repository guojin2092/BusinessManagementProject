package com.africa.crm.businessmanagementproject.main.bean;

import java.io.Serializable;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/14 0014 14:50
 * Modification  History:
 * Why & What is modified:
 */
public class WorkStationInfo implements Serializable {
    private String work_type;
    private String work_name;

    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }

    public String getWork_name() {
        return work_name;
    }

    public void setWork_name(String work_name) {
        this.work_name = work_name;
    }
}
