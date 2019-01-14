package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.CompanyUserInfoBean;
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
public class UserListAdapter extends BaseQuickAdapter<CompanyUserInfoBean, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public UserListAdapter(@Nullable List<CompanyUserInfoBean> data) {
        super(R.layout.item_user_list, data);
    }

    public boolean ismIsDeleted() {
        return mIsDeleted;
    }

    public void setmIsDeleted(boolean mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyUserInfoBean item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        TextView tv_company = helper.getView(R.id.tv_company);
        TextView tv_role_name = helper.getView(R.id.tv_role_name);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        GlideUtil.showImg(iv_icon, item.getHead());
        if (!TextUtils.isEmpty(item.getCompanyName())) {
            tv_company.setText(item.getCompanyName());
        } else {
            tv_company.setText("暂无");
        }
        tv_role_name.setText(item.getRoleName());

        if (mIsDeleted) {
            cb_choose.setVisibility(View.VISIBLE);
        } else {
            cb_choose.setVisibility(View.GONE);
        }
        cb_choose.setChecked(item.isChosen());
    }
}
