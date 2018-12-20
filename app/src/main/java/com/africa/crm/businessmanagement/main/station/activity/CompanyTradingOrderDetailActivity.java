package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyTradingOrderEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyTradingOrderInfo;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanyTradingOrderDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyTradingOrderDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;

public class CompanyTradingOrderDetailActivity extends BaseMvpActivity<CompanyTradingOrderDetailPresenter> implements CompanyTradingOrderDetailContract.View {
    @BindView(R.id.et_order_name)
    EditText et_order_name;
    @BindView(R.id.et_customer_name)
    EditText et_customer_name;
    @BindView(R.id.et_contact_name)
    EditText et_contact_name;
    @BindView(R.id.et_money)
    EditText et_money;
    @BindView(R.id.et_income)
    EditText et_income;
    @BindView(R.id.et_thread_from)
    EditText et_thread_from;
    @BindView(R.id.et_possibility)
    EditText et_possibility;
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.tv_save)
    TextView tv_save;

    private String mTradingOrderId = "";
    private String mCompanyId = "";
    private String mUserId = "";

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, CompanyTradingOrderDetailActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }


    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_trading_order_detail);
    }

    @Override
    public void initView() {
        super.initView();
        mTradingOrderId = getIntent().getStringExtra("id");
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_name.setText("交易单详情");
        tv_save.setOnClickListener(this);

        String roleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (roleCode.equals("companySales")) {
            if (!TextUtils.isEmpty(mTradingOrderId)) {
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
    }

    @Override
    protected CompanyTradingOrderDetailPresenter injectPresenter() {
        return new CompanyTradingOrderDetailPresenter();
    }

    @Override
    protected void requestData() {
        if (!TextUtils.isEmpty(mTradingOrderId)) {
            mPresenter.getCompanyTradingOrderDetail(mTradingOrderId);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
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
                if (TextUtils.isEmpty(et_order_name.getText().toString().trim())) {
                    toastMsg("尚未填写交易单名称");
                    return;
                }
                mPresenter.saveCompanyTradingOrder(mTradingOrderId, mCompanyId, mUserId, et_order_name.getText().toString().trim(), et_customer_name.getText().toString().trim(), et_money.getText().toString().trim(), et_income.getText().toString().trim(), et_contact_name.getText().toString().trim(), et_possibility.getText().toString().trim(), et_thread_from.getText().toString().trim(), et_remark.getText().toString().trim());
                break;
        }
    }

    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        et_order_name.setEnabled(canInput);
        et_customer_name.setEnabled(canInput);
        et_contact_name.setEnabled(canInput);
        et_money.setEnabled(canInput);
        et_income.setEnabled(canInput);
        et_thread_from.setEnabled(canInput);
        et_possibility.setEnabled(canInput);
        et_remark.setEnabled(canInput);

    }


    @Override
    public void getCompanyTradingOrderDetail(CompanyTradingOrderInfo companyTradingOrderInfo) {
        et_order_name.setText(companyTradingOrderInfo.getName());
        et_customer_name.setText(companyTradingOrderInfo.getCustomerName());
        et_money.setText(companyTradingOrderInfo.getPrice());
        et_income.setText(companyTradingOrderInfo.getEstimateProfit());
        et_thread_from.setText(companyTradingOrderInfo.getClueSource());
        et_possibility.setText(companyTradingOrderInfo.getPossibility());
        et_remark.setText(companyTradingOrderInfo.getRemark());
    }

    @Override
    public void saveCompanyTradingOrder(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String toastString = "";
            if (TextUtils.isEmpty(mTradingOrderId)) {
                toastString = "交易单创建成功";
            } else {
                toastString = "交易单修改成功";
            }
            EventBus.getDefault().post(new AddOrSaveCompanyTradingOrderEvent(toastString));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }
}
