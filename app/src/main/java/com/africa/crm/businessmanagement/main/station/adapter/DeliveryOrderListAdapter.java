package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.CompanyDeliveryOrderInfo;
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
public class DeliveryOrderListAdapter extends BaseQuickAdapter<CompanyDeliveryOrderInfo, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public DeliveryOrderListAdapter(@Nullable List<CompanyDeliveryOrderInfo> data) {
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
    protected void convert(BaseViewHolder helper, CompanyDeliveryOrderInfo item) {
        TextView tv_delivery_order_name = helper.getView(R.id.tv_delivery_order_name);
        TextView tv_sale_code = helper.getView(R.id.tv_sale_code);
        TextView tv_logistics_code = helper.getView(R.id.tv_logistics_code);
        TextView tv_create_time = helper.getView(R.id.tv_create_time);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        tv_delivery_order_name.setText(item.getName());
        tv_sale_code.setText("发货单编号："+item.getCode());
        tv_logistics_code.setText("物流单号：" + item.getLogisticsCode());
        if (!TextUtils.isEmpty(item.getCreateTime())) {
            tv_create_time.setText(item.getCreateTime());
        } else {
            tv_create_time.setText("");
        }        cb_choose.setChecked(item.isChosen());

        if (mIsDeleted) {
            cb_choose.setVisibility(View.VISIBLE);
        } else {
            cb_choose.setVisibility(View.GONE);
        }
    }

}
