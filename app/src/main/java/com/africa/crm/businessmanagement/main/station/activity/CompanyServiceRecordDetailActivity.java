package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveServiceRecordEvent;
import com.africa.crm.businessmanagement.main.bean.CompanyServiceRecordInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.OrderProductInfo;
import com.africa.crm.businessmanagement.main.bean.ServiceTrackingInfoBean;
import com.africa.crm.businessmanagement.main.bean.UploadInfoBean;
import com.africa.crm.businessmanagement.main.dao.CompanyServiceRecordInfoDao;
import com.africa.crm.businessmanagement.main.dao.DicInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.ServiceTrackingListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyServiceRecordrDetailContract;
import com.africa.crm.businessmanagement.main.station.dialog.AddServiceRecordDialog;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyServiceRecordDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.widget.DifferentDataUtil;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.MySpinner;
import com.africa.crm.businessmanagement.widget.StringUtil;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_CUSTOMERS;
import static com.africa.crm.businessmanagement.network.api.DicUtil.QUERY_ALL_PRODUCTS;
import static com.africa.crm.businessmanagement.network.api.DicUtil.SERVICE_LEVEL;
import static com.africa.crm.businessmanagement.network.api.DicUtil.SERVICE_STATE;
import static com.africa.crm.businessmanagement.network.api.DicUtil.SERVICE_TYPE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_CUSTOMER_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_ALL_PRODUCTS_LIST;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_SERVICE_RECORD_DETAIL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SAVE_COMPANY_SERVICE_RECORD;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SERVICE_LEVEL;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SERVICE_STATE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_SERVICE_TYPE;

public class CompanyServiceRecordDetailActivity extends BaseMvpActivity<CompanyServiceRecordDetailPresenter> implements CompanyServiceRecordrDetailContract.View {
    @BindView(R.id.et_service_record_name)
    EditText et_service_record_name;

    @BindView(R.id.spinner_product)
    MySpinner spinner_product;
    private String mProductId = "";
    private String mProductName = "";

    @BindView(R.id.spinner_state)
    MySpinner spinner_state;
    private List<DicInfo> mServiceRecordStateList = new ArrayList<>();
    private String mStateCode = "";
    private String mStateName = "";

    @BindView(R.id.spinner_type)
    MySpinner spinner_type;
    private List<DicInfo> mServiceRecordTypeList = new ArrayList<>();
    private String mTypeCode = "";
    private String mTypeName = "";

    @BindView(R.id.spinner_customer_name)
    MySpinner spinner_customer_name;
    private String mCustomerName = "";

