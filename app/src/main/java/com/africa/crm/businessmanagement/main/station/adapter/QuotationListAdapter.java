package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.CompanyQuotationInfo;
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
public class QuotationListAdapter extends BaseQuickAdapter<CompanyQuotationInfo, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public QuotationListAdapter(@Nullable List<CompanyQuotationInfo> data) {
        super(R.layout.item_company_quotation_list, data);
    }

    public boolean ismIsDeleted() {
        return mIsDeleted;
    }

    public void setmIsDeleted(boolean mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyQuotationInfo item) {
        TextView tv_quotation_name = helper.getView(R.id.tv_quotation_name);
        TextView tv_price = helper.getView(R.id.tv_price);
        TextView tv_customer_name = helper.getView(R.id.tv_customer_name);
        TextView tv_create_time = helper.getView(R.id.tv_create_time);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        tv_quotation_name.setText(item.getName());
        tv_price.setText(item.getPrice());
        tv_customer_name.setText(item.getCustomerName());
        if (!TextUtils.isEmpty(item.getCreateTime())) {
            tv_create_time.setText(item.getCreateTime());
        } else {
            tv_create_time.setText("");
        }
        cb_choose.setChecked(item.isChosen());
        if (mIsDeleted) {
            cb_choose.setVisibility(View.VISIBLE);
        } else {
            cb_choose.setVisibility(View.GONE);
        }
    }

}
