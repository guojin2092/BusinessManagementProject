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
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanyTradingOrderDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyTradingOrderDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.MySpinner;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CompanyTradingOrderDetailActivity extends BaseMvpActivity<CompanyTradingOrderDetailPresenter> implements CompanyTradingOrderDetailContract.View {
    @BindView(R.id.et_order_name)
    EditText et_order_name;
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

    @BindView(R.id.spinner_customer_name)
    MySpinner spinner_customer_name;

    @BindView(R.id.spinner_contact_name)
    MySpinner spinner_contact_name;

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
        mPresenter.getAllContact(mCompanyId);
        mPresenter.getAllCustomers(mCompanyId);
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
                mPresenter.saveCompanyTradingOrder(mTradingOrderId, mCompanyId, mUserId, et_order_name.getText().toString().trim(), spinner_customer_name.getText(), et_money.getText().toString().trim(), et_income.getText().toString().trim(), spinner_contact_name.getText(), et_possibility.getText().toString().trim(), et_thread_from.getText().toString().trim(), et_remark.getText().toString().trim());
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
        spinner_customer_name.getTextView().setEnabled(canInput);
        spinner_contact_name.getTextView().setEnabled(canInput);
        et_money.setEnabled(canInput);
        et_income.setEnabled(canInput);
        et_thread_from.setEnabled(canInput);
        et_possibility.setEnabled(canInput);
        et_remark.setEnabled(canInput);

    }

    @Override
    public void getAllContact(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getName(), dicInfo2.getId()));
        }
        spinner_contact_name.setListDatas(this, list);
    }

    @Override
    public void getAllCustomers(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getName(), dicInfo2.getId()));
        }
        spinner_customer_name.setListDatas(this, list);
    }

    @Override
    public void getCompanyTradingOrderDetail(CompanyTradingOrderInfo companyTradingOrderInfo) {
        et_order_name.setText(companyTradingOrderInfo.getName());
        spinner_customer_name.getTextView().setText(companyTradingOrderInfo.getCustomerName());
        spinner_contact_name.getTextView().setText(companyTradingOrderInfo.getContactName());
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
