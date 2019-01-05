package com.africa.crm.businessmanagement.main.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/9 0009 10:31
 * Modification  History:
 * Why & What is modified:
 */
@Entity
public class DicInfo {
    @Id(autoincrement = true)
    private Long localId;
    private String id;
    private String type;
    private String text;
    private String code;

    public DicInfo(String text, String code) {
        this.text = text;
        this.code = code;
    }

    public DicInfo(String type, String text, String code) {
        this.type = type;
        this.text = text;
        this.code = code;
    }

    public DicInfo(String id, String type, String text, String code) {
        this.id = id;
        this.type = type;
        this.text = text;
        this.code = code;
    }

    @Generated(hash = 449232408)
    public DicInfo(Long localId, String id, String type, String text, String code) {
        this.localId = localId;
        this.id = id;
        this.type = type;
        this.text = text;
        this.code = code;
    }

    @Generated(hash = 863027882)
    public DicInfo() {
    }

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