    @BindView(R.id.spinner_level)
    MySpinner spinner_level;
    private List<DicInfo> mLevelList = new ArrayList<>();
    private String mLevelCode = "";
    private String mLevelName = "";

    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_reason)
    EditText et_reason;
    @BindView(R.id.et_solution)
    EditText et_solution;
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.tv_add_tracking)
    TextView tv_add_tracking;
    @BindView(R.id.tv_save)
    TextView tv_save;

    @BindView(R.id.rv_service_tracking)
    RecyclerView rv_service_tracking;
    private ServiceTrackingListAdapter mServiceTrackingListAdapter;
    private List<ServiceTrackingInfoBean> mServiceTrackingInfoBeans = new ArrayList<>();
    private AddServiceRecordDialog mAddServiceRecordDialog;

    private String mServiceRecordId = "";
    private Long mLocalId = 0l;//本地数据库ID
    private String mCompanyId = "";
    private String mCompanyName = "";
    private String mFromName = "";
    private String mUserId = "";
    private String mEditAble = "1";

    private GreendaoManager<CompanyServiceRecordInfo, CompanyServiceRecordInfoDao> mServiceRecordInfoDaoManager;
    private List<CompanyServiceRecordInfo> mServiceRecordLocalList = new ArrayList<>();//本地数据

    private GreendaoManager<DicInfo, DicInfoDao> mDicInfoDaoManager;
    private List<DicInfo> mDicInfoLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id, Long localId) {
        Intent intent = new Intent(activity, CompanyServiceRecordDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("localId", localId);
        activity.startActivity(intent);
    }


    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_service_record_detail);
    }

    @Override
    public void initView() {
        super.initView();
        mServiceRecordId = getIntent().getStringExtra("id");
        mLocalId = getIntent().getLongExtra("localId", 0l);
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mCompanyName = UserInfoManager.getUserLoginInfo(this).getCompanyName();
        mFromName = UserInfoManager.getUserLoginInfo(this).getName();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_name.setText("服务记录详情");
        tv_save.setOnClickListener(this);
        tv_add_tracking.setOnClickListener(this);
        String roleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        if (roleCode.equals("companySales")) {
            if (TextUtils.isEmpty(mServiceRecordId) && mLocalId == 0l) {
                titlebar_right.setVisibility(View.GONE);
                tv_save.setText(R.string.add);
                tv_save.setVisibility(View.VISIBLE);
            } else if (!TextUtils.isEmpty(mServiceRecordId) || mLocalId != 0l) {
                titlebar_right.setText(R.string.edit);
                tv_save.setText(R.string.save);
                setEditTextInput(false);
            }
        } else {
            titlebar_right.setVisibility(View.GONE);
            tv_save.setVisibility(View.GONE);
            setEditTextInput(false);
        }

        //得到Dao对象管理器
        mServiceRecordInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyServiceRecordInfoDao());
        //得到本地数据
        mServiceRecordLocalList = mServiceRecordInfoDaoManager.queryAll();
        //得到Dao对象管理器
        mDicInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getDicInfoDao());
        //得到本地数据
        mDicInfoLocalList = mDicInfoDaoManager.queryAll();
    }

    @Override
    public void initData() {
        mServiceTrackingListAdapter = new ServiceTrackingListAdapter(mServiceTrackingInfoBeans);
        rv_service_tracking.setAdapter(mServiceTrackingListAdapter);
        rv_service_tracking.setLayoutManager(new LinearLayoutManager(this));
        rv_service_tracking.addItemDecoration(new LineItemDecoration(this, DividerItemDecoration.VERTICAL, 0, 0));
        rv_service_tracking.setHasFixedSize(true);
        rv_service_tracking.setNestedScrollingEnabled(false);
    }

    @Override
    protected CompanyServiceRecordDetailPresenter injectPresenter() {
        return new CompanyServiceRecordDetailPresenter();
    }

    @Override
    protected void requestData() {
        mPresenter.getLevels(SERVICE_LEVEL);
        mPresenter.getState(SERVICE_STATE);
        mPresenter.getType(SERVICE_TYPE);
        mPresenter.getAllCustomers(mCompanyId);
        mPresenter.getAllProduct(mCompanyId);
        if (!TextUtils.isEmpty(mServiceRecordId) || mLocalId != 0l) {
            mPresenter.getCompanyServiceRecordDetail(mServiceRecordId);
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
            case R.id.tv_add_tracking:
                mAddServiceRecordDialog = AddServiceRecordDialog.getInstance(this);
                mAddServiceRecordDialog.isCancelableOnTouchOutside(false)
                        .withDuration(300)
                        .withEffect(Effectstype.Fadein)
                        .setCancelClick(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                mAddServiceRecordDialog.dismiss();
                            }
                        })
                        .show();
                mAddServiceRecordDialog.addOnSaveClickListener(new AddServiceRecordDialog.OnSaveClickListener() {
                    @Override
                    public void onSaveClick(OrderProductInfo orderProductInfo) {
                        if (TextUtils.isEmpty(orderProductInfo.getName())) {
                            toastMsg("尚未填写服务追踪");
                            return;
                        }
                        mServiceTrackingInfoBeans.add(new ServiceTrackingInfoBean(orderProductInfo.getName(), orderProductInfo.getNum()));
                        if (mServiceTrackingListAdapter != null) {
                            mServiceTrackingListAdapter.notifyDataSetChanged();
                        }
                        mAddServiceRecordDialog.dismiss();
                    }
                });
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(et_service_record_name.getText().toString().trim())) {
                    toastMsg("尚未填写服务记录名称");
                    return;
                }
                if (TextUtils.isEmpty(mTypeCode)) {
                    toastMsg("尚未选择服务记录类型");
                    return;
                }
                mPresenter.saveCompanyServiceRecord(mServiceRecordId, mCompanyId, mUserId, et_service_record_name.getText().toString().trim(), mStateCode, mTypeCode, mProductId, spinner_customer_name.getText(), mLevelCode, et_phone.getText().toString().trim(), et_email.getText().toString().trim(), et_reason.getText().toString().trim(), et_remark.getText().toString().trim(), et_solution.getText().toString().trim(), new Gson().toJson(mServiceTrackingInfoBeans));
                break;
        }
    }

    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        et_service_record_name.setEnabled(canInput);
        spinner_product.getTextView().setEnabled(canInput);
        spinner_state.getTextView().setEnabled(canInput);
        spinner_type.getTextView().setEnabled(canInput);
        spinner_customer_name.getTextView().setEnabled(canInput);
        spinner_level.getTextView().setEnabled(canInput);
        et_phone.setEnabled(canInput);
        et_email.setEnabled(canInput);
        et_reason.setEnabled(canInput);
        et_solution.setEnabled(canInput);
        et_remark.setEnabled(canInput);
        tv_add_tracking.setEnabled(canInput);
    }

    @Override
    public void getState(List<DicInfo> dicInfoList) {
        mServiceRecordStateList.clear();
        mServiceRecordStateList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mServiceRecordStateList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(SERVICE_STATE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_state.setListDatas(this, mServiceRecordStateList);
        spinner_state.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mStateCode = dicInfo.getCode();
                mStateName = dicInfo.getText();
            }
        });
    }

    @Override
    public void getType(List<DicInfo> dicInfoList) {
        mServiceRecordTypeList.clear();
        mServiceRecordTypeList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mServiceRecordTypeList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(SERVICE_TYPE);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_type.setListDatas(this, mServiceRecordTypeList);
        spinner_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mTypeCode = dicInfo.getCode();
                mTypeName = dicInfo.getText();
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
        spinner_product.setListDatas(this, list);
        spinner_product.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mProductId = dicInfo.getCode();
                mProductName = dicInfo.getText();
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
    public void getLevels(List<DicInfo> dicInfoList) {
        mLevelList.clear();
        mLevelList.addAll(dicInfoList);
        List<DicInfo> addList = DifferentDataUtil.addDataToLocal(mLevelList, mDicInfoLocalList);
        for (DicInfo dicInfo : addList) {
            dicInfo.setType(SERVICE_LEVEL);
            mDicInfoDaoManager.insertOrReplace(dicInfo);
        }
        spinner_level.setListDatas(this, mLevelList);
        spinner_level.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mLevelCode = dicInfo.getCode();
                mLevelName = dicInfo.getText();
            }
        });
    }

    @Override
    public void getCompanyServiceRecordDetail(CompanyServiceRecordInfo companyServiceRecordInfo) {
        if (companyServiceRecordInfo != null) {
            et_service_record_name.setText(companyServiceRecordInfo.getName());
            spinner_product.setText(companyServiceRecordInfo.getProductName());
            mProductId = companyServiceRecordInfo.getProductId();
            spinner_state.setText(companyServiceRecordInfo.getStateName());
            mStateCode = companyServiceRecordInfo.getState();
            spinner_type.setText(companyServiceRecordInfo.getTypeName());
            mTypeCode = companyServiceRecordInfo.getType();
            spinner_customer_name.setText(companyServiceRecordInfo.getCustomerName());
            mCustomerName = companyServiceRecordInfo.getCustomerName();
            spinner_level.setText(companyServiceRecordInfo.getLevelName());
            mLevelCode = companyServiceRecordInfo.getLevel();
            et_phone.setText(companyServiceRecordInfo.getPhone());
            et_email.setText(companyServiceRecordInfo.getEmail());
            et_reason.setText(companyServiceRecordInfo.getReason());
            et_solution.setText(companyServiceRecordInfo.getSolution());
            et_remark.setText(companyServiceRecordInfo.getRemark());
            mCompanyName = companyServiceRecordInfo.getCompanyName();
            mFromName = companyServiceRecordInfo.getUserNickName();
            mEditAble = companyServiceRecordInfo.getEditAble();
            List<ServiceTrackingInfoBean> list = new Gson().fromJson(companyServiceRecordInfo.getTrack(), new TypeToken<List<ServiceTrackingInfoBean>>() {
            }.getType());
            mServiceTrackingInfoBeans.addAll(list);
            if (mServiceTrackingListAdapter != null) {
                mServiceTrackingListAdapter.notifyDataSetChanged();
            }
            for (CompanyServiceRecordInfo localInfo : mServiceRecordLocalList) {
                if (!TextUtils.isEmpty(localInfo.getId()) && !TextUtils.isEmpty(companyServiceRecordInfo.getId())) {
                    if (localInfo.getId().equals(companyServiceRecordInfo.getId())) {
                        companyServiceRecordInfo.setLocalId(localInfo.getLocalId());
                        mServiceRecordInfoDaoManager.correct(companyServiceRecordInfo);
                    }
                }
            }
        }
    }

    @Override
    public void saveCompanyServiceRecord(UploadInfoBean uploadInfoBean, boolean isLocal) {
        String toastString = "";
        if (TextUtils.isEmpty(mServiceRecordId) && mLocalId == 0l) {
            toastString = "服务记录创建成功";
        } else {
            toastString = "服务记录修改成功";
        }
        if (isLocal) {
            CompanyServiceRecordInfo companyServiceRecordInfo = null;
            if (mLocalId == 0l) {
                companyServiceRecordInfo = new CompanyServiceRecordInfo(mCustomerName, TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), StringUtil.getText(et_phone), StringUtil.getText(et_remark), StringUtil.getText(et_reason), new Gson().toJson(mServiceTrackingInfoBeans), mStateCode, mTypeCode, mCompanyName, StringUtil.getText(et_solution), mFromName, mProductId, mTypeName, mServiceRecordId, mLevelCode, StringUtil.getText(et_email), mEditAble, mUserId, StringUtil.getText(et_service_record_name), mStateName, mLevelName, mCompanyId, mProductName, false, isLocal);
                mServiceRecordInfoDaoManager.insertOrReplace(companyServiceRecordInfo);
            } else {
                for (CompanyServiceRecordInfo info : mServiceRecordLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyServiceRecordInfo = new CompanyServiceRecordInfo(info.getLocalId(), mCustomerName, TimeUtils.getCurrentTime(new Date()), TimeUtils.getDateByCreateTime(TimeUtils.getTime(new Date())), StringUtil.getText(et_phone), StringUtil.getText(et_remark), StringUtil.getText(et_reason), new Gson().toJson(mServiceTrackingInfoBeans), mStateCode, mTypeCode, mCompanyName, StringUtil.getText(et_solution), mFromName, mProductId, mTypeName, mServiceRecordId, mLevelCode, StringUtil.getText(et_email), mEditAble, mUserId, StringUtil.getText(et_service_record_name), mStateName, mLevelName, mCompanyId, mProductName, false, isLocal);
                        mServiceRecordInfoDaoManager.correct(companyServiceRecordInfo);
                    }
                }
            }
        }
        EventBus.getDefault().post(new AddOrSaveServiceRecordEvent(toastString));
        finish();
    }

    @Override
    public void loadLocalData(String port) {
        super.loadLocalData(port);
        switch (port) {
            case REQUEST_SERVICE_STATE:
                List<DicInfo> stateList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(SERVICE_STATE)) {
                        stateList.add(dicInfo);
                    }
                }
                getState(stateList);
                break;
            case REQUEST_SERVICE_TYPE:
                List<DicInfo> typeList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(SERVICE_TYPE)) {
                        typeList.add(dicInfo);
                    }
                }
                getType(typeList);
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
            case REQUEST_ALL_CUSTOMER_LIST:
                List<DicInfo2> allCustomers = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(QUERY_ALL_CUSTOMERS)) {
                        allCustomers.add(new DicInfo2(dicInfo.getId(), dicInfo.getText(), dicInfo.getCode()));
                    }
                }
                getAllCustomers(allCustomers);
                break;
            case REQUEST_SERVICE_LEVEL:
                List<DicInfo> levelList = new ArrayList<>();
                for (DicInfo dicInfo : mDicInfoDaoManager.queryAll()) {
                    if (dicInfo.getType().equals(SERVICE_LEVEL)) {
                        levelList.add(dicInfo);
                    }
                }
                getLevels(levelList);
                break;
            case REQUEST_COMPANY_SERVICE_RECORD_DETAIL:
                CompanyServiceRecordInfo companyServiceRecordInfo = null;
                for (CompanyServiceRecordInfo info : mServiceRecordLocalList) {
                    if (mLocalId == info.getLocalId()) {
                        companyServiceRecordInfo = info;
                    }
                }
                getCompanyServiceRecordDetail(companyServiceRecordInfo);
                break;
            case REQUEST_SAVE_COMPANY_SERVICE_RECORD:
                UploadInfoBean uploadInfoBean = new UploadInfoBean();
                uploadInfoBean.setId(mServiceRecordId);
                uploadInfoBean.setCreateTime(TimeUtils.getCurrentTime(new Date()));
                uploadInfoBean.setUpdateTime(TimeUtils.getCurrentTime(new Date()));
                saveCompanyServiceRecord(uploadInfoBean, true);
                break;
        }
    }
}
