package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.ServiceRecordInfoBean;
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
public class ServiceRecordListAdapter extends BaseQuickAdapter<ServiceRecordInfoBean, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public ServiceRecordListAdapter(@Nullable List<ServiceRecordInfoBean> data) {
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
    protected void convert(BaseViewHolder helper, ServiceRecordInfoBean item) {
        TextView tv_client_name = helper.getView(R.id.tv_client_name);
        TextView tv_service_name = helper.getView(R.id.tv_service_name);
        TextView tv_service_state = helper.getView(R.id.tv_service_state);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        tv_client_name.setText(item.getClientName());
        tv_service_name.setText(item.getServiceType());
        tv_service_state.setText(item.getServiceState());
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
