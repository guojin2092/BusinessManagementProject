package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;
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
public class CompanyAccountListAdapter extends BaseQuickAdapter<CompanyAccountInfo, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public CompanyAccountListAdapter(@Nullable List<CompanyAccountInfo> data) {
        super(R.layout.item_company_account_list, data);
    }

    public boolean ismIsDeleted() {
        return mIsDeleted;
    }

    public void setmIsDeleted(boolean mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyAccountInfo item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        TextView tv_company = helper.getView(R.id.tv_company);
        TextView tv_location = helper.getView(R.id.tv_location);
        TextView tv_company_account = helper.getView(R.id.tv_company_account);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        GlideUtil.showImg(iv_icon, item.getHead());
        tv_company.setText(item.getName());
        tv_location.setText(item.getArea());
        tv_company_account.setText("企业账号：" + item.getUserName());
        cb_choose.setChecked(item.isChosen());
        if (mIsDeleted) {
            cb_choose.setVisibility(View.VISIBLE);
        } else {
            cb_choose.setVisibility(View.GONE);
        }
    }

}
