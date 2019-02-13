package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
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
        TextView tv_company_account = helper.getView(R.id.tv_company_account);
        TextView tv_nickname = helper.getView(R.id.tv_nickname);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        GlideUtil.showImg(iv_icon, item.getHead());
        tv_company_account.setText(mContext.getString(R.string.Username) + item.getUserName());
        if (!TextUtils.isEmpty(item.getName())) {
            tv_nickname.setText(mContext.getString(R.string.Nickname) + item.getName());
        } else {
            tv_nickname.setText("");
        }
        cb_choose.setChecked(item.isChosen());
        if (mIsDeleted) {
            cb_choose.setVisibility(View.VISIBLE);
        } else {
            cb_choose.setVisibility(View.GONE);
        }
    }

}
