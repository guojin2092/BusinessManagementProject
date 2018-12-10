package com.africa.crm.businessmanagement.main.bean;

import java.io.Serializable;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/9 0009 10:31
 * Modification  History:
 * Why & What is modified:
 */
public class DicInfo2 implements Serializable {
    private String name;
    private String id;

    public DicInfo2(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
