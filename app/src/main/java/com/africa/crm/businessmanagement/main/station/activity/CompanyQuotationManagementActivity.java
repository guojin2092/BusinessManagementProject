package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyQuotationEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyQuotationInfo;
import com.africa.crm.businessmanagement.main.bean.CompanyQuotationInfoBean;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.UserInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserManagementInfoBean;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.QuotationListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyQuotationContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyQuotationPresenter;
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
public class CompanyQuotationManagementActivity extends BaseRefreshMvpActivity<CompanyQuotationPresenter> implements CompanyQuotationContract.View {
    @BindView(R.id.ll_manager)
    LinearLayout ll_manager;
    @BindView(R.id.et_quotation_name_a)
    EditText et_quotation_name_a;
    @BindView(R.id.et_quotation_name_b)
    EditText et_quotation_name_b;
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

    @BindView(R.id.spinner_user)
    MySpinner spinner_user;
    private List<UserInfoBean> mUserInfoBeanList = new ArrayList<>();
    private List<DicInfo> mUserInfoList = new ArrayList<>();
    private String mUserId = "";

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private QuotationListAdapter mQuotationListAdapter;
    private List<CompanyQuotationInfo> mDeleteList = new ArrayList<>();
    private List<CompanyQuotationInfo> mCompanyQuotationInfoList = new ArrayList<>();

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
        Intent intent = new Intent(activity, CompanyQuotationManagementActivity.class);
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
        setContentView(R.layout.activity_company_quotation_management);
    }

    @Override
    public void initView() {
        super.initView();
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mRoleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
        if (mRoleCode.equals("companyRoot")) {
            ll_manager.setVisibility(View.VISIBLE);
            et_quotation_name_b.setVisibility(View.GONE);
        } else {
            ll_manager.setVisibility(View.GONE);
            et_quotation_name_b.setVisibility(View.VISIBLE);
        }
        if (mRoleCode.equals("companySales")) {
            ll_add.setVisibility(View.VISIBLE);
        } else {
            ll_add.setVisibility(View.GONE);
        }
        ll_add.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);

