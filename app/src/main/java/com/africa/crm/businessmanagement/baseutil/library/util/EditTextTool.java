package com.africa.crm.businessmanagement.baseutil.library.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * EditText工具类
 * 
 * @author Administrator
 * 
 */
public class EditTextTool {
	/**
	 * 移除焦点
	 * 
	 * @param editText
	 */
	public static void removeEditTextFocus(EditText editText) {
		editText.setFocusable(false);
		editText.setCursorVisible(false);
		editText.setFocusableInTouchMode(false);
		editText.clearFocus();
	}

	/**
	 * 弹出键盘
	 * 
	 * @param editText
	 */
	public static void setEditTextFocus(EditText editText) {
		editText.setFocusable(true);
		editText.setFocusableInTouchMode(true);
		editText.setCursorVisible(true);
		// editText.requestFocusFromTouch();
		editText.requestFocus();
	}

	/**
	 * 隐藏键盘
	 * 
	 */
	public static void hideSoftInput(View view, Context context) {
		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}

	/**
	 * 显示键盘
	 * 
	 */
	public static void showSoftInput(EditText view, Context context) {
		view.requestFocus();

		InputMethodManager imm = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * 设置是否显示图片
	 * 
	 * @param edit
	 */
	public static void setVisibilityEditDrawable(EditText edit, ImageView img) {
		String str = edit.getText().toString();
		if (str != null && !str.equals("")) {
			// edit.getBackground().setAlpha(0);
			img.setVisibility(View.GONE);
			return;
		}
		img.setVisibility(View.VISIBLE);
		// edit.getBackground().setAlpha(255);
	}

	/**
	 * 设置是否显示图片
	 * 
	 */
	public static void setVisibilityTextDrawable(TextView text, ImageView img) {
		String str = text.getText().toString();
		if (str != null && !str.equals("")) {
			// edit.getBackground().setAlpha(0);
			img.setVisibility(View.GONE);
			return;
		}
		img.setVisibility(View.VISIBLE);
		// edit.getBackground().setAlpha(255);
	}
}
