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
public class CompanyInfo {
    @Id(autoincrement = true)
    private Long localId;
    private String area;//地区
    private String profession;//行业
    private String code;//编号
    private String address;//地址
    private String numA;//可开账号数量
    private String mid;//企业代码
    private String type;//类型
    private String typeName;//
    private String head;//头像
    private String createTime;
    private String phone;//联系电话
    private String name;//企业名称
    private String id;//企业id
    private String state;//状态 1：正常 2：禁用
    private String stateName;
    private String email;//邮箱

    private boolean chosen = false;
    private boolean isDeleted = false;
    private boolean isLocal = false;



    @Generated(hash = 539495311)
    public CompanyInfo(Long localId, String area, String profession, String code,
            String address, String numA, String mid, String type, String typeName,
            String head, String createTime, String phone, String name, String id,
            String state, String stateName, String email, boolean chosen,
            boolean isDeleted, boolean isLocal) {
        this.localId = localId;
        this.area = area;
        this.profession = profession;
        this.code = code;
        this.address = address;
        this.numA = numA;
        this.mid = mid;
        this.type = type;
        this.typeName = typeName;
        this.head = head;
        this.createTime = createTime;
        this.phone = phone;
        this.name = name;
        this.id = id;
        this.state = state;
        this.stateName = stateName;
        this.email = email;
        this.chosen = chosen;
        this.isDeleted = isDeleted;
        this.isLocal = isLocal;
    }

    @Generated(hash = 1062273323)
    public CompanyInfo() {
    }

    

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNumA() {
        return numA;
    }

    public void setNumA(String numA) {
        this.numA = numA;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }

    public boolean getChosen() {
        return this.chosen;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public boolean getIsLocal() {
        return this.isLocal;
    }

    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

    public boolean getIsDeleted() {
        return this.isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
