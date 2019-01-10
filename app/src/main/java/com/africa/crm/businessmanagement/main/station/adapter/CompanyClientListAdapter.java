package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.CompanyClientInfo;
import com.africa.crm.businessmanagement.main.glide.GlideUtil;
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
public class CompanyClientListAdapter extends BaseQuickAdapter<CompanyClientInfo, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public boolean ismIsDeleted() {
        return mIsDeleted;
    }

    public void setmIsDeleted(boolean mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
        notifyDataSetChanged();
    }

    public CompanyClientListAdapter(@Nullable List<CompanyClientInfo> data) {
        super(R.layout.item_company_client_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyClientInfo item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        TextView tv_from_username = helper.getView(R.id.tv_from_username);
        TextView tv_industry_name = helper.getView(R.id.tv_industry_name);
        TextView tv_client_name = helper.getView(R.id.tv_client_name);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        GlideUtil.showImg(iv_icon, item.getHead());
        tv_from_username.setText(item.getUserNickName());
        tv_industry_name.setText(item.getIndustryName());
        tv_client_name.setText(item.getName());
        cb_choose.setChecked(item.isChosen());
        if (mIsDeleted) {
            cb_choose.setVisibility(View.VISIBLE);
        } else {
            cb_choose.setVisibility(View.GONE);
        }
    }
}
