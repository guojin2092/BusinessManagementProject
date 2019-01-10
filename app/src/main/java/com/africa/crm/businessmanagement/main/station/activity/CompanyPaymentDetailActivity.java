package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyPayOrderEvent;
import com.africa.crm.businessmanagement.main.bean.CompanyPayOrderInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.FileInfoBean;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.dao.CompanyPayOrderInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.glide.GlideUtil;
import com.africa.crm.businessmanagement.main.photo.SinglePopup;
import com.africa.crm.businessmanagement.main.photo.camera.CameraCore;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPayOrderDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyPayOrderDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.widget.DifferentDataUtil;
import com.africa.crm.businessmanagement.widget.EditTextUtil;
import com.africa.crm.businessmanagement.widget.MySpinner;
import com.africa.crm.businessmanagement.widget.StringUtil;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.bigkoo.pickerview.TimePickerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_CUSTOMERS;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_SALE_ORDER;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_TRADING_ORDER;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_CUSTOMER_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_SALES_ORDER;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_TRADING_ORDER;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_PAY_ORDER_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_PAY_ORDER;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_UPLOAD_IMAGE;

public class CompanyPaymentDetailActivity extends BaseMvpActivity<CompanyPayOrderDetailPresenter> implements CompanyPayOrderDetailContract.View, SinglePopup.OnPopItemClickListener, CameraCore.CameraResult {
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
    private String mSalesOrderName = "";
    @BindView(R.id.spinner_trading_order)
    MySpinner spinner_trading_order;
    private String mTragdingOrderId = "";
    private String mTradingOrderName = "";
    @BindView(R.id.fl_sf_invoice)
    FrameLayout fl_sf_invoice;
    @BindView(R.id.cb_sf_invoice)
    CheckBox cb_sf_invoice;
    private String mInvoiceCode = "";//1是2否
    private String mInvoiceName = "";
    @BindView(R.id.fl_sf_print)
    FrameLayout fl_sf_print;
    @BindView(R.id.cb_sf_print)
    CheckBox cb_sf_print;
    private String mPrintCode = "";
    private String mPrintName = "";
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.tv_save)
    TextView tv_save;

    private String mPayOrderId = "";
    private Long mLocalId = 0l;//本地数据库ID
    private String mCompanyId = "";
    private String mUserId = "";
    private String mFromName = "";
    private String mPayCode = "";
    private String mCompanyName = "";
    private String mCustomerName = "";

    private TimePickerView pvTime;

    @BindView(R.id.iv_fp_1)
    ImageView iv_fp_1;
    @BindView(R.id.iv_fp_2)
    ImageView iv_fp_2;
    private CameraCore cameraCore;
    private SinglePopup singlePopup1, singlePopup2;
    private int type = 0;
    private String mHeadCodeTotal = "";//头像ID
    private String mHeadCode1, mHeadCode2;
    private String mLocalPath1, mLocalPath2;
    private String mEditAble = "1";

    private GreendaoManager<CompanyPayOrderInfo, CompanyPayOrderInfoDao> mPayOrderInfoDaoManager;
    private List<CompanyPayOrderInfo> mPayOrderLocalList = new ArrayList<>();//本地数据

    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String payOrderId, Long localId) {
        Intent intent = new Intent(activity, CompanyPaymentDetailActivity.class);
        intent.putExtra("id", payOrderId);
        intent.putExtra("localId", localId);
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
        mLocalId = getIntent().getLongExtra("localId", 0l);
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mCompanyName = UserInfoManager.getUserLoginInfo(this).getCompanyName();
        mFromName = UserInfoManager.getUserLoginInfo(this).getName();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_name.setText("付款单详情");
        tv_save.setOnClickListener(this);
        tv_pay_time.setOnClickListener(this);
        fl_sf_invoice.setOnClickListener(this);
        fl_sf_print.setOnClickListener(this);
        iv_fp_1.setOnClickListener(this);
        iv_fp_2.setOnClickListener(this);
        EditTextUtil.setPricePoint(et_pay_price);

        String roleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (roleCode.equals("companySales")) {
            if (TextUtils.isEmpty(mPayOrderId) && mLocalId == 0l) {
                titlebar_right.setVisibility(View.GONE);
                tv_save.setText(R.string.add);
                tv_save.setVisibility(View.VISIBLE);
            } else if (!TextUtils.isEmpty(mPayOrderId) || mLocalId != 0l) {
                titlebar_right.setText(R.string.edit);
                tv_save.setText(R.string.save);
                setEditTextInput(false);
            }
        } else {
            titlebar_right.setVisibility(View.GONE);
            tv_save.setVisibility(View.GONE);
            setEditTextInput(false);
        }
        initTimePicker();
        cameraCore = new CameraCore.Builder(this)
                .setNeedCrop(true)
                .setZipInfo(new CameraCore.ZipInfo(true, 200, 200, 100 * 1024))
                .build();
        //得到Dao对象管理器
        mPayOrderInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyPayOrderInfoDao());
        //得到本地数据
        mPayOrderLocalList = mPayOrderInfoDaoManager.queryAll();
        //得到Dao对象管理器
        mDicInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoManager.queryAll();
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
            case R.id.iv_fp_1:
                type = 1;
                if (singlePopup1 == null) {
                    List<String> list = new ArrayList<>();
                    list.add("拍照");
                    list.add("从相册选择");
                    singlePopup1 = new SinglePopup(this, list, this);
                    singlePopup1.setTitle(View.GONE, "选择来源");
                }
                singlePopup1.showAtLocation(iv_fp_1, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.iv_fp_2:
                type = 2;
                if (singlePopup2 == null) {
                    List<String> list = new ArrayList<>();
                    list.add("拍照");
                    list.add("从相册选择");
                    singlePopup2 = new SinglePopup(this, list, this);
                    singlePopup2.setTitle(View.GONE, "选择来源");
                }
                singlePopup2.showAtLocation(iv_fp_2, Gravity.BOTTOM, 0, 0);
                break;
            case R.id.tv_pay_time:
                pvTime.show();
                break;
            case R.id.fl_sf_invoice:
                if (cb_sf_invoice.isChecked()) {
                    cb_sf_invoice.setChecked(false);
                    mInvoiceCode = "2";
                    mInvoiceName = "否";
                } else {
                    cb_sf_invoice.setChecked(true);
                    mInvoiceCode = "1";
                    mInvoiceName = "是";
                }
                break;
            case R.id.fl_sf_print:
                if (cb_sf_print.isChecked()) {
                    cb_sf_print.setChecked(false);
                    mPrintCode = "2";
                    mPrintName = "否";
                } else {
                    cb_sf_print.setChecked(true);
                    mPrintCode = "1";
                    mPrintName = "是";
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
                if (TextUtils.isEmpty(mHeadCode1)) {
                    toastMsg("尚未上传第一张发票照片");
                    return;
                }
                if (TextUtils.isEmpty(mHeadCode2)) {
                    toastMsg("尚未上传第二张发票照片");
                    return;
                }
                mHeadCodeTotal = mHeadCode1 + "," + mHeadCode2;
                mPresenter.saveCompanyPayOrder(mPayOrderId, mCompanyId, mUserId, et_pay_name.getText().toString().trim(), mSalesId, mTragdingOrderId, spinner_customer_name.getText(), et_pay_price.getText().toString().trim(), tv_pay_time.getText().toString().trim(), "1", "1", mHeadCodeTotal, et_remark.getText().toString().trim());
                break;
        }
    }

    @Override
    protected void requestData() {
        mPresenter.getAllSaleOrders(mCompanyId, mUserId);
        mPresenter.getAllTradingOrders(mCompanyId, mUserId);
        mPresenter.getAllCustomers(mCompanyId);
        if (!TextUtils.isEmpty(mPayOrderId) || mLocalId != 0l) {
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
        iv_fp_1.setEnabled(canInput);
        iv_fp_2.setEnabled(canInput);
    }

    @Override
    public void getAllSaleOrders(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_SALE_ORDER, dicInfo2.getName(), dicInfo2.getCode()));
        }
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_sales.setListDatas(this, list);
        spinner_sales.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mSalesId = dicInfo.getCode();
                mSalesOrderName = dicInfo.getText();
            }
        });
    }

    @Override
    public void getAllTradingOrders(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getId(), QUERY_ALL_TRADING_ORDER, dicInfo2.getName(), dicInfo2.getCode()));
        }
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(list, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_trading_order.setListDatas(this, list);
        spinner_trading_order.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mTragdingOrderId = dicInfo.getCode();
                mTradingOrderName = dicInfo.getText();
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
                mCustomerName = dicInfo.getText();
            }
        });

    }

    @Override
    public void getCompanyPayOrderDetail(CompanyPayOrderInfo companyPayOrderInfo) {
        if (companyPayOrderInfo != null) {
            et_pay_name.setText(companyPayOrderInfo.getName());
            spinner_customer_name.setText(companyPayOrderInfo.getCustomerName());
            mCustomerName = companyPayOrderInfo.getCustomerName();
            et_pay_price.setText(companyPayOrderInfo.getPrice());
            tv_pay_time.setText(companyPayOrderInfo.getPayTime());
            spinner_sales.setText(companyPayOrderInfo.getSalesOrderName());
            mSalesId = companyPayOrderInfo.getSalesOrderId();
            spinner_trading_order.setText(companyPayOrderInfo.getTradingOrderName());
            mTragdingOrderId = companyPayOrderInfo.getTradingOrderId();
            mInvoiceCode = companyPayOrderInfo.getHasInvoice();
            mEditAble = companyPayOrderInfo.getEditAble();
            mFromName = companyPayOrderInfo.getUserNickName();
            mPayCode = companyPayOrderInfo.getCode();
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
            String fpCodes = companyPayOrderInfo.getInvoiceFiles();
            if (!TextUtils.isEmpty(fpCodes)) {
                String[] codeList = fpCodes.split(",");
                if (codeList.length == 1) {
                    mHeadCode1 = codeList[0];
                    GlideUtil.showNormalImg(iv_fp_1, mHeadCode1);
                } else if (codeList.length == 2) {
                    mHeadCode1 = codeList[0];
                    mHeadCode2 = codeList[1];
                    GlideUtil.showNormalImg(iv_fp_1, mHeadCode1);
                    GlideUtil.showNormalImg(iv_fp_2, mHeadCode2);
                }
            }
            for (CompanyPayOrderInfo localInfo : mPayOrderLocalList) {
                if (!TextUtils.isEmpty(localInfo.getId()) && !TextUtils.isEmpty(companyPayOrderInfo.getId())) {
                    if (localInfo.getId().equals(companyPayOrderInfo.getId())) {
                        companyPayOrderInfo.setLocalId(localInfo.getLocalId());
                        mPayOrderInfoDaoManager.correct(companyPayOrderInfo);
                    }
                }
            }
        }
    }

    @Override
    public void saveCompanyPayOrder(UploadInfoBean uploadInfoBean, boolean isLocal) {
        String toastString = "";
        if (TextUtils.isEmpty(mPayOrderId) && mLocalId == 0l) {
            toastString = "付款单创建成功";
        } else {
            toastString = "付款单修改成功";
        }
        if (isLocal) {
            CompanyPayOrderInfo companyPayOrderInfo = null;
            if (mLocalId == 0l) {
                companyPayOrderInfo = new CompanyPayOrderInfo(mPrintCode, mCustomerName, TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), mInvoiceCode, StringUtil.getText(et_remark), mInvoiceName, mPrintName, mSalesId, mPayCode, mCompanyName, StringUtil.getText(tv_pay_time), mFromName, mPayOrderId, StringUtil.getText(et_pay_price), mEditAble, mUserId, StringUtil.getText(et_pay_name), mSalesOrderName, mHeadCodeTotal, mCompanyId, mTragdingOrderId, mTradingOrderName, false, isLocal);
                mPayOrderInfoDaoManager.insertOrReplace(companyPayOrderInfo);
            } else {
                for (CompanyPayOrderInfo info : mPayOrderLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyPayOrderInfo = new CompanyPayOrderInfo(info.getLocalId(), mPrintCode, mCustomerName, TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), mInvoiceCode, StringUtil.getText(et_remark), mInvoiceName, mPrintName, mSalesId, mPayCode, mCompanyName, StringUtil.getText(tv_pay_time), mFromName, mPayOrderId, StringUtil.getText(et_pay_price), mEditAble, mUserId, StringUtil.getText(et_pay_name), mSalesOrderName, mHeadCodeTotal, mCompanyId, mTragdingOrderId, mTradingOrderName, false, isLocal);
                        mPayOrderInfoDaoManager.correct(companyPayOrderInfo);
                    }
                }
            }
        }
        EventBus.getDefault().post(new AddOrSaveCompanyPayOrderEvent(toastString));
        finish();
    }

    @Override
    public void itemClick(int position, String s) {
        switch (position) {
            case 0:
                cameraCore.openCamera();
                break;
            case 1:
                cameraCore.openAlbum();
                break;
        }
    }

    @Override
    public void success(String path) {
        if (type == 1) {
            if (!TextUtils.isEmpty(path)) {
                mLocalPath1 = path;
                mPresenter.uploadImages(mLocalPath1);
            }
        } else if (type == 2) {
            mLocalPath2 = path;
            mPresenter.uploadImages(mLocalPath2);
        }

    }

    @Override
    public void uploadImages(FileInfoBean fileInfoBean) {
        if (type == 1) {
            mHeadCode1 = fileInfoBean.getCode();
            GlideUtil.showNormalImg(iv_fp_1, mHeadCode1);
        } else if (type == 2) {
            mHeadCode2 = fileInfoBean.getCode();
            GlideUtil.showNormalImg(iv_fp_2, mHeadCode2);
        }
    }

    @Override
    public void fail(int code, String message) {
        toastMsg(message);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cameraCore.onResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        cameraCore.onPermission(requestCode, permissions, grantResults);
    }

    @Override
    public void loadLocalData(String port) {
        super.loadLocalData(port);
        switch (port) {
            case REQUEST_UPLOAD_IMAGE:
                if (type == 1) {
                    FileInfoBean localInfoBean = new FileInfoBean(mLocalPath1);
                    uploadImages(localInfoBean);
                } else if (type == 2) {
                    FileInfoBean localInfoBean = new FileInfoBean(mLocalPath2);
                    uploadImages(localInfoBean);
                }
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
            case REQUEST_ALL_TRADING_ORDER:
                List<DicInfo2> allTradingOrder = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_TRADING_ORDER)) {
                        allTradingOrder.add(new DicInfo2(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getAllTradingOrders(allTradingOrder);
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
            case REQUEST_COMPANY_PAY_ORDER_DETAIL:
                CompanyPayOrderInfo companyPayOrderInfo = null;
                for (CompanyPayOrderInfo info : mPayOrderLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyPayOrderInfo = info;
                    }
                }
                getCompanyPayOrderDetail(companyPayOrderInfo);
                break;
            case REQUEST_SAVE_COMPANY_PAY_ORDER:
                UploadInfoBean uploadInfoBean = new UploadInfoBean();
                uploadInfoBean.setId(mPayOrderId);
                uploadInfoBean.setCreateTime(TimeUtils.getCurrentTime(new Date()));
                uploadInfoBean.setUpdateTime(TimeUtils.getCurrentTime(new Date()));
                saveCompanyPayOrder(uploadInfoBean, true);
                break;
        }
    }
}
