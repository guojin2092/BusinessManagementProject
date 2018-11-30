package com.africa.crm.businessmanagement.main.station.adapter;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.station.bean.ServiceTrackingInfoBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Project：android-heshui
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/8 0008 11:20
 * Modification  History:
 * Why & What is modified:
 */
public class ServiceTrackingListAdapter extends BaseQuickAdapter<ServiceTrackingInfoBean, BaseViewHolder> {

    public ServiceTrackingListAdapter(@Nullable List<ServiceTrackingInfoBean> data) {
        super(R.layout.item_service_tracking_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ServiceTrackingInfoBean logisticsBean) {
        View top_line = helper.getView(R.id.top_line);
        View bottom_line = helper.getView(R.id.bottom_line);
        View view_circle = helper.getView(R.id.view_circle);
        View view_gray_top = helper.getView(R.id.view_gray_top);
        View view_gray_bottom = helper.getView(R.id.view_gray_bottom);
        TextView tv_service_state = helper.getView(R.id.tv_service_state);
        TextView tv_service_date = helper.getView(R.id.tv_service_date);
        TextView tv_add_tracking = helper.getView(R.id.tv_add_tracking);
        helper.addOnClickListener(R.id.tv_add_tracking);
        if (helper.getLayoutPosition() == 0) {
            top_line.setVisibility(View.INVISIBLE);
            view_gray_top.setVisibility(View.GONE);
            view_gray_bottom.setVisibility(View.GONE);
            view_circle.setBackground(ContextCompat.getDrawable(mContext, R.drawable.red_circle));
            tv_service_state.setTextColor(ContextCompat.getColor(mContext, R.color.A606060));
        } else {
            top_line.setVisibility(View.VISIBLE);
            view_gray_top.setVisibility(View.VISIBLE);
            view_gray_bottom.setVisibility(View.VISIBLE);
            view_circle.setBackground(ContextCompat.getDrawable(mContext, R.drawable.gray_circle));
            tv_service_state.setTextColor(ContextCompat.getColor(mContext, R.color.D5D5D5));
        }
        if (getData().size() > 1) {
            if (helper.getLayoutPosition() == getData().size() - 1) {
                bottom_line.setVisibility(View.GONE);
                view_gray_bottom.setVisibility(View.GONE);
                tv_add_tracking.setVisibility(View.VISIBLE);
            } else {
                bottom_line.setVisibility(View.VISIBLE);
            }
        } else {
            bottom_line.setVisibility(View.VISIBLE);
            tv_add_tracking.setVisibility(View.VISIBLE);
        }
        tv_service_state.setText(logisticsBean.getAcceptStation());
        tv_service_date.setText(logisticsBean.getAcceptTime().substring(0, 5) + "\n" + logisticsBean.getAcceptTime().substring(6));

    }
}
