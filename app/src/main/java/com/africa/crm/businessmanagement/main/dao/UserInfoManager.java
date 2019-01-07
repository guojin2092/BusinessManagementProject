package com.africa.crm.businessmanagement.main.dao;

import android.content.Context;
import android.content.SharedPreferences;

import com.africa.crm.businessmanagement.main.bean.LoginInfoBean;

/**
 * Project：resident_project
 * Author:  guojin
 * Version: 1.0.0
 * Description：
 * Date：2017/5/24 16:08
 * Modification  History:
 * Why & What is modified:
 */
public class UserInfoManager {
    public static final String USER_LOGIN_INFO = "user_login_info";

    public static final String ID = "id";
    public static final String UPDATE_TIME = "updateTime";
    public static final String IS_DELETED = "isdeleted";
    public static final String USER_NAME = "userName";
    public static final String COMPANY_NAME = "companyName";
    public static final String COMPANY_ID = "companyId";
    public static final String TYPE = "type";
    public static final String PASSWORD = "passWord";
    public static final String VERSION = "version";
    public static final String CREATE_TIME = "createTime";
    public static final String PHONE = "phone";
    public static final String EMAIL = "email";

    public static final String ROLE_ID = "roleId";
    public static final String ROLE_TYPE_NAME = "roleTypeName";
    public static final String ROLE_CODE = "roleCode";
    public static final String ROLE_NAME = "roleName";


    /**
     * 保存用户登录信息
     */
    public static void saveUserLoginInfo(Context context, LoginInfoBean userInfoBean) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_LOGIN_INFO, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(ID, userInfoBean.getId());
        editor.putString(UPDATE_TIME, userInfoBean.getUpdateTime());
        editor.putBoolean(IS_DELETED, userInfoBean.isIsdeleted());
        editor.putString(USER_NAME, userInfoBean.getUserName());
        editor.putString(COMPANY_ID, userInfoBean.getCompanyId());
        editor.putString(COMPANY_NAME, userInfoBean.getCompanyName());
        editor.putString(TYPE, userInfoBean.getType());
        editor.putString(PASSWORD, userInfoBean.getPassWord());
        editor.putInt(VERSION, userInfoBean.getVersion());
        editor.putString(CREATE_TIME, userInfoBean.getCreateTime());
        editor.putString(PHONE, userInfoBean.getPhone());
        editor.putString(EMAIL, userInfoBean.getEmail());
        editor.putString(ROLE_ID, userInfoBean.getRoleId());
        editor.putString(ROLE_CODE, userInfoBean.getRoleCode());
        editor.putString(ROLE_TYPE_NAME, userInfoBean.getRoleTypeName());
        editor.putString(ROLE_NAME, userInfoBean.getRoleName());
        editor.commit();
    }

    /**
     * 获得用户基本信息
     */
    public static LoginInfoBean getUserLoginInfo(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(USER_LOGIN_INFO, Context.MODE_PRIVATE);
        LoginInfoBean loginInfoBean = new LoginInfoBean();
        loginInfoBean.setId(sharedPreferences.getLong(ID, 0));
        loginInfoBean.setUpdateTime(sharedPreferences.getString(UPDATE_TIME, ""));
        loginInfoBean.setIsdeleted(sharedPreferences.getBoolean(IS_DELETED, false));
        loginInfoBean.setUserName(sharedPreferences.getString(USER_NAME, ""));
        loginInfoBean.setCompanyId(sharedPreferences.getString(COMPANY_ID, ""));
        loginInfoBean.setCompanyName(sharedPreferences.getString(COMPANY_NAME, ""));
        loginInfoBean.setType(sharedPreferences.getString(TYPE, ""));
        loginInfoBean.setPassWord(sharedPreferences.getString(PASSWORD, ""));
        loginInfoBean.setVersion(sharedPreferences.getInt(VERSION, 0));
        loginInfoBean.setCreateTime(sharedPreferences.getString(CREATE_TIME, ""));
        loginInfoBean.setPhone(sharedPreferences.getString(PHONE, ""));
        loginInfoBean.setEmail(sharedPreferences.getString(EMAIL, ""));
        loginInfoBean.setRoleId(sharedPreferences.getString(ROLE_ID, ""));
        loginInfoBean.setRoleCode(sharedPreferences.getString(ROLE_CODE, ""));
        loginInfoBean.setRoleTypeName(sharedPreferences.getString(ROLE_TYPE_NAME, ""));
        loginInfoBean.setRoleName(sharedPreferences.getString(ROLE_NAME, ""));
        return loginInfoBean;
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
