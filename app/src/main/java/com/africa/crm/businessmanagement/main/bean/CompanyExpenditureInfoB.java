package com.africa.crm.businessmanagement.main.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

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
public class CompanyExpenditureInfoB {
    @Id(autoincrement = true)
    private Long localId;
    private String id;
    private String createTime;
    private Long createTimeDate;
    private String estimateId;//预算ID
    private String price;//金额（3.列表显示）
    private String remark;//备注
    private String userId;//所属用户ID
    private String payDate;//日期 yyyy-MM-dd格式（2.列表显示）
    private String companyId;//所属企业ID
    private String companyName;//所属企业名称
    private String estimateTitle;//预算标题（1.列表显示）
    private String userNickName;//所属用户名称

    private boolean isLocal = false;

    @Generated(hash = 2074690780)
    public CompanyExpenditureInfoB(Long localId, String id, String createTime,
            Long createTimeDate, String estimateId, String price, String remark,
            String userId, String payDate, String companyId, String companyName,
            String estimateTitle, String userNickName, boolean isLocal) {
        this.localId = localId;
        this.id = id;
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.estimateId = estimateId;
        this.price = price;
        this.remark = remark;
        this.userId = userId;
        this.payDate = payDate;
        this.companyId = companyId;
        this.companyName = companyName;
        this.estimateTitle = estimateTitle;
        this.userNickName = userNickName;
        this.isLocal = isLocal;
    }

    public CompanyExpenditureInfoB(String id, String createTime,
                                   Long createTimeDate, String estimateId, String price, String remark,
                                   String userId, String payDate, String companyId, String companyName,
                                   String estimateTitle, String userNickName, boolean isLocal) {
        this.id = id;
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.estimateId = estimateId;
        this.price = price;
        this.remark = remark;
        this.userId = userId;
        this.payDate = payDate;
        this.companyId = companyId;
        this.companyName = companyName;
        this.estimateTitle = estimateTitle;
        this.userNickName = userNickName;
        this.isLocal = isLocal;
    }

    @Generated(hash = 1384333129)
    public CompanyExpenditureInfoB() {
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

    public String getEstimateId() {
        return estimateId;
    }

    public void setEstimateId(String estimateId) {
        this.estimateId = estimateId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getEstimateTitle() {
        return estimateTitle;
    }

    public void setEstimateTitle(String estimateTitle) {
        this.estimateTitle = estimateTitle;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
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
