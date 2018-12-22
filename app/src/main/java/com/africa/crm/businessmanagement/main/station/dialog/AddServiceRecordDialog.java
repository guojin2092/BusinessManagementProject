package com.africa.crm.businessmanagement.main.station.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.OrderProductInfo;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.gitonway.lee.niftymodaldialogeffects.lib.ColorUtils;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.effects.BaseEffects;

import java.util.Date;


/**
 * 添加服务追踪
 */
public class AddServiceRecordDialog extends Dialog implements DialogInterface {

    private static Context tmpContext;

    private Effectstype type = null;

    private LinearLayout mLinearLayoutView;

    private RelativeLayout mRelativeLayoutView;

    private LinearLayout mLinearLayoutMsgView;

    private LinearLayout mLinearLayoutTopView;

    private View mDialogView;

    private View mDivider;

    private TextView mTitle;

    private TextView mMessage;

    private ImageView mIcon;

    private TextView tv_save;

    private EditText et_name;

    private EditText et_num;

    private int mDuration = -1;

    private static int mOrientation = 1;

    private boolean isCancelable = true;

    private static AddServiceRecordDialog instance;

    private OnSaveClickListener onSaveClickListener;

    public interface OnSaveClickListener {
        void onSaveClick(OrderProductInfo orderProductInfo);
    }

    public void addOnSaveClickListener(OnSaveClickListener onSaveClickListener) {
        this.onSaveClickListener = onSaveClickListener;
    }

    public AddServiceRecordDialog(Context context) {
        super(context);
        init(context);

    }

    public AddServiceRecordDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((WindowManager.LayoutParams) params);

    }

    public static AddServiceRecordDialog getInstance(Context context) {

/*
        if (instance == null || !tmpContext.equals(context)) {
            synchronized (AddServiceRecordDialog.class) {
                if (instance == null || !tmpContext.equals(context)) {
                    instance = new AddServiceRecordDialog(context, R.style.dialog_untran);
                }
            }
        }
*/
        instance = new AddServiceRecordDialog(context, R.style.dialog_untran);
        tmpContext = context;
        return instance;

    }

    private void init(Context context) {

        mDialogView = View.inflate(context, R.layout.dialog_add_service_record, null);

        mLinearLayoutView = (LinearLayout) mDialogView.findViewById(R.id.parentPanel);
        mRelativeLayoutView = (RelativeLayout) mDialogView.findViewById(R.id.main);
        mLinearLayoutTopView = (LinearLayout) mDialogView.findViewById(R.id.topPanel);
        mLinearLayoutMsgView = (LinearLayout) mDialogView.findViewById(R.id.contentPanel);

        mTitle = (TextView) mDialogView.findViewById(R.id.alertTitle);
        mMessage = (TextView) mDialogView.findViewById(R.id.message);
        mIcon = (ImageView) mDialogView.findViewById(R.id.icon);
        mDivider = mDialogView.findViewById(R.id.titleDivider);
        tv_save = mDialogView.findViewById(R.id.tv_save);
        et_name = mDialogView.findViewById(R.id.et_name);
        et_num = mDialogView.findViewById(R.id.et_num);
        setContentView(mDialogView);

        this.setOnShowListener(new OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {

                mLinearLayoutView.setVisibility(View.VISIBLE);
                if (type == null) {
                    type = Effectstype.Slidetop;
                }
                start(type);

            }
        });
        mRelativeLayoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isCancelable) dismiss();
            }
        });

        tv_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onSaveClickListener != null) {
                    onSaveClickListener.onSaveClick(new OrderProductInfo(et_name.getText().toString().trim(), TimeUtils.getTimeByMinute2(new Date())));
                }
            }
        });

    }

    public AddServiceRecordDialog withDividerColor(String colorString) {
        mDivider.setBackgroundColor(Color.parseColor(colorString));
        return this;
    }

    public AddServiceRecordDialog withDividerColor(int color) {
        mDivider.setBackgroundColor(color);
        return this;
    }


    public AddServiceRecordDialog withTitle(CharSequence title) {
        toggleView(mLinearLayoutTopView, title);
        mTitle.setText(title);
        return this;
    }

    public AddServiceRecordDialog withTitleColor(String colorString) {
        mTitle.setTextColor(Color.parseColor(colorString));
        return this;
    }

    public AddServiceRecordDialog withTitleColor(int color) {
        mTitle.setTextColor(color);
        return this;
    }

    public AddServiceRecordDialog withMessage(int textResId) {
        toggleView(mLinearLayoutMsgView, textResId);
        mMessage.setText(textResId);
        return this;
    }

    public AddServiceRecordDialog withMessage(CharSequence msg) {
        toggleView(mLinearLayoutMsgView, msg);
        mMessage.setText(msg);
        return this;
    }

    public AddServiceRecordDialog withMessageColor(String colorString) {
        mMessage.setTextColor(Color.parseColor(colorString));
        return this;
    }

    public AddServiceRecordDialog withMessageColor(int color) {
        mMessage.setTextColor(color);
        return this;
    }

    public AddServiceRecordDialog withDialogColor(String colorString) {
        mLinearLayoutView.getBackground().setColorFilter(ColorUtils.getColorFilter(Color
                .parseColor(colorString)));
        return this;
    }

    public AddServiceRecordDialog withDialogColor(int color) {
        mLinearLayoutView.getBackground().setColorFilter(ColorUtils.getColorFilter(color));
        return this;
    }

    public AddServiceRecordDialog withIcon(int drawableResId) {
        mIcon.setImageResource(drawableResId);
        return this;
    }

    public AddServiceRecordDialog withIcon(Drawable icon) {
        mIcon.setImageDrawable(icon);
        return this;
    }

    public AddServiceRecordDialog setCancelClick(View.OnClickListener click) {
        mIcon.setOnClickListener(click);
        return this;
    }

    public AddServiceRecordDialog withDuration(int duration) {
        this.mDuration = duration;
        return this;
    }

    public AddServiceRecordDialog withEffect(Effectstype type) {
        this.type = type;
        return this;
    }

    public AddServiceRecordDialog setSaveClick(View.OnClickListener click) {
        tv_save.setOnClickListener(click);
        return this;
    }

    public AddServiceRecordDialog isCancelableOnTouchOutside(boolean cancelable) {
        this.isCancelable = cancelable;
        this.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    public AddServiceRecordDialog isCancelable(boolean cancelable) {
        this.isCancelable = cancelable;
        this.setCancelable(cancelable);
        return this;
    }

    private void toggleView(View view, Object obj) {
        if (obj == null) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void show() {
        super.show();
    }

    private void start(Effectstype type) {
        BaseEffects animator = type.getAnimator();
        if (mDuration != -1) {
            animator.setDuration(Math.abs(mDuration));
        }
        animator.start(mRelativeLayoutView);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}
