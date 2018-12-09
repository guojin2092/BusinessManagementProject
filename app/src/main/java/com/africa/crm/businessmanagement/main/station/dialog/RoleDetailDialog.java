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
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.gitonway.lee.niftymodaldialogeffects.lib.ColorUtils;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.effects.BaseEffects;


/**
 * 角色详情
 */
public class RoleDetailDialog extends Dialog implements DialogInterface {

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

    private int mDuration = -1;

    private static int mOrientation = 1;

    private boolean isCancelable = true;

    private static RoleDetailDialog instance;

    private EditText et_company_name;//角色名称
    private EditText et_number;//角色编码
    private EditText et_type;//分类名称
    private EditText et_sort;//排序号
    private RoleInfoBean mRoleInfoBean;

    private OnSaveClickListener onSaveClickListener;

    public interface OnSaveClickListener {
        void onSaveClick(RoleInfoBean roleInfoBean);
    }

    public void addOnSaveClickListener(OnSaveClickListener onSaveClickListener) {
        this.onSaveClickListener = onSaveClickListener;
    }

    public RoleDetailDialog(Context context) {
        super(context);
        init(context);

    }

    public RoleDetailDialog(Context context, int theme, RoleInfoBean roleInfoBean) {
        super(context, theme);
        init(context);
        setDatas(roleInfoBean);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);

    }

    public static RoleDetailDialog getInstance(Context context, RoleInfoBean roleInfoBean) {

        /*if (instance == null || !tmpContext.equals(context)) {
            synchronized (RoleDetailDialog.class) {
                if (instance == null || !tmpContext.equals(context)) {
                    instance = new RoleDetailDialog(context, R.style.dialog_untran, roleInfoBean);
                }
            }
        }*/
        instance = new RoleDetailDialog(context, R.style.dialog_untran, roleInfoBean);
        tmpContext = context;
        return instance;

    }

    private void init(Context context) {

        mDialogView = View.inflate(context, R.layout.dialog_role_detail, null);

        mLinearLayoutView = (LinearLayout) mDialogView.findViewById(R.id.parentPanel);
        mRelativeLayoutView = (RelativeLayout) mDialogView.findViewById(R.id.main);
        mLinearLayoutTopView = (LinearLayout) mDialogView.findViewById(R.id.topPanel);
        mLinearLayoutMsgView = (LinearLayout) mDialogView.findViewById(R.id.contentPanel);

        et_company_name = mDialogView.findViewById(R.id.et_company_name);
        et_number = mDialogView.findViewById(R.id.et_number);
        et_type = mDialogView.findViewById(R.id.et_type);
        et_sort = mDialogView.findViewById(R.id.et_sort);

        mTitle = (TextView) mDialogView.findViewById(R.id.alertTitle);
        mMessage = (TextView) mDialogView.findViewById(R.id.message);
        mIcon = (ImageView) mDialogView.findViewById(R.id.icon);
        mDivider = mDialogView.findViewById(R.id.titleDivider);
        tv_save = mDialogView.findViewById(R.id.tv_save);

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
                RoleInfoBean roleInfoBean = new RoleInfoBean();
                roleInfoBean.setId(mRoleInfoBean.getId());
                roleInfoBean.setRoleName(et_company_name.getText().toString().trim());
                roleInfoBean.setRoleCode(et_number.getText().toString().trim());
                roleInfoBean.setTypeName(et_type.getText().toString().trim());
                roleInfoBean.setOrderNum(et_sort.getText().toString().trim());
                if (onSaveClickListener != null) {
                    onSaveClickListener.onSaveClick(roleInfoBean);
                }
            }
        });
    }

    private void setDatas(RoleInfoBean roleInfoBean) {
        mRoleInfoBean = roleInfoBean;
        et_company_name.setText(roleInfoBean.getRoleName());
        et_number.setText(roleInfoBean.getRoleCode());
        et_type.setText(roleInfoBean.getTypeName());
        et_sort.setText(roleInfoBean.getOrderNum());
    }

    public RoleDetailDialog withDividerColor(String colorString) {
        mDivider.setBackgroundColor(Color.parseColor(colorString));
        return this;
    }

    public RoleDetailDialog withDividerColor(int color) {
        mDivider.setBackgroundColor(color);
        return this;
    }


    public RoleDetailDialog withTitle(CharSequence title) {
        toggleView(mLinearLayoutTopView, title);
        mTitle.setText(title);
        return this;
    }

    public RoleDetailDialog withTitleColor(String colorString) {
        mTitle.setTextColor(Color.parseColor(colorString));
        return this;
    }

    public RoleDetailDialog withTitleColor(int color) {
        mTitle.setTextColor(color);
        return this;
    }

    public RoleDetailDialog withMessage(int textResId) {
        toggleView(mLinearLayoutMsgView, textResId);
        mMessage.setText(textResId);
        return this;
    }

    public RoleDetailDialog withMessage(CharSequence msg) {
        toggleView(mLinearLayoutMsgView, msg);
        mMessage.setText(msg);
        return this;
    }

    public RoleDetailDialog withMessageColor(String colorString) {
        mMessage.setTextColor(Color.parseColor(colorString));
        return this;
    }

    public RoleDetailDialog withMessageColor(int color) {
        mMessage.setTextColor(color);
        return this;
    }

    public RoleDetailDialog withDialogColor(String colorString) {
        mLinearLayoutView.getBackground().setColorFilter(ColorUtils.getColorFilter(Color
                .parseColor(colorString)));
        return this;
    }

    public RoleDetailDialog withDialogColor(int color) {
        mLinearLayoutView.getBackground().setColorFilter(ColorUtils.getColorFilter(color));
        return this;
    }

    public RoleDetailDialog withIcon(int drawableResId) {
        mIcon.setImageResource(drawableResId);
        return this;
    }

    public RoleDetailDialog withIcon(Drawable icon) {
        mIcon.setImageDrawable(icon);
        return this;
    }

    public RoleDetailDialog setCancelClick(View.OnClickListener click) {
        mIcon.setOnClickListener(click);
        return this;
    }

    public RoleDetailDialog withDuration(int duration) {
        this.mDuration = duration;
        return this;
    }

    public RoleDetailDialog withEffect(Effectstype type) {
        this.type = type;
        return this;
    }

    public RoleDetailDialog setSaveClick(View.OnClickListener click) {
        tv_save.setOnClickListener(click);
        return this;
    }

    public RoleDetailDialog isCancelableOnTouchOutside(boolean cancelable) {
        this.isCancelable = cancelable;
        this.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    public RoleDetailDialog isCancelable(boolean cancelable) {
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
