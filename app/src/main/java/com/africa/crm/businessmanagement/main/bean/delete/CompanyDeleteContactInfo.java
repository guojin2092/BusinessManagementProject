package com.africa.crm.businessmanagement.main.bean.delete;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class CompanyDeleteContactInfo {
    @Id(autoincrement = true)
    private Long localId;
    private String id;
    private String createTime;
    private String address;//地址
    private String companyName;//所属企业名称
    private String mailAddress;//邮寄地址
    private String remark;//备注
    private String userId;//所属用户ID
    private String fromTypeName;//来源名称
    private String head;//头像
    private String companyId;//所属企业ID
    private String fromType;//来源code
    private String phone;//联系手机
    private String name;//姓名
    private String tel;//联系电话
    private String userNickName;//所属用户昵称
    private String job;//职位
    private String email;//邮箱

    private boolean chosen = false;
    private boolean isLocal = false;

    @Generated(hash = 1256067871)
    public CompanyDeleteContactInfo(Long localId, String id, String createTime,
                                    String address, String companyName, String mailAddress, String remark,
                                    String userId, String fromTypeName, String head, String companyId,
                                    String fromType, String phone, String name, String tel,
                                    String userNickName, String job, String email, boolean chosen,
                                    boolean isLocal) {
        this.localId = localId;
        this.id = id;
        this.createTime = createTime;
        this.address = address;
        this.companyName = companyName;
        this.mailAddress = mailAddress;
        this.remark = remark;
        this.userId = userId;
        this.fromTypeName = fromTypeName;
        this.head = head;
        this.companyId = companyId;
        this.fromType = fromType;
        this.phone = phone;
        this.name = name;
        this.tel = tel;
        this.userNickName = userNickName;
        this.job = job;
        this.email = email;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    public CompanyDeleteContactInfo(String id, String createTime,
                                    String address, String companyName, String mailAddress, String remark,
                                    String userId, String fromTypeName, String head, String companyId,
                                    String fromType, String phone, String name, String tel,
                                    String userNickName, String job, String email, boolean chosen,
                                    boolean isLocal) {
        this.id = id;
        this.createTime = createTime;
        this.address = address;
        this.companyName = companyName;
        this.mailAddress = mailAddress;
        this.remark = remark;
        this.userId = userId;
        this.fromTypeName = fromTypeName;
        this.head = head;
        this.companyId = companyId;
        this.fromType = fromType;
        this.phone = phone;
        this.name = name;
        this.tel = tel;
        this.userNickName = userNickName;
        this.job = job;
        this.email = email;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    @Generated(hash = 46637814)
    public CompanyDeleteContactInfo() {
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

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
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

    public String getFromTypeName() {
        return fromTypeName;
    }

    public void setFromTypeName(String fromTypeName) {
        this.fromTypeName = fromTypeName;
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

    public String getFromType() {
        return fromType;
    }

    public void setFromType(String fromType) {
        this.fromType = fromType;
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

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
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
