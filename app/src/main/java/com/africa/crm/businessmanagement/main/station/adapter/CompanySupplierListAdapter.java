package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfo;
import com.africa.crm.businessmanagement.main.glide.GlideUtil;
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
public class CompanySupplierListAdapter extends BaseQuickAdapter<CompanySupplierInfo, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public CompanySupplierListAdapter(@Nullable List<CompanySupplierInfo> data) {
        super(R.layout.item_company_supplier_list, data);
    }

    public boolean ismIsDeleted() {
        return mIsDeleted;
    }

    public void setmIsDeleted(boolean mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanySupplierInfo item) {
        ImageView iv_icon = helper.getView(R.id.iv_icon);
        TextView tv_supplier_name = helper.getView(R.id.tv_supplier_name);
        TextView tv_company_name = helper.getView(R.id.tv_company_name);
        TextView tv_location = helper.getView(R.id.tv_location);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        GlideUtil.showImg(iv_icon, item.getHead());
        tv_supplier_name.setText(item.getName());
        tv_company_name.setText(item.getCompanyName());
        tv_location.setText(item.getArea());
        cb_choose.setChecked(item.isChosen());
        if (mIsDeleted) {
            cb_choose.setVisibility(View.VISIBLE);
        } else {
            cb_choose.setVisibility(View.GONE);
        }
    }

}
