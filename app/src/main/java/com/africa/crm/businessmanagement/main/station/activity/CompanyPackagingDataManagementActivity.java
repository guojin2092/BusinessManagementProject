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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyPackagingDataEvent;
import com.africa.crm.businessmanagement.main.bean.CompanyPackagingDataInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyPackagingDataInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyPackagingDataInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.PackagingDataListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPackagingDataContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyPackagingDataPresenter;
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

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_PACKAGING_DATA_LIST;

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
        private List<DicInfo> mSpinnerCompanyUserList = new ArrayList<>();
        private String mFromUserId = "";*/
    private String mUserId = "";
    private String mCompanyId = "";
    private String mRoleCode = "";

    private GreendaoManager<CompanyPackagingDataInfo, CompanyPackagingDataInfoDao> mPackagingDataInfoDaoManager;
    private List<CompanyPackagingDataInfo> mPackagingDataLocalList = new ArrayList<>();//本地数据

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
        //得到Dao对象管理器
        mPackagingDataInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyPackagingDataInfoDao());
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
    protected CompanyPackagingDataPresenter injectPresenter() {
        return new CompanyPackagingDataPresenter();
    }

    @Override
    protected void requestData() {
   /*     if ("companyRoot".equals(mRoleCode)) {
            mPresenter.getAllCompanyUsers(mCompanyId);
        }*/
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
                CompanyPackagingDataDetailActivity.startActivity(this, "", 0l);
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
    public void getAllCompanyUsers(List<DicInfo2> dicInfo2List) {
      /*  mSpinnerCompanyUserList.clear();
        if (!ListUtils.isEmpty(dicInfo2List)) {
            for (DicInfo2 dicInfo2 : dicInfo2List) {
                DicInfo dicInfo = new DicInfo(dicInfo2.getId(), QUERY_ALL_USERS, dicInfo2.getName(), dicInfo2.getCode());
                mSpinnerCompanyUserList.add(dicInfo);
            }
            List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerCompanyUserList, mDicInfoLocalList);
            for (DicInfo dicInfo : addList) {
                dicInfo.setType(QUERY_ALL_USERS);
                mDicInfoDaoManager.insertOrReplace(dicInfo);
            }
            spinner_user.setListDatas(getBVActivity(), mSpinnerCompanyUserList);
            spinner_user.addOnItemClickListener(new MySpinner.OnItemClickListener() {
                @Override
                public void onItemClick(DicInfo dicInfo, int position) {
                    mFromUserId = dicInfo.getCode();
                }
            });
        }*/
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
                mPackagingDataLocalList = mPackagingDataInfoDaoManager.queryAll();
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
                List<CompanyPackagingDataInfo> addList = DifferentDataUtil.addPackagingDataToLocal(mPackingDataInfoBeanList, mPackagingDataLocalList);
                if (!ListUtils.isEmpty(addList)) {
                    for (CompanyPackagingDataInfo companyInfo : addList) {
                        mPackagingDataInfoDaoManager.insertOrReplace(companyInfo);
                    }
                    mPackagingDataLocalList = new ArrayList<>();
                    mPackagingDataLocalList = mPackagingDataInfoDaoManager.queryAll();
                }
                for (CompanyPackagingDataInfo info : mPackingDataInfoBeanList) {
                    for (CompanyPackagingDataInfo localInfo : mPackagingDataLocalList) {
                        if (!TextUtils.isEmpty(info.getId()) && !TextUtils.isEmpty(localInfo.getId())) {
                            if (info.getId().equals(localInfo.getId())) {
                                info.setLocalId(localInfo.getLocalId());
                                mPackagingDataInfoDaoManager.correct(info);
                            }
                        }
                    }
                }
                if (mPackagingDataListAdapter != null) {
                    mPackagingDataListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mPackingDataInfoBeanList)) {
                mPackagingDataListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        CompanyPackagingDataDetailActivity.startActivity(CompanyPackagingDataManagementActivity.this, mPackingDataInfoBeanList.get(position).getId(), mPackingDataInfoBeanList.get(position).getLocalId());
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

    @Override
    public void loadLocalData(String port) {
        super.loadLocalData(port);
        mRefreshLayout.setEnableLoadmore(false);
        if (port.equals(REQUEST_COMPANY_PACKAGING_DATA_LIST)) {
            List<CompanyPackagingDataInfo> rows = new ArrayList<>();
            if (!TextUtils.isEmpty(StringUtil.getText(tv_start_time)) || !TextUtils.isEmpty(StringUtil.getText(tv_end_time))) {
                rows = mPackagingDataInfoDaoManager.queryBuilder().where(CompanyPackagingDataInfoDao.Properties.CreateTimeDate.gt(TimeUtils.getDateByStartTime(StringUtil.getText(tv_start_time))), CompanyPackagingDataInfoDao.Properties.CreateTimeDate.lt(TimeUtils.getDateByEndTime(StringUtil.getText(tv_end_time)))).list();
            } else {
                rows = mPackagingDataInfoDaoManager.queryAll();
            }
            CompanyPackagingDataInfoBean companyPackagingDataInfoBean = new CompanyPackagingDataInfoBean();
            companyPackagingDataInfoBean.setRows(rows);
            getCompanyPackagingDataList(companyPackagingDataInfoBean);
        }
    }
}
