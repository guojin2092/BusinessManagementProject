package com.africa.crm.businessmanagementproject.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagementproject.R;
import com.africa.crm.businessmanagementproject.bean.GoodAlertBean;
import com.africa.crm.businessmanagementproject.main.adapter.WorkStationListAdapter;
import com.africa.crm.businessmanagementproject.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagementproject.network.error.ComConsumer;
import com.africa.crm.businessmanagementproject.network.util.RxUtils;
import com.africa.crm.businessmanagementproject.station.CostumerManagementActivity;
import com.africa.crm.businessmanagementproject.widget.GridItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import baselibrary.common.util.ListUtils;
import baselibrary.common.util.ToastUtils;
import baselibrary.library.base.progress.BaseActivityProgress;
import butterknife.BindView;
import io.reactivex.functions.Consumer;

public class MainActivity extends BaseActivityProgress {
    @BindView(R.id.titlebar_back)
    ImageView titlebar_back;
    @BindView(R.id.titlebar_name)
    TextView titlebar_name;
    @BindView(R.id.rv_work_station)
    RecyclerView rv_work_station;

    @BindView(R.id.ll_message)
    LinearLayout ll_message;
    @BindView(R.id.iv_cancel)
    ImageView iv_cancel;

    private List<WorkStationInfo> mWorkStationInfoList = new ArrayList<>();
    private WorkStationListAdapter mWorkStationListAdapter;

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {
        super.initView();
        titlebar_back.setVisibility(View.GONE);
        titlebar_name.setText(getString(R.string.work_station));

        iv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TranslateAnimation hideAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 1.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f);
                hideAnim.setDuration(300);
                ll_message.startAnimation(hideAnim);
                ll_message.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void initData() {
        WorkStationInfo workStationInfo = new WorkStationInfo();
        workStationInfo.setWork_type("1");
        workStationInfo.setWork_name(getString(R.string.enterprise_information_management));
        mWorkStationInfoList.add(workStationInfo);
        WorkStationInfo workStationInfo2 = new WorkStationInfo();
        workStationInfo2.setWork_type("2");
        workStationInfo2.setWork_name(getString(R.string.enterprise_account_management));
        mWorkStationInfoList.add(workStationInfo2);
        WorkStationInfo workStationInfo3 = new WorkStationInfo();
        workStationInfo3.setWork_type("3");
        workStationInfo3.setWork_name(getString(R.string.supplier_management));
        mWorkStationInfoList.add(workStationInfo3);
        WorkStationInfo workStationInfo4 = new WorkStationInfo();
        workStationInfo4.setWork_type("4");
        workStationInfo4.setWork_name(getString(R.string.product_management));
        mWorkStationInfoList.add(workStationInfo4);
        WorkStationInfo workStationInfo5 = new WorkStationInfo();
        workStationInfo5.setWork_type("5");
        workStationInfo5.setWork_name(getString(R.string.customer_management));
        mWorkStationInfoList.add(workStationInfo5);
        WorkStationInfo workStationInfo6 = new WorkStationInfo();
        workStationInfo6.setWork_type("6");
        workStationInfo6.setWork_name(getString(R.string.contact_management));
        mWorkStationInfoList.add(workStationInfo6);
        WorkStationInfo workStationInfo7 = new WorkStationInfo();
        workStationInfo7.setWork_type("7");
        workStationInfo7.setWork_name(getString(R.string.trading_order_management));
        mWorkStationInfoList.add(workStationInfo7);
        WorkStationInfo workStationInfo8 = new WorkStationInfo();
        workStationInfo8.setWork_type("8");
        workStationInfo8.setWork_name(getString(R.string.quotation_management));
        mWorkStationInfoList.add(workStationInfo8);
        WorkStationInfo workStationInfo9 = new WorkStationInfo();
        workStationInfo9.setWork_type("9");
        workStationInfo9.setWork_name(getString(R.string.sales_order_management));
        mWorkStationInfoList.add(workStationInfo9);
        setWorkStationDatas(mWorkStationInfoList);
    }

    /**
     * 设置工作台数据
     *
     * @param workStationInfoList
     */
    private void setWorkStationDatas(final List<WorkStationInfo> workStationInfoList) {
        if (!ListUtils.isEmpty(workStationInfoList)) {
            mWorkStationListAdapter = new WorkStationListAdapter(R.layout.item_work_station_list, workStationInfoList);
            rv_work_station.setAdapter(mWorkStationListAdapter);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
            rv_work_station.setLayoutManager(layoutManager);
            rv_work_station.addItemDecoration(new GridItemDecoration(0, 3));
            rv_work_station.setHasFixedSize(true);
            rv_work_station.setNestedScrollingEnabled(false);

            mWorkStationListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    ToastUtils.show(MainActivity.this, workStationInfoList.get(position).getWork_name());
                    if (workStationInfoList.get(position).getWork_type().equals("5")) {
                        startActivity(new Intent(MainActivity.this, CostumerManagementActivity.class));
                        overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                    }
                }
            });
        }
    }
}