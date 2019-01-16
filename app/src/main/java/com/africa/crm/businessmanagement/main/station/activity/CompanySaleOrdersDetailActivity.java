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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanySalesOrderEvent;
import com.africa.crm.businessmanagement.main.bean.CompanySalesOrderInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.OrderProductInfo;
import com.africa.crm.businessmanagement.main.bean.ProductInfo;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.dao.CompanySalesOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.OrderProductListAdapter;
import com.africa.crm.businessmanagement.main.station.adapter.QuotationProductListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanySalesOrderDetailContract;
import com.africa.crm.businessmanagement.main.station.dialog.AddProductDialog;
import com.africa.crm.businessmanagement.main.station.dialog.AddQuotationProductDialog;
import com.africa.crm.businessmanagement.main.station.presenter.CompanySalesOrderDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.widget.DifferentDataUtil;
import com.africa.crm.businessmanagement.widget.EditTextUtil;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.MySpinner;
import com.africa.crm.businessmanagement.widget.StringUtil;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.africa.crm.businessmanagement.widget.dialog.AlertDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_CONTACTS;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_CUSTOMERS;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_PRODUCTS;
import static com.africa.crm.businessmanagement.network.api.DicUtil.SALE_ORDER_STATE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_CONTACT_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_CUSTOMER_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_PRODUCTS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_SALE_ORDER_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SALE_ORDER_STATE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_SALE_ORDER;

public class CompanySaleOrdersDetailActivity extends BaseMvpActivity<CompanySalesOrderDetailPresenter> implements CompanySalesOrderDetailContract.View {
    @BindView(R.id.et_sale_order_name)
    EditText et_sale_order_name;
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
    private List<DicInfo> mSalesOrderStateList = new ArrayList<>();
    private String mStateCode = "";

    private String mSalesOrderId;
    private Long mLocalId = 0l;//本地数据库ID
    private String mCompanyId = "";
    private String mCompanyName = "";
    private String mUserId = "";

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
    private boolean mShowCheckBox = false;

    @BindView(R.id.spinner_customer_name)
    MySpinner spinner_customer_name;
    private String mFromName = "";
    @BindView(R.id.spinner_contact_name)
    MySpinner spinner_contact_name;
    private String mContactName = "";

    private String mEditAble = "1";

    private GreendaoManager<CompanySalesOrderInfo, CompanySalesOrderInfoDao> mSalesOrderInfoDaoManager;
    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;

