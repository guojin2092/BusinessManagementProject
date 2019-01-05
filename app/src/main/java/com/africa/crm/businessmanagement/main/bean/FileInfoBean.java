package com.africa.crm.businessmanagement.main.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2019/1/2 0002 9:53
 * Modification  History:
 * Why & What is modified:
 */
@Entity
public class FileInfoBean {
    @Id(autoincrement = true)
    private Long localId;
    private String parentId;
    private String code;

    private boolean isLocal = false;

    @Generated(hash = 1919844194)
    public FileInfoBean(Long localId, String parentId, String code,
            boolean isLocal) {
        this.localId = localId;
        this.parentId = parentId;
        this.code = code;
        this.isLocal = isLocal;
    }

    @Generated(hash = 410787233)
    public FileInfoBean() {
    }

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public boolean getIsLocal() {
        return this.isLocal;
    }

    public void setIsLocal(boolean isLocal) {
        this.isLocal = isLocal;
    }

}
