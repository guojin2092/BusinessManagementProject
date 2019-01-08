package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.CompanyProductInfo;
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
public class ProductListAdapter extends BaseQuickAdapter<CompanyProductInfo, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public ProductListAdapter(@Nullable List<CompanyProductInfo> data) {
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
    protected void convert(BaseViewHolder helper, CompanyProductInfo item) {
        TextView tv_product_name = helper.getView(R.id.tv_product_name);
        TextView tv_type = helper.getView(R.id.tv_type);
        TextView tv_supplier_name = helper.getView(R.id.tv_supplier_name);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        tv_product_name.setText(item.getName());
        tv_type.setText(item.getTypeName());
        cb_choose.setChecked(item.isChosen());
        tv_supplier_name.setText(item.getSupplierName());
        if (mIsDeleted) {
            cb_choose.setVisibility(View.VISIBLE);
        } else {
            cb_choose.setVisibility(View.GONE);
        }
    }

}
