package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.library.base.BaseActivity;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.StatisticalListAdapter;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2019/1/15 0015 9:05
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyStatisticalFormActivity extends BaseActivity {
    @BindView(R.id.titlebar_back)
    ImageView titlebar_back;
    @BindView(R.id.titlebar_name)
    TextView titlebar_name;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private StatisticalListAdapter mStatisticalListAdapter;
    private List<DicInfo2> mDicInfoList = new ArrayList<>();

    private WorkStationInfo mWorkStationInfo;
    private String mRoleCode = "";

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanyStatisticalFormActivity.class);
        intent.putExtra("info", workStationInfo);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_statistical_form);
    }

    @Override
    public void initView() {
        titlebar_back.setOnClickListener(this);
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        mRoleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
    }

    @Override
    public void initData() {
        mDicInfoList.clear();
        mDicInfoList.add(new DicInfo2("账单金额", "1"));
        mDicInfoList.add(new DicInfo2("账单数量", "2"));
        mDicInfoList.add(new DicInfo2("支付记录", "3"));
        mDicInfoList.add(new DicInfo2("服务记录", "4"));
        if (!mRoleCode.equals("companySales")) {
            mDicInfoList.add(new DicInfo2("库存统计", "5"));
        }
        mStatisticalListAdapter = new StatisticalListAdapter(mDicInfoList);
        recyclerView.setAdapter(mStatisticalListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        mStatisticalListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                CompanyStatisticalFormWebActivity.startActivity(CompanyStatisticalFormActivity.this, mDicInfoList.get(position));
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlebar_back:
                onBackPressed();
                break;
        }
    }

}
