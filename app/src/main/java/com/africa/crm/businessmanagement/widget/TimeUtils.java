package com.africa.crm.businessmanagement.widget;

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
}
