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
public class CompanyExpenditureInfo {
    @Id(autoincrement = true)
    private Long localId;
    private String id;
    private String startDate;//开始日期（2.列表显示）
    private String createTime;
    private Long createTimeDate;
    private String actualPrice;//实际金额（3.列表显示）
    private String title;//标题
    private String remark;//备注
    private String userId;//操作人ID
    private String estimatePrice;//预算金额（3.列表显示）
    private String endDate;//结束日期（2.列表显示）
    private String companyId;//所属企业ID
    private String companyName;//所属企业名称
    private String userNickName;//操作人名称（1.列表显示，替换UI上的备注和时间类型）

    @Generated(hash = 474900949)
    public CompanyExpenditureInfo(Long localId, String id, String startDate,
            String createTime, Long createTimeDate, String actualPrice,
            String title, String remark, String userId, String estimatePrice,
            String endDate, String companyId, String companyName,
            String userNickName) {
        this.localId = localId;
        this.id = id;
        this.startDate = startDate;
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.actualPrice = actualPrice;
        this.title = title;
        this.remark = remark;
        this.userId = userId;
        this.estimatePrice = estimatePrice;
        this.endDate = endDate;
        this.companyId = companyId;
        this.companyName = companyName;
        this.userNickName = userNickName;
    }

    @Generated(hash = 1767452927)
    public CompanyExpenditureInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getEstimatePrice() {
        return estimatePrice;
    }

    public void setEstimatePrice(String estimatePrice) {
        this.estimatePrice = estimatePrice;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
}
