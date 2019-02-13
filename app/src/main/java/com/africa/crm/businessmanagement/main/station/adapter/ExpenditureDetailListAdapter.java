package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.PayRecordInfo;
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
public class ExpenditureDetailListAdapter extends BaseQuickAdapter<PayRecordInfo, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public ExpenditureDetailListAdapter(@Nullable List<PayRecordInfo> data) {
        super(R.layout.item_expenditure_detail_list, data);
    }

    public boolean ismIsDeleted() {
        return mIsDeleted;
    }

    public void setmIsDeleted(boolean mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, PayRecordInfo item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_date = helper.getView(R.id.tv_date);
        TextView tv_price = helper.getView(R.id.tv_price);

        tv_name.setText(mContext.getString(R.string.fromUser) + ":" + item.getUserNickName());
        tv_date.setText(mContext.getString(R.string.date) + ":" + item.getPayDate());
        tv_price.setText(mContext.getString(R.string.expenditure) + ":" + item.getPrice());
    }

}
