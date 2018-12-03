package baseutil.library.http;

import android.content.Context;

/**
 * 处理json和数据库的数据交互
 * 
 * @author Administrator
 * 
 */
public class JsonDbUtil {
	/**
	 * 将json保存到数据库中
	 * 
	 * @param context
	 * @param tableName
	 * @param url
	 * @param json
	 */
	public static void onSaveRequestJsonToDb(Context context, String tableName,
			String url, String json) {
		JsonHelper.getInstance(context).insertJson(tableName, url, json);
	}

	/**
	 * 从数据库中得到object
	 * 
	 * @param context
	 * @param tableName
	 * @param url
	 * @return
	 */
	public static String onGetResponseJsonFromDb(Context context,
			String tableName, String url) {
		return JsonHelper.getInstance(context).queryJsonObject(tableName, url);
	}
}
