package com.simplesoft.baselibrary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Project：haloos-ryoen
 * Author:  alex
 * Version: 1.5.0
 * Description：判断一个控件是否被多次点击
 * Date：2016/10/19 17:33
 * Modification  History:
 * Why & What is modified:
 */
public class CommonUtils {
    private static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < 3000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


    /**
     * @param timeSecond 时间（毫秒）
     * @return
     */
    public static boolean isFastDoubleClick(int timeSecond) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < timeSecond) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 获得版本号的名称
     *
     * @param context
     * @return
     */
    public static String getVersionName(Context context) {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获得Versioncode
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context) {
        PackageInfo pi = null;
        try {
            pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
