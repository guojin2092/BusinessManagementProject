package com.africa.crm.businessmanagement.network.api;

import com.africa.crm.businessmanagement.network.retrofit.RetrofitUrlManager;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 15:08
 * Modification  History:
 * Why & What is modified:
 */
public class ApiConfig {
    public static final String BASE_URL = "http://www.baidu.com";//外网地址
//    public static final String BASE_URL = "http://c.sushimt.com/SYCRMPC/api/";//外网地址

    //    public static final String BASE_URL = "http://15p66g2561.51mypc.cn/SYCRMPC/api/";//测试地址

    public static final String IMG_URL = RetrofitUrlManager.getInstance().getGlobalDomain().toString() + "defile/file/image/";//图片加载地址
}
