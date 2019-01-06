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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanySalesOrderEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanySalesOrderInfo;
import com.africa.crm.businessmanagement.main.bean.CompanySalesOrderInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.UserInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.SalesListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanySalesOrderContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanySalesOrderPresenter;
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
public class CompanySalesOrderManagementActivity extends BaseRefreshMvpActivity<CompanySalesOrderPresenter> implements CompanySalesOrderContract.View {
    @BindView(R.id.et_sales_name)
    EditText et_sales_name;
    @BindView(R.id.tv_start_time)
    TextView tv_start_time;
    @BindView(R.id.tv_end_time)
    TextView tv_end_time;
    @BindView(R.id.ll_right)
    LinearLayout ll_right;
    @BindView(R.id.ll_add)
    LinearLayout ll_add;
    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.tv_search)
    TextView tv_search;

    @BindView(R.id.spinner_user)
    MySpinner spinner_user;
    private List<UserInfoBean> mUserInfoBeanList = new ArrayList<>();
    private List<DicInfo> mUserInfoList = new ArrayList<>();
    private String mUserId = "";

    @BindView(R.id.spinner_state)
    MySpinner spinner_state;
    private static final String STATE_CODE = "SALEORDERSTATE";
    private List<DicInfo> mSalesOrderStateList = new ArrayList<>();
    private String mStateCode = "";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private SalesListAdapter mSalesListAdapter;
    private List<CompanySalesOrderInfo> mDeleteList = new ArrayList<>();
    private List<CompanySalesOrderInfo> mCompanySalesOrderInfoList = new ArrayList<>();

    private AlertDialog mDeleteDialog;
    private boolean mShowCheckBox = false;
    private WorkStationInfo mWorkStationInfo;
    private String mRoleCode = "";
    private String mCompanyId = "";

    private TimePickerView pvStartTime, pvEndTime;
    private Date mStartDate, mEndDate;

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, CompanySalesOrderManagementActivity.class);
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
        setContentView(R.layout.activity_company_sales_order_management);
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
        ll_add.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);

        if (mRoleCode.equals("companyRoot")) {
            spinner_user.setVisibility(View.VISIBLE);
            titlebar_right.setVisibility(View.GONE);
            ll_right.setVisibility(View.GONE);
        } else {
            spinner_user.setVisibility(View.GONE);
            titlebar_right.setVisibility(View.VISIBLE);
            ll_right.setVisibility(View.VISIBLE);
        }
        if (mRoleCode.equals("companySales")) {
            ll_add.setVisibility(View.VISIBLE);
        } else {
            ll_add.setVisibility(View.GONE);
        }
