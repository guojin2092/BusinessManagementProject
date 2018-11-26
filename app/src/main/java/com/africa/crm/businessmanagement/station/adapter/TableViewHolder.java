package com.africa.crm.businessmanagement.station.adapter;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;

import drawthink.expandablerecyclerview.holder.BaseViewHolder;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/15 0015 13:59
 * Modification  History:
 * Why & What is modified:
 */
public class TableViewHolder extends BaseViewHolder {
    /**
     * Group
     */
    public TextView tv_info_type;
    public TextView tv_info_add;

    /**
     * Child
     */
    public LinearLayout ll_type1;
    public TextView tv_type1_title;
    public TextView tv_type1_content;
    public View line1;

    public LinearLayout ll_type2;
    public TextView tv_type2_title;
    public TextView tv_type2_content;
    public View line2;

    public LinearLayout ll_type3;
    public TextView tv_type3_title;
    public TextView tv_type3_content;
    public View line3;

    public LinearLayout ll_type4;
    public TextView tv_type4_title;
    public TextView tv_type4_content;
    public View line4;

    public LinearLayout ll_type5;
    public TextView tv_type5_title;
    public TextView tv_type5_content;
    public View line5;

    public LinearLayout ll_type6;
    public TextView tv_type6_title;
    public TextView tv_type6_content;
    public View line6;


    public TableViewHolder(Context ctx, View itemView, int viewType) {
        super(ctx, itemView, viewType);
        /**
         * Group
         */
        tv_info_type = itemView.findViewById(R.id.tv_info_type);
        tv_info_add = itemView.findViewById(R.id.tv_info_add);

        /**
         * Child
         */
        ll_type1 = itemView.findViewById(R.id.ll_type1);
        tv_type1_title = itemView.findViewById(R.id.tv_type1_title);
        tv_type1_content = itemView.findViewById(R.id.tv_type1_content);
        line1 = itemView.findViewById(R.id.line1);

        ll_type2 = itemView.findViewById(R.id.ll_type2);
        tv_type2_title = itemView.findViewById(R.id.tv_type2_title);
        tv_type2_content = itemView.findViewById(R.id.tv_type2_content);
        line2 = itemView.findViewById(R.id.line2);

        ll_type3 = itemView.findViewById(R.id.ll_type3);
        tv_type3_title = itemView.findViewById(R.id.tv_type3_title);
        tv_type3_content = itemView.findViewById(R.id.tv_type3_content);
        line3 = itemView.findViewById(R.id.line3);

        ll_type4 = itemView.findViewById(R.id.ll_type4);
        tv_type4_title = itemView.findViewById(R.id.tv_type4_title);
        tv_type4_content = itemView.findViewById(R.id.tv_type4_content);
        line4 = itemView.findViewById(R.id.line4);

        ll_type5 = itemView.findViewById(R.id.ll_type5);
        tv_type5_title = itemView.findViewById(R.id.tv_type5_title);
        tv_type5_content = itemView.findViewById(R.id.tv_type5_content);
        line5 = itemView.findViewById(R.id.line5);

        ll_type6 = itemView.findViewById(R.id.ll_type6);
        tv_type6_title = itemView.findViewById(R.id.tv_type6_title);
        tv_type6_content = itemView.findViewById(R.id.tv_type6_content);
        line6 = itemView.findViewById(R.id.line6);
    }

    @Override
    public int getGroupViewResId() {
        return R.id.group;
    }

    @Override
    public int getChildViewResId() {
        return R.id.child;
    }
}
