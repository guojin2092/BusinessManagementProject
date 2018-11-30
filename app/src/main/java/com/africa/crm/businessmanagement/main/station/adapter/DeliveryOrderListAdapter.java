package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.TradingOrderInfoBean;
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
public class DeliveryOrderListAdapter extends BaseQuickAdapter<TradingOrderInfoBean, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public DeliveryOrderListAdapter(@Nullable List<TradingOrderInfoBean> data) {
        super(R.layout.item_delivery_order_list, data);
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
        TextView tv_money = helper.getView(R.id.tv_money);
        TextView tv_sender = helper.getView(R.id.tv_sender);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        tv_company_name.setText(item.getCompany());
        tv_money.setText(item.getMoney());
        tv_sender.setText(item.getSender());
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
