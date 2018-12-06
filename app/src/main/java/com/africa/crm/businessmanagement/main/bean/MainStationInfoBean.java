package com.africa.crm.businessmanagement.main.bean;

import java.io.Serializable;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/6 0006 13:37
 * Modification  History:
 * Why & What is modified:
 */
public class MainStationInfoBean implements Serializable {
    private long id;
    private long parentId;//父级ID
    private String icon;//图标
    private String ins;//简介
    private String resCode;//模块编码
    private String orderNum;//排序号
    private String resName;//模块名称
    private String type;//1为主菜单 2为二级菜单 3为三级菜单（工作台只返回1）
    private String url;//url地址

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIns() {
        return ins;
    }

    public void setIns(String ins) {
        this.ins = ins;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
