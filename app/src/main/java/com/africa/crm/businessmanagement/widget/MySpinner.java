package com.africa.crm.businessmanagement.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.DicInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/12/8 0008 21:54
 * Modification  History:
 * Why & What is modified:
 */
public class MySpinner extends FrameLayout {
    private static final int DEFAULT_ELEVATION = 16;
    private static final int MAX_LEVEL = 10000;
    private View mRootView;
    private AppCompatTextView tv_choose_type;
    private TextView arrow;
    private Drawable arrowDrawable;
    @DrawableRes
    private int arrowDrawableResId;
    private int backgroundSelector;
    private int arrowDrawableTint;
    private String hintText;

    private PopupWindow popupWindow;
    private ListView listView;
    private MyAdapter myAdapter;
    private List<DicInfo> mList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(DicInfo dicInfo, int position);
    }

    public void addOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public MySpinner(@NonNull Context context) {
        super(context);
        initView(context, null);
    }

    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public MySpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.layout_my_spinner, this);
        tv_choose_type = mRootView.findViewById(R.id.tv_choose_type);
        arrow = mRootView.findViewById(R.id.arrow);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MySpinner);
        backgroundSelector = typedArray.getResourceId(R.styleable.MySpinner_backgroundSelector, R.drawable.background_selector);
        tv_choose_type.setBackgroundResource(backgroundSelector);
        arrowDrawableTint = typedArray.getColor(R.styleable.MySpinner_arrowTint, Integer.MAX_VALUE);
        arrowDrawableResId = typedArray.getResourceId(R.styleable.MySpinner_arrowDrawable, R.drawable.arrow);
        hintText = typedArray.getString(R.styleable.MySpinner_hintText);
        tv_choose_type.setHint(hintText);

        listView = new ListView(context);
        // Set the spinner's id into the listview to make it pretend to be the right parent in
        // onItemClick
        listView.setId(getId());
        listView.setDivider(null);
        listView.setItemsCanFocus(true);
        listView.setVerticalScrollBarEnabled(false);
        listView.setHorizontalScrollBarEnabled(false);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mList.get(position).getId().equals("0")) {
                    tv_choose_type.setText("");
                } else {
                    tv_choose_type.setText(mList.get(position).getName());
                }
                dismissDropDown();
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(mList.get(position), position);
                }
            }
        });

        popupWindow = new PopupWindow(context);
        popupWindow.setContentView(listView);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.spinner_drawable));

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                animateArrow(false);
            }
        });

        tv_choose_type.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!popupWindow.isShowing()) {
                    showDropDown();
                } else {
                    dismissDropDown();
                }
            }
        });

    }

    @Override
    protected void onVisibilityChanged(View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        arrowDrawable = initArrowDrawable(arrowDrawableTint);
        arrow.setCompoundDrawablesWithIntrinsicBounds(null, null, arrowDrawable, null);
    }

    private Drawable initArrowDrawable(int drawableTint) {
        Drawable drawable = ContextCompat.getDrawable(getContext(), arrowDrawableResId);
        if (drawable != null) {
            drawable = DrawableCompat.wrap(drawable);
            if (drawableTint != Integer.MAX_VALUE && drawableTint != 0) {
                DrawableCompat.setTint(drawable, drawableTint);
            }
        }
        return drawable;
    }

    private void animateArrow(boolean shouldRotateUp) {
        int start = shouldRotateUp ? 0 : MAX_LEVEL;
        int end = shouldRotateUp ? MAX_LEVEL : 0;
        ObjectAnimator animator = ObjectAnimator.ofInt(arrowDrawable, "level", start, end);
        animator.setInterpolator(new LinearOutSlowInInterpolator());
        animator.start();
    }

    public void dismissDropDown() {
        animateArrow(false);
        popupWindow.dismiss();
    }

    public void showDropDown() {
        animateArrow(true);
        popupWindow.setWidth(tv_choose_type.getMeasuredWidth());
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.showAsDropDown(this);
    }

    public void setListDatas(Context context, List<DicInfo> list) {
        mList = list;
        myAdapter = new MyAdapter(context, mList);
        listView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }

    public String getText() {
        String textString = tv_choose_type.getText().toString().trim();
        return textString;
    }

    public void setText(String text) {
        tv_choose_type.setText(text);
    }

    private class MyAdapter extends BaseAdapter {
        private Context mContext;
        private List<DicInfo> mDicInfoList;

        private MyAdapter(Context context, List<DicInfo> list) {
            mContext = context;
            mDicInfoList = list;
        }

        @Override
        public int getCount() {
            return mDicInfoList.size();
        }

        @Override
        public Object getItem(int position) {
            return mDicInfoList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_spinner_menu, null);
                holder.tv_type = convertView.findViewById(R.id.tv_type);
                holder.view_bottom = convertView.findViewById(R.id.view_bottom);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            DicInfo dicInfo = mDicInfoList.get(position);
            holder.tv_type.setText(dicInfo.getName());
            if (position == mDicInfoList.size() - 1) {
                holder.view_bottom.setVisibility(GONE);
            } else {
                holder.view_bottom.setVisibility(VISIBLE);
            }
            return convertView;
        }
    }

    private class ViewHolder {
        private TextView tv_type;
        private View view_bottom;
    }
}
