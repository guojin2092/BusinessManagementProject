package com.africa.crm.businessmanagement.main.bean.delete;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/7 0007 12:59
 * Modification  History:
 * Why & What is modified:
 */
@Entity
public class CompanyUserDeleteInfoBean {
    @Id(autoincrement = true)
    private Long localId;
    private String id;
    private String head;//头像
    private String companyId;//所属公司ID
    private String createTime;
    private Long createTimeDate;
    private String companyName;//所属公司名称
    private String name;//昵称
    private String roleName;//角色名称
    private String state;//1：正常，2：禁用
    private String stateName;
    private String userName;//用户名
    private String type;//1：系统用户，2：企业用户
    private String typeName;
    private String password;

    private String address;//地址
    private String phone;//电话
    private String email;//邮箱
    private String roleId;//角色Id
    private String roleTypeName;//角色类型
    private String roleCode;//角色Code root:超级管理员，companyRoot:企业管理员，companySales:企业销售员

    private boolean chosen = false;
    private boolean isLocal = false;


    @Generated(hash = 194954524)
    public CompanyUserDeleteInfoBean(Long localId, String id, String head,
            String companyId, String createTime, Long createTimeDate,
            String companyName, String name, String roleName, String state,
            String stateName, String userName, String type, String typeName,
            String password, String address, String phone, String email,
            String roleId, String roleTypeName, String roleCode, boolean chosen,
            boolean isLocal) {
        this.localId = localId;
        this.id = id;
        this.head = head;
        this.companyId = companyId;
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.companyName = companyName;
        this.name = name;
        this.roleName = roleName;
        this.state = state;
        this.stateName = stateName;
        this.userName = userName;
        this.type = type;
        this.typeName = typeName;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.roleId = roleId;
        this.roleTypeName = roleTypeName;
        this.roleCode = roleCode;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    public CompanyUserDeleteInfoBean(String id, String head,
                                     String companyId, String createTime, Long createTimeDate,
                                     String companyName, String name, String roleName, String state,
                                     String stateName, String userName, String type, String typeName,
                                     String password, String address, String phone, String email,
                                     String roleId, String roleTypeName, String roleCode, boolean chosen,
                                     boolean isLocal) {
        this.id = id;
        this.head = head;
        this.companyId = companyId;
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.companyName = companyName;
        this.name = name;
        this.roleName = roleName;
        this.state = state;
        this.stateName = stateName;
        this.userName = userName;
        this.type = type;
        this.typeName = typeName;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.roleId = roleId;
        this.roleTypeName = roleTypeName;
        this.roleCode = roleCode;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    @Generated(hash = 190043160)
    public CompanyUserDeleteInfoBean() {
    }


    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }

    public Long getCreateTimeDate() {
        return createTimeDate;
    }

    public void setCreateTimeDate(Long createTimeDate) {
        this.createTimeDate = createTimeDate;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public boolean getChosen() {
        return this.chosen;
    }

    public boolean getIsLocal() {
        return this.isLocal;
    }

    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleTypeName() {
        return roleTypeName;
    }

    public void setRoleTypeName(String roleTypeName) {
        this.roleTypeName = roleTypeName;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
