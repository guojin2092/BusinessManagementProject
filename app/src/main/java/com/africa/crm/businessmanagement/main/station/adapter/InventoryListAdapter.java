package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.CompanyInventoryInfo;
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
public class InventoryListAdapter extends BaseQuickAdapter<CompanyInventoryInfo, BaseViewHolder> {

    public InventoryListAdapter(@Nullable List<CompanyInventoryInfo> data) {
        super(R.layout.item_inventory_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyInventoryInfo item) {
        TextView tv_product_name = helper.getView(R.id.tv_product_name);
        TextView tv_type = helper.getView(R.id.tv_type);
        TextView tv_num = helper.getView(R.id.tv_num);
        TextView tv_time = helper.getView(R.id.tv_time);
        tv_product_name.setText(item.getProductName());
        tv_type.setText(item.getTypeName());
        tv_num.setText(item.getNum());
        tv_time.setText(item.getCreateTime());
    }

}
