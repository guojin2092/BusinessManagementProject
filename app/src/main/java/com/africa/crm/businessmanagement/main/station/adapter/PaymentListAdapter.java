package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.PaymentInfoBean;
import com.africa.crm.businessmanagement.main.bean.PurchasingInfoBean;
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
public class PaymentListAdapter extends BaseQuickAdapter<PaymentInfoBean, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public PaymentListAdapter(@Nullable List<PaymentInfoBean> data) {
        super(R.layout.item_payment_list, data);
    }

    public boolean ismIsDeleted() {
        return mIsDeleted;
    }

    public void setmIsDeleted(boolean mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, PaymentInfoBean item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_company_name = helper.getView(R.id.tv_company_name);
        TextView tv_date = helper.getView(R.id.tv_date);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        tv_name.setText(item.getUserName());
        tv_company_name.setText(item.getCompanyName());
        tv_date.setText(item.getDate());
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
