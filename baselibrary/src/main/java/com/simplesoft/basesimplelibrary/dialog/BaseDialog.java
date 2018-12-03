package com.simplesoft.baselibrary.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.simplesoft.baselibrary.R;
import com.simplesoft.baselibrary.utils.CommonUtils;
import com.simplesoft.baselibrary.utils.PixelMethod;


/**
 * Project：halobear
 * Author: sunkeqiang
 * Version: 1.0.0
 * Description：自定义Dialog基类
 * Date：2016/12/7 16:38
 * Modification  History:
 * Why & What is modified:
 */
public abstract class BaseDialog implements View.OnClickListener {
    public Context context;
    public View mView;
    public Dialog dialog;
    public LayoutInflater mInflater;
    private Window dialogWindow;


    public BaseDialog(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mView = mInflater.inflate(createView(), null, false);
        findView(mView);
        init(false, R.style.ViewDialog);//默认不可以点击阴影部分，以及显示默认样式
    }


    protected abstract int createView();

    protected abstract void findView(View view);

    protected abstract void initData();

    public void init(boolean isCanTouch, int dialogStyle) {
        dialog = new Dialog(context, dialogStyle);
        dialog.setContentView(mView);
        dialog.setCanceledOnTouchOutside(isCanTouch);
        dialogWindow = dialog.getWindow();
    }

    public void setCanceledOnTouchOutside(boolean isCanTouch) {
        dialog.setCanceledOnTouchOutside(isCanTouch);

    }

    public void setOnKeyListener(DialogInterface.OnKeyListener ls) {
        dialog.setOnKeyListener(ls);
    }

    /**
     * Dialog是否有背景
     *
     * @param dialogStyle
     */
    private void init(int dialogStyle) {
        dialog = new Dialog(context, dialogStyle);
        dialog.setContentView(mView);
        dialog.setCanceledOnTouchOutside(false);
        dialogWindow = dialog.getWindow();
    }

    /**
     * @param width  unit:dp
     * @param height unit:dp
     */
    public void setWidthAndHeight(int width, int height) {
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.width = (PixelMethod.dip2px(context, width));
        layoutParams.height = (PixelMethod.dip2px(context, height));
        dialogWindow.setAttributes(layoutParams);
    }

    public void setGravity(int gravity) {
        dialogWindow.setGravity(gravity);
    }

    public void setAnim(int animationStyle) {
        dialogWindow.setWindowAnimations(animationStyle);
    }

    public void show() {
        initData();
        if (dialog != null && dialog.isShowing())
            return;
        dialog.show();
    }

    /**
     * 关闭弹出框
     */
    public void closeDialog() {
        if (dialog != null && dialog.isShowing()) {
            dismissProgressDialog();
            dialog.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        if (CommonUtils.isFastDoubleClick(800)) {
            return;
        }
    }

    private MaterialDialog progressDialog;

    /**
     * 加载框
     *
     * @param message 显示提示信息
     */
    public void showProgressDialog(String message) {
        progressDialog = MyMaterialDialog.createMaterialDialog(context, message);
        if (progressDialog != null && progressDialog.isShowing())
            return;
        progressDialog.show();
    }

    /**
     * 显示新信息
     *
     * @param new_message
     */
    public void setProgressContent(String new_message) {
        if (progressDialog != null)
            progressDialog.setContent(new_message);
    }

    /**
     * 不显示提示信息
     */
    public void showProgressDialog() {
        progressDialog = MyMaterialDialog.createMaterialDialog(context, context.getResources().getString(R.string.common_please_wait));
        if (progressDialog != null && progressDialog.isShowing())
            return;
        progressDialog.show();
    }

    /**
     * 隐藏进度框
     */
    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog.cancel();
            progressDialog = null;
        }
    }


}
