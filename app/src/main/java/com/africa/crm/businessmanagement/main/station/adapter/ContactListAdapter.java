package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.CompanyContactInfo;
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
public class ContactListAdapter extends BaseQuickAdapter<CompanyContactInfo, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public ContactListAdapter(@Nullable List<CompanyContactInfo> data) {
        super(R.layout.item_contact_list, data);
    }

    public boolean ismIsDeleted() {
        return mIsDeleted;
    }

    public void setmIsDeleted(boolean mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyContactInfo item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_location = helper.getView(R.id.tv_location);
        TextView tv_phone_number = helper.getView(R.id.tv_phone_number);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        GlideUtil.showImg(iv_icon, item.getHead());
        tv_name.setText(item.getName());
        tv_phone_number.setText(item.getPhone());
        tv_location.setText(item.getAddress());
        cb_choose.setChecked(item.isChosen());
        if (mIsDeleted) {
            cb_choose.setVisibility(View.VISIBLE);
        } else {
            cb_choose.setVisibility(View.GONE);
        }
    }

}
