package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.CompanyPdfInfo;
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
public class PdfReportListAdapter extends BaseQuickAdapter<CompanyPdfInfo, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public PdfReportListAdapter(@Nullable List<CompanyPdfInfo> data) {
        super(R.layout.item_pdf_report_list, data);
    }

    public boolean ismIsDeleted() {
        return mIsDeleted;
    }

    public void setmIsDeleted(boolean mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyPdfInfo item) {
        TextView tv_company_name = helper.getView(R.id.tv_company_name);
        TextView tv_username_name = helper.getView(R.id.tv_username_name);
        TextView tv_file_name = helper.getView(R.id.tv_file_name);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        tv_company_name.setText(item.getCompanyName());
        tv_username_name.setText(item.getUserNickName());
        tv_file_name.setText(item.getName());
        cb_choose.setChecked(item.isChosen());

        if (mIsDeleted) {
            cb_choose.setVisibility(View.VISIBLE);
        } else {
            cb_choose.setVisibility(View.GONE);
        }
    }

}
