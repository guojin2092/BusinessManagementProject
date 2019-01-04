package com.africa.crm.businessmanagement.widget;

import android.widget.EditText;
import android.widget.TextView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2019/1/4 0004 10:50
 * Modification  History:
 * Why & What is modified:
 */
public class StringUtil {

    public static String getText(TextView textView) {
        return textView.getText().toString().trim();
    }

    public static String getText(EditText editText) {
        return editText.getText().toString().trim();
    }
}
