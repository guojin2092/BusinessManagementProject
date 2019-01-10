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
public class CompanyDeleteServiceRecordInfo {
    @Id(autoincrement = true)
    private Long localId;
    private String customerName;//客户名称
    private String createTime;
    private Long createTimeDate;
    private String phone;//电话
    private String remark;//备注
    private String reason;//服务原因
    private String track;//追踪记录
    private String state;//状态
    private String type;//类型
    private String companyName;//所属企业名称
    private String solution;//解决方案
    private String userNickName;//所属用户名称
    private String productId;//产品ID
    private String typeName;//类型名称
    private String id;
    private String level;//优先等级
    private String email;//邮箱
    private String editAble;//是否可编辑 1：可编辑 2：不可编辑
    private String userId;//所属用户ID
    private String name;//服务记录名称
    private String stateName;//状态名称
    private String levelName;//优先等级名称
    private String companyId;//所属企业ID
    private String productName;//产品名称

    private boolean chosen = false;
    private boolean isLocal = false;

    @Generated(hash = 521197234)
    public CompanyDeleteServiceRecordInfo(Long localId, String customerName,
                                          String createTime, Long createTimeDate, String phone, String remark,
                                          String reason, String track, String state, String type,
                                          String companyName, String solution, String userNickName,
                                          String productId, String typeName, String id, String level,
                                          String email, String editAble, String userId, String name,
                                          String stateName, String levelName, String companyId,
                                          String productName, boolean chosen, boolean isLocal) {
        this.localId = localId;
        this.customerName = customerName;
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.phone = phone;
        this.remark = remark;
        this.reason = reason;
        this.track = track;
        this.state = state;
        this.type = type;
        this.companyName = companyName;
        this.solution = solution;
        this.userNickName = userNickName;
        this.productId = productId;
        this.typeName = typeName;
        this.id = id;
        this.level = level;
        this.email = email;
        this.editAble = editAble;
        this.userId = userId;
        this.name = name;
        this.stateName = stateName;
        this.levelName = levelName;
        this.companyId = companyId;
        this.productName = productName;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    public CompanyDeleteServiceRecordInfo(String customerName,
                                          String createTime, Long createTimeDate, String phone, String remark,
                                          String reason, String track, String state, String type,
                                          String companyName, String solution, String userNickName,
                                          String productId, String typeName, String id, String level,
                                          String email, String editAble, String userId, String name,
                                          String stateName, String levelName, String companyId,
                                          String productName, boolean chosen, boolean isLocal) {
        this.customerName = customerName;
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.phone = phone;
        this.remark = remark;
        this.reason = reason;
        this.track = track;
        this.state = state;
        this.type = type;
        this.companyName = companyName;
        this.solution = solution;
        this.userNickName = userNickName;
        this.productId = productId;
        this.typeName = typeName;
        this.id = id;
        this.level = level;
        this.email = email;
        this.editAble = editAble;
        this.userId = userId;
        this.name = name;
        this.stateName = stateName;
        this.levelName = levelName;
        this.companyId = companyId;
        this.productName = productName;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    @Generated(hash = 1684316298)
    public CompanyDeleteServiceRecordInfo() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEditAble() {
        return editAble;
    }

    public void setEditAble(String editAble) {
        this.editAble = editAble;
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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
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
