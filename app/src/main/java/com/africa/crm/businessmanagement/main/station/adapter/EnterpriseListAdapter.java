package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.EnterpriseInfoBean;
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
public class EnterpriseListAdapter extends BaseQuickAdapter<EnterpriseInfoBean, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public EnterpriseListAdapter(@Nullable List<EnterpriseInfoBean> data) {
        super(R.layout.item_enterprise_list, data);
    }

    public boolean ismIsDeleted() {
        return mIsDeleted;
    }

    public void setmIsDeleted(boolean mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, EnterpriseInfoBean item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        TextView tv_company = helper.getView(R.id.tv_company);
        TextView tv_type = helper.getView(R.id.tv_type);
        TextView tv_location = helper.getView(R.id.tv_location);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        int icon = Integer.valueOf(item.getIcon());
        int iconSrc = 0;
        switch (icon) {
            case 1:
                iconSrc = R.drawable.iv_head_icon1;
                break;
            case 2:
                iconSrc = R.drawable.iv_head_icon2;
                break;
            case 3:
                iconSrc = R.drawable.iv_head_icon3;
                break;
        }
        iv_icon.setImageResource(iconSrc);
        tv_company.setText(item.getCompany());
        tv_type.setText(item.getType());
        tv_location.setText(item.getLocation());
        if (mIsDeleted) {
            cb_choose.setVisibility(View.VISIBLE);
        } else {
            cb_choose.setVisibility(View.GONE);
        }
        if (item.isChosen()) {
            cb_choose.setChecked(true);
        } else {
            cb_choose.setChecked(false);
        }
    }
}
