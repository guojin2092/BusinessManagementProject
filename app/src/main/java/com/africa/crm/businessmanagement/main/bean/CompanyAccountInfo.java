package com.africa.crm.businessmanagement.main.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/10 0010 13:20
 * Modification  History:
 * Why & What is modified:
 */
@Entity
public class CompanyAccountInfo {
    @Id(autoincrement = true)
    private Long localId;
    private String id;
    private String createTime;
    private String name;//昵称
    private String userName;//用户名
    private String roleId;//角色Id
    private String roleName;//角色名称
    private String companyId;//所属企业ID
    private String head;//头像
    private String companyName;//企业名称
    private String type;//类型
    private String typeName;//分类名称
    private String state;//状态 1：正常 2：禁用
    private String stateName;//状态名称
    private String address;//地址
    private String roleTypeName;//角色分类
    private String phone;//联系电话
    private String roleCode;//角色编码
    private String email;//邮箱
    private String password;

    private boolean chosen = false;
    private boolean isLocal = false;


    @Generated(hash = 1749676467)
    public CompanyAccountInfo(Long localId, String id, String createTime,
            String name, String userName, String roleId, String roleName,
            String companyId, String head, String companyName, String type,
            String typeName, String state, String stateName, String address,
            String roleTypeName, String phone, String roleCode, String email,
            String password, boolean chosen, boolean isLocal) {
        this.localId = localId;
        this.id = id;
        this.createTime = createTime;
        this.name = name;
        this.userName = userName;
        this.roleId = roleId;
        this.roleName = roleName;
        this.companyId = companyId;
        this.head = head;
        this.companyName = companyName;
        this.type = type;
        this.typeName = typeName;
        this.state = state;
        this.stateName = stateName;
        this.address = address;
        this.roleTypeName = roleTypeName;
        this.phone = phone;
        this.roleCode = roleCode;
        this.email = email;
        this.password = password;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    public CompanyAccountInfo(String id, String createTime,
                              String name, String userName, String roleId, String roleName,
                              String companyId, String head, String companyName, String type,
                              String typeName, String state, String stateName, String address,
                              String roleTypeName, String phone, String roleCode, String email,
                              String password, boolean chosen, boolean isLocal) {
        this.id = id;
        this.createTime = createTime;
        this.name = name;
        this.userName = userName;
        this.roleId = roleId;
        this.roleName = roleName;
        this.companyId = companyId;
        this.head = head;
        this.companyName = companyName;
        this.type = type;
        this.typeName = typeName;
        this.state = state;
        this.stateName = stateName;
        this.address = address;
        this.roleTypeName = roleTypeName;
        this.phone = phone;
        this.roleCode = roleCode;
        this.email = email;
        this.password = password;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    @Generated(hash = 1170551434)
    public CompanyAccountInfo() {
    }


    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRoleTypeName() {
        return roleTypeName;
    }

    public void setRoleTypeName(String roleTypeName) {
        this.roleTypeName = roleTypeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
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
}
