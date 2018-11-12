package com.simplesoft.basesimplelibrary.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Project：resident_project
 * Author:  guojin
 * Version: 1.0.0
 * Description：
 * Date：2017/5/24 16:08
 * Modification  History:
 * Why & What is modified:
 */
public class SimpleUserInfoManager {
    public static final String USER_LOGIN_INFO = "user_login_info";

    public static final String USER_ID = "user_id";//用户id
    public static final String USER_ACCOUNT = "user_account";//账号
    public static final String USER_NAME = "user_name";//真实姓名
    public static final String USER_NICKNAME = "user_nickname";//昵称
    public static final String USER_SFZ = "user_sfz";//身份证(带星号)
    public static final String USER_REAL_SFZ = "user_real_sfz";//身份证(不带星号)
    public static final String USER_PHONE = "user_phone";//联系电话
    public static final String USER_ICON = "user_icon";//头像
    public static final String USER_SEX = "user_sex";//性别
    public static final String USER_OPEN_ID = "user_open_id";//三方登录ID
    public static final String USER_RZXQLIST = "user_rzxqlist";//认证小区
    public static final String THIRD_LOGIN = "third_login";//三方登录
    public static final String USER_AUTH = "auth";//是否实名认证

    /*保存小区信息*/
    public static final String SSDQ = "ssdq";
    public static final String XQCODE = "xqcode";
    public static final String XQMC = "xqmc";
    public static final String SQNAME = "sqname";
    public static final String LAT = "lat";
    public static final String LNG = "lng";


    /**
     * 保存用户登录信息
     */
    public static void saveUserLoginInfo(Context context, UserLoginInfoBean userInfoBean) {
        AccountInfoBean accountInfoBean = userInfoBean.result.accountInfo;
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_LOGIN_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, accountInfoBean.id);
        editor.putString(USER_ACCOUNT, accountInfoBean.account);
        editor.putString(USER_NAME, accountInfoBean.name);
        editor.putString(USER_NICKNAME, accountInfoBean.nickName);
        editor.putString(USER_SFZ, accountInfoBean.sfz);
        editor.putString(USER_REAL_SFZ, userInfoBean.result.realsfz);
        editor.putString(USER_PHONE, accountInfoBean.phone);
        editor.putString(USER_SEX, accountInfoBean.sex);
        editor.putString(USER_ICON, accountInfoBean.head);
        editor.putString(USER_OPEN_ID, accountInfoBean.openid);
        editor.putString(USER_RZXQLIST, accountInfoBean.rzxqlist);
//        editor.putInt(USER_AUTH,accountInfoBean.auth);
        editor.commit();
    }

    /**
     * 保存用户登录信息
     */
    public static void saveUserLoginInfo(Context context, RegisterInfo userInfoBean) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_LOGIN_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_ID, String.valueOf(userInfoBean.id));
        editor.putString(USER_ACCOUNT, userInfoBean.account);
        editor.putString(USER_NAME, userInfoBean.name);
        editor.putString(USER_NICKNAME, userInfoBean.nickName);
        editor.putString(USER_SFZ, userInfoBean.sfz);
        editor.putString(USER_PHONE, userInfoBean.phone);
        editor.putString(USER_SEX, String.valueOf(userInfoBean.sex));
        editor.putString(USER_ICON, userInfoBean.head);
        editor.putString(USER_OPEN_ID, userInfoBean.openid);
        editor.putInt(USER_AUTH, userInfoBean.auth);
        editor.commit();
    }

    /**
     * 获得用户基本信息
     */
    public static AccountInfoBean getUserLoginInfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_LOGIN_INFO, Context.MODE_PRIVATE);
        AccountInfoBean accountInfoBean = new AccountInfoBean();
        accountInfoBean.id = sharedPreferences.getString(USER_ID, "");
        accountInfoBean.account = sharedPreferences.getString(USER_ACCOUNT, "");
        accountInfoBean.name = sharedPreferences.getString(USER_NAME, "");
        accountInfoBean.nickName = sharedPreferences.getString(USER_NICKNAME, "");
        accountInfoBean.sfz = sharedPreferences.getString(USER_SFZ, "");
        accountInfoBean.phone = sharedPreferences.getString(USER_PHONE, "");
        accountInfoBean.sex = sharedPreferences.getString(USER_SEX, "");
        accountInfoBean.head = sharedPreferences.getString(USER_ICON, "");
        accountInfoBean.openid = sharedPreferences.getString(USER_OPEN_ID, "");
        accountInfoBean.auth = sharedPreferences.getInt(USER_AUTH, 0);

        return accountInfoBean;
    }

    /**
     * 根据key获取用户信息对应的values
     */
    public static String getValue(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(USER_LOGIN_INFO,
                Context.MODE_PRIVATE);
        return preferences.getString(key, "");
    }

    /**
     * 根据key保存value值
     *
     * @param context
     * @param key
     * @return
     */
    public static void saveIntValue(Context context, String key, int value) {
        SharedPreferences preferences = context.getSharedPreferences(USER_LOGIN_INFO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * 根据key获取用户信息对应的values
     */
    public static int getIntValue(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(USER_LOGIN_INFO,
                Context.MODE_PRIVATE);
        return preferences.getInt(key, 0);
    }

    /**
     * 根据key保存value值
     *
     * @param context
     * @param key
     * @return
     */
    public static void saveValue(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(USER_LOGIN_INFO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 根据key获取用户信息对应的values
     */
    public static Boolean getBooleanValue(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(USER_LOGIN_INFO,
                Context.MODE_PRIVATE);
        return preferences.getBoolean(key, false);
    }

    /**
     * 根据key保存value值
     *
     * @param context
     * @param key
     * @return
     */
    public static void saveBooleanValue(Context context, String key, Boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(USER_LOGIN_INFO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 删除用户的基本信息
     *
     * @param context
     * @return
     */
    public static void deleteUserInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(USER_LOGIN_INFO,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear().commit();
    }
}
