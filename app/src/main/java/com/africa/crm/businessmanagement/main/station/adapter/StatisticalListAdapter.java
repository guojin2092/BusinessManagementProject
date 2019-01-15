package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
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
public class StatisticalListAdapter extends BaseQuickAdapter<DicInfo2, BaseViewHolder> {
    private boolean mIsDeleted = false;

    public StatisticalListAdapter(@Nullable List<DicInfo2> data) {
        super(R.layout.item_statistical_list, data);
    }

    public boolean ismIsDeleted() {
        return mIsDeleted;
    }

    public void setmIsDeleted(boolean mIsDeleted) {
        this.mIsDeleted = mIsDeleted;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, DicInfo2 item) {
        TextView tv_type = helper.getView(R.id.tv_type);
        View view_bottom = helper.getView(R.id.view_bottom);
        tv_type.setText(item.getName());

        if (getData().size() > 1) {
            if (helper.getLayoutPosition() == getData().size() - 1) {
                view_bottom.setVisibility(View.GONE);
            } else {
                view_bottom.setVisibility(View.VISIBLE);
            }
        } else {
            view_bottom.setVisibility(View.VISIBLE);
        }
    }

}
