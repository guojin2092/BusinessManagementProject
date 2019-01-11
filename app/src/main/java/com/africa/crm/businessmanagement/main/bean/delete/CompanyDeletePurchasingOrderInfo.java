package com.africa.crm.businessmanagement.main.bean.delete;

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
public class CompanyDeletePurchasingOrderInfo {
    @Id(autoincrement = true)
    private Long localId;
    private String createTime;
    private Long createTimeDate;
    private String supplierName;//供应商名称
    private String sendAddress;//发货地址
    private String remark;//备注
    private String orderDate;//订单日期 yyyy-MM-dd格式
    private String state;//状态CODE
    private String code;//编号，新增时后台自动生成
    private String companyName;//所属企业名称
    private String userNickName;//所属用户名称
    private String id;
    private String arriveDate;//到达日期 yyyy-MM-dd格式
    private String editAble;//是否可编辑 1：可编辑 2：不可编辑
    private String destinationAddress;//收货地址
    private String sendAddressZipCode;//发货地址邮编
    private String userId;//所属用户ID
    private String name;//采购单名称
    private String stateName;//状态名称
    private String companyId;//所属企业ID
    private String products;//产品子列表
    private String clause;//条款及条件
    private String destinationAddressZipCode;//收货地址邮编

    private boolean chosen = false;
    private boolean isLocal = false;

    @Generated(hash = 2034934918)
    public CompanyDeletePurchasingOrderInfo(Long localId, String createTime,
            Long createTimeDate, String supplierName, String sendAddress,
            String remark, String orderDate, String state, String code,
            String companyName, String userNickName, String id, String arriveDate,
            String editAble, String destinationAddress, String sendAddressZipCode,
            String userId, String name, String stateName, String companyId,
            String products, String clause, String destinationAddressZipCode,
            boolean chosen, boolean isLocal) {
        this.localId = localId;
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.supplierName = supplierName;
        this.sendAddress = sendAddress;
        this.remark = remark;
        this.orderDate = orderDate;
        this.state = state;
        this.code = code;
        this.companyName = companyName;
        this.userNickName = userNickName;
        this.id = id;
        this.arriveDate = arriveDate;
        this.editAble = editAble;
        this.destinationAddress = destinationAddress;
        this.sendAddressZipCode = sendAddressZipCode;
        this.userId = userId;
        this.name = name;
        this.stateName = stateName;
        this.companyId = companyId;
        this.products = products;
        this.clause = clause;
        this.destinationAddressZipCode = destinationAddressZipCode;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    public CompanyDeletePurchasingOrderInfo(String createTime,
                                            Long createTimeDate, String supplierName, String sendAddress,
                                            String remark, String orderDate, String state, String code,
                                            String companyName, String userNickName, String id, String arriveDate,
                                            String editAble, String destinationAddress, String sendAddressZipCode,
                                            String userId, String name, String stateName, String companyId,
                                            String products, String clause, String destinationAddressZipCode,
                                            boolean chosen, boolean isLocal) {
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.supplierName = supplierName;
        this.sendAddress = sendAddress;
        this.remark = remark;
        this.orderDate = orderDate;
        this.state = state;
        this.code = code;
        this.companyName = companyName;
        this.userNickName = userNickName;
        this.id = id;
        this.arriveDate = arriveDate;
        this.editAble = editAble;
        this.destinationAddress = destinationAddress;
        this.sendAddressZipCode = sendAddressZipCode;
        this.userId = userId;
        this.name = name;
        this.stateName = stateName;
        this.companyId = companyId;
        this.products = products;
        this.clause = clause;
        this.destinationAddressZipCode = destinationAddressZipCode;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    @Generated(hash = 1093998687)
    public CompanyDeletePurchasingOrderInfo() {
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
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
