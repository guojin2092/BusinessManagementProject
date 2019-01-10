package com.africa.crm.businessmanagement.main.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/17 0017 15:39
 * Modification  History:
 * Why & What is modified:
 */
@Entity
public class CompanyInventoryInfo {
    @Id(autoincrement = true)
    private Long localId;
    private String typeName;//操作类型名称
    private String id;
    private String createTime;
    private Long createTimeDate;
    private String num;//数量
    private String remark;//备注
    private String numAfter;//操作后数量
    private String numBefore;//操作前数量
    private String companyId;//所属企业ID
    private String type;//操作类型CODE
    private String companyName;//所属企业名称
    private String productName;//产品名称
    private String productId;//产品ID

    private boolean isLocal = false;

    @Generated(hash = 1644696497)
    public CompanyInventoryInfo(Long localId, String typeName, String id,
            String createTime, Long createTimeDate, String num, String remark,
            String numAfter, String numBefore, String companyId, String type,
            String companyName, String productName, String productId,
            boolean isLocal) {
        this.localId = localId;
        this.typeName = typeName;
        this.id = id;
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.num = num;
        this.remark = remark;
        this.numAfter = numAfter;
        this.numBefore = numBefore;
        this.companyId = companyId;
        this.type = type;
        this.companyName = companyName;
        this.productName = productName;
        this.productId = productId;
        this.isLocal = isLocal;
    }

    public CompanyInventoryInfo(String typeName, String id,
                                String createTime, Long createTimeDate, String num, String remark,
                                String numAfter, String numBefore, String companyId, String type,
                                String companyName, String productName, String productId,
                                boolean isLocal) {
        this.typeName = typeName;
        this.id = id;
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.num = num;
        this.remark = remark;
        this.numAfter = numAfter;
        this.numBefore = numBefore;
        this.companyId = companyId;
        this.type = type;
        this.companyName = companyName;
        this.productName = productName;
        this.productId = productId;
        this.isLocal = isLocal;
    }

    @Generated(hash = 277871266)
    public CompanyInventoryInfo() {
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNumAfter() {
        return numAfter;
    }

    public void setNumAfter(String numAfter) {
        this.numAfter = numAfter;
    }

    public String getNumBefore() {
        return numBefore;
    }

    public void setNumBefore(String numBefore) {
        this.numBefore = numBefore;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public boolean getIsLocal() {
        return this.isLocal;
    }

    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }
}
