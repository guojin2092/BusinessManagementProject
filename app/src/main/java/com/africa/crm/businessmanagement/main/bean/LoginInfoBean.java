package com.africa.crm.businessmanagement.main.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/23 0023 10:02
 * Modification  History:
 * Why & What is modified:
 */
public class LoginInfoBean implements Serializable {
    private long id;//用户Id
    private String updateTime;//更新日期
    private boolean isdeleted;//是否删除
    private List<Roles> roles;//角色权限
    private String userName;//用户名
    private String companyId;//所属公司ID
    private String type;//用户类型，1：系统管理员，2：企业用户
    private String passWord;//密码
    private int version;//版本号
    private String createTime;//创建时间
    private String phone;//电话号
    private String email;//电子邮件

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public boolean isIsdeleted() {
        return isdeleted;
    }

    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }

    public List<Roles> getRoles() {
        return roles;
    }

    public void setRoles(List<Roles> roles) {
        this.roles = roles;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
