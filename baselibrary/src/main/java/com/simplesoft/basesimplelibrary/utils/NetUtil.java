/*
 *
 * Copyright 2015 TedXiong xiong-wei@hotmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.simplesoft.baselibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * 网络连接的一些工具类
 */
public class NetUtil {
    public static final int NETWORK_TYPE_NONE = 0;
    public static final int NETWORK_TYPE_2G = 2;
    public static final int NETWORK_TYPE_3G = 3;
    public static final int NETWORK_TYPE_4G = 4;
    public static final int NETWORK_TYPE_WIFI = 10;

    /**
     * 判断当前网络是否可用
     */
    public static boolean isNetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
    }

    /**
     * 判断WIFI是否使用
     */
    public static boolean isWIFIActivate(Context context) {
        return ((WifiManager) context.getSystemService(Context.WIFI_SERVICE))
                .isWifiEnabled();
    }


    /**
     * 修改WIFI状态
     *
     * @param status true为开启WIFI，false为关闭WIFI
     */
    public static void changeWIFIStatus(Context context, boolean status) {
        ((WifiManager) context.getSystemService(Context.WIFI_SERVICE))
                .setWifiEnabled(status);
    }

    /**
     * 判断网络类型
     */
    public static int getNetWorkType(Context context) {
        ConnectivityManager connectMgr = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectMgr.getActiveNetworkInfo();
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                switch (info.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_CDMA:  // telcom
                    case TelephonyManager.NETWORK_TYPE_1xRTT: // telecom
                    case TelephonyManager.NETWORK_TYPE_GPRS:  // unicom
                    case TelephonyManager.NETWORK_TYPE_EDGE:  // cmcc
                        return NETWORK_TYPE_2G;
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  // telecom
                    case TelephonyManager.NETWORK_TYPE_EVDO_0: // telecom
                    case TelephonyManager.NETWORK_TYPE_EVDO_A: // telecom 3.5G
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: // telecom 3.5G
                    case TelephonyManager.NETWORK_TYPE_HSPA:   // unicom
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  // unicom
                    case TelephonyManager.NETWORK_TYPE_HSDPA:  // unicom 3.5G
                    case TelephonyManager.NETWORK_TYPE_HSUPA:  // unicom 3.5G
                    case TelephonyManager.NETWORK_TYPE_UMTS:   // unicom
                        return NETWORK_TYPE_3G;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return NETWORK_TYPE_4G;
                }
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return NETWORK_TYPE_WIFI;
            }
        }
        return NETWORK_TYPE_NONE;
    }
}
