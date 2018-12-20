package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.OrderProductInfo;
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
public class OrderProductListAdapter extends BaseQuickAdapter<OrderProductInfo, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public OrderProductListAdapter(@Nullable List<OrderProductInfo> data) {
        super(R.layout.item_product_list, data);
    }

    public boolean ismIsDeleted() {
        return mIsDeleted;
    }

    public void setmIsDeleted(boolean mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderProductInfo item) {
        TextView tv_product_name = helper.getView(R.id.tv_product_name);
        TextView tv_product_num = helper.getView(R.id.tv_product_num);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        tv_product_name.setText(item.getName());
        tv_product_num.setText(item.getNum());
        cb_choose.setChecked(item.isChosen());
        if (mIsDeleted) {
            cb_choose.setVisibility(View.VISIBLE);
        } else {
            cb_choose.setVisibility(View.GONE);
        }
    }

}
