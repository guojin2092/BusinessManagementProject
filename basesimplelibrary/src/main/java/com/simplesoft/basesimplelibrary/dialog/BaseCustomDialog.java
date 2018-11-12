package com.simplesoft.basesimplelibrary.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.simplesoft.basesimplelibrary.R;
import com.simplesoft.basesimplelibrary.utils.CommonUtils;
import com.simplesoft.basesimplelibrary.utils.PixelMethod;


/**
 * Project：MyApplication
 * Author:  alex
 * Version:
 * Description：
 * Date：2016/11/22 18:11
 * Modification  History:
 * Why & What is modified:
 */
public abstract class BaseCustomDialog implements View.OnClickListener {
    public Activity activity;
    public View view;
    public Dialog dialog;
    private LayoutInflater mInflater;
    public String content;

    public BaseCustomDialog(Activity activity, String content, int layoutId) {
        this.activity = activity;
        this.content = content;
        mInflater = LayoutInflater.from(activity);
        view = mInflater.inflate(layoutId, null);
        findView(view);
    }

    public BaseCustomDialog(Activity activity, int layoutId) {
        this.activity = activity;
        mInflater = LayoutInflater.from(activity);
        view = mInflater.inflate(layoutId, null);
        findView(view);
    }

    /**
     * 寻找控件
     *
     * @param view
     */
    protected abstract void findView(View view);

    /**
     * 显示自定义Dialog
     */
    public void showDialog() {
        //创建dialog并进行相关设置
        dialog = new Dialog(activity, R.style.ViewDialog);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
//         view.setAnimation(AnimationUtils.loadAnimation(context, R.anim));
        Window dialogWindow = dialog.getWindow();
//        dialogWindow.setWindowAnimations(R.style.down_up);
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        layoutParams.width = (PixelMethod.dip2px(activity, 300));
        layoutParams.height = (PixelMethod.dip2px(activity, 200));
        dialogWindow.setGravity(Gravity.CENTER);
        dialogWindow.setAttributes(layoutParams);
        if (dialog != null && dialog.isShowing())
            return;
        dialog.show();
    }

    /**
     * 显示自定义Dialog（可以自己控制宽高，是否能够触摸）
     *
     * @param dpWidth              宽度
     * @param isHeightWrap         高度
     * @param animationStyle       动画资源
     * @param cancelOnTouchOutside 灰色阴影部分能否点击
     * @param gravity              显示位置
     * @param isShowBlackGround    是否显示灰色填充部分
     */
    public void showDialog(int dpWidth,
                           boolean isHeightWrap,
                           boolean hasAnimation,
                           int animationStyle,
                           boolean cancelOnTouchOutside,
                           int gravity,
                           boolean isShowBlackGround) {
        //创建dialog并进行相关设置
        int bgStyle;
        if (isShowBlackGround) {
            bgStyle = R.style.ViewDialog;
        } else {
            bgStyle = R.style.ViewDialogNoBlack;
        }
        dialog = new Dialog(activity, bgStyle);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(cancelOnTouchOutside);
        Window dialogWindow = dialog.getWindow();
        if (hasAnimation) {
//            view.setAnimation(AnimationUtils.loadAnimation(activity, animationId));
            dialogWindow.setWindowAnimations(animationStyle);
        }
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        if (dpWidth == -1) {
            layoutParams.width = layoutParams.MATCH_PARENT;
        } else {
            layoutParams.width = (PixelMethod.dip2px(activity, dpWidth));
        }
        if (isHeightWrap) {
            layoutParams.height = layoutParams.WRAP_CONTENT;
        } else {
            layoutParams.height = (PixelMethod.dip2px(activity, 200));
        }
        if (gravity != -1) {
            dialogWindow.setGravity(gravity);
        } else {
            dialogWindow.setGravity(Gravity.CENTER);
        }
        dialogWindow.setAttributes(layoutParams);

        if (dialog != null && dialog.isShowing())
            return;
        dialog.show();
        initData();
    }

