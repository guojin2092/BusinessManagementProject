package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.CompanyTaskInfo;
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
public class TaskListAdapter extends BaseQuickAdapter<CompanyTaskInfo, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public TaskListAdapter(@Nullable List<CompanyTaskInfo> data) {
        super(R.layout.item_task_list, data);
    }

    public boolean ismIsDeleted() {
        return mIsDeleted;
    }

    public void setmIsDeleted(boolean mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, CompanyTaskInfo item) {
        TextView tv_task_name = helper.getView(R.id.tv_task_name);
        TextView tv_level_name = helper.getView(R.id.tv_level_name);
        TextView tv_remind_time = helper.getView(R.id.tv_remind_time);
        CheckBox cb_choose = helper.getView(R.id.cb_choose);

        tv_task_name.setText(item.getName());
        tv_level_name.setText(item.getLevelName());
        tv_remind_time.setText(item.getRemindTime());
        cb_choose.setChecked(item.isChosen());

        if (mIsDeleted) {
            cb_choose.setVisibility(View.VISIBLE);
        } else {
            cb_choose.setVisibility(View.GONE);
        }
    }

}
