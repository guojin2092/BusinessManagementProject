package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/25 0025 18:29
 * Modification  History:
 * Why & What is modified:
 */
public class PackagingDataPartListAdapter extends BaseQuickAdapter<DicInfo, BaseViewHolder> {
    public PackagingDataPartListAdapter(@Nullable List<DicInfo> data) {
        super(R.layout.item_packaging_data_part_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, DicInfo item) {
        TextView tv_part_name = helper.getView(R.id.tv_part_name);
        TextView tv_part_num = helper.getView(R.id.tv_part_num);
        tv_part_name.setText(item.getText());
        tv_part_num.setText(item.getCode());
    }
}
