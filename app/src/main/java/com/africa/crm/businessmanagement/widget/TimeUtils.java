package com.africa.crm.businessmanagement.widget;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/19 0019 13:59
 * Modification  History:
 * Why & What is modified:
 */
public class TimeUtils {

    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getTimeByMinute(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    public static String getTimeByMinute2(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        return format.format(date);
    }

    public static String getCurrentTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }

    public static Date getDataByString(String dateString) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        // String转Date
        try {
            date = format.parse(dateString);  // Thu Jan 18 00:00:00 CST 2007
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Long getDateByCreateTime(String dateString) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        // String转Date
        try {
            date = format.parse(dateString);  // Thu Jan 18 00:00:00 CST 2007
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

    public static Long getDateByStartTime(String dateString) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        // String转Date
        try {
            if (!TextUtils.isEmpty(dateString)) {
                date = format.parse(dateString);  // Thu Jan 18 00:00:00 CST 2007
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(dateString)) {
            return date.getTime();
        } else {
            return 0l;
        }
    }

    public static Long getDateByEndTime(String dateString) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        // String转Date
        try {
            if (!TextUtils.isEmpty(dateString)) {
                date = format.parse(dateString);  // Thu Jan 18 00:00:00 CST 2007
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (!TextUtils.isEmpty(dateString)) {
            return date.getTime();
        } else {
            return Long.MAX_VALUE;
        }
    }
}
