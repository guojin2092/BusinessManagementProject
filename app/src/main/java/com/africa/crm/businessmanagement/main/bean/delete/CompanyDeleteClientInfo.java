package com.africa.crm.businessmanagement.main.bean.delete;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/14 0014 16:21
 * Modification  History:
 * Why & What is modified:
 */
@Entity
public class CompanyDeleteClientInfo {
    @Id(autoincrement = true)
    private Long localId;
    private String id;
    private String createTime;
    private String industryName;//行业名称
    private String remark;//备注
    private String workerNum;//员工数量
    private String tel;//联系电话
    private String companyName;//所属企业名称
    private String address;//地址
    private String yearIncome;//年收入
    private String userId;//所属用户ID
    private String userNickName;//所属用户名称
    private String name;//名称
    private String companyId;//所属企业ID
    private String head;//头像
    private String industry;//行业CODE

    private boolean chosen = false;
    private boolean isLocal = false;

    @Generated(hash = 2115662772)
    public CompanyDeleteClientInfo(Long localId, String id, String createTime,
                                   String industryName, String remark, String workerNum, String tel,
                                   String companyName, String address, String yearIncome, String userId,
                                   String userNickName, String name, String companyId, String head,
                                   String industry, boolean chosen, boolean isLocal) {
        this.localId = localId;
        this.id = id;
        this.createTime = createTime;
        this.industryName = industryName;
        this.remark = remark;
        this.workerNum = workerNum;
        this.tel = tel;
        this.companyName = companyName;
        this.address = address;
        this.yearIncome = yearIncome;
        this.userId = userId;
        this.userNickName = userNickName;
        this.name = name;
        this.companyId = companyId;
        this.head = head;
        this.industry = industry;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    public CompanyDeleteClientInfo(String id, String createTime,
                                   String industryName, String remark, String workerNum, String tel,
                                   String companyName, String address, String yearIncome, String userId,
                                   String userNickName, String name, String companyId, String head,
                                   String industry, boolean chosen, boolean isLocal) {
        this.id = id;
        this.createTime = createTime;
        this.industryName = industryName;
        this.remark = remark;
        this.workerNum = workerNum;
        this.tel = tel;
        this.companyName = companyName;
        this.address = address;
        this.yearIncome = yearIncome;
        this.userId = userId;
        this.userNickName = userNickName;
        this.name = name;
        this.companyId = companyId;
        this.head = head;
        this.industry = industry;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    @Generated(hash = 1238558823)
    public CompanyDeleteClientInfo() {
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

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWorkerNum() {
        return workerNum;
    }

    public void setWorkerNum(String workerNum) {
        this.workerNum = workerNum;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getYearIncome() {
        return yearIncome;
    }

    public void setYearIncome(String yearIncome) {
        this.yearIncome = yearIncome;
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

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
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
