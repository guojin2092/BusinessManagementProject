package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.CompanyExpenditureInfoB;
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
public class ExpenditureListAdapterB extends BaseQuickAdapter<CompanyExpenditureInfoB, BaseViewHolder> {

    public ExpenditureListAdapterB(@Nullable List<CompanyExpenditureInfoB> data) {
        super(R.layout.item_expenditure_listb, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyExpenditureInfoB item) {
        TextView tv_title = helper.getView(R.id.tv_title);
        TextView tv_date = helper.getView(R.id.tv_date);
        TextView tv_money = helper.getView(R.id.tv_money);

        tv_title.setText(item.getEstimateTitle());
        tv_date.setText(item.getPayDate());
        tv_money.setText(item.getPrice());
    }

}
