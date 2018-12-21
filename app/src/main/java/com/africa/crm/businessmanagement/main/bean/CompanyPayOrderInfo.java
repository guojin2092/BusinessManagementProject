package com.africa.crm.businessmanagement.main.bean;

import java.io.Serializable;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/19 0019 9:09
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyPayOrderInfo implements Serializable {
    private String hasPrint;//是否打印CODE  字典项：SF
    private String customerName;//客户名称
    private String createTime;
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
    private boolean chosen;

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
}
