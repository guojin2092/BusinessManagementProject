package com.africa.crm.businessmanagement.main.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

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
public class CompanySupplierInfo {
    @Id(autoincrement = true)
    private Long localId;
    private String id;//供应商id
    private String area;//地区
    private String zipCode;//邮编
    private String address;//地址
    private String companyName;//所属企业名称
    private String typeName;
    private String remark;//备注
    private String type;//类型
    private String head;//头像
    private String companyId;//所属企业id
    private String createTime;
    private String phone;//联系电话
    private String name;//供应商名称
    private String email;//邮箱

    private boolean chosen = false;
    private boolean isLocal = false;


    @Generated(hash = 832913479)
    public CompanySupplierInfo(Long localId, String id, String area, String zipCode,
                               String address, String companyName, String typeName, String remark,
                               String type, String head, String companyId, String createTime,
                               String phone, String name, String email, boolean chosen,
                               boolean isLocal) {
        this.localId = localId;
        this.id = id;
        this.area = area;
        this.zipCode = zipCode;
        this.address = address;
        this.companyName = companyName;
        this.typeName = typeName;
        this.remark = remark;
        this.type = type;
        this.head = head;
        this.companyId = companyId;
        this.createTime = createTime;
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    public CompanySupplierInfo(String id, String area, String zipCode,
                               String address, String companyName, String typeName, String remark,
                               String type, String head, String companyId, String createTime,
                               String phone, String name, String email, boolean chosen,
                               boolean isLocal) {
        this.id = id;
        this.area = area;
        this.zipCode = zipCode;
        this.address = address;
        this.companyName = companyName;
        this.typeName = typeName;
        this.remark = remark;
        this.type = type;
        this.head = head;
        this.companyId = companyId;
        this.createTime = createTime;
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    @Generated(hash = 435056888)
    public CompanySupplierInfo() {
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
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
