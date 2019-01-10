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
public class CompanyDeletePayOrderInfo {
    @Id(autoincrement = true)
    private Long localId;
    private String hasPrint;//是否打印CODE  字典项：SF
    private String customerName;//客户名称
    private String createTime;
    private Long createTimeDate;
    private String hasInvoice;//是否有发票CODE  字典项：SF
    private String remark;//备注
    private String hasInvoiceName;//是否有发票名称
    private String hasPrintName;//是否打印名称
    private String salesOrderId;//销售单
    private String code;//付款单编号（新增时自动生成）
    private String companyName;//所属企业名称
    private String payTime;//付款时间 yyyy-MM-dd HH:mm格式
    private String userNickName;//所属用户名称
    private String id;
    private String price;//付款金额
    private String editAble;//是否可编辑
    private String userId;//所属用户ID
    private String name;//付款单名称
    private String salesOrderName;//销售单名称
    private String invoiceFiles;//发票文件CODE，使用,隔开
    private String companyId;//所属企业ID
    private String tradingOrderId;//交易单ID
    private String tradingOrderName;//交易单名称

    private boolean chosen = false;
    private boolean isLocal = false;

    @Generated(hash = 862846106)
    public CompanyDeletePayOrderInfo(Long localId, String hasPrint,
                                     String customerName, String createTime, Long createTimeDate,
                                     String hasInvoice, String remark, String hasInvoiceName,
                                     String hasPrintName, String salesOrderId, String code,
                                     String companyName, String payTime, String userNickName, String id,
                                     String price, String editAble, String userId, String name,
                                     String salesOrderName, String invoiceFiles, String companyId,
                                     String tradingOrderId, String tradingOrderName, boolean chosen,
                                     boolean isLocal) {
        this.localId = localId;
        this.hasPrint = hasPrint;
        this.customerName = customerName;
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.hasInvoice = hasInvoice;
        this.remark = remark;
        this.hasInvoiceName = hasInvoiceName;
        this.hasPrintName = hasPrintName;
        this.salesOrderId = salesOrderId;
        this.code = code;
        this.companyName = companyName;
        this.payTime = payTime;
        this.userNickName = userNickName;
        this.id = id;
        this.price = price;
        this.editAble = editAble;
        this.userId = userId;
        this.name = name;
        this.salesOrderName = salesOrderName;
        this.invoiceFiles = invoiceFiles;
        this.companyId = companyId;
        this.tradingOrderId = tradingOrderId;
        this.tradingOrderName = tradingOrderName;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    public CompanyDeletePayOrderInfo(String hasPrint,
                                     String customerName, String createTime, Long createTimeDate,
                                     String hasInvoice, String remark, String hasInvoiceName,
                                     String hasPrintName, String salesOrderId, String code,
                                     String companyName, String payTime, String userNickName, String id,
                                     String price, String editAble, String userId, String name,
                                     String salesOrderName, String invoiceFiles, String companyId,
                                     String tradingOrderId, String tradingOrderName, boolean chosen,
                                     boolean isLocal) {
        this.hasPrint = hasPrint;
        this.customerName = customerName;
        this.createTime = createTime;
        this.createTimeDate = createTimeDate;
        this.hasInvoice = hasInvoice;
        this.remark = remark;
        this.hasInvoiceName = hasInvoiceName;
        this.hasPrintName = hasPrintName;
        this.salesOrderId = salesOrderId;
        this.code = code;
        this.companyName = companyName;
        this.payTime = payTime;
        this.userNickName = userNickName;
        this.id = id;
        this.price = price;
        this.editAble = editAble;
        this.userId = userId;
        this.name = name;
        this.salesOrderName = salesOrderName;
        this.invoiceFiles = invoiceFiles;
        this.companyId = companyId;
        this.tradingOrderId = tradingOrderId;
        this.tradingOrderName = tradingOrderName;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    @Generated(hash = 317145584)
    public CompanyDeletePayOrderInfo() {
    }

    public String getHasPrint() {
        return hasPrint;
    }

    public void setHasPrint(String hasPrint) {
        this.hasPrint = hasPrint;
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

    public String getHasInvoice() {
        return hasInvoice;
    }

    public void setHasInvoice(String hasInvoice) {
        this.hasInvoice = hasInvoice;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getHasInvoiceName() {
        return hasInvoiceName;
    }

    public void setHasInvoiceName(String hasInvoiceName) {
        this.hasInvoiceName = hasInvoiceName;
    }

    public String getHasPrintName() {
        return hasPrintName;
    }

    public void setHasPrintName(String hasPrintName) {
        this.hasPrintName = hasPrintName;
    }

    public String getSalesOrderId() {
        return salesOrderId;
    }

    public void setSalesOrderId(String salesOrderId) {
        this.salesOrderId = salesOrderId;
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

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
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

    public String getInvoiceFiles() {
        return invoiceFiles;
    }

    public void setInvoiceFiles(String invoiceFiles) {
        this.invoiceFiles = invoiceFiles;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getTradingOrderId() {
        return tradingOrderId;
    }

    public void setTradingOrderId(String tradingOrderId) {
        this.tradingOrderId = tradingOrderId;
    }

    public String getTradingOrderName() {
        return tradingOrderName;
    }

    public void setTradingOrderName(String tradingOrderName) {
        this.tradingOrderName = tradingOrderName;
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
