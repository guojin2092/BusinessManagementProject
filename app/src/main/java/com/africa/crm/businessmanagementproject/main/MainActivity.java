package com.africa.crm.businessmanagementproject.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.africa.crm.businessmanagementproject.R;
import com.africa.crm.businessmanagementproject.main.adapter.WorkStationListAdapter;
import com.africa.crm.businessmanagementproject.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagementproject.widget.GridItemDecoration;

import java.util.ArrayList;
import java.util.List;

import baselibrary.common.util.ListUtils;
import baselibrary.library.base.progress.BaseActivityProgress;
import butterknife.BindView;

public class MainActivity extends BaseActivityProgress {
    @BindView(R.id.titlebar_name)
    TextView titlebar_name;
    @BindView(R.id.rv_work_station)
    RecyclerView rv_work_station;

    private List<WorkStationInfo> mWorkStationInfoList = new ArrayList<>();
    private WorkStationListAdapter mWorkStationListAdapter;

    /**
     * 在activity中请求回调,显示登录界面
     *
     * @param activity
     */
    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.right_push_in, R.anim.hold);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initView() {
        super.initView();
        titlebar_name.setText(getString(R.string.work_station));
    }

    @Override
    public void initData() {
        WorkStationInfo workStationInfo = new WorkStationInfo();
        workStationInfo.setWork_type("1");
        mWorkStationInfoList.add(workStationInfo);
        WorkStationInfo workStationInfo2 = new WorkStationInfo();
        workStationInfo2.setWork_type("2");
        mWorkStationInfoList.add(workStationInfo2);
        WorkStationInfo workStationInfo3 = new WorkStationInfo();
        workStationInfo3.setWork_type("3");
        mWorkStationInfoList.add(workStationInfo3);
        WorkStationInfo workStationInfo4 = new WorkStationInfo();
        workStationInfo4.setWork_type("4");
        mWorkStationInfoList.add(workStationInfo4);
        WorkStationInfo workStationInfo5 = new WorkStationInfo();
        workStationInfo5.setWork_type("5");
        mWorkStationInfoList.add(workStationInfo5);
        WorkStationInfo workStationInfo6 = new WorkStationInfo();
        workStationInfo6.setWork_type("6");
        mWorkStationInfoList.add(workStationInfo6);
        WorkStationInfo workStationInfo7 = new WorkStationInfo();
        workStationInfo7.setWork_type("7");
        mWorkStationInfoList.add(workStationInfo7);
        WorkStationInfo workStationInfo8 = new WorkStationInfo();
        workStationInfo8.setWork_type("8");
        mWorkStationInfoList.add(workStationInfo8);
        WorkStationInfo workStationInfo9 = new WorkStationInfo();
        workStationInfo9.setWork_type("9");
        mWorkStationInfoList.add(workStationInfo9);
        setWorkStationDatas(mWorkStationInfoList);
    }

    /**
     * 设置工作台数据
     *
     * @param workStationInfoList
     */
    private void setWorkStationDatas(List<WorkStationInfo> workStationInfoList) {
        if (!ListUtils.isEmpty(workStationInfoList)) {
            mWorkStationListAdapter = new WorkStationListAdapter(R.layout.item_work_station_list, workStationInfoList);
            rv_work_station.setAdapter(mWorkStationListAdapter);
            GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
            rv_work_station.setLayoutManager(layoutManager);
            rv_work_station.addItemDecoration(new GridItemDecoration(0, 3));
            rv_work_station.setHasFixedSize(true);
            rv_work_station.setNestedScrollingEnabled(false);
        }
    }
}
