package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.baseutil.library.util.NetUtil;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyExpanditureEventA;
import com.africa.crm.businessmanagement.main.bean.CompanyExpenditureInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyeExpenditureInfoBean;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyExpenditureInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.ExpenditureListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyExpenditureManagementContractA;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyExpenditurePresenterA;
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

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_EXPENDITURE_A_LIST;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/28 0028 9:03
 * Modification  History:
 * Why & What is modified:
 */
public class CompanyExpenditureManagementActivityA extends BaseRefreshMvpActivity<CompanyExpenditurePresenterA> implements CompanyExpenditureManagementContractA.View {
    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.tv_search)
    TextView tv_search;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ExpenditureListAdapter mExpenditureListAdapter;
    private List<CompanyExpenditureInfo> mExpenditureInfoList = new ArrayList<>();

    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    private TimePickerView pvStartTime, pvEndTime;
    private Date mStartDate, mEndDate;

    private WorkStationInfo mWorkStationInfo;
    private String mCompanyId = "";

    private GreendaoManager<CompanyExpenditureInfo, CompanyExpenditureInfoDao> mExpenditureInfoDaoManager;
    private List<CompanyExpenditureInfo> mExpenditureLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanyExpenditureManagementActivityA.class);
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
        setContentView(R.layout.activity_expenditure_management_a);
    }

    @Override
    public void initView() {
        super.initView();
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
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
        mExpenditureInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyExpenditureInfoDao());
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
    protected CompanyExpenditurePresenterA injectPresenter() {
        return new CompanyExpenditurePresenterA();
    }

    @Override
    protected void requestData() {
        pullDownRefresh(page);
    }

    @Override
    public void pullDownRefresh(int page) {
        mPresenter.getExpenditureList(page, rows, mCompanyId, et_title.getText().toString().trim(), tv_start_time.getText().toString().trim(), tv_end_time.getText().toString().trim());
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
                if (NetUtil.isNetAvailable(this)) {
                    CompanyExpenditureDetailActivityA.startActivity(CompanyExpenditureManagementActivityA.this, "", 0l);
                } else {
                    toastMsg("网络连接失败，请重试");
                }
                break;
        }
    }

    @Override
    public void initData() {
        mExpenditureListAdapter = new ExpenditureListAdapter(mExpenditureInfoList);
        recyclerView.setAdapter(mExpenditureListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }


    @Override
    public void getExpenditureList(CompanyeExpenditureInfoBean companyeExpenditureInfoBean) {
        if (companyeExpenditureInfoBean != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companyeExpenditureInfoBean.getRows())) {
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
                mExpenditureLocalList = mExpenditureInfoDaoManager.queryAll();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companyeExpenditureInfoBean.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companyeExpenditureInfoBean.getRows())) {
                mExpenditureInfoList.addAll(companyeExpenditureInfoBean.getRows());
                List<CompanyExpenditureInfo> addList = DifferentDataUtil.addExpenditureDataToLocal(mExpenditureInfoList, mExpenditureLocalList);
                if (!ListUtils.isEmpty(addList)) {
                    for (CompanyExpenditureInfo companyInfo : addList) {
                        mExpenditureInfoDaoManager.insertOrReplace(companyInfo);
                    }
                    mExpenditureLocalList = new ArrayList<>();
                    mExpenditureLocalList = mExpenditureInfoDaoManager.queryAll();
                }
                for (CompanyExpenditureInfo info : mExpenditureInfoList) {
                    for (CompanyExpenditureInfo localInfo : mExpenditureLocalList) {
                        if (!TextUtils.isEmpty(info.getId()) && !TextUtils.isEmpty(localInfo.getId())) {
                            if (info.getId().equals(localInfo.getId())) {
                                info.setLocalId(localInfo.getLocalId());
                                mExpenditureInfoDaoManager.correct(info);
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
                        CompanyExpenditureDetailActivityA.startActivity(CompanyExpenditureManagementActivityA.this, mExpenditureInfoList.get(position).getId(), mExpenditureInfoList.get(position).getLocalId());
                    }
                });
            }
        }

    }

    @Subscribe
    public void Event(AddOrSaveCompanyExpanditureEventA addOrSaveCompanyExpanditureEventA) {
        toastMsg(addOrSaveCompanyExpanditureEventA.getMsg());
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
        if (port.equals(REQUEST_COMPANY_EXPENDITURE_A_LIST)) {
            List<CompanyExpenditureInfo> rows = new ArrayList<>();
            if (!TextUtils.isEmpty(StringUtil.getText(et_title)) || !TextUtils.isEmpty(StringUtil.getText(tv_start_time)) || !TextUtils.isEmpty(StringUtil.getText(tv_end_time))) {
                if (!TextUtils.isEmpty(StringUtil.getText(et_title))) {
                    rows = mExpenditureInfoDaoManager.queryBuilder().where(CompanyExpenditureInfoDao.Properties.Title.like("%" + StringUtil.getText(et_title) + "%"), CompanyExpenditureInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyExpenditureInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                } else {
                    rows = mExpenditureInfoDaoManager.queryBuilder().where(CompanyExpenditureInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyExpenditureInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
                }
            } else {
                rows = mExpenditureInfoDaoManager.queryAll();
            }
            CompanyeExpenditureInfoBean companyeExpenditureInfoBean = new CompanyeExpenditureInfoBean();
            companyeExpenditureInfoBean.setRows(rows);
            getExpenditureList(companyeExpenditureInfoBean);
        }
    }
}
