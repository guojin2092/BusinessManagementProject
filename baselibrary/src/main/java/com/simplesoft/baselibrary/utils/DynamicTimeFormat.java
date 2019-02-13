package com.simplesoft.baselibrary.utils;

import android.content.Context;
import android.support.annotation.NonNull;

import com.simplesoft.baselibrary.R;

import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 动态时间格式化
 * Created by SCWANG on 2017/6/17.
 */

public class DynamicTimeFormat extends SimpleDateFormat {
    private Context mContext;
    private static Locale locale = Locale.ENGLISH;
    private static String weeks[] = new String[7]; /*= {"周日", "周一", "周二", "周三", "周四", "周五", "周六"}*/
    private static String moments[] = new String[5];/* = {"中午", "凌晨", "早上", "下午", "晚上"}*/

    private String mFormat = "%s";

    public DynamicTimeFormat() {
        this("%s", "yyyy-", "M-d", "HH:mm");
    }

    public DynamicTimeFormat(String format, Context context) {
        this();
        this.mFormat = format;
        this.mContext = context;
    }

    public DynamicTimeFormat(String yearFormat, String dateFormat, String timeFormat) {
        super(String.format(locale, "%s %s %s", yearFormat, dateFormat, timeFormat), locale);
    }

    public DynamicTimeFormat(String format, String yearFormat, String dateFormat, String timeFormat) {
        this(yearFormat, dateFormat, timeFormat);
        this.mFormat = format;
    }

    @Override
    public StringBuffer format(@NonNull Date date, @NonNull StringBuffer toAppendTo, @NonNull FieldPosition pos) {
        weeks[0] = mContext.getString(R.string.Sunday);
        weeks[1] = mContext.getString(R.string.Monday);
        weeks[2] = mContext.getString(R.string.Tuesday);
        weeks[3] = mContext.getString(R.string.Wednesday);
        weeks[4] = mContext.getString(R.string.Thursday);
        weeks[5] = mContext.getString(R.string.Friday);
        weeks[6] = mContext.getString(R.string.Saturday);
        moments[0] = mContext.getString(R.string.noon);
        moments[1] = mContext.getString(R.string.Early_morning);
        moments[2] = mContext.getString(R.string.morning);
        moments[3] = mContext.getString(R.string.afternoon);
        moments[4] = mContext.getString(R.string.night);
        toAppendTo = super.format(date, toAppendTo, pos);

        Calendar otherCalendar = calendar;
        Calendar todayCalendar = Calendar.getInstance();

        int hour = otherCalendar.get(Calendar.HOUR_OF_DAY);

        String[] times = toAppendTo.toString().split(" ");
        String moment = hour == 12 ? moments[0] : moments[hour / 6 + 1];
        String timeFormat = moment + " " + times[2];
        String dateFormat = times[1] + " " + timeFormat;
        String yearFormat = times[0] + dateFormat;
        toAppendTo.delete(0, toAppendTo.length());

        boolean yearTemp = todayCalendar.get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR);
        if (yearTemp) {
            int todayMonth = todayCalendar.get(Calendar.MONTH);
            int otherMonth = otherCalendar.get(Calendar.MONTH);
            if (todayMonth == otherMonth) {//表示是同一个月
                int temp = todayCalendar.get(Calendar.DATE) - otherCalendar.get(Calendar.DATE);
                switch (temp) {
                    case 0:
                        toAppendTo.append(timeFormat);
                        break;
                    case 1:
                        toAppendTo.append("昨天 ");
                        toAppendTo.append(timeFormat);
                        break;
                    case 2:
                        toAppendTo.append("前天 ");
                        toAppendTo.append(timeFormat);
                        break;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                        int dayOfMonth = otherCalendar.get(Calendar.WEEK_OF_MONTH);
                        int todayOfMonth = todayCalendar.get(Calendar.WEEK_OF_MONTH);
                        if (dayOfMonth == todayOfMonth) {//表示是同一周
                            int dayOfWeek = otherCalendar.get(Calendar.DAY_OF_WEEK);
                            if (dayOfWeek != 1) {//判断当前是不是星期日     如想显示为：周日 12:09 可去掉此判断
                                toAppendTo.append(weeks[otherCalendar.get(Calendar.DAY_OF_WEEK) - 1]);
                                toAppendTo.append(' ');
                                toAppendTo.append(timeFormat);
                            } else {
                                toAppendTo.append(dateFormat);
                            }
                        } else {
                            toAppendTo.append(dateFormat);
                        }
                        break;
                    default:
                        toAppendTo.append(dateFormat);
                        break;
                }
            } else {
                toAppendTo.append(dateFormat);
            }
        } else {
            toAppendTo.append(yearFormat);
        }

        int length = toAppendTo.length();
        toAppendTo.append(String.format(locale, mFormat, toAppendTo.toString()));
        toAppendTo.delete(0, length);
        return toAppendTo;
    }

}
