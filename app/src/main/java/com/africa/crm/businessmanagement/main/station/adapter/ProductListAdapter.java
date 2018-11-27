package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.station.bean.ProductInfoBean;
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
public class ProductListAdapter extends BaseQuickAdapter<ProductInfoBean, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public ProductListAdapter(@Nullable List<ProductInfoBean> data) {
        super(R.layout.item_product_management_list, data);
    }

    public boolean ismIsDeleted() {
        return mIsDeleted;
    }

    public void setmIsDeleted(boolean mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductInfoBean item) {
        TextView tv_product_name = helper.getView(R.id.tv_product_name);
        TextView tv_type = helper.getView(R.id.tv_type);
        TextView tv_location = helper.getView(R.id.tv_location);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        tv_product_name.setText(item.getProduct());
        tv_type.setText(item.getType());
        tv_location.setText(item.getLocation());
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
