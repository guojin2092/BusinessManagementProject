package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyPayOrderEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPayOrderInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPayOrderDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyPayOrderDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.MySpinner;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.bigkoo.pickerview.TimePickerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class CompanyPaymentDetailActivity extends BaseMvpActivity<CompanyPayOrderDetailPresenter> implements CompanyPayOrderDetailContract.View {
    @BindView(R.id.et_pay_name)
    EditText et_pay_name;
    @BindView(R.id.spinner_customer_name)
    MySpinner spinner_customer_name;
    @BindView(R.id.et_pay_price)
    EditText et_pay_price;
    @BindView(R.id.tv_pay_time)
    TextView tv_pay_time;
    @BindView(R.id.spinner_sales)
    MySpinner spinner_sales;
    private String mSalesId = "";
    @BindView(R.id.spinner_trading_order)
    MySpinner spinner_trading_order;
    private String mTragdingOrderId = "";
    @BindView(R.id.fl_sf_invoice)
    FrameLayout fl_sf_invoice;
    @BindView(R.id.cb_sf_invoice)
    CheckBox cb_sf_invoice;
    private String mInvoiceCode = "";//1是2否
    @BindView(R.id.fl_sf_print)
    FrameLayout fl_sf_print;
    @BindView(R.id.cb_sf_print)
    CheckBox cb_sf_print;
    private String mPrintCode = "";
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.tv_save)
    TextView tv_save;

    private String mPayOrderId = "";
    private String mCompanyId = "";
    private String mUserId = "";

    private TimePickerView pvTime;


    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String payOrderId) {
        Intent intent = new Intent(activity, CompanyPaymentDetailActivity.class);
        intent.putExtra("id", payOrderId);
        activity.startActivity(intent);
    }


    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company_payment_detail);
    }

    @Override
    protected CompanyPayOrderDetailPresenter injectPresenter() {
        return new CompanyPayOrderDetailPresenter();
    }

    @Override
    public void initView() {
        super.initView();
        mPayOrderId = getIntent().getStringExtra("id");
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_name.setText("付款单详情");
        tv_save.setOnClickListener(this);
        tv_pay_time.setOnClickListener(this);
        fl_sf_invoice.setOnClickListener(this);
        fl_sf_print.setOnClickListener(this);

        String roleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (roleCode.equals("companySales")) {
            if (!TextUtils.isEmpty(mPayOrderId)) {
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
                tv_pay_time.setText(TimeUtils.getTimeByMinute(date));
            }
        })
                .setType(new boolean[]{true, true, true, true, true, false})
                .isDialog(true));
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_pay_time:
                pvTime.show();
                break;
            case R.id.fl_sf_invoice:
                if (cb_sf_invoice.isChecked()) {
                    cb_sf_invoice.setChecked(false);
                    mInvoiceCode = "2";
                } else {
                    cb_sf_invoice.setChecked(true);
                    mInvoiceCode = "1";
                }
                break;
            case R.id.fl_sf_print:
                if (cb_sf_print.isChecked()) {
                    cb_sf_print.setChecked(false);
                    mPrintCode = "2";
                } else {
                    cb_sf_print.setChecked(true);
                    mPrintCode = "1";
                }
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
                if (TextUtils.isEmpty(et_pay_name.getText())) {
                    toastMsg("尚未填写付款单名称");
                    return;
                }
                if (TextUtils.isEmpty(spinner_customer_name.getText())) {
                    toastMsg("尚未选择客户名称");
                    return;
                }
                if (TextUtils.isEmpty(et_pay_price.getText().toString().trim())) {
                    toastMsg("尚未输入付款金额");
                    return;
                }
                mPresenter.saveCompanyPayOrder(mPayOrderId, mCompanyId, mUserId, et_pay_name.getText().toString().trim(), mSalesId, mTragdingOrderId, spinner_customer_name.getText(), et_pay_price.getText().toString().trim(), tv_pay_time.getText().toString().trim(), "1", "1", "", et_remark.getText().toString().trim());
                break;
        }
    }

    @Override
    protected void requestData() {
        mPresenter.getAllSaleOrders(mCompanyId, mUserId);
        mPresenter.getAllTradingOrders(mCompanyId, mUserId);
        mPresenter.getAllCustomers(mCompanyId);
        if (!TextUtils.isEmpty(mPayOrderId)) {
            mPresenter.getCompanyPayOrderDetail(mPayOrderId);
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
        et_pay_name.setEnabled(canInput);
        spinner_customer_name.getTextView().setEnabled(canInput);
        et_pay_price.setEnabled(canInput);
        tv_pay_time.setEnabled(canInput);
        spinner_sales.getTextView().setEnabled(canInput);
        spinner_trading_order.getTextView().setEnabled(canInput);
        fl_sf_invoice.setEnabled(canInput);
        cb_sf_invoice.setEnabled(canInput);
        fl_sf_print.setEnabled(canInput);
        cb_sf_print.setEnabled(canInput);
        et_remark.setEnabled(canInput);
    }

    @Override
    public void getAllSaleOrders(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getName(), dicInfo2.getId()));
        }
        spinner_sales.setListDatas(this, list);
        spinner_sales.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mSalesId = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getAllTradingOrders(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getName(), dicInfo2.getId()));
        }
        spinner_trading_order.setListDatas(this, list);
        spinner_trading_order.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mTragdingOrderId = dicInfo.getCode();
            }
        });
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
    public void getCompanyPayOrderDetail(CompanyPayOrderInfo companyPayOrderInfo) {
        et_pay_name.setText(companyPayOrderInfo.getName());
        spinner_customer_name.setText(companyPayOrderInfo.getCustomerName());
        et_pay_price.setText(companyPayOrderInfo.getPrice());
        tv_pay_time.setText(companyPayOrderInfo.getPayTime());
        spinner_sales.setText(companyPayOrderInfo.getSalesOrderName());
        mSalesId = companyPayOrderInfo.getSalesOrderId();
        spinner_trading_order.setText(companyPayOrderInfo.getTradingOrderName());
        mTragdingOrderId = companyPayOrderInfo.getTradingOrderId();
        mInvoiceCode = companyPayOrderInfo.getHasInvoice();
        if (mInvoiceCode.equals("1")) {
            cb_sf_invoice.setChecked(true);
        } else {
            cb_sf_invoice.setChecked(false);
        }
        mPrintCode = companyPayOrderInfo.getHasPrint();
        if (mPrintCode.equals("1")) {
            cb_sf_print.setChecked(true);
        } else {
            cb_sf_print.setChecked(false);
        }
        et_remark.setText(companyPayOrderInfo.getRemark());
    }

    @Override
    public void saveCompanyPayOrder(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String toastString = "";
            if (TextUtils.isEmpty(mPayOrderId)) {
                toastString = "付款单创建成功";
            } else {
                toastString = "付款单修改成功";
            }
            EventBus.getDefault().post(new AddOrSaveCompanyPayOrderEvent(toastString));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }
}