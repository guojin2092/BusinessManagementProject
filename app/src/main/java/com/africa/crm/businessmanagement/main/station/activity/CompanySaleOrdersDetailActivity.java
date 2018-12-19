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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanySalesOrderEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanySalesOrderInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanySalesOrderDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanySalesOrderDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.MySpinner;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CompanySaleOrdersDetailActivity extends BaseMvpActivity<CompanySalesOrderDetailPresenter> implements CompanySalesOrderDetailContract.View {
    @BindView(R.id.et_sale_order_name)
    EditText et_sale_order_name;
    @BindView(R.id.et_customer_name)
    EditText et_customer_name;
    @BindView(R.id.et_contact_name)
    EditText et_contact_name;
    @BindView(R.id.et_money)
    EditText et_money;
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

    @BindView(R.id.spinner_state)
    MySpinner spinner_state;
    private static final String STATE_CODE = "SALEORDERSTATE";
    private List<DicInfo> mSalesOrderStateList = new ArrayList<>();
    private String mStateCode = "";

    private String mSalesOrderId;
    private String mCompanyId = "";
    private String mUserId = "";

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, CompanySaleOrdersDetailActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }


    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sales_detail);
    }

    @Override
    protected CompanySalesOrderDetailPresenter injectPresenter() {
        return new CompanySalesOrderDetailPresenter();
    }

    @Override
    public void initView() {
        super.initView();
        mSalesOrderId = getIntent().getStringExtra("id");
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_name.setText("销售单详情");
        tv_save.setOnClickListener(this);

        String roleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (roleCode.equals("companySales")) {
            if (!TextUtils.isEmpty(mSalesOrderId)) {
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
                if (TextUtils.isEmpty(et_sale_order_name.getText().toString().trim())) {
                    toastMsg("尚未填写销售单名称");
                    return;
                }
                mPresenter.saveCompanySalesOrder(mSalesOrderId, mCompanyId, mUserId, et_sale_order_name.getText().toString().trim(), et_customer_name.getText().toString().trim(), et_contact_name.getText().toString().trim(), et_money.getText().toString().trim(), mStateCode, et_deliver_address.getText().toString().trim(), et_deliver_zip_code.getText().toString().trim(), et_receiver_address.getText().toString().trim(), et_receiver_zip_code.getText().toString().trim(), "", et_clause.getText().toString().trim(), et_remark.getText().toString().trim());
                break;
        }
    }

    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        et_sale_order_name.setEnabled(canInput);
        spinner_state.getTextView().setEnabled(canInput);
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
    protected void requestData() {
        mPresenter.getOrderState(STATE_CODE);
        if (!TextUtils.isEmpty(mSalesOrderId)) {
            mPresenter.getCompanySalesOrderDetail(mSalesOrderId);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void getOrderState(List<DicInfo> dicInfoList) {
        mSalesOrderStateList.clear();
        mSalesOrderStateList.addAll(dicInfoList);
        spinner_state.setListDatas(this, mSalesOrderStateList);

        spinner_state.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mStateCode = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getCompanySalesOrderDetail(CompanySalesOrderInfo companySalesOrderInfo) {
        et_sale_order_name.setText(companySalesOrderInfo.getName());
        spinner_state.setText(companySalesOrderInfo.getStateName());
        mStateCode = companySalesOrderInfo.getState();
        et_customer_name.setText(companySalesOrderInfo.getCustomerName());
        et_contact_name.setText(companySalesOrderInfo.getContactName());
        et_money.setText(companySalesOrderInfo.getSaleCommission());
        et_deliver_address.setText(companySalesOrderInfo.getSendAddress());
        et_deliver_zip_code.setText(companySalesOrderInfo.getSendAddressZipCode());
        et_receiver_address.setText(companySalesOrderInfo.getDestinationAddress());
        et_receiver_zip_code.setText(companySalesOrderInfo.getDestinationAddressZipCode());
        et_clause.setText(companySalesOrderInfo.getClause());
        et_remark.setText(companySalesOrderInfo.getRemark());
    }

    @Override
    public void saveCompanySalesOrder(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String toastString = "";
            if (TextUtils.isEmpty(mSalesOrderId)) {
                toastString = "销售单创建成功";
            } else {
                toastString = "销售单修改成功";
            }
            EventBus.getDefault().post(new AddOrSaveCompanySalesOrderEvent(toastString));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }
}
