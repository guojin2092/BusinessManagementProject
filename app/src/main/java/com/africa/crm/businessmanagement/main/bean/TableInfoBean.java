package com.africa.crm.businessmanagement.main.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/15 0015 13:57
 * Modification  History:
 * Why & What is modified:
 */
public class TableInfoBean implements Serializable {
    private List<TableInfo> content;

    public TableInfoBean(List<TableInfo> content) {
        this.content = content;
    }

    public List<TableInfo> getContent() {
        return content;
    }

    public void setContent(List<TableInfo> content) {
        this.content = content;
    }
}
