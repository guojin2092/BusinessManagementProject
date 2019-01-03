package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveServiceRecordEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyServiceRecordInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyServiceRecordInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.UserInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.ServiceRecordListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyServiceRecordOrderContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyServiceRecordPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseRefreshMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.MySpinner;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.africa.crm.businessmanagement.widget.dialog.AlertDialog;
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
public class CompanyServiceRecordManagementActivity extends BaseRefreshMvpActivity<CompanyServiceRecordPresenter> implements CompanyServiceRecordOrderContract.View {
    @BindView(R.id.et_service_record_name)
    EditText et_service_record_name;

    @BindView(R.id.spinner_user)
    MySpinner spinner_user;
    private List<UserInfoBean> mUserInfoBeanList = new ArrayList<>();
    private List<DicInfo> mUserInfoList = new ArrayList<>();
    private String mUserId = "";

    @BindView(R.id.spinner_state)
    MySpinner spinner_state;
    private static final String STATE_CODE = "SERVICESTATE";
    private List<DicInfo> mServiceRecordStateList = new ArrayList<>();
    private String mStateCode = "";

    @BindView(R.id.spinner_type)
    MySpinner spinner_type;
    private static final String TYPE_CODE = "SERVICETYPE";
    private List<DicInfo> mServiceRecordTypeList = new ArrayList<>();
    private String mTypeCode = "";

    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.tv_search)
    TextView tv_search;
    @BindView(R.id.tv_delete)
    TextView tv_delete;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private ServiceRecordListAdapter mServiceRecordListAdapter;
    private List<CompanyServiceRecordInfo> mDeleteList = new ArrayList<>();
    private List<CompanyServiceRecordInfo> mServiceRecordInfoBeanList = new ArrayList<>();

    private WorkStationInfo mWorkStationInfo;
    private boolean mShowCheckBox = false;
    private AlertDialog mDeleteDialog;
    private String mCompanyId = "";
    private String mRoleCode = "";

    private TimePickerView pvStartTime, pvEndTime;
    private Date mStartDate, mEndDate;

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanyServiceRecordManagementActivity.class);
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
        setContentView(R.layout.activity_service_record_management);
    }

    @Override
    public void initView() {
        super.initView();
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mRoleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
        if (mRoleCode.equals("companyRoot")) {
            spinner_user.setVisibility(View.VISIBLE);
        } else {
            spinner_user.setVisibility(View.GONE);
        }
        ll_add.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        initTimePicker();
    }

    private void initTimePicker() {
        pvStartTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                if (mEndDate != null) {
                    if (mEndDate.getTime() < mStartDate.getTime()) {
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
    protected CompanyServiceRecordPresenter injectPresenter() {
        return new CompanyServiceRecordPresenter();
    }

    @Override
    protected void requestData() {
        mPresenter.getState(STATE_CODE);
        mPresenter.getType(TYPE_CODE);
        mPresenter.getCompanyUserList(page, rows, "", "2", mCompanyId, "1", "");
        pullDownRefresh(page);
    }

    @Override
    public void pullDownRefresh(int page) {
        if (mRoleCode.equals("companyRoot")) {
            mPresenter.getServiceRecordList(page, rows, mCompanyId, mUserId, et_service_record_name.getText().toString().trim(), mStateCode, mTypeCode, tv_start_time.getText().toString().trim(), tv_end_time.getText().toString().trim());
        } else {
            mPresenter.getServiceRecordList(page, rows, mCompanyId, String.valueOf(UserInfoManager.getUserLoginInfo(this).getId()), et_service_record_name.getText().toString().trim(), mStateCode, mTypeCode, tv_start_time.getText().toString().trim(), tv_end_time.getText().toString().trim());
        }
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
            case R.id.titlebar_right:
                if (titlebar_right.getText().toString().equals(getString(R.string.delete))) {
                    titlebar_right.setText(R.string.cancel);
                    tv_delete.setVisibility(View.VISIBLE);
                    mShowCheckBox = true;
                } else {
                    titlebar_right.setText(R.string.delete);
                    tv_delete.setVisibility(View.GONE);
                    mShowCheckBox = false;
                }
                if (mServiceRecordListAdapter != null) {
                    mServiceRecordListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                CompanyServiceRecordDetailActivity.startActivity(CompanyServiceRecordManagementActivity.this, "");
                break;
            case R.id.tv_delete:
                mDeleteList.clear();
                for (CompanyServiceRecordInfo serviceRecordInfo : mServiceRecordInfoBeanList) {
                    if (serviceRecordInfo.isChosen()) {
                        mDeleteList.add(serviceRecordInfo);
                    }
                }
                if (ListUtils.isEmpty(mDeleteList)) {
                    toastMsg("尚未选择删除项");
                    return;
                }
                mDeleteDialog = new AlertDialog.Builder(CompanyServiceRecordManagementActivity.this)
                        .setTitle(R.string.tips)
                        .setMessage(R.string.confirm_delete)
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();
                            }
                        })
                        .setPositiveButton(R.string.OK, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                for (int i = 0; i < mDeleteList.size(); i++) {
                                    mPresenter.deleteServiceRecord(mDeleteList.get(i).getId());
                                }
                            }
                        })
                        .show();
                break;
        }
    }


    @Override
    public void initData() {
        mServiceRecordListAdapter = new ServiceRecordListAdapter(mServiceRecordInfoBeanList);
        recyclerView.setAdapter(mServiceRecordListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
    }


    @Override
    public void getState(List<DicInfo> dicInfoList) {
        mServiceRecordStateList.clear();
        mServiceRecordStateList.addAll(dicInfoList);
        spinner_state.setListDatas(this, mServiceRecordStateList);
        spinner_state.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mStateCode = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getType(List<DicInfo> dicInfoList) {
        mServiceRecordTypeList.clear();
        mServiceRecordTypeList.addAll(dicInfoList);
        spinner_type.setListDatas(this, mServiceRecordTypeList);
        spinner_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mTypeCode = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getCompanyUserList(UserManagementInfoBean userManagementInfoBean) {
        mUserInfoBeanList.clear();
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
        });
    }

    @Override
    public void getServiceRecordList(CompanyServiceRecordInfoBean companyServiceRecordInfoBean) {
        if (companyServiceRecordInfoBean != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companyServiceRecordInfoBean.getRows())) {
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.GONE);
                    layout_no_data.setVisibility(View.VISIBLE);
                    return;
                } else {
                    layout_no_data.setVisibility(View.GONE);
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
                }
                mServiceRecordInfoBeanList.clear();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companyServiceRecordInfoBean.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companyServiceRecordInfoBean.getRows())) {
                mServiceRecordInfoBeanList.addAll(companyServiceRecordInfoBean.getRows());
                if (mServiceRecordListAdapter != null) {
                    mServiceRecordListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mServiceRecordInfoBeanList)) {
                mServiceRecordListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mShowCheckBox) {
                            CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(recyclerView, position, R.id.cb_choose);
                            mServiceRecordInfoBeanList.get(position).setChosen(!cb_choose.isChecked());
                            mServiceRecordListAdapter.notifyDataSetChanged();
                        } else {
                            CompanyServiceRecordDetailActivity.startActivity(CompanyServiceRecordManagementActivity.this, mServiceRecordInfoBeanList.get(position).getId());
                        }
                    }
                });
            }
        }

    }

    @Override
    public void deleteServiceRecord(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            for (int i = 0; i < mDeleteList.size(); i++) {
                if (mServiceRecordInfoBeanList.contains(mDeleteList.get(i))) {
                    int position = mServiceRecordInfoBeanList.indexOf(mDeleteList.get(i));
                    mServiceRecordInfoBeanList.remove(mDeleteList.get(i));
                    if (mServiceRecordListAdapter != null) {
                        mServiceRecordListAdapter.notifyItemRemoved(position);
                    }
                }
            }
            if (ListUtils.isEmpty(mServiceRecordInfoBeanList)) {
                titlebar_right.setText(R.string.delete);
                tv_delete.setVisibility(View.GONE);
                mShowCheckBox = false;
                layout_network_error.setVisibility(View.GONE);
                mRefreshLayout.getLayout().setVisibility(View.GONE);
                layout_no_data.setVisibility(View.VISIBLE);
            }
            mDeleteDialog.dismiss();
        } else {
            ErrorMsg.showErrorMsg(baseEntity.getReturnMsg());
        }
    }

    @Subscribe
    public void Event(AddOrSaveServiceRecordEvent addOrSaveServiceRecordEvent) {
        toastMsg(addOrSaveServiceRecordEvent.getMsg());
        requestData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
