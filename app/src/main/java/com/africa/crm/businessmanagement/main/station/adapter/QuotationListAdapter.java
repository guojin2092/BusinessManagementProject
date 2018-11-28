package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.station.bean.TradingOrderInfoBean;
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
public class QuotationListAdapter extends BaseQuickAdapter<TradingOrderInfoBean, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public QuotationListAdapter(@Nullable List<TradingOrderInfoBean> data) {
        super(R.layout.item_quotation_list, data);
    }

    public boolean ismIsDeleted() {
        return mIsDeleted;
    }

    public void setmIsDeleted(boolean mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, TradingOrderInfoBean item) {
        TextView tv_company_name = helper.getView(R.id.tv_company_name);
        TextView tv_quoter = helper.getView(R.id.tv_quoter);
        TextView tv_date = helper.getView(R.id.tv_date);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        tv_company_name.setText(item.getCompany());
        tv_quoter.setText(item.getQuoter());
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
