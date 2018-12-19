package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyQuotationEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyQuotationInfo;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanyQuotationDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyQuotationDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.bigkoo.pickerview.TimePickerView;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

import butterknife.BindView;

public class CompanyQuotationDetailActivity extends BaseMvpActivity<CompanyQuotationDetailPresenter> implements CompanyQuotationDetailContract.View {
    @BindView(R.id.et_quotation_name)
    EditText et_quotation_name;
    @BindView(R.id.tv_validity_date)
    TextView tv_validity_date;
    @BindView(R.id.et_customer_name)
    EditText et_customer_name;
    @BindView(R.id.et_contact_name)
    EditText et_contact_name;
    @BindView(R.id.et_deliver_address)
    EditText et_deliver_address;
    @BindView(R.id.et_deliver_zip_code)
    EditText et_deliver_zip_code;
    @BindView(R.id.et_receiver_address)
    EditText et_receiver_address;
    @BindView(R.id.et_receiver_zip_code)
    EditText et_receiver_zip_code;
    @BindView(R.id.et_clause)
    EditText et_clause;
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.tv_save)
    TextView tv_save;
    private String mQuotationId = "";//报价单Id
    private String mCompanyId = "";
    private String mUserId = "";

    private TimePickerView pvTime;


    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, CompanyQuotationDetailActivity.class);
        intent.putExtra("quotationId", id);
        activity.startActivity(intent);
    }


    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_quotation_detail);
    }

    @Override
    protected CompanyQuotationDetailPresenter injectPresenter() {
        return new CompanyQuotationDetailPresenter();
    }

    @Override
    public void initView() {
        super.initView();
        mQuotationId = getIntent().getStringExtra("quotationId");
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_name.setText("报价单详情");
        tv_save.setOnClickListener(this);
        tv_validity_date.setOnClickListener(this);
        String roleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (roleCode.equals("companySales")) {
            if (!TextUtils.isEmpty(mQuotationId)) {
                titlebar_right.setText(R.string.edit);
                tv_save.setText(R.string.save);
                setEditTextInput(false);
            } else {
                titlebar_right.setVisibility(View.GONE);
                tv_save.setText(R.string.add);
                tv_save.setVisibility(View.VISIBLE);
            }
        } else {
            titlebar_right.setVisibility(View.GONE);
            tv_save.setVisibility(View.GONE);
            setEditTextInput(false);
        }
        initTimePicker();
    }

    private void initTimePicker() {
        pvTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                tv_validity_date.setText(TimeUtils.getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true));
    }

    @Override
    public void initData() {

    }

    @Override
    protected void requestData() {
        if (!TextUtils.isEmpty(mQuotationId)) {
            mPresenter.getCompanyQuotationDetail(mQuotationId);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_validity_date:
                pvTime.show();
                break;
            case R.id.titlebar_right:
                if (titlebar_right.getText().toString().equals(getString(R.string.edit))) {
                    titlebar_right.setText(R.string.cancel);
                    tv_save.setVisibility(View.VISIBLE);
                    setEditTextInput(true);
                } else {
                    titlebar_right.setText(R.string.edit);
                    tv_save.setVisibility(View.GONE);
                    setEditTextInput(false);
                }
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(et_quotation_name.getText().toString().trim())) {
                    toastMsg("尚未填写报价单名称");
                    return;
                }
                if (TextUtils.isEmpty(et_deliver_address.getText().toString().trim())) {
                    toastMsg("尚未填写发货地址");
                    return;
                }
                if (TextUtils.isEmpty(et_deliver_zip_code.getText().toString().trim())) {
                    toastMsg("尚未填写发货地址邮编");
                    return;
                }
                if (TextUtils.isEmpty(et_receiver_address.getText().toString().trim())) {
                    toastMsg("尚未填写收货地址");
                    return;
                }
                if (TextUtils.isEmpty(et_receiver_zip_code.getText().toString().trim())) {
                    toastMsg("尚未填写收货地址邮编");
                    return;
                }
                if (TextUtils.isEmpty(et_clause.getText().toString().trim())) {
                    toastMsg("尚未输入条款及条件");
                    return;
                }
                if (TextUtils.isEmpty(et_remark.getText().toString().trim())) {
                    toastMsg("尚未输入备注");
                    return;
                }
                mPresenter.saveCompanyQuotation(mQuotationId, mCompanyId, mUserId, et_quotation_name.getText().toString().trim(), et_customer_name.getText().toString().trim(), et_contact_name.getText().toString().trim(), tv_validity_date.getText().toString().trim(), et_deliver_address.getText().toString().trim(), et_deliver_zip_code.getText().toString().trim(), et_receiver_address.getText().toString().trim(), et_receiver_zip_code.getText().toString().trim(), "", et_clause.getText().toString().trim(), et_remark.getText().toString().trim());
                break;
        }
    }


    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        et_quotation_name.setEnabled(canInput);
        tv_validity_date.setEnabled(canInput);
        et_customer_name.setEnabled(canInput);
        et_contact_name.setEnabled(canInput);
        et_deliver_address.setEnabled(canInput);
        et_deliver_zip_code.setEnabled(canInput);
        et_receiver_address.setEnabled(canInput);
        et_receiver_zip_code.setEnabled(canInput);
        et_clause.setEnabled(canInput);
        et_remark.setEnabled(canInput);
    }


    @Override
    public void getCompanyQuotationDetail(CompanyQuotationInfo companyQuotationInfo) {
        et_quotation_name.setText(companyQuotationInfo.getName());
        tv_validity_date.setText(companyQuotationInfo.getTermOfValidity());
        et_customer_name.setText(companyQuotationInfo.getCustomerName());
        et_contact_name.setText(companyQuotationInfo.getContactName());
        et_deliver_address.setText(companyQuotationInfo.getSendAddress());
        et_deliver_zip_code.setText(companyQuotationInfo.getSendAddressZipCode());
        et_receiver_address.setText(companyQuotationInfo.getDestinationAddress());
        et_receiver_zip_code.setText(companyQuotationInfo.getDestinationAddressZipCode());
        et_clause.setText(companyQuotationInfo.getClause());
        et_remark.setText(companyQuotationInfo.getRemark());
    }

    @Override
    public void saveCompanyQuotation(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String toastString = "";
            if (TextUtils.isEmpty(mQuotationId)) {
                toastString = "报价单创建成功";
            } else {
                toastString = "报价单修改成功";
            }
            EventBus.getDefault().post(new AddOrSaveCompanyQuotationEvent(toastString));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }
}
