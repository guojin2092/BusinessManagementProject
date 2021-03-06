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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyExpanditureEventA;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyExpenditureInfo;
import com.africa.crm.businessmanagement.main.bean.PayRecordInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyExpenditureInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.PayRecordInfoDao;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.ExpenditureDetailListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyExpenditureDetailContractA;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyExpenditureDetailPresenterA;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.DifferentDataUtil;
import com.africa.crm.businessmanagement.widget.EditTextUtil;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.bigkoo.pickerview.TimePickerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_CHECK_YS_DATE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_EXPENDITURE_A_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_PAY_RECORD;

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
    private Long mLocalId = 0l;//本地数据库ID
    private String mCompanyId = "";
    private String mUserId = "";

    private GreendaoManager<CompanyExpenditureInfo, CompanyExpenditureInfoDao> mExpenditureInfoDaoManager;
    private List<CompanyExpenditureInfo> mExpenditureLocalList = new ArrayList<>();//本地数据

    private GreendaoManager<PayRecordInfo, PayRecordInfoDao> mPayRecordInfoDaoManager;
    private List<PayRecordInfo> mPayRecordInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id, Long localId) {
        Intent intent = new Intent(activity, CompanyExpenditureDetailActivityA.class);
        intent.putExtra("id", id);
        intent.putExtra("localId", localId);
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
        mLocalId = getIntent().getLongExtra("localId", 0l);
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_right.setVisibility(View.GONE);
        titlebar_name.setText(R.string.Business_budget);
        tv_save.setText(R.string.Add);
        tv_save.setOnClickListener(this);
        tv_start_date.setOnClickListener(this);
        tv_end_date.setOnClickListener(this);
        EditTextUtil.setPricePoint(et_price);
        if (TextUtils.isEmpty(mExpenditureId) && mLocalId == 0l) {
            tv_save.setVisibility(View.VISIBLE);
            ll_zc.setVisibility(View.GONE);
        } else if (!TextUtils.isEmpty(mExpenditureId) || mLocalId != 0l) {
            tv_save.setVisibility(View.GONE);
            setEditTextInput(false);
        }
        initTimePicker();
        initProductList();
        mExpenditureInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyExpenditureInfoDao());
        mExpenditureLocalList = mExpenditureInfoDaoManager.queryAll();
        mPayRecordInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getPayRecordInfoDao());
        mPayRecordInfoLocalList = mPayRecordInfoDaoManager.queryAll();
    }

    private void initTimePicker() {
        pvStartTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mStartDate = date;
                if (mEndDate != null) {
                    if (mEndDate.getTime() < mStartDate.getTime()) {
                        toastMsg(getString(R.string.The_end_date_cannot_be_earlier_than_the_start_date));
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
                        toastMsg(getString(R.string.The_end_date_cannot_be_earlier_than_the_start_date));
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
        if (!TextUtils.isEmpty(mExpenditureId) || mLocalId != 0l) {
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
                    toastMsg(getString(R.string.Please_fill_in_the_title));
                    return;
                }
                if (TextUtils.isEmpty(tv_start_date.getText().toString().trim())) {
                    toastMsg(getString(R.string.Please_select_the_start_date));
                    return;
                }
                if (TextUtils.isEmpty(tv_end_date.getText().toString().trim())) {
                    toastMsg(getString(R.string.Please_select_the_end_date));
                    return;
                }
                if (TextUtils.isEmpty(et_price.getText().toString().trim())) {
                    toastMsg(getString(R.string.Please_fill_in_the_budget_amount));
                    return;
                }
                mPresenter.saveExpenditureA(mCompanyId, mUserId, et_title.getText().toString().trim(), tv_start_date.getText().toString().trim(), tv_end_date.getText().toString().trim(), et_price.getText().toString().trim(), et_remark.getText().toString().trim());
                break;
        }
    }


    @Override
    public void getPayRecord(List<PayRecordInfo> payRecordInfoList) {
        if (payRecordInfoList != null) {
            mPayRecordInfoList.clear();
            if (!ListUtils.isEmpty(payRecordInfoList)) {
                ll_zc.setVisibility(View.VISIBLE);
                mPayRecordInfoList.addAll(payRecordInfoList);
                List<PayRecordInfo> addList = DifferentDataUtil.addPayRecordDataToLocal(payRecordInfoList, mPayRecordInfoLocalList);
                if (!ListUtils.isEmpty(addList)) {
                    for (PayRecordInfo payRecordInfo : addList) {
                        mPayRecordInfoDaoManager.insertOrReplace(payRecordInfo);
                    }
                    mPayRecordInfoLocalList = new ArrayList<>();
                    mPayRecordInfoLocalList = mPayRecordInfoDaoManager.queryAll();
                }
                for (PayRecordInfo info : payRecordInfoList) {
                    for (PayRecordInfo localInfo : mPayRecordInfoLocalList) {
                        if (!TextUtils.isEmpty(info.getId()) && !TextUtils.isEmpty(localInfo.getId())) {
                            if (info.getId().equals(localInfo.getId())) {
                                info.setLocalId(localInfo.getLocalId());
                                mPayRecordInfoDaoManager.correct(info);
                            }
                        }
                    }
                }
                if (mExpenditureDetailListAdapter != null) {
                    mExpenditureDetailListAdapter.notifyDataSetChanged();
                }
            } else {
                ll_zc.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void getExpenditureDetail(CompanyExpenditureInfo companyExpenditureInfo) {
        if (companyExpenditureInfo != null) {
            et_title.setText(companyExpenditureInfo.getTitle());
            et_price.setText(companyExpenditureInfo.getEstimatePrice());
            tv_start_date.setText(companyExpenditureInfo.getStartDate());
            mStartDate = TimeUtils.getDataByString(companyExpenditureInfo.getStartDate());
            tv_end_date.setText(companyExpenditureInfo.getEndDate());
            mEndDate = TimeUtils.getDataByString(companyExpenditureInfo.getEndDate());
            et_remark.setText(companyExpenditureInfo.getRemark());
            for (CompanyExpenditureInfo localInfo : mExpenditureLocalList) {
                if (!TextUtils.isEmpty(localInfo.getId()) && !TextUtils.isEmpty(companyExpenditureInfo.getId())) {
                    if (localInfo.getId().equals(companyExpenditureInfo.getId())) {
                        companyExpenditureInfo.setLocalId(localInfo.getLocalId());
                        mExpenditureInfoDaoManager.correct(companyExpenditureInfo);
                    }
                }
            }
        }
    }

    @Override
    public void saveExpenditureA(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String toastString = "";
            if (TextUtils.isEmpty(mExpenditureId)) {
                toastString = getString(R.string.Added_Successfully);
            }
            EventBus.getDefault().post(new AddOrSaveCompanyExpanditureEventA(toastString));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }

    @Override
    public void loadLocalData(String port) {
        super.loadLocalData(port);
        switch (port) {
            case REQUEST_COMPANY_EXPENDITURE_A_DETAIL:
                CompanyExpenditureInfo companyExpenditureInfo = null;
                for (CompanyExpenditureInfo info : mExpenditureLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyExpenditureInfo = info;
                    }
                }
                getExpenditureDetail(companyExpenditureInfo);
                break;
            case REQUEST_COMPANY_PAY_RECORD:
                List<PayRecordInfo> payRecordInfoList = new ArrayList<>();
                for (PayRecordInfo payRecordInfo : mPayRecordInfoLocalList) {
                    if (mExpenditureId.equals(payRecordInfo.getEstimateId())) {
                        payRecordInfoList.add(payRecordInfo);
                    }
                }
                getPayRecord(payRecordInfoList);
                break;
            case REQUEST_CHECK_YS_DATE:
                toastMsg("网络连接失败，请检查网络是否可用");
                break;
        }
    }
}
