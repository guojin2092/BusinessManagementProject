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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyPurchasingOrderEvent;
import com.africa.crm.businessmanagement.main.bean.CompanyPurchasingOrderInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.OrderProductInfo;
import com.africa.crm.businessmanagement.main.bean.ProductInfo;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.dao.CompanyPurchasingOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.OrderProductListAdapter;
import com.africa.crm.businessmanagement.main.station.adapter.QuotationProductListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPurchasingDetailContract;
import com.africa.crm.businessmanagement.main.station.dialog.AddProductDialog;
import com.africa.crm.businessmanagement.main.station.dialog.AddQuotationProductDialog;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyPurchasingDetailPresenter;
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

import static com.africa.crm.businessmanagement.network.api.DicUtil.PURCHASE_STATE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_PRODUCTS;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_SUPPLIERS;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_PRODUCTS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_SUPPLIER_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_PURCHASING_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_PURCHASING_STATE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_PURCHASING;

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
    private List<DicInfo> mOrderStateList = new ArrayList<>();
    private String mStateCode = "";
    private String mStateName = "";

    @BindView(R.id.tv_delete)
    TextView tv_delete;
    @BindView(R.id.tv_delete_product)
    TextView tv_delete_product;
    @BindView(R.id.tv_add_product)
    TextView tv_add_product;
    @BindView(R.id.rv_product)
    RecyclerView rv_product;
    private QuotationProductListAdapter mOrderProductListAdapter;
    private AddQuotationProductDialog mAddProductDialog;
    private List<ProductInfo> mDeleteList = new ArrayList<>();
    private List<ProductInfo> mOrderProductInfoList = new ArrayList<>();
    private List<DicInfo2> mProductTypeList = new ArrayList<>();

    private TimePickerView pvOrderTime, pvArriveTime;
    private Date mOrderDate, mArriveDate;

    private boolean mShowCheckBox = false;
    private String mPurchasingOrderId = "";
    private Long mLocalId = 0l;//本地数据库ID
    private String mCompanyId = "";
    private String mCompanyName = "";
    private String mFromName = "";
    private String mUserId = "";
    private String mSupplierName = "";
    private String mPurchasingCode = "";
    private String mEditAble = "1";

    private GreendaoManager<CompanyPurchasingOrderInfo, CompanyPurchasingOrderInfoDao> mPurchasingOrderInfoDaoManager;
    private List<CompanyPurchasingOrderInfo> mPurchasingOrderLocalList = new ArrayList<>();//本地数据

    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id, Long localId) {
        Intent intent = new Intent(activity, CompanyPurchasingDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("localId", localId);
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
        mLocalId = getIntent().getLongExtra("localId", 0l);
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mCompanyName = UserInfoManager.getUserLoginInfo(this).getCompanyName();
        mFromName = UserInfoManager.getUserLoginInfo(this).getName();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_name.setText(R.string.Purchase_order_details);
        tv_delete.setOnClickListener(this);
        tv_delete_product.setOnClickListener(this);
        tv_add_product.setOnClickListener(this);
        tv_order_date.setOnClickListener(this);
        tv_arrive_date.setOnClickListener(this);
        tv_save.setOnClickListener(this);

        String roleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (roleCode.equals("companySales")) {
            if (TextUtils.isEmpty(mPurchasingOrderId) && mLocalId == 0l) {
                titlebar_right.setVisibility(View.GONE);
                tv_save.setText(R.string.Add);
                tv_save.setVisibility(View.VISIBLE);
            } else if (!TextUtils.isEmpty(mPurchasingOrderId) || mLocalId != 0l) {
                titlebar_right.setText(R.string.Edit);
                tv_save.setText(R.string.Save);
                setEditTextInput(false);
            }
        } else {
            titlebar_right.setVisibility(View.GONE);
            tv_save.setVisibility(View.GONE);
            setEditTextInput(false);
        }
        mAddProductDialog = AddQuotationProductDialog.getInstance(this);
        mAddProductDialog.isCancelableOnTouchOutside(false)
                .withDuration(300)
                .withEffect(Effectstype.Fadein)
                .setCancelClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAddProductDialog.dismiss();
                    }
                });
        mAddProductDialog.addOnSaveClickListener(new AddQuotationProductDialog.OnSaveClickListener() {
            @Override
            public void onSaveClick(ProductInfo orderProductInfo) {
                if (TextUtils.isEmpty(orderProductInfo.getName())) {
                    toastMsg(getString(R.string.Please_select_a_product));
                    return;
                }
                mOrderProductInfoList.add(new ProductInfo(orderProductInfo.getName(), orderProductInfo.getPrice(), orderProductInfo.getNum()));
                if (mOrderProductListAdapter != null) {
                    mOrderProductListAdapter.notifyDataSetChanged();
                }
                mAddProductDialog.dismiss();
            }
        });
        initTimePicker();
        initProductList();
        //得到Dao对象管理器
        mPurchasingOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyPurchasingOrderInfoDao());
        //得到本地数据
        mPurchasingOrderLocalList = mPurchasingOrderInfoDaoManager.queryAll();
        //得到Dao对象管理器
        mDicInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoManager.queryAll();
    }

    private void initTimePicker() {
        pvOrderTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mOrderDate = date;
                if (mArriveDate != null) {
                    if (mArriveDate.getTime() < mOrderDate.getTime()) {
                        toastMsg(getString(R.string.The_arrival_date_cannot_be_earlier_than_the_order_date));
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
                        toastMsg(getString(R.string.The_arrival_date_cannot_be_earlier_than_the_order_date));
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
        mOrderProductListAdapter = new QuotationProductListAdapter(mOrderProductInfoList);
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
        mPresenter.getStateType(PURCHASE_STATE);
        mPresenter.getAllSuppliers(mCompanyId);
        if (!TextUtils.isEmpty(mPurchasingOrderId) || mLocalId != 0l) {
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
                if (tv_delete_product.getText().toString().equals(getString(R.string.Delete))) {
                    tv_delete_product.setText(R.string.cancel);
                    tv_delete.setVisibility(View.VISIBLE);
                    mShowCheckBox = true;
                } else {
                    tv_delete_product.setText(R.string.Delete);
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
                for (ProductInfo productInfo : mOrderProductInfoList) {
                    if (productInfo.isChosen()) {
                        mDeleteList.add(productInfo);
                    }
                }
                if (ListUtils.isEmpty(mDeleteList)) {
                    toastMsg(getString(R.string.no_choose_delete));
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
                                toastMsg(getString(R.string.Delete_Success));
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
                break;
            case R.id.titlebar_right:
                if (titlebar_right.getText().toString().equals(getString(R.string.Edit))) {
                    titlebar_right.setText(R.string.cancel);
                    tv_save.setVisibility(View.VISIBLE);
                    setEditTextInput(true);
                } else {
                    titlebar_right.setText(R.string.Edit);
                    tv_save.setVisibility(View.GONE);
                    setEditTextInput(false);
                }
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(et_purchasing_order_name.getText().toString().trim())) {
                    toastMsg(getString(R.string.Please_fill_in_the_name));
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
            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_SUPPLIERS, dicInfo2.getName(), dicInfo2.getCode()));
        }
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_supplier_name.setListDatas(this, list);
        spinner_supplier_name.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mSupplierName = dicInfo.getText();
            }
        });
    }

    @Override
    public void getStateType(List<DicInfo> dicInfoList) {
        mOrderStateList.clear();
        mOrderStateList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mOrderStateList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(PURCHASE_STATE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_state.setListDatas(this, mOrderStateList);

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
        mProductTypeList.addAll(dicInfoList);
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_PRODUCTS, dicInfo2.getName(), dicInfo2.getCode()));
        }
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        mAddProductDialog.show();
        mAddProductDialog.setListDatas(this, list);
    }

    @Override
    public void getCompanyPurchasingDetail(CompanyPurchasingOrderInfo companyPurchasingOrderInfo) {
        if (companyPurchasingOrderInfo != null) {
            et_purchasing_order_name.setText(companyPurchasingOrderInfo.getName());
            spinner_supplier_name.setText(companyPurchasingOrderInfo.getSupplierName());
            spinner_state.setText(companyPurchasingOrderInfo.getStateName());
            mStateName = companyPurchasingOrderInfo.getStateName();
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
            mPurchasingCode = companyPurchasingOrderInfo.getCode();
            mEditAble = companyPurchasingOrderInfo.getEditAble();
            if (mEditAble.equals("2")) {
                titlebar_right.setVisibility(View.GONE);
                tv_save.setVisibility(View.GONE);
                setEditTextInput(false);
            }
            List<ProductInfo> list = new Gson().fromJson(companyPurchasingOrderInfo.getProducts(), new TypeToken<List<ProductInfo>>() {
            }.getType());
            mOrderProductInfoList.addAll(list);
            if (mOrderProductListAdapter != null) {
                mOrderProductListAdapter.notifyDataSetChanged();
            }
            for (CompanyPurchasingOrderInfo localInfo : mPurchasingOrderLocalList) {
                if (!TextUtils.isEmpty(localInfo.getId()) && !TextUtils.isEmpty(companyPurchasingOrderInfo.getId())) {
                    if (localInfo.getId().equals(companyPurchasingOrderInfo.getId())) {
                        companyPurchasingOrderInfo.setLocalId(localInfo.getLocalId());
                        mPurchasingOrderInfoDaoManager.correct(companyPurchasingOrderInfo);
                    }
                }
            }
        }

    }

    @Override
    public void saveCompanyPurchasing(UploadInfoBean uploadInfoBean, boolean isLocal) {
        String toastString = "";
        if (TextUtils.isEmpty(mPurchasingOrderId)) {
            toastString = getString(R.string.Added_Successfully);
        } else {
            toastString = getString(R.string.Successfully_Modified);
        }
        if (isLocal) {
            CompanyPurchasingOrderInfo companyPayOrderInfo = null;
            if (mLocalId == 0l) {
                companyPayOrderInfo = new CompanyPurchasingOrderInfo(TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), mSupplierName, StringUtil.getText(et_deliver_address), StringUtil.getText(et_remark), StringUtil.getText(tv_order_date), mStateCode, mPurchasingCode, mCompanyName, mFromName, mPurchasingOrderId, StringUtil.getText(tv_arrive_date), mEditAble, StringUtil.getText(et_receiver_address), StringUtil.getText(et_deliver_zip_code), mUserId, StringUtil.getText(et_purchasing_order_name), mStateName, mCompanyId, new Gson().toJson(mOrderProductInfoList), StringUtil.getText(et_clause), StringUtil.getText(et_receiver_zip_code), false, isLocal);
                mPurchasingOrderInfoDaoManager.insertOrReplace(companyPayOrderInfo);
            } else {
                for (CompanyPurchasingOrderInfo info : mPurchasingOrderLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyPayOrderInfo = new CompanyPurchasingOrderInfo(info.getLocalId(), TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), mSupplierName, StringUtil.getText(et_deliver_address), StringUtil.getText(et_remark), StringUtil.getText(tv_order_date), mStateCode, mPurchasingCode, mCompanyName, mFromName, mPurchasingOrderId, StringUtil.getText(tv_arrive_date), mEditAble, StringUtil.getText(et_receiver_address), StringUtil.getText(et_deliver_zip_code), mUserId, StringUtil.getText(et_purchasing_order_name), mStateName, mCompanyId, new Gson().toJson(mOrderProductInfoList), StringUtil.getText(et_clause), StringUtil.getText(et_receiver_zip_code), false, isLocal);
                        mPurchasingOrderInfoDaoManager.correct(companyPayOrderInfo);
                    }
                }
            }
        }
        EventBus.getDefault().post(new AddOrSaveCompanyPurchasingOrderEvent(toastString));
        finish();
    }

    @Override
    public void loadLocalData(String port) {
        super.loadLocalData(port);
        switch (port) {
            case REQUEST_ALL_SUPPLIER_LIST:
                List<DicInfo2> allSuppliers = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_SUPPLIERS)) {
                        allSuppliers.add(new DicInfo2(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getAllSuppliers(allSuppliers);
                break;
            case REQUEST_PURCHASING_STATE:
                List<DicInfo> typeList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(PURCHASE_STATE)) {
                        typeList.add(dicInfo);
                    }
                }
                getStateType(typeList);
                break;
            case REQUEST_ALL_PRODUCTS_LIST:
                List<DicInfo2> allProduts = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_PRODUCTS)) {
                        allProduts.add(new DicInfo2(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getAllProduct(allProduts);
                break;
            case REQUEST_COMPANY_PURCHASING_DETAIL:
                CompanyPurchasingOrderInfo companyPurchasingOrderInfo = null;
                for (CompanyPurchasingOrderInfo info : mPurchasingOrderLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyPurchasingOrderInfo = info;
                    }
                }
                getCompanyPurchasingDetail(companyPurchasingOrderInfo);
                break;
            case REQUEST_SAVE_COMPANY_PURCHASING:
                UploadInfoBean uploadInfoBean = new UploadInfoBean();
                uploadInfoBean.setId(mPurchasingOrderId);
                uploadInfoBean.setCreateTime(TimeUtils.getCurrentTime(new Date()));
                uploadInfoBean.setUpdateTime(TimeUtils.getCurrentTime(new Date()));
                saveCompanyPurchasing(uploadInfoBean, true);
                break;
        }
    }
}
