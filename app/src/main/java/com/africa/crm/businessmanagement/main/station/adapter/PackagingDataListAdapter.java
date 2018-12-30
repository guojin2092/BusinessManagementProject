package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.CompanyPackagingDataInfo;
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
public class PackagingDataListAdapter extends BaseQuickAdapter<CompanyPackagingDataInfo, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public PackagingDataListAdapter(@Nullable List<CompanyPackagingDataInfo> data) {
        super(R.layout.item_packaging_data_list, data);
    }

    public boolean ismIsDeleted() {
        return mIsDeleted;
    }

    public void setmIsDeleted(boolean mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyPackagingDataInfo item) {
        TextView tv_package_num = helper.getView(R.id.tv_package_num);
        TextView tv_start_date = helper.getView(R.id.tv_start_date);
        TextView tv_end_date = helper.getView(R.id.tv_end_date);
        TextView tv_operation_person = helper.getView(R.id.tv_operation_person);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        tv_package_num.setText(item.getNum());
        tv_start_date.setText(item.getStartDate());
        tv_end_date.setText(item.getEndDate());
        tv_operation_person.setText(item.getUserNickName());
        cb_choose.setChecked(item.isChosen());
        if (mIsDeleted) {
            cb_choose.setVisibility(View.VISIBLE);
        } else {
            cb_choose.setVisibility(View.GONE);
        }
    }

}
