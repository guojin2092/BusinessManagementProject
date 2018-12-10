package com.africa.crm.businessmanagement.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/10 0010 14:18
 * Modification  History:
 * Why & What is modified:
 */
public class KeyboardUtil {

    /**
     * 隐藏软键盘
     *
     * @param view :一般为EditText
     */
    public static void hideKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 清空输入框
     *
     * @param editText
     */
    public static void clearInputBox(EditText editText) {
        if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
            editText.setText("");
        }
    }
}
