package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.station.bean.PurchasingInfoBean;
import com.africa.crm.businessmanagement.main.station.bean.ServiceRecordInfoBean;
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
public class PurchasingListAdapter extends BaseQuickAdapter<PurchasingInfoBean, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public PurchasingListAdapter(@Nullable List<PurchasingInfoBean> data) {
        super(R.layout.item_purchasing_list, data);
    }

    public boolean ismIsDeleted() {
        return mIsDeleted;
    }

    public void setmIsDeleted(boolean mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, PurchasingInfoBean item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_type = helper.getView(R.id.tv_type);
        TextView tv_number = helper.getView(R.id.tv_number);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        tv_name.setText(item.getName());
        tv_type.setText(item.getType());
        tv_number.setText(item.getNumber());
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
