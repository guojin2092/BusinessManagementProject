package com.simplesoft.baselibrary.manager;

import java.io.Serializable;

/**
 * Project：resident_project
 * Author:  guojin
 * Version: 1.0.0
 * Description：
 * Date：2017/5/24 16:43
 * Modification  History:
 * Why & What is modified:
 */
public class AccountInfoBean implements Serializable {
    public boolean deleted;
    public String createTime;
    public String updateTime;
    public String version;
    public String id;
    public String account;
    public String password;
    public String name;
    public String nickName;
    public String head;
    public String sfz;
    public String phone;
    public String address;
    public String description;
    public String handleTime;
    public String xqcode;
    public String xqList;
    public String ggfwgz;
    public String sex;
    public String openid;
    public int auth;//0认证中，1已认证，2认证失败，-1默认
    public String rzxqlist;
}
