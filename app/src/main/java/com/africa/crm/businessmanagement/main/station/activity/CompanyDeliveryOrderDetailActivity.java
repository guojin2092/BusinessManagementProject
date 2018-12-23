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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyDeliveryOrderEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyDeliveryOrderInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.OrderProductInfo;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.OrderProductListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyDeliveryOrderDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyDeliveryOrderDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.MySpinner;
import com.africa.crm.businessmanagement.widget.MySpinner2;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.africa.crm.businessmanagement.widget.dialog.AlertDialog;
import com.bigkoo.pickerview.TimePickerView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

public class CompanyDeliveryOrderDetailActivity extends BaseMvpActivity<CompanyDeliveryOrderDetailPresenter> implements CompanyDeliveryOrderDetailContract.View {
    @BindView(R.id.et_delivery_order_name)
    EditText et_delivery_order_name;
    @BindView(R.id.tv_arrive_date)
    TextView tv_arrive_date;
    @BindView(R.id.et_logistics_code)
    EditText et_logistics_code;
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

    private String mDeliveryOrderId = "";
    private String mCompanyId = "";
    private String mUserId = "";

    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.tv_delete_product)
    TextView tv_delete_product;
    @BindView(R.id.rv_product)
    RecyclerView rv_product;
    private OrderProductListAdapter mOrderProductListAdapter;
    private List<OrderProductInfo> mDeleteList = new ArrayList<>();
    private List<OrderProductInfo> mOrderProductInfoList = new ArrayList<>();
    private boolean mShowCheckBox = false;
    @BindView(R.id.spinner_add_product)
    MySpinner2 spinner_add_product;

    private TimePickerView pvTime;

    @BindView(R.id.spinner_sales_id)
    MySpinner spinner_sales_id;
    private String mSalesId = "";

    @BindView(R.id.spinner_state)
    MySpinner spinner_state;
    private static final String STATE_TYPE = "INVOICESTATE";
    private List<DicInfo> mSpinnerStateList = new ArrayList<>();
    private String mStateCode = "";

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String deliveryOrderId) {
        Intent intent = new Intent(activity, CompanyDeliveryOrderDetailActivity.class);
        intent.putExtra("id", deliveryOrderId);
        activity.startActivity(intent);
    }


    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_delivery_order_detail);
    }

    @Override
    protected CompanyDeliveryOrderDetailPresenter injectPresenter() {
        return new CompanyDeliveryOrderDetailPresenter();
    }

    @Override
    public void initView() {
        super.initView();
        mDeliveryOrderId = getIntent().getStringExtra("id");
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_name.setText("发货单详情");

        tv_delete.setOnClickListener(this);
        tv_delete_product.setOnClickListener(this);
        tv_arrive_date.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        String roleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (roleCode.equals("companySales")) {
            if (!TextUtils.isEmpty(mDeliveryOrderId)) {
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
                tv_arrive_date.setText(TimeUtils.getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true));
    }

    private void initProductList() {
        tv_delete_product.setOnClickListener(this);
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
    protected void requestData() {
        mPresenter.getState(STATE_TYPE);
        mPresenter.getAllSaleOrders(mCompanyId, mUserId);
        mPresenter.getAllProduct(mCompanyId);
        if (!TextUtils.isEmpty(mDeliveryOrderId)) {
            mPresenter.getCompanyDeliveryOrderDetail(mDeliveryOrderId);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_arrive_date:
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
                if (TextUtils.isEmpty(et_delivery_order_name.getText().toString().trim())) {
                    toastMsg("尚未填写发货单");
                    return;
                }
                if (TextUtils.isEmpty(et_logistics_code.getText().toString().trim())) {
                    toastMsg("尚未填写物流编号");
                    return;
                }
                if (TextUtils.isEmpty(tv_arrive_date.getText().toString().trim())) {
                    toastMsg("尚未选择到达时间");
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
                mPresenter.saveCompanyDeliveryOrder(mDeliveryOrderId, mCompanyId, mUserId, et_delivery_order_name.getText().toString().trim(), mSalesId, et_logistics_code.getText().toString().trim(), mStateCode, tv_arrive_date.getText().toString().trim(), et_deliver_address.getText().toString().trim(), et_deliver_zip_code.getText().toString().trim(), et_receiver_address.getText().toString().trim(), et_receiver_zip_code.getText().toString().trim(), new Gson().toJson(mOrderProductInfoList), et_clause.getText().toString().trim(), et_remark.getText().toString().trim());
                break;
        }
    }

    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        et_delivery_order_name.setEnabled(canInput);
        spinner_sales_id.getTextView().setEnabled(canInput);
        spinner_state.getTextView().setEnabled(canInput);
        et_logistics_code.setEnabled(canInput);
        tv_arrive_date.setEnabled(canInput);
        et_deliver_address.setEnabled(canInput);
        et_deliver_zip_code.setEnabled(canInput);
        et_receiver_address.setEnabled(canInput);
        et_receiver_zip_code.setEnabled(canInput);
        et_clause.setEnabled(canInput);
        et_remark.setEnabled(canInput);
        tv_delete_product.setEnabled(canInput);
        spinner_add_product.getTextView().setEnabled(canInput);
    }


    @Override
    public void getState(List<DicInfo> dicInfoList) {
        mSpinnerStateList.clear();
        mSpinnerStateList.addAll(dicInfoList);
        spinner_state.setListDatas(this, mSpinnerStateList);

        spinner_state.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mStateCode = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getAllProduct(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getName(), dicInfo2.getId()));
        }
        spinner_add_product.setListDatas(this, list);
        spinner_add_product.addOnItemClickListener(new MySpinner2.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mOrderProductInfoList.add(new OrderProductInfo(dicInfo.getText(), dicInfo.getCode()));
                if (mOrderProductListAdapter != null) {
                    mOrderProductListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void getAllSaleOrders(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getName(), dicInfo2.getId()));
        }
        spinner_sales_id.setListDatas(this, list);
        spinner_sales_id.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mSalesId = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getCompanyDeliveryOrderDetail(CompanyDeliveryOrderInfo companyDeliveryOrderInfo) {
        et_delivery_order_name.setText(companyDeliveryOrderInfo.getName());
        spinner_sales_id.setText(companyDeliveryOrderInfo.getSalesOrderName());
        mSalesId = companyDeliveryOrderInfo.getSalesOrderId();
        spinner_state.setText(companyDeliveryOrderInfo.getStateName());
        mStateCode = companyDeliveryOrderInfo.getState();
        et_logistics_code.setText(companyDeliveryOrderInfo.getLogisticsCode());
        tv_arrive_date.setText(companyDeliveryOrderInfo.getArriveDate());
        et_deliver_address.setText(companyDeliveryOrderInfo.getSendAddress());
        et_deliver_zip_code.setText(companyDeliveryOrderInfo.getSendAddressZipCode());
        et_receiver_address.setText(companyDeliveryOrderInfo.getDestinationAddress());
        et_receiver_zip_code.setText(companyDeliveryOrderInfo.getDestinationAddressZipCode());
        List<OrderProductInfo> list = new Gson().fromJson(companyDeliveryOrderInfo.getProducts(), new TypeToken<List<OrderProductInfo>>() {
        }.getType());
        mOrderProductInfoList.addAll(list);
        if (mOrderProductListAdapter != null) {
            mOrderProductListAdapter.notifyDataSetChanged();
        }
        et_clause.setText(companyDeliveryOrderInfo.getClause());

    }

    @Override
    public void saveCompanyDeliveryOrder(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String toastString = "";
            if (TextUtils.isEmpty(mDeliveryOrderId)) {
                toastString = "发货单创建成功";
            } else {
                toastString = "发货单修改成功";
            }
            EventBus.getDefault().post(new AddOrSaveCompanyDeliveryOrderEvent(toastString));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }
}