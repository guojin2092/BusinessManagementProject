package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyPackagingDataEvent;
import com.africa.crm.businessmanagement.main.bean.CompanyPackagingDataInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyPackagingDataInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.PackagingDataListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPackagingDataContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyPackagingDataPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseRefreshMvpActivity;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.bigkoo.pickerview.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/28 0028 9:03
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyPackagingDataManagementActivity extends BaseRefreshMvpActivity<CompanyPackagingDataPresenter> implements CompanyPackagingDataContract.View {
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;

    private WorkStationInfo mWorkStationInfo;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private PackagingDataListAdapter mPackagingDataListAdapter;
    private List<CompanyPackagingDataInfo> mPackingDataInfoBeanList = new ArrayList<>();

    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    private TimePickerView pvStartTime, pvEndTime;
    private Date mStartDate, mEndDate;

    /*    @BindView(R.id.spinner_user)
        MySpinner spinner_user;
        private List<UserInfoBean> mUserInfoBeanList = new ArrayList<>();
        private List<DicInfo> mUserInfoList = new ArrayList<>();*/
    private String mUserId = "";
    private String mCompanyId = "";
    private String mRoleCode = "";

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanyPackagingDataManagementActivity.class);
        intent.putExtra("info", workStationInfo);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company_packagingdata_management);
    }

    @Override
    public void initView() {
        super.initView();
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        mRoleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
        /*if (mRoleCode.equals("companyRoot")) {
            spinner_user.setVisibility(View.VISIBLE);
        } else {
            spinner_user.setVisibility(View.GONE);
        }*/
        titlebar_right.setVisibility(View.GONE);
        tv_search.setOnClickListener(this);
        ll_add.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        initTimePicker();
    }

    private void initTimePicker() {
        pvStartTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (mEndDate != null) {
                    if (mEndDate.getTime() < date.getTime()) {
                        toastMsg("起止时间不得小于起始时间");
                        return;
                    }
                }
                mStartDate = date;
                tv_start_time.setText(TimeUtils.getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true));

        pvEndTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (mStartDate != null) {
                    if (date.getTime() < mStartDate.getTime()) {
                        toastMsg("起止时间不得小于起始时间");
                        return;
                    }
                }
                mEndDate = date;
                tv_end_time.setText(TimeUtils.getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true));
    }

    @Override
    protected CompanyPackagingDataPresenter injectPresenter() {
        return new CompanyPackagingDataPresenter();
    }

    @Override
    protected void requestData() {
        mPresenter.getCompanyUserList(page, rows, "", "2", mCompanyId, "1", "");
        pullDownRefresh(page);
    }

    @Override
    public void pullDownRefresh(int page) {
        mPresenter.getCompanyPackagingDataList(page, rows, mCompanyId, mUserId, tv_start_time.getText().toString().trim(), tv_end_time.getText().toString().trim());
       /* if (mRoleCode.equals("companyRoot")) {
            mPresenter.getCompanyPackagingDataList(page, rows, mCompanyId, mUserId, tv_start_time.getText().toString().trim(), tv_end_time.getText().toString().trim());
        } else {
            mPresenter.getCompanyPackagingDataList(page, rows, mCompanyId, String.valueOf(UserInfoManager.getUserLoginInfo(this).getId()), tv_start_time.getText().toString().trim(), tv_end_time.getText().toString().trim());
        }*/
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_start_time:
                if (pvStartTime != null)
                    pvStartTime.show();
                break;
            case R.id.tv_end_time:
                if (pvEndTime != null)
                    pvEndTime.show();
                break;
            case R.id.tv_search:
                page = 1;
                pullDownRefresh(page);
                break;
            case R.id.ll_add:
                CompanyPackagingDataDetailActivity.startActivity(this, "");
                break;
        }
    }


    @Override
    public void initData() {
        mPackagingDataListAdapter = new PackagingDataListAdapter(mPackingDataInfoBeanList);
        recyclerView.setAdapter(mPackagingDataListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }

    @Override
    public void getCompanyUserList(UserManagementInfoBean userManagementInfoBean) {
       /* mUserInfoBeanList.clear();
        mUserInfoBeanList.addAll(userManagementInfoBean.getRows());
        mUserInfoList.clear();
        if (!ListUtils.isEmpty(mUserInfoBeanList)) {
            for (int i = 0; i < mUserInfoBeanList.size(); i++) {
                mUserInfoList.add(new DicInfo(mUserInfoBeanList.get(i).getUserName(), mUserInfoBeanList.get(i).getId()));
            }
        }
        spinner_user.setListDatas(this, mUserInfoList);
        spinner_user.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mUserId = dicInfo.getCode();
            }
        });*/
    }

    @Override
    public void getCompanyPackagingDataList(CompanyPackagingDataInfoBean companyPackagingDataInfoBean) {
        if (companyPackagingDataInfoBean != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companyPackagingDataInfoBean.getRows())) {
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.GONE);
                    layout_no_data.setVisibility(View.VISIBLE);
                    return;
                } else {
                    layout_no_data.setVisibility(View.GONE);
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
                }
                mPackingDataInfoBeanList.clear();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companyPackagingDataInfoBean.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companyPackagingDataInfoBean.getRows())) {
                mPackingDataInfoBeanList.addAll(companyPackagingDataInfoBean.getRows());
                if (mPackagingDataListAdapter != null) {
                    mPackagingDataListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mPackingDataInfoBeanList)) {
                mPackagingDataListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        CompanyPackagingDataDetailActivity.startActivity(CompanyPackagingDataManagementActivity.this, mPackingDataInfoBeanList.get(position).getId());
                    }
                });
            }
        }
    }

    @Subscribe
    public void Event(AddOrSaveCompanyPackagingDataEvent addOrSaveCompanyPackagingDataEvent) {
        toastMsg(addOrSaveCompanyPackagingDataEvent.getMsg());
        page = 1;
        requestData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
