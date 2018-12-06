package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
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
public class InventoryListAdapter extends BaseQuickAdapter<EnterpriseInfoBean, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public InventoryListAdapter(@Nullable List<EnterpriseInfoBean> data) {
        super(R.layout.item_inventory_list, data);
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
        TextView tv_product_name = helper.getView(R.id.tv_product_name);
        TextView tv_num = helper.getView(R.id.tv_num);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);
        helper.addOnClickListener(R.id.tv_stock_in);
        helper.addOnClickListener(R.id.tv_stock_out);
        tv_product_name.setText(item.getCompany());
        tv_num.setText(item.getAccount());
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
