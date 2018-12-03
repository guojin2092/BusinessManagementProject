package baseutil.library.broadcast.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 百度推送保存的userid和channelid(个推推送使用clientid)
 *
 * @author Administrator
 */
public class PushCookieManager {
    private final  static  String SHAREDPF = "PushCookie";
    /*百度推送*/
  /*  public static final String USERID = "userid";*/
   /* public static final String CHANNELID = "channelid";*/

    /*个推推送*/
    public static final String CLIENTID = "clientid";

    /**
     * 保存值
     *
     * @param context
     * @param key     键名
     * @param value   值
     */
    public static void saveValue(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(
                "PushCookie", Context.MODE_PRIVATE);
        Editor editor = preferences.edit();

        editor.putString(key, value);

        editor.commit();
    }

    /**
     * 通过键获取值
     *
     * @param context
     * @param key     键名
     * @return 值
     */
    public static String getValue(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences(
                "PushCookie", Context.MODE_PRIVATE);
        return preferences.getString(key,"");
    }
    /**
     * 删除推送本地信息
     *
     * @param context
     * @return
     */
    public static void deletePushInfo(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SHAREDPF,
                0);
        Editor editor = preferences.edit();
        editor.clear().commit();
    }

}
