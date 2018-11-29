package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.station.adapter.ServiceTrackingListAdapter;
import com.africa.crm.businessmanagement.main.station.bean.ServiceRecordInfoBean;
import com.africa.crm.businessmanagement.main.station.bean.ServiceTrackingInfoBean;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;

import java.util.ArrayList;
import java.util.List;

import baselibrary.common.util.ListUtils;
import baselibrary.library.base.BaseActivity;
import butterknife.BindView;

public class ServiceRecordDetailActivity extends BaseActivity {
    @BindView(R.id.titlebar_back)
    ImageView titlebar_back;
    @BindView(R.id.titlebar_name)
    TextView titlebar_name;
    @BindView(R.id.titlebar_right)
    TextView titlebar_right;
    @BindView(R.id.tv_save)
    TextView tv_save;
    private ServiceRecordInfoBean mServiceRecordInfoBean;

    @BindView(R.id.rv_service_tracking)
    RecyclerView rv_service_tracking;
    private ServiceTrackingListAdapter mServiceTrackingListAdapter;
    private List<ServiceTrackingInfoBean> mServiceTrackingInfoBeans = new ArrayList<>();

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, ServiceRecordInfoBean serviceRecordInfoBean) {
        Intent intent = new Intent(activity, ServiceRecordDetailActivity.class);
        intent.putExtra("info", serviceRecordInfoBean);
        activity.startActivity(intent);
    }


    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_service_record_detail);
    }

    @Override
    public void initView() {
        mServiceRecordInfoBean = (ServiceRecordInfoBean) getIntent().getSerializableExtra("info");
        if (mServiceRecordInfoBean != null) {
            titlebar_name.setText(mServiceRecordInfoBean.getClientName());
        }
        titlebar_back.setOnClickListener(this);
        titlebar_right.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        titlebar_right.setText(R.string.edit);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlebar_back:
                onBackPressed();
                break;
            case R.id.titlebar_right:
                if (titlebar_right.getText().toString().equals(getString(R.string.edit))) {
                    titlebar_right.setText(R.string.cancel);
                    tv_save.setVisibility(View.VISIBLE);
                } else {
                    titlebar_right.setText(R.string.edit);
                    tv_save.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_save:
                showShortToast("保存数据");
                break;
        }
    }

    @Override
    public void initData() {
        ServiceTrackingInfoBean serviceTrackingInfoBean = new ServiceTrackingInfoBean();
        serviceTrackingInfoBean.setAcceptStation("已下单（订单开始处理）");
        serviceTrackingInfoBean.setAcceptTime("11-12 10：20");
        mServiceTrackingInfoBeans.add(serviceTrackingInfoBean);

        ServiceTrackingInfoBean serviceTrackingInfoBean2 = new ServiceTrackingInfoBean();
        serviceTrackingInfoBean2.setAcceptStation("添加订单描述");
        serviceTrackingInfoBean2.setAcceptTime("11-13 10：20");
        mServiceTrackingInfoBeans.add(serviceTrackingInfoBean2);

        ServiceTrackingInfoBean serviceTrackingInfoBean3 = new ServiceTrackingInfoBean();
        serviceTrackingInfoBean3.setAcceptStation("添加订单描述");
        serviceTrackingInfoBean3.setAcceptTime("11-14 11：30");
        mServiceTrackingInfoBeans.add(serviceTrackingInfoBean3);

        setServiceRecordDatas(mServiceTrackingInfoBeans);
    }

    private void setServiceRecordDatas(List<ServiceTrackingInfoBean> serviceRecordDatas) {
        if (!ListUtils.isEmpty(serviceRecordDatas)) {
            mServiceTrackingListAdapter = new ServiceTrackingListAdapter(serviceRecordDatas);
            rv_service_tracking.setAdapter(mServiceTrackingListAdapter);
            rv_service_tracking.setLayoutManager(new LinearLayoutManager(this));
            rv_service_tracking.addItemDecoration(new LineItemDecoration(this, DividerItemDecoration.VERTICAL, 0, 0));
            rv_service_tracking.setHasFixedSize(true);
            rv_service_tracking.setNestedScrollingEnabled(false);
        }
    }

}
