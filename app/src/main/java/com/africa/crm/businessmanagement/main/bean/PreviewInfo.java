package com.africa.crm.businessmanagement.main.bean;

import java.io.Serializable;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/29 0029 16:19
 * Modification  History:
 * Why & What is modified:
 */
public class PreviewInfo implements Serializable {
    private String tradingOrder;//交易单
    private String quotationOrder;//报价单
    private String paymentOrder;//付款单
    private String purchaseOrder;//采购单
    private String invoiceOrder;//发货单
    private String salesOrder;//销售单
    private String total;//总数

    public String getTradingOrder() {
        return tradingOrder;
    }

    public void setTradingOrder(String tradingOrder) {
        this.tradingOrder = tradingOrder;
    }

    public String getQuotationOrder() {
        return quotationOrder;
    }

    public void setQuotationOrder(String quotationOrder) {
        this.quotationOrder = quotationOrder;
    }

    public String getPaymentOrder() {
        return paymentOrder;
    }

    public void setPaymentOrder(String paymentOrder) {
        this.paymentOrder = paymentOrder;
    }

    public String getPurchaseOrder() {
        return purchaseOrder;
    }

    public void setPurchaseOrder(String purchaseOrder) {
        this.purchaseOrder = purchaseOrder;
    }

    public String getInvoiceOrder() {
        return invoiceOrder;
    }

    public void setInvoiceOrder(String invoiceOrder) {
        this.invoiceOrder = invoiceOrder;
    }

    public String getSalesOrder() {
        return salesOrder;
    }

    public void setSalesOrder(String salesOrder) {
        this.salesOrder = salesOrder;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
