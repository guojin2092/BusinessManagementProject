package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyExpanditureEventB;
import com.africa.crm.businessmanagement.main.bean.CompanyExpenditureInfoB;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.dao.CompanyExpenditureInfoBDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanyExpenditureDetailContractB;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyExpenditureDetailPresenterB;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.widget.EditTextUtil;
import com.africa.crm.businessmanagement.widget.StringUtil;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.bigkoo.pickerview.TimePickerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_EXPENDITURE_B_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_EXPENDITURE_B;

public class CompanyExpenditureDetailActivityB extends BaseMvpActivity<CompanyExpenditureDetailPresenterB> implements CompanyExpenditureDetailContractB.View {
    @BindView(R.id.et_price)
    EditText et_price;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.tv_save)
    TextView tv_save;

    private TimePickerView pvTime;

    private String mExpenditureId = "";//支出ID
    private Long mLocalId = 0l;//本地数据库ID
    private String mCompanyId = "";
    private String mCompanyName = "";
    private String mFromName = "";
    private String mUserId = "";
    private String mEstimateId = "";//预算ID
    private String mEstimateTitle = "";//预算标题

    private GreendaoManager<CompanyExpenditureInfoB, CompanyExpenditureInfoBDao> mExpenditureInfoBDaoManager;
    private List<CompanyExpenditureInfoB> mExpenditureLocalBList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id, Long localId, String estimateId, String estimateTitle) {
        Intent intent = new Intent(activity, CompanyExpenditureDetailActivityB.class);
        intent.putExtra("id", id);
        intent.putExtra("localId", localId);
        intent.putExtra("estimateId", estimateId);
        intent.putExtra("estimateTitle", estimateTitle);
        activity.startActivity(intent);
    }


    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_expenditure_detail_b);
    }

    @Override
    public void initView() {
        super.initView();
        mExpenditureId = getIntent().getStringExtra("id");
        mEstimateId = getIntent().getStringExtra("estimateId");
        mEstimateTitle = getIntent().getStringExtra("mEstimateTitle");
        mLocalId = getIntent().getLongExtra("localId", 0l);
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mCompanyName = UserInfoManager.getUserLoginInfo(this).getCompanyName();
        mFromName = UserInfoManager.getUserLoginInfo(this).getName();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_right.setVisibility(View.GONE);
        titlebar_name.setText("企业支出");
        tv_save.setText(R.string.add);
        tv_save.setOnClickListener(this);
        tv_date.setOnClickListener(this);
        EditTextUtil.setPricePoint(et_price);
        if (TextUtils.isEmpty(mExpenditureId) && mLocalId == 0l) {
            tv_save.setVisibility(View.VISIBLE);

        } else if (!TextUtils.isEmpty(mExpenditureId) || mLocalId != 0l) {
            tv_save.setVisibility(View.GONE);
            setEditTextInput(false);
        }
        initTimePicker();
        mExpenditureInfoBDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyExpenditureInfoBDao());
        mExpenditureLocalBList = mExpenditureInfoBDaoManager.queryAll();
    }

    private void initTimePicker() {
        pvTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tv_date.setText(TimeUtils.getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true));
    }


    @Override
    protected CompanyExpenditureDetailPresenterB injectPresenter() {
        return new CompanyExpenditureDetailPresenterB();
    }

    @Override
    protected void requestData() {
        if (!TextUtils.isEmpty(mExpenditureId) || mLocalId != 0l) {
            mPresenter.getExpenditureDetailB(mExpenditureId);
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
        et_price.setEnabled(canInput);
        tv_date.setEnabled(canInput);
        et_remark.setEnabled(canInput);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_date:
                if (pvTime != null) {
                    pvTime.show();
                }
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(tv_date.getText().toString().trim())) {
                    toastMsg("尚未选择支出日期");
                    return;
                }
                if (TextUtils.isEmpty(et_price.getText().toString().trim())) {
                    toastMsg("尚未填写支出金额");
                    return;
                }
                mPresenter.saveExpenditureB(mCompanyId, mUserId, tv_date.getText().toString().trim(), et_price.getText().toString().trim(), et_remark.getText().toString().trim());
                break;
        }
    }

    @Override
    public void getExpenditureDetailB(CompanyExpenditureInfoB companyExpenditureInfoB) {
        if (companyExpenditureInfoB != null) {
            et_price.setText(companyExpenditureInfoB.getPrice());
            tv_date.setText(companyExpenditureInfoB.getPayDate());
            et_remark.setText(companyExpenditureInfoB.getRemark());
            mCompanyId = companyExpenditureInfoB.getCompanyId();
            mCompanyName = companyExpenditureInfoB.getCompanyName();
            mFromName = companyExpenditureInfoB.getUserNickName();
            for (CompanyExpenditureInfoB localInfo : mExpenditureLocalBList) {
                if (!TextUtils.isEmpty(localInfo.getId()) && !TextUtils.isEmpty(companyExpenditureInfoB.getId())) {
                    if (localInfo.getId().equals(companyExpenditureInfoB.getId())) {
                        companyExpenditureInfoB.setLocalId(localInfo.getLocalId());
                        mExpenditureInfoBDaoManager.correct(companyExpenditureInfoB);
                    }
                }
            }
        }

    }

    @Override
    public void saveExpenditureB(UploadInfoBean uploadInfoBean, boolean isLocal) {
        String toastString = "";
        if (TextUtils.isEmpty(mExpenditureId) && mLocalId == 0l) {
            toastString = "企业支出单创建成功";
        }
        if (isLocal) {
            CompanyExpenditureInfoB companyExpenditureInfoB = null;
            if (mLocalId == 0l) {
                companyExpenditureInfoB = new CompanyExpenditureInfoB(mExpenditureId, TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), mEstimateId, StringUtil.getText(et_price), StringUtil.getText(et_remark), mUserId, StringUtil.getText(tv_date), mCompanyId, mCompanyName, mEstimateTitle, mFromName, isLocal);
                mExpenditureInfoBDaoManager.insertOrReplace(companyExpenditureInfoB);
            } else {
                for (CompanyExpenditureInfoB info : mExpenditureLocalBList) {
                    if (mLocalId == info.getLocalId()) {
                        companyExpenditureInfoB = new CompanyExpenditureInfoB(info.getLocalId(), mExpenditureId, TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), mEstimateId, StringUtil.getText(et_price), StringUtil.getText(et_remark), mUserId, StringUtil.getText(tv_date), mCompanyId, mCompanyName, mEstimateTitle, mFromName, isLocal);
                        mExpenditureInfoBDaoManager.correct(companyExpenditureInfoB);
                    }
                }
            }
        }
        EventBus.getDefault().post(new AddOrSaveCompanyExpanditureEventB(toastString));
        finish();
    }

    @Override
    public void loadLocalData(String port) {
        super.loadLocalData(port);
        switch (port) {
            case REQUEST_COMPANY_EXPENDITURE_B_DETAIL:
                CompanyExpenditureInfoB companyExpenditureInfoB = null;
                for (CompanyExpenditureInfoB info : mExpenditureLocalBList) {
                    if (mLocalId == info.getLocalId()) {
                        companyExpenditureInfoB = info;
                    }
                }
                getExpenditureDetailB(companyExpenditureInfoB);
                break;
            case REQUEST_SAVE_COMPANY_EXPENDITURE_B:
                UploadInfoBean uploadInfoBean = new UploadInfoBean();
                uploadInfoBean.setId(mExpenditureId);
                uploadInfoBean.setCreateTime(TimeUtils.getCurrentTime(new Date()));
                uploadInfoBean.setUpdateTime(TimeUtils.getCurrentTime(new Date()));
                saveExpenditureB(uploadInfoBean, true);
                break;
        }
    }
}
