package com.simplesoft.baselibrary.manager;

import java.io.Serializable;

/**
 * Project：resident_project
 * Author:  guojin
 * Version: 1.0.0
 * Description：
 * Date：2017/7/10 10:59
 * Modification  History:
 * Why & What is modified:
 */
public class RegisterInfo implements Serializable {
    /*"deleted":false,
    "createTime":"2017-07-10 10:57:27",
    "updateTime":"2017-07-10 10:57:27",
    "version":0,
    "id":76,
    "account":"18862153125",
    "password":"111111aa",
    "name":"",
    "userFrom":null,
    "nickName":"xb_1835732510000000",
    "head":"",
    "sfz":"",
    "phone":"18862153125",
    "address":"",
    "description":"",
    "handleTime":"2017-07-10 10:57:27",
    "xqcode":"",
    "xqList":"",
    "ggfwgz":"",
    "sex":0,
    "auth":0,
    "openid":null*/
    public Boolean deleted;
    public String createTime;
    public String updateTime;
    public int version;
    public int id;
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
    public int sex;
    public int auth;
    public String openid;
}
