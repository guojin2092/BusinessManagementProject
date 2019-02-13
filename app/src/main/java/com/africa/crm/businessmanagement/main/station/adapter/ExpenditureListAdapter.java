package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.CompanyExpenditureInfo;
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
public class ExpenditureListAdapter extends BaseQuickAdapter<CompanyExpenditureInfo, BaseViewHolder> {

    public ExpenditureListAdapter(@Nullable List<CompanyExpenditureInfo> data) {
        super(R.layout.item_expenditure_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyExpenditureInfo item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_operation_name = helper.getView(R.id.tv_operation_name);
        TextView tv_start_time = helper.getView(R.id.tv_start_time);
        TextView tv_end_time = helper.getView(R.id.tv_end_time);
        TextView tv_ys_money = helper.getView(R.id.tv_ys_money);
        TextView tv_sj_money = helper.getView(R.id.tv_sj_money);
        tv_title.setText(item.getTitle());
        tv_operation_name.setText(item.getUserNickName());
        tv_start_time.setText(item.getStartDate());
        tv_end_time.setText(item.getEndDate());
        tv_ys_money.setText(mContext.getString(R.string.Budget_amount) + item.getEstimatePrice());
        tv_sj_money.setText(mContext.getString(R.string.The_actual_amount) + item.getActualPrice());
    }

}
