package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.CompanyInfo;
import com.africa.crm.businessmanagement.main.glide.GlideUtil;
import com.africa.crm.businessmanagement.network.api.ApiConfig;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/15 0015 9:56
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyInfoListAdapter extends BaseQuickAdapter<CompanyInfo, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public CompanyInfoListAdapter(@Nullable List<CompanyInfo> data) {
        super(R.layout.item_company_info_list, data);
    }

    public boolean ismIsDeleted() {
        return mIsDeleted;
    }

    public void setmIsDeleted(boolean mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
        notifyDataSetChanged();
    }


    @Override
    protected void convert(BaseViewHolder helper, CompanyInfo item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        TextView tv_company = helper.getView(R.id.tv_company);
        TextView tv_type = helper.getView(R.id.tv_type);
        TextView tv_location = helper.getView(R.id.tv_location);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        GlideUtil.showImg(iv_icon, item.getHead());
        tv_company.setText(item.getName());
        if (!TextUtils.isEmpty(item.getType())) {
            tv_type.setVisibility(View.VISIBLE);
            tv_type.setText(item.getTypeName());
        } else {
            tv_type.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(item.getArea())) {
            tv_location.setVisibility(View.VISIBLE);
            tv_location.setText(item.getArea());
        } else {
            tv_location.setVisibility(View.GONE);
        }
        cb_choose.setChecked(item.isChosen());
        if (mIsDeleted) {
            cb_choose.setVisibility(View.VISIBLE);
        } else {
            cb_choose.setVisibility(View.GONE);
        }
    }
}