/*
        et_quotation_name_a.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence.toString())) {
                    spinner_user.setText("");
                    mUserId = "";
                    tv_start_time.setText("");
                    tv_end_time.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
*/
/*
        et_quotation_name_b.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!TextUtils.isEmpty(charSequence.toString())) {
                    tv_start_time.setText("");
                    tv_end_time.setText("");
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
                mStartDate = date;
                if (mEndDate != null) {
                    if (mEndDate.getTime() <= mStartDate.getTime()) {
                        toastMsg("起止时间不得小于起始时间");
                        return;
                    }
                }
                tv_start_time.setText(TimeUtils.getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true));

        pvEndTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mEndDate = date;
                if (mEndDate.getTime() <= mStartDate.getTime()) {
                    toastMsg("起止时间不得小于起始时间");
                    return;
                }
                tv_end_time.setText(TimeUtils.getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true));
    }

    @Override
    protected CompanyQuotationPresenter injectPresenter() {
        return new CompanyQuotationPresenter();
    }

    @Override
    protected void requestData() {
        mPresenter.getCompanyUserList(page, rows, "", "2", mCompanyId, "1", "");
        pullDownRefresh(page);
    }

    @Override
    public void pullDownRefresh(int page) {
        if (mRoleCode.equals("companyRoot")) {
            mPresenter.getCompanyQuotationList(page, rows, mCompanyId, mUserId, et_quotation_name_a.getText().toString().trim(), tv_start_time.getText().toString().trim(), tv_end_time.getText().toString().trim());
        } else {
            mPresenter.getCompanyQuotationList(page, rows, mCompanyId, String.valueOf(UserInfoManager.getUserLoginInfo(this).getId()), et_quotation_name_b.getText().toString().trim(), tv_start_time.getText().toString().trim(), tv_end_time.getText().toString().trim());
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
                if (mQuotationListAdapter != null) {
                    mQuotationListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.ll_add:
                CompanyQuotationDetailActivity.startActivity(CompanyQuotationManagementActivity.this, "");
                break;
            case R.id.tv_delete:
                mDeleteList.clear();
                for (CompanyQuotationInfo companyQuotationInfo : mCompanyQuotationInfoList) {
                    if (companyQuotationInfo.isChosen()) {
                        mDeleteList.add(companyQuotationInfo);
                    }
                }
                if (ListUtils.isEmpty(mDeleteList)) {
                    toastMsg("尚未选择删除项");
                    return;
                }
                mDeleteDialog = new AlertDialog.Builder(CompanyQuotationManagementActivity.this)
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
                                    mPresenter.deleteCompanyQuotation(mDeleteList.get(i).getId());
                                }
                            }
                        })
                        .show();
                break;
        }
    }

    @Override
    public void initData() {
        mQuotationListAdapter = new QuotationListAdapter(mCompanyQuotationInfoList);
        recyclerView.setAdapter(mQuotationListAdapter);
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
//                et_quotation_name_a.setText("");
            }
        });
    }

    @Override
    public void getCompanyQuotationList(CompanyQuotationInfoBean companyQuotationInfoBean) {
        if (companyQuotationInfoBean != null) {
            if (page == 1) {
                if (ListUtils.isEmpty(companyQuotationInfoBean.getRows())) {
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.GONE);
                    layout_no_data.setVisibility(View.VISIBLE);
                  /*  KeyboardUtil.clearInputBox(et_quotation_name_a);
                    KeyboardUtil.clearInputBox(et_quotation_name_b);
                    KeyboardUtil.clearInputBox(tv_start_time);
                    KeyboardUtil.clearInputBox(tv_end_time);
                    spinner_user.setText("");
                    mUserId = "";*/
                    return;
                } else {
                    layout_no_data.setVisibility(View.GONE);
                    layout_network_error.setVisibility(View.GONE);
                    mRefreshLayout.getLayout().setVisibility(View.VISIBLE);
                }
                mCompanyQuotationInfoList.clear();
                recyclerView.smoothScrollToPosition(0);
            }
            if (mRefreshLayout != null) {
                if (ListUtils.isEmpty(companyQuotationInfoBean.getRows()) && page > 1) {
                    mRefreshLayout.finishLoadmoreWithNoMoreData();
                } else {
                    mRefreshLayout.finishLoadmore();
                }
            }
            if (!ListUtils.isEmpty(companyQuotationInfoBean.getRows())) {
                mCompanyQuotationInfoList.addAll(companyQuotationInfoBean.getRows());
                if (mQuotationListAdapter != null) {
                    mQuotationListAdapter.notifyDataSetChanged();
                }
            }
            if (!ListUtils.isEmpty(mCompanyQuotationInfoList)) {
                mQuotationListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        if (mShowCheckBox) {
                            CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(recyclerView, position, R.id.cb_choose);
                            mCompanyQuotationInfoList.get(position).setChosen(!cb_choose.isChecked());
                            mQuotationListAdapter.notifyDataSetChanged();
                        } else {
                            CompanyQuotationDetailActivity.startActivity(CompanyQuotationManagementActivity.this, mCompanyQuotationInfoList.get(position).getId());
                        }
                    }
                });
            }
        }
    }

    @Override
    public void deleteCompanyQuotation(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            for (int i = 0; i < mDeleteList.size(); i++) {
                if (mCompanyQuotationInfoList.contains(mDeleteList.get(i))) {
                    int position = mCompanyQuotationInfoList.indexOf(mDeleteList.get(i));
                    mCompanyQuotationInfoList.remove(mDeleteList.get(i));
                    if (mQuotationListAdapter != null) {
                        mQuotationListAdapter.notifyItemRemoved(position);
                    }
                }
            }
            if (ListUtils.isEmpty(mCompanyQuotationInfoList)) {
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
    public void Event(AddOrSaveCompanyQuotationEvent addOrSaveCompanyQuotationEvent) {
        toastMsg(addOrSaveCompanyQuotationEvent.getMsg());
        requestData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
