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
public class CompanyDeleteDeliveryOrderInfo {
    @Id(autoincrement = true)
    private Long localId;
    private String createTime;
    private Long createTimeDate;
    private String sendAddress;//发货地址
    private String remark;//备注
    private String salesOrderId;//销售单ID
    private String state;//状态CODE
    private String code;//发货单编号
    private String companyName;//所属企业名称
    private String userNickName;//所属用户名称
    private String id;
    private String logisticsCode;//物流单号
    private String arriveDate;//到达日期
    private String editAble;//是否可编辑 1：可编辑 2：不可编辑
    private String destinationAddress;//收货地址
    private String sendAddressZipCode;//发货地址邮编
    private String stateName;//状态名称
    private String userId;//所属用户ID
    private String name;//发货单名称
    private String salesOrderName;//销售单名称
    private String companyId;//所属企业ID
    private String products;//APP自定义格式，后台保存的一个字段 存入和取出一致
    private String clause;//条款及条件
    private String destinationAddressZipCode;//收货地址邮编

    private boolean chosen = false;
    private boolean isLocal = false;

    @Generated(hash = 1199378878)
    public CompanyDeleteDeliveryOrderInfo(Long localId, String createTime,
                                          Long createTimeDate, String sendAddress, String remark,
                                          String salesOrderId, String state, String code, String companyName,
                                          String userNickName, String id, String logisticsCode, String arriveDate,
                                          String editAble, String destinationAddress, String sendAddressZipCode,
                                          String stateName, String userId, String name, String salesOrderName,
                                          String companyId, String products, String clause,
                                          String destinationAddressZipCode, boolean chosen, boolean isLocal) {
        this.localId = localId;
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.sendAddress = sendAddress;
        this.remark = remark;
        this.salesOrderId = salesOrderId;
        this.state = state;
        this.code = code;
        this.companyName = companyName;
        this.userNickName = userNickName;
        this.id = id;
        this.logisticsCode = logisticsCode;
        this.arriveDate = arriveDate;
        this.editAble = editAble;
        this.destinationAddress = destinationAddress;
        this.sendAddressZipCode = sendAddressZipCode;
        this.stateName = stateName;
        this.userId = userId;
        this.name = name;
        this.salesOrderName = salesOrderName;
        this.companyId = companyId;
        this.products = products;
        this.clause = clause;
        this.destinationAddressZipCode = destinationAddressZipCode;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    public CompanyDeleteDeliveryOrderInfo(String createTime,
                                          Long createTimeDate, String sendAddress, String remark,
                                          String salesOrderId, String state, String code, String companyName,
                                          String userNickName, String id, String logisticsCode, String arriveDate,
                                          String editAble, String destinationAddress, String sendAddressZipCode,
                                          String stateName, String userId, String name, String salesOrderName,
                                          String companyId, String products, String clause,
                                          String destinationAddressZipCode, boolean chosen, boolean isLocal) {
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.sendAddress = sendAddress;
        this.remark = remark;
        this.salesOrderId = salesOrderId;
        this.state = state;
        this.code = code;
        this.companyName = companyName;
        this.userNickName = userNickName;
        this.id = id;
        this.logisticsCode = logisticsCode;
        this.arriveDate = arriveDate;
        this.editAble = editAble;
        this.destinationAddress = destinationAddress;
        this.sendAddressZipCode = sendAddressZipCode;
        this.stateName = stateName;
        this.userId = userId;
        this.name = name;
        this.salesOrderName = salesOrderName;
        this.companyId = companyId;
        this.products = products;
        this.clause = clause;
        this.destinationAddressZipCode = destinationAddressZipCode;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    @Generated(hash = 21290027)
    public CompanyDeleteDeliveryOrderInfo() {
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSendAddress() {
        return sendAddress;
    }

    public void setSendAddress(String sendAddress) {
        this.sendAddress = sendAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(String salesOrderId) {
        this.salesOrderId = salesOrderId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(String arriveDate) {
        this.arriveDate = arriveDate;
    }

    public String getEditAble() {
        return editAble;
    }

    public void setEditAble(String editAble) {
        this.editAble = editAble;
    }

    public String getDestinationAddress() {
        return destinationAddress;
    }

    public void setDestinationAddress(String destinationAddress) {
        this.destinationAddress = destinationAddress;
    }

    public String getSendAddressZipCode() {
        return sendAddressZipCode;
    }

    public void setSendAddressZipCode(String sendAddressZipCode) {
        this.sendAddressZipCode = sendAddressZipCode;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
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

    public String getSalesOrderName() {
        return salesOrderName;
    }

    public void setSalesOrderName(String salesOrderName) {
        this.salesOrderName = salesOrderName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getProducts() {
        return products;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getClause() {
        return clause;
    }

    public void setClause(String clause) {
        this.clause = clause;
    }

    public String getDestinationAddressZipCode() {
        return destinationAddressZipCode;
    }

    public void setDestinationAddressZipCode(String destinationAddressZipCode) {
        this.destinationAddressZipCode = destinationAddressZipCode;
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
