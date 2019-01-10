package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyExpanditureEventB;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyExpenditureInfoB;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanyExpenditureDetailContractB;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyExpenditureDetailPresenterB;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.EditTextUtil;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.bigkoo.pickerview.TimePickerView;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

import butterknife.BindView;

public class CompanyExpenditureDetailActivityB extends BaseMvpActivity<CompanyExpenditureDetailPresenterB> implements CompanyExpenditureDetailContractB.View {
    /*    @BindView(R.id.et_title)
        EditText et_title;*/
    @BindView(R.id.et_price)
    EditText et_price;
    @BindView(R.id.tv_date)
    TextView tv_date;
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.tv_save)
    TextView tv_save;

    private TimePickerView pvTime;

    private String mExpenditureId = "";
    private String mCompanyId = "";
    private String mUserId = "";


    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, CompanyExpenditureDetailActivityB.class);
        intent.putExtra("id", id);
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
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_right.setVisibility(View.GONE);
        titlebar_name.setText("企业支出");
        tv_save.setText(R.string.add);
        tv_save.setOnClickListener(this);
        tv_date.setOnClickListener(this);
        EditTextUtil.setPricePoint(et_price);
        if (!TextUtils.isEmpty(mExpenditureId)) {
            tv_save.setVisibility(View.GONE);
            setEditTextInput(false);
        } else {
            tv_save.setVisibility(View.VISIBLE);
        }
        initTimePicker();
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
        if (!TextUtils.isEmpty(mExpenditureId)) {
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
//        et_title.setEnabled(canInput);
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
              /*  if (TextUtils.isEmpty(et_title.getText().toString().trim())) {
                    toastMsg("尚未填写支出标题");
                    return;
                }*/
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
        et_price.setText(companyExpenditureInfoB.getPrice());
        tv_date.setText(companyExpenditureInfoB.getPayDate());
        et_remark.setText(companyExpenditureInfoB.getRemark());
    }

    @Override
    public void saveExpenditureB(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String toastString = "";
            if (TextUtils.isEmpty(mExpenditureId)) {
                toastString = "企业支出单创建成功";
            }
            EventBus.getDefault().post(new AddOrSaveCompanyExpanditureEventB(toastString));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }
}
