package com.africa.crm.businessmanagement.main.photo;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

import com.africa.crm.businessmanagement.R;


/**
 * 作者: 51hs_android
 * 时间: 2017/3/2
 * 简介:
 */

public class BasePopup extends PopupWindow {
    private static final String TAG = "MyPopouWindow";

    ViewGroup vOut;
    private View v;

    protected OnClickSure onClickSure;

    protected OnViewClickListener onViewClickListener;

    public void setOnClickSure(OnClickSure onClickSure) {
        this.onClickSure = onClickSure;
    }


    public BasePopup(Context context) {
        super(context);
        init(context);

    }

    private void init(Context context) {
        setFocusable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setAnimationStyle(R.style.popwin_anim_style);
        addBackGround(context);
    }

    public void addBackGround(Context context) {
        v = new View(context);
        v.setBackgroundColor(context.getResources().getColor(R.color.black));
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        v.setLayoutParams(lp);
    }


    public BasePopup(int width, int height) {
        this(null, width, height);
    }


    public BasePopup(View contentView, int width, int height) {
        super(contentView, width, height);
        init(contentView.getContext());
    }



    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);

        vOut = findSuitableParent(parent);
        vOut.addView(v);
        anim(0f, 0.5f, 300);
    }

    public void showAsDropDown(View anchor, ViewGroup viewGroup, boolean full, int xoff, int yoff, int gravity) {
        this.showAsDropDown(anchor, xoff, yoff, gravity);
        if (full) {
            vOut = findSuitableParent(viewGroup);
        } else {
            vOut = viewGroup;
        }
        vOut.addView(v);
        anim(0f, 0.5f, 300);
    }


    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);

    }

    @Override
    public void dismiss() {
        super.dismiss();
//        anim(0.5f, 0f, 300);
        vOut.removeView(v);

    }

    public void switchPopup() {
        if (isShowing()) {
            dismiss();
        }
    }


    private void anim(float start, float end, int time) {
        Log.d(TAG, "anim: ");
        ValueAnimator animator = ValueAnimator.ofFloat(start, end);
        animator.setInterpolator(new FastOutLinearInInterpolator());
        animator.setDuration(time);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.setAlpha((float) animation.getAnimatedValue());

            }
        });
        animator.start();
    }

    public interface OnViewClickListener {
        void viewClick(View v);
    }

    public interface OnClickSure {

        void click(View view, int position);

    }

    /**
     * 根据View 寻找最外层ViewGroup
     * @param view
     * @return
     */
    private ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;
        do {
            if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    // If we've hit the decor content view, then we didn't find a CoL in the
                    // hierarchy, so use it.
                    return (ViewGroup) view;
                } else {
                    // It's not the content view but we'll use it as our fallback
                    fallback = (ViewGroup) view;
                }
            }

            if (view != null) {
                // Else, we will loop and crawl up the view hierarchy and try to find a parent
                final ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }
        } while (view != null);

        // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
        return fallback;
    }




}