    private List<CompanySalesOrderInfo> mCompanySalesOrderLocalList = new ArrayList<>();//本地数据
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id, Long localId) {
        Intent intent = new Intent(activity, CompanySaleOrdersDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("localId", localId);
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
    protected void requestData() {
        mPresenter.getAllContact(mCompanyId);
        mPresenter.getAllCustomers(mCompanyId);
        mPresenter.getOrderState(SALE_ORDER_STATE);
        if (!TextUtils.isEmpty(mSalesOrderId) || mLocalId != 0l) {
            mPresenter.getCompanySalesOrderDetail(mSalesOrderId);
        }
    }

    @Override
    public void initView() {
        super.initView();
        mSalesOrderId = getIntent().getStringExtra("id");
        mLocalId = getIntent().getLongExtra("localId", 0l);
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mCompanyName = UserInfoManager.getUserLoginInfo(this).getCompanyName();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_name.setText("销售单详情");
        tv_delete.setOnClickListener(this);
        tv_delete_product.setOnClickListener(this);
        tv_add_product.setOnClickListener(this);
        tv_save.setOnClickListener(this);
        EditTextUtil.setPricePoint(et_money);

        String roleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (roleCode.equals("companySales")) {
            if (TextUtils.isEmpty(mSalesOrderId) && mLocalId == 0l) {
                titlebar_right.setVisibility(View.GONE);
                tv_save.setText(R.string.add);
                tv_save.setVisibility(View.VISIBLE);
            } else if (!TextUtils.isEmpty(mSalesOrderId) || mLocalId != 0l) {
                titlebar_right.setText(R.string.edit);
                tv_save.setText(R.string.save);
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
                    toastMsg("尚未选择产品");
                    return;
                }
                mOrderProductInfoList.add(new ProductInfo(orderProductInfo.getName(), orderProductInfo.getPrice(), orderProductInfo.getNum()));
                if (mOrderProductListAdapter != null) {
                    mOrderProductListAdapter.notifyDataSetChanged();
                }
                mAddProductDialog.dismiss();
            }
        });
        //得到Dao对象管理器
        mSalesOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanySalesOrderInfoDao());
        //得到本地数据
        mCompanySalesOrderLocalList = mSalesOrderInfoDaoManager.queryAll();
        //得到Dao对象管理器
        mDicInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoManager.queryAll();
        initProductList();
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
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
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
                for (ProductInfo productInfo : mOrderProductInfoList) {
                    if (productInfo.isChosen()) {
                        mDeleteList.add(productInfo);
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
          /*  case R.id.tv_add_product:
                final AddServiceRecordDialog addProductDialog = AddServiceRecordDialog.getInstance(this);
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
                addProductDialog.addOnSaveClickListener(new AddServiceRecordDialog.OnSaveClickListener() {
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
                break;*/
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
                mPresenter.saveCompanySalesOrder(mSalesOrderId, mCompanyId, mUserId, et_sale_order_name.getText().toString().trim(), spinner_customer_name.getText(), spinner_contact_name.getText(), et_money.getText().toString().trim(), mStateCode, et_deliver_address.getText().toString().trim(), et_deliver_zip_code.getText().toString().trim(), et_receiver_address.getText().toString().trim(), et_receiver_zip_code.getText().toString().trim(), new Gson().toJson(mOrderProductInfoList), et_clause.getText().toString().trim(), et_remark.getText().toString().trim());
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
        spinner_customer_name.getTextView().setEnabled(canInput);
        spinner_contact_name.getTextView().setEnabled(canInput);
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
    public void initData() {

    }

    @Override
    public void getAllContact(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_CONTACTS, dicInfo2.getName(), dicInfo2.getCode()));
        }
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_contact_name.setListDatas(this, list);
        spinner_contact_name.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mContactName = dicInfo.getText();
            }
        });
    }

    @Override
    public void getAllCustomers(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_CUSTOMERS, dicInfo2.getName(), dicInfo2.getCode()));
        }
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_customer_name.setListDatas(this, list);
        spinner_customer_name.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mFromName = dicInfo.getText();
            }
        });
    }

    @Override
    public void getAllProduct(List<DicInfo2> dicInfoList) {
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
    public void getOrderState(List<DicInfo> dicInfoList) {
        mSalesOrderStateList.clear();
        mSalesOrderStateList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mSalesOrderStateList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(SALE_ORDER_STATE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
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
        if (companySalesOrderInfo != null) {
            et_sale_order_name.setText(companySalesOrderInfo.getName());
            spinner_state.setText(companySalesOrderInfo.getStateName());
            mStateCode = companySalesOrderInfo.getState();
            spinner_customer_name.setText(companySalesOrderInfo.getCustomerName());
            spinner_contact_name.setText(companySalesOrderInfo.getContactName());
            et_money.setText(companySalesOrderInfo.getSaleCommission());
            et_deliver_address.setText(companySalesOrderInfo.getSendAddress());
            et_deliver_zip_code.setText(companySalesOrderInfo.getSendAddressZipCode());
            et_receiver_address.setText(companySalesOrderInfo.getDestinationAddress());
            et_receiver_zip_code.setText(companySalesOrderInfo.getDestinationAddressZipCode());
            et_clause.setText(companySalesOrderInfo.getClause());
            et_remark.setText(companySalesOrderInfo.getRemark());
            mFromName = companySalesOrderInfo.getUserNickName();
            mContactName = companySalesOrderInfo.getContactName();
            mEditAble = companySalesOrderInfo.getEditAble();
            if (mEditAble.equals("2")) {
                titlebar_right.setVisibility(View.GONE);
                tv_save.setVisibility(View.GONE);
                setEditTextInput(false);
            }
            List<ProductInfo> list = new Gson().fromJson(companySalesOrderInfo.getProducts(), new TypeToken<List<ProductInfo>>() {
            }.getType());
            mOrderProductInfoList.addAll(list);
            if (mOrderProductListAdapter != null) {
                mOrderProductListAdapter.notifyDataSetChanged();
            }
            for (CompanySalesOrderInfo localInfo : mCompanySalesOrderLocalList) {
                if (!TextUtils.isEmpty(localInfo.getId()) && !TextUtils.isEmpty(companySalesOrderInfo.getId())) {
                    if (localInfo.getId().equals(companySalesOrderInfo.getId())) {
                        companySalesOrderInfo.setLocalId(localInfo.getLocalId());
                        mSalesOrderInfoDaoManager.correct(companySalesOrderInfo);
                    }
                }
            }
        }
    }

    @Override
    public void saveCompanySalesOrder(UploadInfoBean uploadInfoBean, boolean isLocal) {
        String toastString = "";
        if (TextUtils.isEmpty(mSalesOrderId) && mLocalId == 0l) {
            toastString = "销售单创建成功";
        } else {
            toastString = "销售单修改成功";
        }
        if (isLocal) {
            CompanySalesOrderInfo companySalesOrderInfo = null;
            if (mLocalId == 0l) {
                companySalesOrderInfo = new CompanySalesOrderInfo(spinner_customer_name.getText(), TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), StringUtil.getText(et_deliver_address), StringUtil.getText(et_remark), mStateCode, mCompanyName, mFromName, mSalesOrderId, mEditAble, StringUtil.getText(et_receiver_address), mContactName, StringUtil.getText(et_deliver_zip_code), mUserId, StringUtil.getText(et_sale_order_name), spinner_state.getText(), StringUtil.getText(et_money), mCompanyId, new Gson().toJson(mOrderProductInfoList), StringUtil.getText(et_clause), StringUtil.getText(et_receiver_zip_code), false, isLocal);
                mSalesOrderInfoDaoManager.insertOrReplace(companySalesOrderInfo);
            } else {
                for (CompanySalesOrderInfo info : mCompanySalesOrderLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companySalesOrderInfo = new CompanySalesOrderInfo(info.getLocalId(), spinner_customer_name.getText(), TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), StringUtil.getText(et_deliver_address), StringUtil.getText(et_remark), mStateCode, mCompanyName, mFromName, mSalesOrderId, mEditAble, StringUtil.getText(et_receiver_address), mContactName, StringUtil.getText(et_deliver_zip_code), mUserId, StringUtil.getText(et_sale_order_name), spinner_state.getText(), StringUtil.getText(et_money), mCompanyId, new Gson().toJson(mOrderProductInfoList), StringUtil.getText(et_clause), StringUtil.getText(et_receiver_zip_code), false, isLocal);
                        mSalesOrderInfoDaoManager.correct(companySalesOrderInfo);
                    }
                }
            }
        }
        EventBus.getDefault().post(new AddOrSaveCompanySalesOrderEvent(toastString));
        finish();
    }

    @Override
    public void loadLocalData(String port) {
        super.loadLocalData(port);
        switch (port) {
            case REQUEST_ALL_CONTACT_LIST:
                List<DicInfo2> allContactList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_CONTACTS)) {
                        allContactList.add(new DicInfo2(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getAllContact(allContactList);
                break;
            case REQUEST_ALL_CUSTOMER_LIST:
                List<DicInfo2> allCustomers = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_CUSTOMERS)) {
                        allCustomers.add(new DicInfo2(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getAllCustomers(allCustomers);
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
            case REQUEST_SALE_ORDER_STATE:
                List<DicInfo> typeList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(SALE_ORDER_STATE)) {
                        typeList.add(dicInfo);
                    }
                }
                getOrderState(typeList);
                break;
            case REQUEST_COMPANY_SALE_ORDER_DETAIL:
                CompanySalesOrderInfo companySalesOrderInfo = null;
                for (CompanySalesOrderInfo info : mCompanySalesOrderLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companySalesOrderInfo = info;
                    }
                }
                getCompanySalesOrderDetail(companySalesOrderInfo);
                break;
            case REQUEST_SAVE_COMPANY_SALE_ORDER:
                UploadInfoBean uploadInfoBean = new UploadInfoBean();
                uploadInfoBean.setId(mSalesOrderId);
                uploadInfoBean.setCreateTime(TimeUtils.getCurrentTime(new Date()));
                uploadInfoBean.setUpdateTime(TimeUtils.getCurrentTime(new Date()));
                saveCompanySalesOrder(uploadInfoBean, true);
                break;
        }
    }
}
