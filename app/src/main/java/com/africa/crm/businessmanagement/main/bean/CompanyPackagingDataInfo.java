package com.africa.crm.businessmanagement.main.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

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
public class CompanyPackagingDataInfo {
    @Id(autoincrement = true)
    private Long localId;
    private String id;
    private String startDate;//开始日期
    private String createTime;//操作时间
    private Long createTimeDate;
    private String num;//总数量
    private String remark;//备注
    private String previewInfo;//预览信息，参考销售单中的products字段
    private String userId;//操作人ID
    private String endDate;//结束日期
    private String companyId;//所属企业ID
    private String companyName;//所属企业名称
    private String userNickName;//操作人名称

    private boolean chosen = false;

    @Generated(hash = 531546869)
    public CompanyPackagingDataInfo(Long localId, String id, String startDate,
            String createTime, Long createTimeDate, String num, String remark,
            String previewInfo, String userId, String endDate, String companyId,
            String companyName, String userNickName, boolean chosen) {
        this.localId = localId;
        this.id = id;
        this.startDate = startDate;
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.num = num;
        this.remark = remark;
        this.previewInfo = previewInfo;
        this.userId = userId;
        this.endDate = endDate;
        this.companyId = companyId;
        this.companyName = companyName;
        this.userNickName = userNickName;
        this.chosen = chosen;
    }

    @Generated(hash = 859395849)
    public CompanyPackagingDataInfo() {
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

    public String getPreviewInfo() {
        return previewInfo;
    }

    public void setPreviewInfo(String previewInfo) {
        this.previewInfo = previewInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public boolean isChosen() {
        return chosen;
    }

    public void setChosen(boolean chosen) {
        this.chosen = chosen;
    }

    public Long getLocalId() {
        return this.localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }

    public Long getCreateTimeDate() {
        return this.createTimeDate;
    }

    public void setCreateTimeDate(Long createTimeDate) {
        this.createTimeDate = createTimeDate;
    }

    public boolean getChosen() {
        return this.chosen;
    }
}
