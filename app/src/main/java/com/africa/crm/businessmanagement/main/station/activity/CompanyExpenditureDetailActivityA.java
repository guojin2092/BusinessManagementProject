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

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyExpanditureEventA;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyExpenditureInfo;
import com.africa.crm.businessmanagement.main.bean.PayRecordInfo;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.ExpenditureDetailListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyExpenditureDetailContractA;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyExpenditureDetailPresenterA;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.EditTextUtil;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.bigkoo.pickerview.TimePickerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class CompanyExpenditureDetailActivityA extends BaseMvpActivity<CompanyExpenditureDetailPresenterA> implements CompanyExpenditureDetailContractA.View {
    @BindView(R.id.et_title)
    EditText et_title;
    @BindView(R.id.et_price)
    EditText et_price;
    @BindView(R.id.tv_start_date)
    TextView tv_start_date;
    @BindView(R.id.tv_end_date)
    TextView tv_end_date;
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.ll_zc)
    LinearLayout ll_zc;
    @BindView(R.id.tv_save)
    TextView tv_save;

    @BindView(R.id.rv_zc_list)
    RecyclerView rv_zc_list;
    private ExpenditureDetailListAdapter mExpenditureDetailListAdapter;
    private List<PayRecordInfo> mPayRecordInfoList = new ArrayList<>();

    private TimePickerView pvStartTime, pvEndTime;
    private Date mStartDate, mEndDate;

    private String mExpenditureId = "";
    private String mCompanyId = "";
    private String mUserId = "";


    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, CompanyExpenditureDetailActivityA.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }


    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_expenditure_detail_a);
    }

    @Override
    public void initView() {
        super.initView();
        mExpenditureId = getIntent().getStringExtra("id");
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_right.setVisibility(View.GONE);
        titlebar_name.setText("企业预算");
        tv_save.setText(R.string.add);
        tv_save.setOnClickListener(this);
        tv_start_date.setOnClickListener(this);
        tv_end_date.setOnClickListener(this);
        EditTextUtil.setPricePoint(et_price);
        if (!TextUtils.isEmpty(mExpenditureId)) {
            tv_save.setVisibility(View.GONE);
            setEditTextInput(false);
        } else {
            tv_save.setVisibility(View.VISIBLE);
            ll_zc.setVisibility(View.GONE);
        }
        initTimePicker();
        initProductList();
    }

    private void initTimePicker() {
        pvStartTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mStartDate = date;
                if (mEndDate != null) {
                    if (mEndDate.getTime() < mStartDate.getTime()) {
                        toastMsg("结束日期不得小于开始日期");
                        return;
                    }
                }
                tv_start_date.setText(TimeUtils.getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true));
        pvEndTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mEndDate = date;
                if (mStartDate != null) {
                    if (mEndDate.getTime() < mStartDate.getTime()) {
                        toastMsg("结束日期不得小于开始日期");
                        return;
                    }
                }
                tv_end_date.setText(TimeUtils.getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true));
    }

    private void initProductList() {
        mExpenditureDetailListAdapter = new ExpenditureDetailListAdapter(mPayRecordInfoList);
        rv_zc_list.setAdapter(mExpenditureDetailListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_zc_list.setLayoutManager(layoutManager);
        rv_zc_list.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        rv_zc_list.setHasFixedSize(true);
        rv_zc_list.setNestedScrollingEnabled(false);
    }


    @Override
    protected CompanyExpenditureDetailPresenterA injectPresenter() {
        return new CompanyExpenditureDetailPresenterA();
    }

    @Override
    protected void requestData() {
        if (!TextUtils.isEmpty(mExpenditureId)) {
            mPresenter.getPayRecord(mExpenditureId);
            mPresenter.getExpenditureDetail(mExpenditureId);
        }
    }

    @Override
    public void initData() {

    }

    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        et_title.setEnabled(canInput);
        et_price.setEnabled(canInput);
        tv_start_date.setEnabled(canInput);
        tv_end_date.setEnabled(canInput);
        et_remark.setEnabled(canInput);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_start_date:
                if (pvStartTime != null) {
                    pvStartTime.show();
                }
                break;
            case R.id.tv_end_date:
                if (pvEndTime != null) {
                    pvEndTime.show();
                }
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(et_title.getText().toString().trim())) {
                    toastMsg("尚未填写标题");
                    return;
                }
                if (TextUtils.isEmpty(tv_start_date.getText().toString().trim())) {
                    toastMsg("尚未选择开始日期");
                    return;
                }
                if (TextUtils.isEmpty(tv_end_date.getText().toString().trim())) {
                    toastMsg("尚未选择结束日期");
                    return;
                }
                if (TextUtils.isEmpty(et_price.getText().toString().trim())) {
                    toastMsg("尚未填写预算金额");
                    return;
                }
                mPresenter.saveExpenditureA(mCompanyId, mUserId, et_title.getText().toString().trim(), tv_start_date.getText().toString().trim(), tv_end_date.getText().toString().trim(), et_price.getText().toString().trim(), et_remark.getText().toString().trim());
                break;
        }
    }


    @Override
    public void getPayRecord(List<PayRecordInfo> payRecordInfoList) {
        mPayRecordInfoList.clear();
        if (!ListUtils.isEmpty(payRecordInfoList)) {
            ll_zc.setVisibility(View.VISIBLE);
            mPayRecordInfoList.addAll(payRecordInfoList);
            if (mExpenditureDetailListAdapter != null) {
                mExpenditureDetailListAdapter.notifyDataSetChanged();
            }
        } else {
            ll_zc.setVisibility(View.GONE);
        }
    }

    @Override
    public void getExpenditureDetail(CompanyExpenditureInfo companyExpenditureInfo) {
        et_title.setText(companyExpenditureInfo.getTitle());
        et_price.setText(companyExpenditureInfo.getEstimatePrice());
        tv_start_date.setText(companyExpenditureInfo.getStartDate());
        mStartDate = TimeUtils.getDataByString(companyExpenditureInfo.getStartDate());
        tv_end_date.setText(companyExpenditureInfo.getEndDate());
        mEndDate = TimeUtils.getDataByString(companyExpenditureInfo.getEndDate());
        et_remark.setText(companyExpenditureInfo.getRemark());
    }

    @Override
    public void saveExpenditureA(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String toastString = "";
            if (TextUtils.isEmpty(mExpenditureId)) {
                toastString = "企业预算单创建成功";
            }
            EventBus.getDefault().post(new AddOrSaveCompanyExpanditureEventA(toastString));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }
}
