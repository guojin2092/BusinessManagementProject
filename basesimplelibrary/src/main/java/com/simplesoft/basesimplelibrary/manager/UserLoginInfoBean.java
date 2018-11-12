package com.simplesoft.basesimplelibrary.manager;

import com.simplesoft.basesimplelibrary.bean.BaseSimpleBean;

import java.io.Serializable;


/**
 * Created by Administrator on 2016/10/24.
 * 用户登录信息
 */

public class UserLoginInfoBean extends BaseSimpleBean {
    public UserLoginInfos result;

    public class UserLoginInfos implements Serializable {
        public String auth;
        public String realsfz;
        public AccountInfoBean accountInfo;
        public SsdqInfoBean ssdqInfo;
    }
    /**
     * deleted : false
     * createTime : 2017-03-07 13:05:26
     * updateTime : 2017-03-16 10:57:00
     * version : 157
     * id : 1
     * account : yefeitest
     * password : 12345
     * name : null
     * nickName : yefei
     * head : null
     * sfz : 320483199102234413
     * phone : yefeitest
     * handleTime : 2017-03-16 10:57:00
     * xqcode : 32040200901703
     */

    /**
     * sqmc : 盛世名门社区
     * xqmc : 盛世名门
     * sqcode : 320402009017
     * xqcode : 32040200901703
     */


}
