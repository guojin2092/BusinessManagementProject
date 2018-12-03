package com.africa.crm.businessmanagement.baseutil.library.util;

import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.africa.crm.businessmanagement.R;


/**
 * Project：halobear
 * Author:  alex
 * Version:
 * Description：
 * Date：2016/12/16 15:18
 * Modification  History:
 * Why & What is modified:
 */
public class AnimUtils {
    /**
     * 首页缩放动画
     *
     * @param context
     * @param view
     */
    public static Animation homePageScaleAnim(Context context, View view) {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.home_page_scale);
        view.startAnimation(anim);
        return anim;
    }

    /**
     * 缩放动画
     *
     * @param context
     * @param view
     */
    public static void scaleTransform(Context context, View view) {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.zoom);
        view.startAnimation(anim);
    }

    /**
     * 向下平移出现
     *
     * @param context
     * @param view
     */
    public static void slideTopDown(Context context, View view) {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.slide_top_down);
        view.startAnimation(anim);
    }

    /**
     * 向上平移然后消失
     *
     * @param context
     * @param view
     */
    public static void slideTopUp(Context context, View view) {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.slide_top_up);
        view.startAnimation(anim);
    }

    /**
     * 从右边飞进来
     */
    public static void slideFromRight(Context context, View view) {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.right_slide_in);
        view.startAnimation(anim);
    }

    /**
     * 从左边飞进来
     */
    public static void slideFromLeft(Context context, View view) {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.left_slide_in);
        view.startAnimation(anim);
    }
}
