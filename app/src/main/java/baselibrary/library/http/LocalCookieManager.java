package baselibrary.library.http;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import baselibrary.library.special.ui.user.bean.UserVariable;


/**
 * 管理cookie
 *
 * @author Administrator
 */
public class LocalCookieManager {

    public static final String COOKIE = "cookie";
    public static final String COOKIEPRE = "cookiepre";
    public static final String AUTH = "auth";
    public static final String SALTKEY = "saltkey";
    public static final String MEMBER_UID = "member_uid";
    public static final String MEMBER_USERNAME = "member_username";
    /*public static final String GROUPID = "groupid";*/

    public static final String FORMHASH = "formhash";
	/*public static final String ISMODERATOR = "ismoderator";*/

    /*public static final String READACCESS = "readaccess";*/
    public static final String NOTICE = "notice";
    public static final String HASH = "hash";

    // public static final String AVATAR = "avatar";

    // store
    public static final String STATE = "state";
    public static final String APPLY_STATE = "apply_state";
    public static final String APPLY_TYPE = "apply_type";

    public static final String QINIU_MALL_TOKEN = "qiniu_mall_token";
    public static final String QINIU_FORUM_TOKEN = "qiniu_forum_token";
    public static final String QINIU_AVATAR_TOKEN = "qiniu_avatar_token";
    public static final String AVATAR_TOKEN_KEY = "avatar_token_key";

    /**
     * cookie保存在本地
     *
     * @param context
     * @param variable
     */
    public static void saveCookie(Context context, UserVariable variable) {
        SharedPreferences preferences = context.getSharedPreferences(COOKIE, 0);
        Editor editor = preferences.edit();
        editor.putString(LocalCookieManager.COOKIEPRE, variable.cookiepre);
        editor.putString(LocalCookieManager.AUTH, variable.auth);
        editor.putString(LocalCookieManager.SALTKEY, variable.saltkey);
        editor.putString(LocalCookieManager.MEMBER_UID, variable.member_uid);
        editor.putString(LocalCookieManager.MEMBER_USERNAME,
                variable.member_username);
		/*editor.putString(CookieManager.GROUPID, variable.groupid);*/
        editor.putString(LocalCookieManager.FORMHASH, variable.formhash);
	/*	editor.putString(CookieManager.ISMODERATOR, variable.ismoderator);
		editor.putString(CookieManager.READACCESS, variable.readaccess);*/
        editor.putString(LocalCookieManager.HASH, variable.hash);
        // editor.putString(CookieManager.AVATAR, variable.avatar);

        if (variable.store != null) {
            if (!TextUtils.isEmpty(variable.store.state))
                editor.putString(LocalCookieManager.APPLY_TYPE, variable.store.state);

            if (!TextUtils.isEmpty(variable.store.apply_state))
                editor.putString(LocalCookieManager.APPLY_STATE,
                        variable.store.apply_state);

            if (!TextUtils.isEmpty(variable.store.apply_type))
                editor.putString(LocalCookieManager.APPLY_STATE,
                        variable.store.apply_type);
        }

        editor.putString(LocalCookieManager.QINIU_MALL_TOKEN,
                variable.qiniu_mall_token);
        editor.putString(LocalCookieManager.QINIU_FORUM_TOKEN,
                variable.qiniu_forum_token);
        editor.putString(LocalCookieManager.QINIU_AVATAR_TOKEN,
                variable.qiniu_avatar_token);
        editor.putString(LocalCookieManager.AVATAR_TOKEN_KEY,
                variable.avatar_token_key);

        editor.commit();
    }

    /**
     * 获得本地的cookie
     *
     * @param context
     * @return
     */
    public static String getCookie(Context context) {
        String cookie = null;

        SharedPreferences preferences = context.getSharedPreferences(COOKIE, 0);
        if (preferences != null) {
            String cookiePre = preferences.getString(LocalCookieManager.COOKIEPRE,
                    null);
            if (!TextUtils.isEmpty(cookiePre))
                cookie = cookiePre;

            String saltkey = preferences.getString(LocalCookieManager.SALTKEY, null);
            if (!TextUtils.isEmpty(saltkey))
                cookie += "saltkey=" + saltkey + ";";

            if (!TextUtils.isEmpty(cookiePre))
                cookie += cookiePre;

            String auth = preferences.getString(LocalCookieManager.AUTH, null);
            if (!TextUtils.isEmpty(auth))
				/*try {*/
                cookie += "auth=" + auth/*URLEncoder.encode(auth, "utf-8")*/;
				/*} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}*/
        }
        return cookie;
    }

    /**
     * 删除cookie
     *
     * @param context
     * @return
     */
    public static void deleteCookie(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(COOKIE, 0);
        Editor editor = preferences.edit();
        editor.clear().commit();
    }

    /**
     * 根据key获取value值
     *
     * @param context
     * @param key
     * @return
     */
    public static String getValue(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(COOKIE, 0);
        return preferences.getString(key, "");
    }

    /**
     * 保存修改cookie文件
     *
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static void saveValue(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(COOKIE, 0);
        Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 根据key获取state值
     *
     * @param context
     * @param key
     * @return 默认返回0
     */
    public static String getState(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(COOKIE, 0);
        return preferences.getString(key, "0");
    }
}
