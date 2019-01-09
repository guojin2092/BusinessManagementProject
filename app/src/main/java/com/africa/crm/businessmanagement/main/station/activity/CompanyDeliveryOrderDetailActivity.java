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

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyDeliveryOrderEvent;
import com.africa.crm.businessmanagement.main.bean.CompanyDeliveryOrderInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.OrderProductInfo;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.dao.CompanyDeliveryOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.OrderProductListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyDeliveryOrderDetailContract;
import com.africa.crm.businessmanagement.main.station.dialog.AddProductDialog;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyDeliveryOrderDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.widget.DifferentDataUtil;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.MySpinner;
import com.africa.crm.businessmanagement.widget.StringUtil;
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

import static com.africa.crm.businessmanagement.network.api.DicUtil.INVOICE_STATE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_PRODUCTS;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_SALE_ORDER;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_PRODUCTS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_SALES_ORDER;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_DELIVERY_ORDER_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_INVOICE_STATE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_DELIVERY_ORDER;

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
    private Long mLocalId = 0l;//本地数据库ID
    private String mCompanyId = "";
    private String mCompanyName = "";
    private String mFromName = "";
    private String mUserId = "";
    private String mDeliveryCode = "";
    private String mEditAble = "1";

    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.tv_delete_product)
    TextView tv_delete_product;
    @BindView(R.id.tv_add_product)
    TextView tv_add_product;
    @BindView(R.id.rv_product)
    RecyclerView rv_product;
    private AddProductDialog mAddProductDialog;
    private OrderProductListAdapter mOrderProductListAdapter;
    private List<OrderProductInfo> mDeleteList = new ArrayList<>();
    private List<OrderProductInfo> mOrderProductInfoList = new ArrayList<>();
    private boolean mShowCheckBox = false;


    private TimePickerView pvTime;

    @BindView(R.id.spinner_sales_id)
    MySpinner spinner_sales_id;
    private String mSalesId = "";

    @BindView(R.id.spinner_state)
    MySpinner spinner_state;
    private List<DicInfo> mSpinnerStateList = new ArrayList<>();
    private String mStateCode = "";
    private String mStateName = "";

    private GreendaoManager<CompanyDeliveryOrderInfo, CompanyDeliveryOrderInfoDao> mDeliveryOrderInfoDaoManager;
    private List<CompanyDeliveryOrderInfo> mDeliveryOrderLocalList = new ArrayList<>();//本地数据

    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String deliveryOrderId, Long localId) {
        Intent intent = new Intent(activity, CompanyDeliveryOrderDetailActivity.class);
        intent.putExtra("id", deliveryOrderId);
        intent.putExtra("localId", localId);
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
        mLocalId = getIntent().getLongExtra("localId", 0l);
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mCompanyName = UserInfoManager.getUserLoginInfo(this).getCompanyName();
        mFromName = UserInfoManager.getUserLoginInfo(this).getName();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_name.setText("发货单详情");

        tv_delete.setOnClickListener(this);
        tv_delete_product.setOnClickListener(this);
        tv_add_product.setOnClickListener(this);
        tv_arrive_date.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        String roleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (roleCode.equals("companySales")) {
            if (TextUtils.isEmpty(mDeliveryOrderId) && mLocalId == 0l) {
                titlebar_right.setVisibility(View.GONE);
                tv_save.setText(R.string.add);
                tv_save.setVisibility(View.VISIBLE);
            } else if (!TextUtils.isEmpty(mDeliveryOrderId) || mLocalId != 0l) {
                titlebar_right.setText(R.string.edit);
                tv_save.setText(R.string.save);
                setEditTextInput(false);
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
        //得到Dao对象管理器
        mDeliveryOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyDeliveryOrderInfoDao());
        //得到本地数据
        mDeliveryOrderLocalList = mDeliveryOrderInfoDaoManager.queryAll();
        //得到Dao对象管理器
        mDicInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoManager.queryAll();
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
        mPresenter.getState(INVOICE_STATE);
        mPresenter.getAllSaleOrders(mCompanyId, mUserId);
        if (!TextUtils.isEmpty(mDeliveryOrderId) || mLocalId != 0l) {
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
        tv_add_product.setEnabled(canInput);
    }


    @Override
    public void getState(List<DicInfo> dicInfoList) {
        mSpinnerStateList.clear();
        mSpinnerStateList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSpinnerStateList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(INVOICE_STATE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_state.setListDatas(this, mSpinnerStateList);

        spinner_state.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mStateCode = dicInfo.getCode();
                mStateName = dicInfo.getText();
            }
        });
    }

    @Override
    public void getAllProduct(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_PRODUCTS, dicInfo2.getName(), dicInfo2.getId()));
        }
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        mAddProductDialog.show();
        mAddProductDialog.setListDatas(this, list);
    }

    @Override
    public void getAllSaleOrders(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_SALE_ORDER, dicInfo2.getName(), dicInfo2.getId()));
        }
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            mDicInfoDaoManager.insertOrReplace(dicInfo);
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
        if (companyDeliveryOrderInfo != null) {
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
            mDeliveryCode = companyDeliveryOrderInfo.getCode();
            mCompanyName = companyDeliveryOrderInfo.getCompanyName();
            mFromName = companyDeliveryOrderInfo.getUserNickName();
            mEditAble = companyDeliveryOrderInfo.getEditAble();
            List<OrderProductInfo> list = new Gson().fromJson(companyDeliveryOrderInfo.getProducts(), new TypeToken<List<OrderProductInfo>>() {
            }.getType());
            mOrderProductInfoList.addAll(list);
            if (mOrderProductListAdapter != null) {
                mOrderProductListAdapter.notifyDataSetChanged();
            }
            et_clause.setText(companyDeliveryOrderInfo.getClause());
            for (CompanyDeliveryOrderInfo localInfo : mDeliveryOrderLocalList) {
                if (!TextUtils.isEmpty(localInfo.getId()) && !TextUtils.isEmpty(companyDeliveryOrderInfo.getId())) {
                    if (localInfo.getId().equals(companyDeliveryOrderInfo.getId())) {
                        companyDeliveryOrderInfo.setLocalId(localInfo.getLocalId());
                        mDeliveryOrderInfoDaoManager.correct(companyDeliveryOrderInfo);
                    }
                }
            }
        }
    }

    @Override
    public void saveCompanyDeliveryOrder(UploadInfoBean uploadInfoBean, boolean isLocal) {
        String toastString = "";
        if (TextUtils.isEmpty(mDeliveryOrderId) && mLocalId == 0l) {
            toastString = "发货单创建成功";
        } else {
            toastString = "发货单修改成功";
        }
        if (isLocal) {
            CompanyDeliveryOrderInfo companyDeliveryOrderInfo = null;
            if (mLocalId == 0l) {
                companyDeliveryOrderInfo = new CompanyDeliveryOrderInfo(TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), StringUtil.getText(et_deliver_address), StringUtil.getText(et_remark), mSalesId, mStateCode, mDeliveryCode, mCompanyName, mFromName, mDeliveryOrderId, StringUtil.getText(et_logistics_code), StringUtil.getText(tv_arrive_date), mEditAble, StringUtil.getText(et_receiver_address), StringUtil.getText(et_deliver_zip_code), mStateName, mUserId, StringUtil.getText(et_delivery_order_name), spinner_sales_id.getText(), mCompanyId, new Gson().toJson(mOrderProductInfoList), StringUtil.getText(et_clause), StringUtil.getText(et_receiver_zip_code), false, isLocal);
                mDeliveryOrderInfoDaoManager.insertOrReplace(companyDeliveryOrderInfo);
            } else {
                for (CompanyDeliveryOrderInfo info : mDeliveryOrderLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyDeliveryOrderInfo = new CompanyDeliveryOrderInfo(info.getLocalId(), TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), StringUtil.getText(et_deliver_address), StringUtil.getText(et_remark), mSalesId, mStateCode, mDeliveryCode, mCompanyName, mFromName, mDeliveryOrderId, StringUtil.getText(et_logistics_code), StringUtil.getText(tv_arrive_date), mEditAble, StringUtil.getText(et_receiver_address), StringUtil.getText(et_deliver_zip_code), mStateName, mUserId, StringUtil.getText(et_delivery_order_name), spinner_sales_id.getText(), mCompanyId, new Gson().toJson(mOrderProductInfoList), StringUtil.getText(et_clause), StringUtil.getText(et_receiver_zip_code), false, isLocal);
                        mDeliveryOrderInfoDaoManager.correct(companyDeliveryOrderInfo);
                    }
                }
            }
        }
        EventBus.getDefault().post(new AddOrSaveCompanyDeliveryOrderEvent(toastString));
        finish();
    }

    @Override
    public void loadLocalData(String port) {
        super.loadLocalData(port);
        switch (port) {
            case REQUEST_INVOICE_STATE:
                List<DicInfo> typeList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(INVOICE_STATE)) {
                        typeList.add(dicInfo);
                    }
                }
                getState(typeList);
                break;
            case REQUEST_ALL_PRODUCTS_LIST:
                List<DicInfo2> allProducts = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_PRODUCTS)) {
                        allProducts.add(new DicInfo2(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getAllProduct(allProducts);
                break;
            case REQUEST_ALL_SALES_ORDER:
                List<DicInfo2> allSaleOrder = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_SALE_ORDER)) {
                        allSaleOrder.add(new DicInfo2(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getAllSaleOrders(allSaleOrder);
                break;
            case REQUEST_COMPANY_DELIVERY_ORDER_DETAIL:
                CompanyDeliveryOrderInfo companyDeliveryOrderInfo = null;
                for (CompanyDeliveryOrderInfo info : mDeliveryOrderLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyDeliveryOrderInfo = info;
                    }
                }
                getCompanyDeliveryOrderDetail(companyDeliveryOrderInfo);
                break;
            case REQUEST_SAVE_COMPANY_DELIVERY_ORDER:
                UploadInfoBean uploadInfoBean = new UploadInfoBean();
                uploadInfoBean.setId(mDeliveryOrderId);
                uploadInfoBean.setCreateTime(TimeUtils.getCurrentTime(new Date()));
                uploadInfoBean.setUpdateTime(TimeUtils.getCurrentTime(new Date()));
                saveCompanyDeliveryOrder(uploadInfoBean, true);
                break;
        }
    }
}
