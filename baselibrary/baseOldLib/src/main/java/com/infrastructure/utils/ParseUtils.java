package com.infrastructure.utils;

import java.text.DecimalFormat;

/**
 * 文字数字转换工具
 *
 * @author Administrator
 */
public class ParseUtils {
    /**
     * 处理字符串转数字,如果字符串不能强转,则返回0
     *
     * @param parseString 强转字符串
     * @return
     */
    public static int parseInt(String parseString) {
        int parseInt;
        try {
            parseInt = Integer.parseInt(parseString);
        } catch (NumberFormatException exception) {
            parseInt = 0;
        } catch (Exception e) {
            parseInt = 0;
        }
        return parseInt;
    }

    /**
     * 处理字符串转数字,如果字符串不能强转,则返回0
     *
     * @param parseString 强转字符串
     * @return
     */
    public static long parseLong(String parseString) {
        long parseLong;
        try {
            parseLong = Long.parseLong(parseString);
        } catch (NumberFormatException exception) {
            parseLong = 0;
        } catch (Exception e) {
            parseLong = 0;
        }
        return parseLong;
    }

    /**
     * 处理字符串转单精度浮点型,如果字符串不能强转,则返回0
     *
     * @param parseString 强转字符串
     * @return
     */
    public static Float parseFloat(String parseString) {
        Float parseFloat;
        try {
            parseFloat = Float.parseFloat(parseString);
        } catch (NumberFormatException exception) {
            parseFloat = 0.00f;
        } catch (Exception e) {
            parseFloat = 0.00f;
        }
        return parseFloat;
    }

    /**
     * 处理字符串转单精度浮点型,如果字符串不能强转,则返回0
     *
     * @param parseString 强转字符串
     * @return
     */
    public static Double parseDouble(String parseString) {
        Double parseDouble;
        try {
            parseDouble = Double.parseDouble(parseString);
        } catch (NumberFormatException exception) {
            parseDouble = 0.00;
        } catch (Exception e) {
            parseDouble = 0.00;
        }
        return parseDouble;
    }


    /**
     * 整型转字符串
     *
     * @param parseInteger
     * @return
     */
    public static String parseString(int parseInteger) {
        String parseString;
        try {
            parseString = Integer.toString(parseInteger);
        } catch (Exception e) {
            parseString = "";
        }
        return parseString;
    }

    /**
     * 保留小数点后几位
     */
    public static String parseDecimal(String decimal, int count) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < count; i++) {
            if (i == 0) {
                buffer.append(".0");
            } else {
                buffer.append("0");
            }
        }
        DecimalFormat decimalFormat = new DecimalFormat(buffer.toString());//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String result = decimalFormat.format(Float.parseFloat(decimal));

        return result;
    }
}
