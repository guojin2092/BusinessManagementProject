package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.CompanyServiceRecordInfo;
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
public class ServiceRecordListAdapter extends BaseQuickAdapter<CompanyServiceRecordInfo, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public ServiceRecordListAdapter(@Nullable List<CompanyServiceRecordInfo> data) {
        super(R.layout.item_service_record_list, data);
    }

    public boolean ismIsDeleted() {
        return mIsDeleted;
    }

    public void setmIsDeleted(boolean mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyServiceRecordInfo item) {
        TextView tv_name = helper.getView(R.id.tv_name);
        TextView tv_reason = helper.getView(R.id.tv_reason);
        TextView tv_create_time = helper.getView(R.id.tv_create_time);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        tv_name.setText(item.getName());
        tv_reason.setText(item.getReason());
        if (!TextUtils.isEmpty(item.getCreateTime())) {
            tv_create_time.setText(item.getCreateTime());
        } else {
            tv_create_time.setText("");
        }
        cb_choose.setChecked(item.isChosen());

        if (mIsDeleted) {
            cb_choose.setVisibility(View.VISIBLE);
        } else {
            cb_choose.setVisibility(View.GONE);
        }
    }

}
