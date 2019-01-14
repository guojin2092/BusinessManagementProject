package com.africa.crm.businessmanagement.main.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/9 0009 14:12
 * Modification  History:
 * Why & What is modified:
 */
@Entity
public class RoleInfoBean {
    @Id(autoincrement = true)
    private Long localId;
    private String typeName;//分类
    private String id;
    private String orderNum;//排序号
    private String roleCode;//角色编码
    private String roleName;//角色名称

    public RoleInfoBean() {
    }

    public RoleInfoBean(String id, String roleName, String roleCode) {
        this.id = id;
        this.roleName = roleName;
        this.roleCode = roleCode;
    }

    @Generated(hash = 1043232268)
    public RoleInfoBean(Long localId, String typeName, String id, String orderNum,
                        String roleCode, String roleName) {
        this.localId = localId;
        this.typeName = typeName;
        this.id = id;
        this.orderNum = orderNum;
        this.roleCode = roleCode;
        this.roleName = roleName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Long getLocalId() {
        return localId;
    }

    public void setLocalId(Long localId) {
        this.localId = localId;
    }
}
