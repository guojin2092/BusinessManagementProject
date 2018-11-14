package baselibrary.library.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

/**
 * 应用程序控制工具类
 * 
 * @author Administrator
 * 
 */
public class AndroidUtils {
	public static boolean isFirst(String paramString, Context paramContext) {
		SharedPreferences localSharedPreferences = paramContext
				.getSharedPreferences("config", 0);
		String str = "first_" + paramString;
		boolean bool = localSharedPreferences.getBoolean(str, true);
		if (localSharedPreferences.getBoolean(str, true))
			localSharedPreferences.edit().putBoolean(str, false).commit();
		return bool;
	}

	public static void setSharedPreferencesBooleanValue(Context paramContext,
			String paramString, boolean paramBoolean) {
		paramContext.getSharedPreferences("config", 0).edit()
				.putBoolean(paramString, paramBoolean).commit();
	}

	public static boolean isSDCardAvailable() {
		return Environment.getExternalStorageState().equals("mounted");
	}

}