/*
        et_sales_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence.toString())) {
                    spinner_user.setText("");
                    mUserId = "";
                    spinner_state.setText("");
                    mStateCode = "";
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/
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
    protected CompanySalesOrderPresenter injectPresenter() {
        return new CompanySalesOrderPresenter();
    }

    @Override
    protected void requestData() {
        mPresenter.getCompanyUserList(page, rows, "", "2", mCompanyId, "1", "");
        mPresenter.getStateType(STATE_CODE);
        pullDownRefresh(page);
    }

    @Override
    public void pullDownRefresh(int page) {
        if (mRoleCode.equals("companyRoot")) {
            mPresenter.getCompanySalesOrderList(page, rows, mCompanyId, mUserId, et_sales_name.getText().toString().trim(), mStateCode, tv_start_time.getText().toString().trim(), tv_end_time.getText().toString().trim());
        } else {
            mPresenter.getCompanySalesOrderList(page, rows, mCompanyId, String.valueOf(UserInfoManager.getUserLoginInfo(this).getId()), et_sales_name.getText().toString().trim(), mStateCode, tv_start_time.getText().toString().trim(), tv_end_time.getText().toString().trim());
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
                if (mSalesListAdapter != null) {
                    mSalesListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                CompanySaleOrdersDetailActivity.startActivity(CompanySalesOrderManagementActivity.this, "");
                break;
            case R.id.tv_delete:
                mDeleteList.clear();
                for (CompanySalesOrderInfo companySalesOrderInfo : mCompanySalesOrderInfoList) {
                    if (companySalesOrderInfo.isChosen()) {
                        mDeleteList.add(companySalesOrderInfo);
                    }
                }
                if (ListUtils.isEmpty(mDeleteList)) {
                    toastMsg("尚未选择删除项");
                    return;
                }
                mDeleteDialog = new AlertDialog.Builder(CompanySalesOrderManagementActivity.this)
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
                                    mPresenter.deleteCompanySalesOrder(mDeleteList.get(i).getId());
                                }
                            }
                        })
                        .show();
                break;
        }
    }


    @Override
    public void initData() {
        mSalesListAdapter = new SalesListAdapter(mCompanySalesOrderInfoList);
        recyclerView.setAdapter(mSalesListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
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
/*
                if (!TextUtils.isEmpty(mUserId)) {
                    et_sales_name.setText("");
                    spinner_state.setText("");
                    mStateCode = "";
                }
*/
            }
        });
    }

    @Override
    public void getStateType(List<DicInfo> dicInfoList) {
        mSalesOrderStateList.clear();
        mSalesOrderStateList.addAll(dicInfoList);
        spinner_state.setListDatas(this, mSalesOrderStateList);

        spinner_state.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mStateCode = dicInfo.getCode();
/*
                if (!TextUtils.isEmpty(mStateCode)) {
                    et_sales_name.setText("");
                    spinner_user.setText("");
                    mUserId = "";
                }
*/
            }
        });
    }

    @Override
    public void getCompanySalesOrderList(CompanySalesOrderInfoBean companySalesOrderInfoBean) {
        if (companySalesOrderInfoBean != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companySalesOrderInfoBean.getRows())) {
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.GONE);
                    layout_no_data.setVisibility(View.VISIBLE);
                  /*  KeyboardUtil.clearInputBox(et_sales_name);
                    KeyboardUtil.clearInputBox(tv_start_time);
                    KeyboardUtil.clearInputBox(tv_end_time);
                    mStartDate = null;
                    mEndDate = null;
                    spinner_user.setText("");
                    mUserId = "";
                    spinner_state.setText("");
                    mStateCode = "";*/
                    return;
                } else {
                    layout_no_data.setVisibility(View.GONE);
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
                }
                mCompanySalesOrderInfoList.clear();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companySalesOrderInfoBean.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companySalesOrderInfoBean.getRows())) {
                mCompanySalesOrderInfoList.addAll(companySalesOrderInfoBean.getRows());
                if (mSalesListAdapter != null) {
                    mSalesListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mCompanySalesOrderInfoList)) {
                mSalesListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mShowCheckBox) {
                            CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(recyclerView, position, R.id.cb_choose);
                            mCompanySalesOrderInfoList.get(position).setChosen(!cb_choose.isChecked());
                            mSalesListAdapter.notifyDataSetChanged();
                        } else {
                            CompanySaleOrdersDetailActivity.startActivity(CompanySalesOrderManagementActivity.this, mCompanySalesOrderInfoList.get(position).getId());
                        }
                    }
                });
            }
        }
    }

    @Override
    public void deleteCompanySalesOrder(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            for (int i = 0; i < mDeleteList.size(); i++) {
                if (mCompanySalesOrderInfoList.contains(mDeleteList.get(i))) {
                    int position = mCompanySalesOrderInfoList.indexOf(mDeleteList.get(i));
                    mCompanySalesOrderInfoList.remove(mDeleteList.get(i));
                    if (mSalesListAdapter != null) {
                        mSalesListAdapter.notifyItemRemoved(position);
                    }
                }
            }
            if (ListUtils.isEmpty(mCompanySalesOrderInfoList)) {
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
    public void Event(AddOrSaveCompanySalesOrderEvent addOrSaveCompanySalesOrderEvent) {
        toastMsg(addOrSaveCompanySalesOrderEvent.getMsg());
        page = 1;
        requestData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

}
