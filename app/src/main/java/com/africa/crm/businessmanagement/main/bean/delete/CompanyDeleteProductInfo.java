package com.africa.crm.businessmanagement.main.bean.delete;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/17 0017 15:39
 * Modification  History:
 * Why & What is modified:
 */
@Entity
public class CompanyDeleteProductInfo {
    @Id(autoincrement = true)
    private Long localId;
    private String createTime;
    private String supplierName;//供应商名称
    private String remark;//备注
    private String stockNum;//库存数量
    private String unitPrice;//单价
    private String code;//编码
    private String companyName;//所属企业名称
    private String type;//分类
    private String warnNum;//警戒数量
    private String id;
    private String typeName;//分类名称
    private String unit;//单位
    private String name;//名称
    private String companyId;//所属企业ID
    private String makerName;//制造商名称

    private boolean chosen;
    private boolean isLocal = false;

    @Generated(hash = 1348269015)
    public CompanyDeleteProductInfo(Long localId, String createTime,
            String supplierName, String remark, String stockNum, String unitPrice,
            String code, String companyName, String type, String warnNum, String id,
            String typeName, String unit, String name, String companyId,
            String makerName, boolean chosen, boolean isLocal) {
        this.localId = localId;
        this.createTime = createTime;
        this.supplierName = supplierName;
        this.remark = remark;
        this.stockNum = stockNum;
        this.unitPrice = unitPrice;
        this.code = code;
        this.companyName = companyName;
        this.type = type;
        this.warnNum = warnNum;
        this.id = id;
        this.typeName = typeName;
        this.unit = unit;
        this.name = name;
        this.companyId = companyId;
        this.makerName = makerName;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    public CompanyDeleteProductInfo(String createTime,
                                    String supplierName, String remark, String stockNum, String unitPrice,
                                    String code, String companyName, String type, String warnNum, String id,
                                    String typeName, String unit, String name, String companyId,
                                    String makerName, boolean chosen, boolean isLocal) {
        this.createTime = createTime;
        this.supplierName = supplierName;
        this.remark = remark;
        this.stockNum = stockNum;
        this.unitPrice = unitPrice;
        this.code = code;
        this.companyName = companyName;
        this.type = type;
        this.warnNum = warnNum;
        this.id = id;
        this.typeName = typeName;
        this.unit = unit;
        this.name = name;
        this.companyId = companyId;
        this.makerName = makerName;
        this.chosen = chosen;
        this.isLocal = isLocal;
    }

    @Generated(hash = 1162138210)
    public CompanyDeleteProductInfo() {
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStockNum() {
        return stockNum;
    }

    public void setStockNum(String stockNum) {
        this.stockNum = stockNum;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWarnNum() {
        return warnNum;
    }

    public void setWarnNum(String warnNum) {
        this.warnNum = warnNum;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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

    public String getMakerName() {
        return makerName;
    }

    public void setMakerName(String makerName) {
        this.makerName = makerName;
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
