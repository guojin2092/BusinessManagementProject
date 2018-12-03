package com.africa.crm.businessmanagement.baseutil.library.special.ui.user.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.africa.crm.businessmanagement.baseutil.library.http.LocalCookieManager;
import com.africa.crm.businessmanagement.baseutil.library.special.ui.user.bean.UserDetailInfoBean;
import com.africa.crm.businessmanagement.baseutil.library.special.ui.user.bean.UserInfoBean;

/**
 * 将每次登录后用户的信息(七中包括我的婚礼，我的评论，我的收藏数目)保存，如果退出账户则删除
 *
 * @author Administrator
 */
public class UserInfoManager {
    public static final String SHAREDPF = "userInfo";
    public static final String MEMBER_ID = "member_uid";
    public static final String MEMBER_NAME = "member_username";
    public static final String AVATAR = "avatar";
    public static final String AVATAR_BIG = "avatar_big";
    public static final String COMMENTS = "comments";
    public static final String COLLECTS = "collects";
   /* public static final String WEDDINGS = "weddings";*/
    public static final String FOLLOW = "follow";
 /*   public static final String REGDATE = "regdate";*/

    // 详细信息中增加的
    // public String groupid;
    public static final String GENDER = "gender";
    public static final String OCCUPATION = "occupation";
    public static final String FIELD = "field1";
    public static final String FIELD2 = "field2";
    public static final String FIELD3 = "field3";
    public static final String SIGHTML = "sightml";

    public static final String ACCOUNT = "acount";

    /**
     * 保存用户基本信息
     *
     * @param context
     * @param userInfoBean
     */
    public static void saveUserInfo(Context context, UserInfoBean userInfoBean) {
        SharedPreferences preferences = context.getSharedPreferences(SHAREDPF,
                0);
        Editor editor = preferences.edit();
        editor.putString(MEMBER_ID, userInfoBean.Variables.member_uid);

        editor.putString(MEMBER_NAME, userInfoBean.Variables.member_username);

        LocalCookieManager.saveValue(context, LocalCookieManager.MEMBER_USERNAME,
                userInfoBean.Variables.member_username);

        editor.putString(AVATAR, userInfoBean.Variables.avatar);

        // CookieManager.saveValue(context, CookieManager.AVATAR,
        // userInfoBean.Variables.avatar);

        editor.putString(AVATAR_BIG, userInfoBean.Variables.avatar_big);
        editor.putString(COMMENTS, userInfoBean.Variables.user.comments);
        editor.putString(COLLECTS, userInfoBean.Variables.user.collects);
        editor.putString(FOLLOW, userInfoBean.Variables.user.follow);
     /*   editor.putString(REGDATE, userInfoBean.Variables.user.regdate);*/
        editor.commit();

    }

    /**
     * 获得用户基本信息
     *
     * @param context
     * @return
     */
    public static UserInfoBean getUserInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHAREDPF,
                0);
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.Variables = new UserInfoBean().new Variable();
        userInfoBean.Variables.member_uid = preferences
                .getString(MEMBER_ID, "");
        userInfoBean.Variables.member_username = preferences.getString(
                MEMBER_NAME, "");
        userInfoBean.Variables.avatar = preferences.getString(AVATAR, "");
        userInfoBean.Variables.avatar_big = preferences.getString(AVATAR_BIG,
                "");
        userInfoBean.Variables.user = new UserInfoBean().new Variable().new User();
        userInfoBean.Variables.user.comments = preferences.getString(COMMENTS,
                "");
        userInfoBean.Variables.user.collects = preferences.getString(COLLECTS,
                "");
        userInfoBean.Variables.user.follow = preferences.getString(FOLLOW,
                "");

     /*   userInfoBean.Variables.user.regdate = preferences
                .getString(REGDATE, "");*/
  /*      if (TextUtils.isEmpty(userInfoBean.Variables.user.regdate)) {
            // 默认显示当前的日期
            Calendar calendar = Calendar.getInstance();
            String date = calendar.get(Calendar.YEAR) + "-"
                    + (calendar.get(Calendar.MONTH) + 1) + "-"
                    + calendar.get(Calendar.DAY_OF_MONTH);
            userInfoBean.Variables.user.regdate = date;
        }*/
        return userInfoBean;
    }

    /**
     * 保存用户详细信息
     *
     * @param context
     * @param userDetailInfoBean
     */
    public static void saveUserDetailInfo(Context context,
                                          UserDetailInfoBean userDetailInfoBean) {
        SharedPreferences preferences = context.getSharedPreferences(SHAREDPF,
                0);
        Editor editor = preferences.edit();
        editor.putString(GENDER, userDetailInfoBean.Variables.space.gender);
        editor.putString(OCCUPATION,
                userDetailInfoBean.Variables.space.occupation);
        editor.putString(FIELD, userDetailInfoBean.Variables.space.field1);
        editor.putString(FIELD2, userDetailInfoBean.Variables.space.field2);
        editor.putString(FIELD3, userDetailInfoBean.Variables.space.field3);
        editor.putString(SIGHTML, userDetailInfoBean.Variables.space.sightml);
        editor.commit();
    }

    /**
     * 获得用户详细信息
     *
     * @param context
     * @return
     */
    public static UserDetailInfoBean getUserDetailInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHAREDPF,
                0);
        UserDetailInfoBean userDetailInfoBean = new UserDetailInfoBean();
        userDetailInfoBean.Variables = new UserDetailInfoBean().new Variable();
        userDetailInfoBean.Variables.space = new UserDetailInfoBean().new Variable().new Space();
        userDetailInfoBean.Variables.space.gender = preferences.getString(
                GENDER, "");
        userDetailInfoBean.Variables.space.occupation = preferences.getString(
                OCCUPATION, "");
        userDetailInfoBean.Variables.space.field1 = preferences.getString(
                FIELD, "");
        userDetailInfoBean.Variables.space.field2 = preferences.getString(
                FIELD2, "");
        userDetailInfoBean.Variables.space.field3 = preferences.getString(
                FIELD3, "");
        userDetailInfoBean.Variables.space.sightml = preferences.getString(
                SIGHTML, "");
        return userDetailInfoBean;
    }

    /**
     * 根据key获取value值
     *
     * @param context
     * @param key
     * @return
     */
    public static String getValue(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(SHAREDPF,
                0);
        return preferences.getString(key, "");
    }

    /**
     * 根据key保存value值
     *
     * @param context
     * @param key
     * @return
     */
    public static void saveValue(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(SHAREDPF,
                0);
        Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 删除用户的基本信息
     *
     * @param context
     * @return
     */
    public static void deleteUserInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHAREDPF,
                0);
        Editor editor = preferences.edit();
        editor.clear().commit();
    }
}
