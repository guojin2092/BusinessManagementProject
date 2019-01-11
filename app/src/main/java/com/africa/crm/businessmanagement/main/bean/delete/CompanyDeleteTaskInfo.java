package com.africa.crm.businessmanagement.main.bean.delete;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/19 0019 15:09
 * Modification  History:
 * Why & What is modified:
 */
@Entity
public class CompanyDeleteTaskInfo {
    @Id(autoincrement = true)
    private Long localId;
    private String customerName;//客户名称
    private String createTime;
    private Long createTimeDate;
    private String remark;//备注
    private String state;//状态CODE（字典项：SALEORDERSTATE）
    private String companyName;//所属企业名称
    private String userNickName;//所属用户名称
    private String id;
    private String level;//优先等级CODE
    private String remindTime;//提醒时间  yyyy-MM-dd HH:mm格式
    private String contactName;//联系人名称
    private String userId;//所属用户ID
    private String name;//销售单名称
    private String stateName;//状态名称
    private String levelName;//优先等级名称
    private String companyId;//所属企业ID
    private String hasRemind;//是否已提醒  1：已提醒  2：未提醒

    private boolean chosen = false;
    private boolean isLocal = false;

    @Generated(hash = 2117526426)
    public CompanyDeleteTaskInfo(Long localId, String customerName,
            String createTime, Long createTimeDate, String remark, String state,
            String companyName, String userNickName, String id, String level,
            String remindTime, String contactName, String userId, String name,
            String stateName, String levelName, String companyId, String hasRemind,
            boolean chosen, boolean isLocal) {
        this.localId = localId;
        this.customerName = customerName;
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.remark = remark;
        this.state = state;
        this.companyName = companyName;
        this.userNickName = userNickName;
        this.id = id;
        this.level = level;
        this.remindTime = remindTime;
        this.contactName = contactName;
        this.userId = userId;
        this.name = name;
        this.stateName = stateName;
        this.levelName = levelName;
        this.companyId = companyId;
        this.hasRemind = hasRemind;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    public CompanyDeleteTaskInfo(String customerName,
                                 String createTime, Long createTimeDate, String remark, String state,
                                 String companyName, String userNickName, String id, String level,
                                 String remindTime, String contactName, String userId, String name,
                                 String stateName, String levelName, String companyId, String hasRemind,
                                 boolean chosen, boolean isLocal) {
        this.customerName = customerName;
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.remark = remark;
        this.state = state;
        this.companyName = companyName;
        this.userNickName = userNickName;
        this.id = id;
        this.level = level;
        this.remindTime = remindTime;
        this.contactName = contactName;
        this.userId = userId;
        this.name = name;
        this.stateName = stateName;
        this.levelName = levelName;
        this.companyId = companyId;
        this.hasRemind = hasRemind;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    @Generated(hash = 2012487316)
    public CompanyDeleteTaskInfo() {
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public void setRemindTime(String remindTime) {
        this.remindTime = remindTime;
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

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getHasRemind() {
        return hasRemind;
    }

    public void setHasRemind(String hasRemind) {
        this.hasRemind = hasRemind;
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
}
