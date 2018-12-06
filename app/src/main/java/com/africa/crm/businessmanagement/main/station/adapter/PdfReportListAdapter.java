package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.PdfInfoBean;
import com.africa.crm.businessmanagement.main.bean.PurchasingInfoBean;
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
public class PdfReportListAdapter extends BaseQuickAdapter<PdfInfoBean, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public PdfReportListAdapter(@Nullable List<PdfInfoBean> data) {
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
    protected void convert(BaseViewHolder helper, PdfInfoBean item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_date = helper.getView(R.id.tv_date);
        TextView tv_remark = helper.getView(R.id.tv_remark);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        tv_name.setText(item.getName());
        tv_date.setText(item.getTime());
        tv_remark.setText(item.getRemark());
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
