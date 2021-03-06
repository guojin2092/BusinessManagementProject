package com.africa.crm.businessmanagement.main.station.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.RoleLimitInfoBean;
import com.africa.crm.businessmanagement.main.station.adapter.AuthLimitListAdapter;
import com.africa.crm.businessmanagement.main.station.adapter.PackagingDataPartListAdapter;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.gitonway.lee.niftymodaldialogeffects.lib.ColorUtils;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.effects.BaseEffects;

import java.util.List;


/**
 * 包装管理
 */
public class PackagingDataListDialog extends Dialog implements DialogInterface {

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

    private static PackagingDataListDialog instance;

    private PackagingDataPartListAdapter mAuthLimitListAdapter;
    private RecyclerView rv_part_list;

    public PackagingDataListDialog(Context context) {
        super(context);
        init(context);

    }

    public PackagingDataListDialog(Context context, int theme) {
        super(context, theme);
        init(context);
    }

    public PackagingDataListDialog(Context context, int theme, List<DicInfo> list) {
        super(context, theme);
        init(context);
        setLimitDatas(list);
    }

    private void setLimitDatas(List<DicInfo> dicInfoList) {
        if (!ListUtils.isEmpty(dicInfoList)) {
            mAuthLimitListAdapter = new PackagingDataPartListAdapter(dicInfoList);
            rv_part_list.setAdapter(mAuthLimitListAdapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            rv_part_list.setLayoutManager(layoutManager);
            rv_part_list.addItemDecoration(new LineItemDecoration(getContext(), LinearLayoutManager.VERTICAL, 0, ContextCompat.getColor(getContext(), R.color.transparent)));
            rv_part_list.setHasFixedSize(true);
            rv_part_list.setNestedScrollingEnabled(false);

        }

    }

    public RecyclerView getRecyclerView() {
        return rv_part_list;
    }

    public PackagingDataPartListAdapter getAdapter() {
        return mAuthLimitListAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes((WindowManager.LayoutParams) params);

    }

    public static PackagingDataListDialog getInstance(Context context, List<DicInfo> list) {

/*
        if (instance == null || !tmpContext.equals(context)) {
            synchronized (RoleAuthLimitDialog.class) {
                if (instance == null || !tmpContext.equals(context)) {
                    instance = new RoleAuthLimitDialog(context, R.style.dialog_untran, list);
                }
            }
        }
*/
        instance = new PackagingDataListDialog(context, R.style.dialog_untran, list);
        tmpContext = context;
        return instance;

    }

    private void init(Context context) {

        mDialogView = View.inflate(context, R.layout.dialog_packaging_data_list, null);

        mLinearLayoutView = (LinearLayout) mDialogView.findViewById(R.id.parentPanel);
        mRelativeLayoutView = (RelativeLayout) mDialogView.findViewById(R.id.main);
        mLinearLayoutTopView = (LinearLayout) mDialogView.findViewById(R.id.topPanel);
        mLinearLayoutMsgView = (LinearLayout) mDialogView.findViewById(R.id.contentPanel);

        mTitle = (TextView) mDialogView.findViewById(R.id.alertTitle);
        mMessage = (TextView) mDialogView.findViewById(R.id.message);
        mIcon = (ImageView) mDialogView.findViewById(R.id.icon);
        mDivider = mDialogView.findViewById(R.id.titleDivider);
        tv_save = mDialogView.findViewById(R.id.tv_save);
        rv_part_list = mDialogView.findViewById(R.id.rv_auth_limit);
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
    }

    public PackagingDataListDialog withDividerColor(String colorString) {
        mDivider.setBackgroundColor(Color.parseColor(colorString));
        return this;
    }

    public PackagingDataListDialog withDividerColor(int color) {
        mDivider.setBackgroundColor(color);
        return this;
    }


    public PackagingDataListDialog withTitle(CharSequence title) {
        toggleView(mLinearLayoutTopView, title);
        mTitle.setText(title);
        return this;
    }

    public PackagingDataListDialog withTitleColor(String colorString) {
        mTitle.setTextColor(Color.parseColor(colorString));
        return this;
    }

    public PackagingDataListDialog withTitleColor(int color) {
        mTitle.setTextColor(color);
        return this;
    }

    public PackagingDataListDialog withMessage(int textResId) {
        toggleView(mLinearLayoutMsgView, textResId);
        mMessage.setText(textResId);
        return this;
    }

    public PackagingDataListDialog withMessage(CharSequence msg) {
        toggleView(mLinearLayoutMsgView, msg);
        mMessage.setText(msg);
        return this;
    }

    public PackagingDataListDialog withMessageColor(String colorString) {
        mMessage.setTextColor(Color.parseColor(colorString));
        return this;
    }

    public PackagingDataListDialog withMessageColor(int color) {
        mMessage.setTextColor(color);
        return this;
    }

    public PackagingDataListDialog withDialogColor(String colorString) {
        mLinearLayoutView.getBackground().setColorFilter(ColorUtils.getColorFilter(Color
                .parseColor(colorString)));
        return this;
    }

    public PackagingDataListDialog withDialogColor(int color) {
        mLinearLayoutView.getBackground().setColorFilter(ColorUtils.getColorFilter(color));
        return this;
    }

    public PackagingDataListDialog withIcon(int drawableResId) {
        mIcon.setImageResource(drawableResId);
        return this;
    }

    public PackagingDataListDialog withIcon(Drawable icon) {
        mIcon.setImageDrawable(icon);
        return this;
    }

    public PackagingDataListDialog setCancelClick(View.OnClickListener click) {
        mIcon.setOnClickListener(click);
        return this;
    }

    public PackagingDataListDialog withDuration(int duration) {
        this.mDuration = duration;
        return this;
    }

    public PackagingDataListDialog withEffect(Effectstype type) {
        this.type = type;
        return this;
    }

    public PackagingDataListDialog setSaveClick(View.OnClickListener click) {
        tv_save.setOnClickListener(click);
        return this;
    }

    public PackagingDataListDialog isCancelableOnTouchOutside(boolean cancelable) {
        this.isCancelable = cancelable;
        this.setCanceledOnTouchOutside(cancelable);
        return this;
    }

    public PackagingDataListDialog isCancelable(boolean cancelable) {
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
