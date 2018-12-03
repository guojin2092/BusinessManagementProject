package com.africa.crm.businessmanagement.baseutil.library.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证EditText的格式
 *
 * @author Administrator
 */
public class EditTextIsValidated {
    /**
     * 正则表达式 判断邮箱格式是否正确
     *
     * @param email
     * @return
     */
    public static boolean isEmail(String email) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * 验证是不是正确的手机号
     *
     * @param str
     * @return
     */
    public static boolean isCellphone(String str) {
        Pattern pattern = Pattern.compile("^[1][3-8]+\\d{9}$");
        Matcher m = pattern.matcher(str);
        return m.matches();
    }

}
