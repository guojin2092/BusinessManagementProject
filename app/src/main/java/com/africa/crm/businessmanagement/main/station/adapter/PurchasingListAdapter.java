package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.CompanyPurchasingOrderInfo;
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
public class PurchasingListAdapter extends BaseQuickAdapter<CompanyPurchasingOrderInfo, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public PurchasingListAdapter(@Nullable List<CompanyPurchasingOrderInfo> data) {
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
    protected void convert(BaseViewHolder helper, CompanyPurchasingOrderInfo item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_code = helper.getView(R.id.tv_code);
        TextView tv_arrive_date = helper.getView(R.id.tv_arrive_date);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        tv_name.setText(item.getName());
        tv_code.setText(item.getCode());
        tv_arrive_date.setText("送达时间：" + item.getArriveDate());
        cb_choose.setChecked(item.isChosen());

        if (mIsDeleted) {
            cb_choose.setVisibility(View.VISIBLE);
        } else {
            cb_choose.setVisibility(View.GONE);
        }
    }

}
