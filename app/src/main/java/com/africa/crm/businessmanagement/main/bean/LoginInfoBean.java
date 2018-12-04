package com.africa.crm.businessmanagement.main.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/23 0023 10:02
 * Modification  History:
 * Why & What is modified:
 */
@Entity
public class LoginInfoBean {
    private int id;//用户Id
    private String createTime;//创建时间
    private String phone;//电话号
    private String email;//电子邮件
    private String updateTime;//更新日期
    private boolean isdeleted;//是否删除
    private String userName;//用户名
    private String companyId;//所属公司ID
    private String type;//用户类型，1：系统管理员，2：企业用户
    private String passWord;//密码
    private int version;//版本号
    @Generated(hash = 488650808)
    public LoginInfoBean(int id, String createTime, String phone, String email,
            String updateTime, boolean isdeleted, String userName, String companyId,
            String type, String passWord, int version) {
        this.id = id;
        this.createTime = createTime;
        this.phone = phone;
        this.email = email;
        this.updateTime = updateTime;
        this.isdeleted = isdeleted;
        this.userName = userName;
        this.companyId = companyId;
        this.type = type;
        this.passWord = passWord;
        this.version = version;
    }
    @Generated(hash = 410655696)
    public LoginInfoBean() {
    }
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return this.email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getUpdateTime() {
        return this.updateTime;
    }
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
    public boolean getIsdeleted() {
        return this.isdeleted;
    }
    public void setIsdeleted(boolean isdeleted) {
        this.isdeleted = isdeleted;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getCompanyId() {
        return this.companyId;
    }
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
    public String getType() {
        return this.type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getPassWord() {
        return this.passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    public int getVersion() {
        return this.version;
    }
    public void setVersion(int version) {
        this.version = version;
    }

}
