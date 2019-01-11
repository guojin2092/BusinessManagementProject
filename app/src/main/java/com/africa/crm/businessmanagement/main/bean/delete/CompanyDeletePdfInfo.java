package com.africa.crm.businessmanagement.main.bean.delete;

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
public class CompanyDeletePdfInfo {
    @Id(autoincrement = true)
    private Long localId;
    private String id;
    private String createTime;
    private Long createTimeDate;
    private String remark;//备注
    private String editAble;//是否可编辑 1：可编辑 2：不可编辑
    private String name;//文件名
    private String userId;//所属用户ID
    private String code;//文件CODE
    private String companyId;//所属企业ID
    private String companyName;//所属企业名称
    private String userNickName;//所属用户名称

    private boolean chosen = false;
    private boolean isLocal = false;

    @Generated(hash = 2095402296)
    public CompanyDeletePdfInfo(Long localId, String id, String createTime,
            Long createTimeDate, String remark, String editAble, String name,
            String userId, String code, String companyId, String companyName,
            String userNickName, boolean chosen, boolean isLocal) {
        this.localId = localId;
        this.id = id;
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.remark = remark;
        this.editAble = editAble;
        this.name = name;
        this.userId = userId;
        this.code = code;
        this.companyId = companyId;
        this.companyName = companyName;
        this.userNickName = userNickName;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    public CompanyDeletePdfInfo(String id, String createTime,
                                Long createTimeDate, String remark, String editAble, String name,
                                String userId, String code, String companyId, String companyName,
                                String userNickName, boolean chosen, boolean isLocal) {
        this.id = id;
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.remark = remark;
        this.editAble = editAble;
        this.name = name;
        this.userId = userId;
        this.code = code;
        this.companyId = companyId;
        this.companyName = companyName;
        this.userNickName = userNickName;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    @Generated(hash = 1399457939)
    public CompanyDeletePdfInfo() {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getEditAble() {
        return editAble;
    }

    public void setEditAble(String editAble) {
        this.editAble = editAble;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