    /**
     * 显示自定义Dialog（可以自己控制宽高，是否能够触摸）
     *
     * @param dpWidth              宽度
     * @param dpHeight             高度
     * @param animationStyle       动画资源
     * @param cancelOnTouchOutside 灰色阴影部分能否点击
     * @param gravity              显示位置
     * @param isShowBlackGround    是否显示灰色填充部分
     */
    public void showDialog(int dpWidth,
                           int dpHeight,
                           boolean hasAnimation,
                           int animationStyle,
                           boolean cancelOnTouchOutside,
                           int gravity,
                           boolean isShowBlackGround) {
        //创建dialog并进行相关设置
        int bgStyle;
        if (isShowBlackGround) {
            bgStyle = R.style.ViewDialog;
        } else {
            bgStyle = R.style.ViewDialogNoBlack;
        }
        dialog = new Dialog(activity, bgStyle);
        dialog.setContentView(view);
        Window dialogWindow = dialog.getWindow();
        if (hasAnimation) {
//            view.setAnimation(AnimationUtils.loadAnimation(activity, animationId));
            dialogWindow.setWindowAnimations(animationStyle);
        }
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        if (dpWidth == -1) {
            layoutParams.width = layoutParams.MATCH_PARENT;
        } else {
            layoutParams.width = (PixelMethod.dip2px(activity, dpWidth));
        }
        if (dpHeight == -1) {
            layoutParams.height = layoutParams.MATCH_PARENT;
        } else {
            layoutParams.height = (PixelMethod.dip2px(activity, dpHeight));
        }

        if (gravity != -1) {
            dialogWindow.setGravity(gravity);
        } else {
            dialogWindow.setGravity(Gravity.CENTER);
        }
        dialogWindow.setAttributes(layoutParams);

        if (dialog != null && dialog.isShowing())
            return;
        dialog.show();
        dialog.setCanceledOnTouchOutside(cancelOnTouchOutside);
        initData();
    }

    /**
     * 显示自定义Dialog（可以自己控制宽高，是否能够触摸）
     *
     * @param isWidthMatch         宽度是否match
     * @param isHeightWrap         高度是否wrap
     * @param animationStyle       动画资源
     * @param cancelOnTouchOutside 灰色阴影部分能否点击
     * @param gravity              显示位置
     * @param isShowBlackGround    是否显示灰色填充部分
     */
    public void showWrapDialog(boolean isWidthMatch,
                               boolean isHeightWrap,
                               boolean hasAnimation,
                               int animationStyle,
                               boolean cancelOnTouchOutside,
                               int gravity,
                               boolean isShowBlackGround) {
        //创建dialog并进行相关设置
        int bgStyle;
        if (isShowBlackGround) {
            bgStyle = R.style.ViewDialog;
        } else {
            bgStyle = R.style.ViewDialogNoBlack;
        }
        dialog = new Dialog(activity, bgStyle);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(cancelOnTouchOutside);
        Window dialogWindow = dialog.getWindow();
        if (hasAnimation) {
//            view.setAnimation(AnimationUtils.loadAnimation(activity, animationId));
            dialogWindow.setWindowAnimations(animationStyle);
        }
        WindowManager.LayoutParams layoutParams = dialogWindow.getAttributes();
        if (isWidthMatch) {
            layoutParams.width = layoutParams.MATCH_PARENT;
        } else {
            layoutParams.width = (PixelMethod.dip2px(activity, 300));
        }

        if (isHeightWrap) {
            layoutParams.height = layoutParams.WRAP_CONTENT;
        } else {
            layoutParams.height = (PixelMethod.dip2px(activity, 200));
        }

        if (gravity != -1) {
            dialogWindow.setGravity(gravity);
        } else {
            dialogWindow.setGravity(Gravity.CENTER);
        }
        dialogWindow.setAttributes(layoutParams);
        initData();
        if (dialog != null && dialog.isShowing())
            return;
        dialog.show();
    }

    protected abstract void initData();

    public void showMatchDialog() {

    }

    /**
     * 关闭弹出框
     */
    public void closeDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onClick(View view) {
        if (CommonUtils.isFastDoubleClick(800)) {
            return;
        }
    }
}
