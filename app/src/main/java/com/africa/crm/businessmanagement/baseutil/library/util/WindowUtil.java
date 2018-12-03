package com.africa.crm.businessmanagement.baseutil.library.util;

import android.app.Activity;

/**
 * 屏幕控制
 * 
 * @author Administrator
 * 
 */
public class WindowUtil {
	/**
	 * 设置全屏
	 * 
	 * @param activity
	 */
	public static void setFullWindow(Activity activity) {
		int height = activity.getWindow().getAttributes().height;
		activity.getWindow().setLayout(
				activity.getWindowManager().getDefaultDisplay().getWidth(),
				height);
	}
}
