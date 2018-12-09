package com.africa.crm.businessmanagement.main.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/9 0009 18:12
 * Modification  History:
 * Why & What is modified:
 */
public class RoleLimitInfoBean implements Serializable {
    private String id;
    private String resName;//模块名称
    private boolean checked;//是否有权限 true为有权限 false为无权限
    private String resCode;//模块编码
    private List<LimitBtnInfo> btns;//模块下的按钮

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public List<LimitBtnInfo> getBtns() {
        return btns;
    }

    public void setBtns(List<LimitBtnInfo> btns) {
        this.btns = btns;
    }
}
