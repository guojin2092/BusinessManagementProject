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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyPurchasingOrderEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPurchasingOrderInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.OrderProductInfo;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.OrderProductListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPurchasingDetailContract;
import com.africa.crm.businessmanagement.main.station.dialog.AddProductDialog;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyPurchasingDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.MySpinner;
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

public class CompanyPurchasingDetailActivity extends BaseMvpActivity<CompanyPurchasingDetailPresenter> implements CompanyPurchasingDetailContract.View {
    @BindView(R.id.et_purchasing_order_name)
    EditText et_purchasing_order_name;
    @BindView(R.id.spinner_supplier_name)
    MySpinner spinner_supplier_name;
    @BindView(R.id.tv_order_date)
    TextView tv_order_date;
    @BindView(R.id.tv_arrive_date)
    TextView tv_arrive_date;
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
    private static final String STATE_CODE = "PURCHASESTATE";
    private List<DicInfo> mOrderStateList = new ArrayList<>();
    private String mStateCode = "";

    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.tv_delete_product)
    TextView tv_delete_product;
    @BindView(R.id.tv_add_product)
    TextView tv_add_product;
    @BindView(R.id.rv_product)
    RecyclerView rv_product;
    private OrderProductListAdapter mOrderProductListAdapter;
    private AddProductDialog mAddProductDialog;
    private List<OrderProductInfo> mDeleteList = new ArrayList<>();
    private List<OrderProductInfo> mOrderProductInfoList = new ArrayList<>();
    private List<DicInfo2> mProductTypeList = new ArrayList<>();

    private TimePickerView pvOrderTime, pvArriveTime;
    private Date mOrderDate, mArriveDate;

    private boolean mShowCheckBox = false;
    private String mPurchasingOrderId = "";
    private String mCompanyId = "";
    private String mUserId = "";

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, CompanyPurchasingDetailActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }


    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_purchasing_detail);
    }

    @Override
    public void initView() {
        super.initView();
        mPurchasingOrderId = getIntent().getStringExtra("id");
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_name.setText("采购单详情");
        tv_delete.setOnClickListener(this);
        tv_delete_product.setOnClickListener(this);
        tv_add_product.setOnClickListener(this);
        tv_order_date.setOnClickListener(this);
        tv_arrive_date.setOnClickListener(this);
        tv_save.setOnClickListener(this);

        String roleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (roleCode.equals("companySales")) {
            if (!TextUtils.isEmpty(mPurchasingOrderId)) {
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
        mAddProductDialog = AddProductDialog.getInstance(this);
        mAddProductDialog.isCancelableOnTouchOutside(false)
                .withDuration(300)
                .withEffect(Effectstype.Fadein)
                .setCancelClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAddProductDialog.dismiss();
                    }
                });
        mAddProductDialog.addOnSaveClickListener(new AddProductDialog.OnSaveClickListener() {
            @Override
            public void onSaveClick(OrderProductInfo orderProductInfo) {
                if (TextUtils.isEmpty(orderProductInfo.getName())) {
                    toastMsg("尚未选择产品");
                    return;
                }
                mOrderProductInfoList.add(new OrderProductInfo(orderProductInfo.getName(), orderProductInfo.getNum()));
                if (mOrderProductListAdapter != null) {
                    mOrderProductListAdapter.notifyDataSetChanged();
                }
                mAddProductDialog.dismiss();
            }
        });
        initTimePicker();
        initProductList();
    }

    private void initTimePicker() {
        pvOrderTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mOrderDate = date;
                if (mArriveDate != null) {
                    if (mArriveDate.getTime() < mOrderDate.getTime()) {
                        toastMsg("到达日期不得小于订单日期");
                        return;
                    }
                }
                tv_order_date.setText(TimeUtils.getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true));
        pvArriveTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mArriveDate = date;
                if (mOrderDate != null) {
                    if (mArriveDate.getTime() < mOrderDate.getTime()) {
                        toastMsg("到达日期不得小于订单日期");
                        return;
                    }
                }
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
    protected CompanyPurchasingDetailPresenter injectPresenter() {
        return new CompanyPurchasingDetailPresenter();
    }

    @Override
    protected void requestData() {
        mPresenter.getStateType(STATE_CODE);
        mPresenter.getAllSuppliers(mCompanyId);
        if (!TextUtils.isEmpty(mPurchasingOrderId)) {
            mPresenter.getCompanyPurchasingDetail(mPurchasingOrderId);
        }
    }


    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_order_date:
                if (pvOrderTime != null) {
                    pvOrderTime.show();
                }
                break;
            case R.id.tv_arrive_date:
                if (pvArriveTime != null) {
                    pvArriveTime.show();
                }
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
            case R.id.tv_add_product:
                mPresenter.getAllProduct(mCompanyId);
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
                if (TextUtils.isEmpty(et_purchasing_order_name.getText().toString().trim())) {
                    toastMsg("尚未填写采购单名称");
                    return;
                }
                mPresenter.saveCompanyPurchasing(mPurchasingOrderId, mCompanyId, mUserId, et_purchasing_order_name.getText().toString().trim(), spinner_supplier_name.getText(), mStateCode, tv_order_date.getText().toString().trim(), tv_arrive_date.getText().toString().trim(), et_deliver_address.getText().toString().trim(), et_deliver_zip_code.getText().toString().trim(), et_receiver_address.getText().toString().trim(), et_receiver_zip_code.getText().toString().trim(), new Gson().toJson(mOrderProductInfoList), et_clause.getText().toString().trim(), et_remark.getText().toString().trim());
                break;
        }
    }

    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        et_purchasing_order_name.setEnabled(canInput);
        spinner_supplier_name.getTextView().setEnabled(canInput);
        spinner_state.getTextView().setEnabled(canInput);
        tv_order_date.setEnabled(canInput);
        tv_arrive_date.setEnabled(canInput);
        et_deliver_address.setEnabled(canInput);
        et_deliver_zip_code.setEnabled(canInput);
        et_receiver_address.setEnabled(canInput);
        et_receiver_zip_code.setEnabled(canInput);
        tv_delete_product.setEnabled(canInput);
        tv_add_product.setEnabled(canInput);
        et_clause.setEnabled(canInput);
        et_remark.setEnabled(canInput);
    }

    @Override
    public void getAllSuppliers(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getName(), dicInfo2.getId()));
        }
        spinner_supplier_name.setListDatas(this, list);
    }

    @Override
    public void getStateType(List<DicInfo> dicInfoList) {
        mOrderStateList.clear();
        mOrderStateList.addAll(dicInfoList);
        spinner_state.setListDatas(this, mOrderStateList);

        spinner_state.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mStateCode = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getAllProduct(List<DicInfo2> dicInfoList) {
        mProductTypeList.addAll(dicInfoList);
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getName(), dicInfo2.getId()));
        }
        mAddProductDialog.show();
        mAddProductDialog.setListDatas(this, list);
    }

    @Override
    public void getCompanyPurchasingDetail(CompanyPurchasingOrderInfo companyPurchasingOrderInfo) {
        et_purchasing_order_name.setText(companyPurchasingOrderInfo.getName());
        spinner_supplier_name.setText(companyPurchasingOrderInfo.getSupplierName());
        spinner_state.setText(companyPurchasingOrderInfo.getStateName());
        mStateCode = companyPurchasingOrderInfo.getState();
        tv_order_date.setText(companyPurchasingOrderInfo.getOrderDate());
        mOrderDate = TimeUtils.getDataByString(companyPurchasingOrderInfo.getOrderDate());
        tv_arrive_date.setText(companyPurchasingOrderInfo.getArriveDate());
        mArriveDate = TimeUtils.getDataByString(companyPurchasingOrderInfo.getArriveDate());
        et_deliver_address.setText(companyPurchasingOrderInfo.getSendAddress());
        et_deliver_zip_code.setText(companyPurchasingOrderInfo.getSendAddressZipCode());
        et_receiver_address.setText(companyPurchasingOrderInfo.getDestinationAddress());
        et_receiver_zip_code.setText(companyPurchasingOrderInfo.getDestinationAddressZipCode());
        et_clause.setText(companyPurchasingOrderInfo.getClause());
        et_remark.setText(companyPurchasingOrderInfo.getRemark());
        List<OrderProductInfo> list = new Gson().fromJson(companyPurchasingOrderInfo.getProducts(), new TypeToken<List<OrderProductInfo>>() {
        }.getType());
        mOrderProductInfoList.addAll(list);
        if (mOrderProductListAdapter != null) {
            mOrderProductListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void saveCompanyPurchasing(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String toastString = "";
            if (TextUtils.isEmpty(mPurchasingOrderId)) {
                toastString = "采购单创建成功";
            } else {
                toastString = "采购单修改成功";
            }
            EventBus.getDefault().post(new AddOrSaveCompanyPurchasingOrderEvent(toastString));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }
}
