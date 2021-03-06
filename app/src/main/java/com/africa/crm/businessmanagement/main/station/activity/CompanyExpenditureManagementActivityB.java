package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyExpanditureEventB;
import com.africa.crm.businessmanagement.main.bean.CompanyExpenditureInfoB;
import com.africa.crm.businessmanagement.main.bean.CompanyeExpenditureInfoBeanB;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyExpenditureInfoBDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.ExpenditureListAdapterB;
import com.africa.crm.businessmanagement.main.station.contract.CompanyExpenditureManagementContractB;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyExpenditurePresenterB;
import com.africa.crm.businessmanagement.mvp.activity.BaseRefreshMvpActivity;
import com.africa.crm.businessmanagement.widget.DifferentDataUtil;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.StringUtil;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.bigkoo.pickerview.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_EXPENDITURE_B_LIST;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/28 0028 9:03
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyExpenditureManagementActivityB extends BaseRefreshMvpActivity<CompanyExpenditurePresenterB> implements CompanyExpenditureManagementContractB.View {
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.tv_search)
    TextView tv_search;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ExpenditureListAdapterB mExpenditureListAdapter;
    private List<CompanyExpenditureInfoB> mExpenditureInfoList = new ArrayList<>();

    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    private TimePickerView pvStartTime, pvEndTime;
    private Date mStartDate, mEndDate;

    private WorkStationInfo mWorkStationInfo;
    private String mCompanyId = "";
    private String mUserId = "";

    private GreendaoManager<CompanyExpenditureInfoB, CompanyExpenditureInfoBDao> mExpenditureInfoBDaoManager;
    private List<CompanyExpenditureInfoB> mExpenditureInfoBList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanyExpenditureManagementActivityB.class);
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
        setContentView(R.layout.activity_expenditure_management_b);
    }

    @Override
    public void initView() {
        super.initView();
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
        titlebar_right.setVisibility(View.GONE);
        tv_search.setOnClickListener(this);
        ll_add.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        initTimePicker();
        mExpenditureInfoBDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyExpenditureInfoBDao());
    }

    private void initTimePicker() {
        pvStartTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (mEndDate != null) {
                    if (mEndDate.getTime() < date.getTime()) {
                        toastMsg(getString(R.string.The_end_time_cannot_be_earlier_than_the_start_time));
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
                        toastMsg(getString(R.string.The_end_time_cannot_be_earlier_than_the_start_time));
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
    protected CompanyExpenditurePresenterB injectPresenter() {
        return new CompanyExpenditurePresenterB();
    }

    @Override
    protected void requestData() {
        pullDownRefresh(page);
    }

    @Override
    public void pullDownRefresh(int page) {
        mPresenter.getExpenditureListB(page, rows, mCompanyId, mUserId, tv_start_time.getText().toString().trim(), tv_end_time.getText().toString().trim());
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
                CompanyExpenditureDetailActivityB.startActivity(CompanyExpenditureManagementActivityB.this, "", 0l, "", "");
                break;
        }
    }

    @Override
    public void initData() {
        mExpenditureListAdapter = new ExpenditureListAdapterB(mExpenditureInfoList);
        recyclerView.setAdapter(mExpenditureListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }


    @Override
    public void getExpenditureListB(CompanyeExpenditureInfoBeanB companyeExpenditureInfoBeanB) {
        if (companyeExpenditureInfoBeanB != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companyeExpenditureInfoBeanB.getRows())) {
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.GONE);
                    layout_no_data.setVisibility(View.VISIBLE);
                    return;
                } else {
                    layout_no_data.setVisibility(View.GONE);
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
                }
                mExpenditureInfoList.clear();
                mExpenditureInfoBList = mExpenditureInfoBDaoManager.queryAll();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companyeExpenditureInfoBeanB.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companyeExpenditureInfoBeanB.getRows())) {
                mExpenditureInfoList.addAll(companyeExpenditureInfoBeanB.getRows());
                List<CompanyExpenditureInfoB> addList = DifferentDataUtil.addExpenditureBDataToLocal(mExpenditureInfoList, mExpenditureInfoBList);
                if (!ListUtils.isEmpty(addList)) {
                    for (CompanyExpenditureInfoB companyInfo : addList) {
                        mExpenditureInfoBDaoManager.insertOrReplace(companyInfo);
                    }
                    mExpenditureInfoBList = new ArrayList<>();
                    mExpenditureInfoBList = mExpenditureInfoBDaoManager.queryAll();
                }
                for (CompanyExpenditureInfoB info : mExpenditureInfoList) {
                    for (CompanyExpenditureInfoB localInfo : mExpenditureInfoBList) {
                        if (!TextUtils.isEmpty(info.getId()) && !TextUtils.isEmpty(localInfo.getId())) {
                            if (info.getId().equals(localInfo.getId())) {
                                info.setLocalId(localInfo.getLocalId());
                                mExpenditureInfoBDaoManager.correct(info);
                            }
                        }
                    }
                }
                if (mExpenditureListAdapter != null) {
                    mExpenditureListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mExpenditureInfoList)) {
                mExpenditureListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        CompanyExpenditureDetailActivityB.startActivity(CompanyExpenditureManagementActivityB.this, mExpenditureInfoList.get(position).getId(), mExpenditureInfoList.get(position).getLocalId(), mExpenditureInfoList.get(position).getEstimateId(), mExpenditureInfoList.get(position).getEstimateTitle());
                    }
                });
            }
        }

    }

    @Subscribe
    public void Event(AddOrSaveCompanyExpanditureEventB addOrSaveCompanyExpanditureEventB) {
        toastMsg(addOrSaveCompanyExpanditureEventB.getMsg());
        page = 1;
        requestData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void loadLocalData(String port) {
        super.loadLocalData(port);
        mRefreshLayout.setEnableLoadmore(false);
        if (port.equals(REQUEST_COMPANY_EXPENDITURE_B_LIST)) {
            List<CompanyExpenditureInfoB> rows = new ArrayList<>();
            if (!TextUtils.isEmpty(StringUtil.getText(tv_start_time)) || !TextUtils.isEmpty(StringUtil.getText(tv_end_time))) {
                rows = mExpenditureInfoBDaoManager.queryBuilder().where(CompanyExpenditureInfoBDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyExpenditureInfoBDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
            } else {
                rows = mExpenditureInfoBDaoManager.queryAll();
            }
            CompanyeExpenditureInfoBeanB companyeExpenditureInfoBeanB = new CompanyeExpenditureInfoBeanB();
            companyeExpenditureInfoBeanB.setRows(rows);
            getExpenditureListB(companyeExpenditureInfoBeanB);
        }
    }
}
