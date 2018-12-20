package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyQuotationEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyQuotationInfo;
import com.africa.crm.businessmanagement.main.bean.OrderProductInfo;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.OrderProductListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyQuotationDetailContract;
import com.africa.crm.businessmanagement.main.station.dialog.AddProductDialog;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyQuotationDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.africa.crm.businessmanagement.widget.dialog.AlertDialog;
import com.bigkoo.pickerview.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.tv_delete_product)
    TextView tv_delete_product;
    @BindView(R.id.tv_add_product)
    TextView tv_add_product;
    @BindView(R.id.rv_product)
    RecyclerView rv_product;
    private OrderProductListAdapter mOrderProductListAdapter;
    private List<OrderProductInfo> mDeleteList = new ArrayList<>();
    private List<OrderProductInfo> mOrderProductInfoList = new ArrayList<>();
    private boolean mShowCheckBox = false;

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
        tv_delete.setOnClickListener(this);
        tv_delete_product.setOnClickListener(this);
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
        initProductList();
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

    private void initProductList() {
        tv_delete_product.setOnClickListener(this);
        tv_add_product.setOnClickListener(this);
        mOrderProductListAdapter = new OrderProductListAdapter(mOrderProductInfoList);
        rv_product.setAdapter(mOrderProductListAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_product.setLayoutManager(layoutManager);
        rv_product.addItemDecoration(new LineItemDecoration(this, LinearLayoutManager.VERTICAL, 2, ContextCompat.getColor(this, R.color.F2F2F2)));
        rv_product.setHasFixedSize(true);
        rv_product.setNestedScrollingEnabled(false);

        mOrderProductListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (mShowCheckBox) {
                    CheckBox cb_choose = (CheckBox) adapter.getViewByPosition(rv_product, position, R.id.cb_choose);
                    mOrderProductInfoList.get(position).setChosen(!cb_choose.isChecked());
                    adapter.notifyDataSetChanged();
                }
            }
        });

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
            case R.id.tv_delete_product:
                if (tv_delete_product.getText().toString().equals(getString(R.string.delete))) {
                    tv_delete_product.setText(R.string.cancel);
                    tv_delete.setVisibility(View.VISIBLE);
                    mShowCheckBox = true;
                } else {
                    tv_delete_product.setText(R.string.delete);
                    tv_delete.setVisibility(View.GONE);
                    mShowCheckBox = false;
                }
                if (mOrderProductListAdapter != null) {
                    mOrderProductListAdapter.setmIsDeleted(mShowCheckBox);
                }
                break;
            case R.id.tv_delete:
                mDeleteList.clear();
                for (OrderProductInfo orderProductInfo : mOrderProductInfoList) {
                    if (orderProductInfo.isChosen()) {
                        mDeleteList.add(orderProductInfo);
                    }
                }
                if (ListUtils.isEmpty(mDeleteList)) {
                    toastMsg("尚未选择删除项");
                    return;
                }
                new AlertDialog.Builder(this)
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
                                    if (mOrderProductInfoList.contains(mDeleteList.get(i))) {
                                        int position = mOrderProductInfoList.indexOf(mDeleteList.get(i));
                                        mOrderProductInfoList.remove(mDeleteList.get(i));
                                        if (mOrderProductListAdapter != null) {
                                            mOrderProductListAdapter.notifyItemRemoved(position);
                                        }
                                    }
                                }
                                toastMsg("删除成功");
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.tv_add_product:
                final AddProductDialog addProductDialog = AddProductDialog.getInstance(this);
                addProductDialog.isCancelableOnTouchOutside(false)
                        .withDuration(300)
                        .withEffect(Effectstype.Fadein)
                        .setCancelClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                addProductDialog.dismiss();
                            }
                        })
                        .show();
                addProductDialog.addOnSaveClickListener(new AddProductDialog.OnSaveClickListener() {
                    @Override
                    public void onSaveClick(OrderProductInfo orderProductInfo) {
                        if (TextUtils.isEmpty(orderProductInfo.getName())) {
                            toastMsg("尚未填写名称");
                            return;
                        }
                        if (TextUtils.isEmpty(orderProductInfo.getNum())) {
                            toastMsg("尚未填写名称");
                            return;
                        }
                        mOrderProductInfoList.add(orderProductInfo);
                        if (mOrderProductListAdapter != null) {
                            mOrderProductListAdapter.notifyDataSetChanged();
                        }
                        addProductDialog.dismiss();
                    }
                });
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
                mPresenter.saveCompanyQuotation(mQuotationId, mCompanyId, mUserId, et_quotation_name.getText().toString().trim(), et_customer_name.getText().toString().trim(), et_contact_name.getText().toString().trim(), tv_validity_date.getText().toString().trim(), et_deliver_address.getText().toString().trim(), et_deliver_zip_code.getText().toString().trim(), et_receiver_address.getText().toString().trim(), et_receiver_zip_code.getText().toString().trim(), new Gson().toJson(mOrderProductInfoList), et_clause.getText().toString().trim(), et_remark.getText().toString().trim());
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
        tv_delete_product.setEnabled(canInput);
        tv_add_product.setEnabled(canInput);
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
        List<OrderProductInfo> list = new Gson().fromJson(companyQuotationInfo.getProducts(), new TypeToken<List<OrderProductInfo>>() {}.getType());
        mOrderProductInfoList.addAll(list);
        if (mOrderProductListAdapter != null) {
            mOrderProductListAdapter.notifyDataSetChanged();
        }
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
