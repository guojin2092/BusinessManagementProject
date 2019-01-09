package com.africa.crm.businessmanagement.main.bean.delete;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/19 0019 9:09
 * Modification  History:
 * Why & What is modified:
 */
@Entity
public class CompanyDeleteTradingOrderInfo {
    @Id(autoincrement = true)
    private Long localId;
    private String customerName;//客户名称
    private Long createTimeDate;
    private String createTime;
    private String remark;//备注
    private String clueSource;//线索来源
    private String possibility;//可能性
    private String companyName;//所属企业名称
    private String userNickName;//所属用户名称
    private String id;
    private String estimateProfit;//预计收益
    private String price;//金额
    private String editAble;//是否可编辑 1：可编辑 2：不可编辑
    private String contactName;//联系人名称
    private String userId;//所属用户ID
    private String name;//交易单名称
    private String companyId;//所属企业ID

    private boolean chosen = false;
    private boolean isLocal = false;


    @Generated(hash = 1938348128)
    public CompanyDeleteTradingOrderInfo(Long localId, String customerName,
                                         Long createTimeDate, String createTime, String remark,
                                         String clueSource, String possibility, String companyName,
                                         String userNickName, String id, String estimateProfit, String price,
                                         String editAble, String contactName, String userId, String name,
                                         String companyId, boolean chosen, boolean isLocal) {
        this.localId = localId;
        this.customerName = customerName;
        this.createTimeDate = createTimeDate;
        this.createTime = createTime;
        this.remark = remark;
        this.clueSource = clueSource;
        this.possibility = possibility;
        this.companyName = companyName;
        this.userNickName = userNickName;
        this.id = id;
        this.estimateProfit = estimateProfit;
        this.price = price;
        this.editAble = editAble;
        this.contactName = contactName;
        this.userId = userId;
        this.name = name;
        this.companyId = companyId;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    public CompanyDeleteTradingOrderInfo(String customerName,
                                         Long createTimeDate, String createTime, String remark,
                                         String clueSource, String possibility, String companyName,
                                         String userNickName, String id, String estimateProfit, String price,
                                         String editAble, String contactName, String userId, String name,
                                         String companyId, boolean chosen, boolean isLocal) {
        this.customerName = customerName;
        this.createTimeDate = createTimeDate;
        this.createTime = createTime;
        this.remark = remark;
        this.clueSource = clueSource;
        this.possibility = possibility;
        this.companyName = companyName;
        this.userNickName = userNickName;
        this.id = id;
        this.estimateProfit = estimateProfit;
        this.price = price;
        this.editAble = editAble;
        this.contactName = contactName;
        this.userId = userId;
        this.name = name;
        this.companyId = companyId;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    @Generated(hash = 1654982628)
    public CompanyDeleteTradingOrderInfo() {
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getClueSource() {
        return clueSource;
    }

    public void setClueSource(String clueSource) {
        this.clueSource = clueSource;
    }

    public String getPossibility() {
        return possibility;
    }

    public void setPossibility(String possibility) {
        this.possibility = possibility;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEstimateProfit() {
        return estimateProfit;
    }

    public void setEstimateProfit(String estimateProfit) {
        this.estimateProfit = estimateProfit;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEditAble() {
        return editAble;
    }

    public void setEditAble(String editAble) {
        this.editAble = editAble;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
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

    public Long getCreateTimeDate() {
        return createTimeDate;
    }

    public void setCreateTimeDate(Long createTimeDate) {
        this.createTimeDate = createTimeDate;
    }
}
