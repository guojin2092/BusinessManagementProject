package com.simplesoft.basesimplelibrary.dialog;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.simplesoft.basesimplelibrary.R;

/**
 * Created by daxiongzaici on 15/7/9.
 */
public class MyMaterialDialog {

    /**
     * 自定义主题颜色对话框
     *
     * @param context
     * @return
     */
    public static MaterialDialog.Builder getMyBuilder(Context context) {
        MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(context);
        materialDialog.positiveColorRes(R.color.app_main_color);
        materialDialog.negativeColorRes(R.color.app_main_color);
        materialDialog.cancelable(true);
        return materialDialog;
    }

    /**
     * 创建materialdialog风格对话框
     *
     * @param context
     * @return
     */
    public static MaterialDialog createMaterialDialog(Context context, String message) {
        MaterialDialog.Builder materialProgressDialog = new MaterialDialog.Builder(context);
        materialProgressDialog.contentColorRes(R.color.app_main_color);
        materialProgressDialog.widgetColorRes(R.color.app_main_color);
        materialProgressDialog.cancelable(true);
        materialProgressDialog
                .content(message)
                .progress(true, 0);
        /*初始化结束后返回*/
        MaterialDialog materialDialog = materialProgressDialog.build();
        return materialDialog;
    }
}
