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

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveServiceRecordEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyServiceRecordInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.OrderProductInfo;
import com.africa.crm.businessmanagement.main.bean.ServiceTrackingInfoBean;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.adapter.ServiceTrackingListAdapter;
import com.africa.crm.businessmanagement.main.station.contract.CompanyServiceRecordrDetailContract;
import com.africa.crm.businessmanagement.main.station.dialog.AddServiceRecordDialog;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyServiceRecordDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.LineItemDecoration;
import com.africa.crm.businessmanagement.widget.MySpinner;
import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CompanyServiceRecordDetailActivity extends BaseMvpActivity<CompanyServiceRecordDetailPresenter> implements CompanyServiceRecordrDetailContract.View {
    @BindView(R.id.et_service_record_name)
    EditText et_service_record_name;

    @BindView(R.id.spinner_product)
    MySpinner spinner_product;
    private String mProductId = "";

    @BindView(R.id.spinner_state)
    MySpinner spinner_state;
    private static final String STATE_CODE = "SERVICESTATE";
    private List<DicInfo> mServiceRecordStateList = new ArrayList<>();
    private String mStateCode = "";

    @BindView(R.id.spinner_type)
    MySpinner spinner_type;
    private static final String TYPE_CODE = "SERVICETYPE";
    private List<DicInfo> mServiceRecordTypeList = new ArrayList<>();
    private String mTypeCode = "";

    @BindView(R.id.spinner_customer_name)
    MySpinner spinner_customer_name;

    @BindView(R.id.spinner_level)
    MySpinner spinner_level;
    private static final String LEVEL_CODE = "SERVICELEVEL";
    private List<DicInfo> mLevelList = new ArrayList<>();
    private String mLevelCode = "";

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
    private String mCompanyId = "";
    private String mUserId = "";

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, CompanyServiceRecordDetailActivity.class);
        intent.putExtra("id", id);
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
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_name.setText("服务记录详情");
        tv_save.setOnClickListener(this);
        tv_add_tracking.setOnClickListener(this);
        if (!TextUtils.isEmpty(mServiceRecordId)) {
            titlebar_right.setText(R.string.edit);
            tv_save.setText(R.string.save);
            setEditTextInput(false);
        } else {
            titlebar_right.setVisibility(View.GONE);
            tv_save.setText(R.string.add);
            tv_save.setVisibility(View.VISIBLE);
        }
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
        mPresenter.getLevels(LEVEL_CODE);
        mPresenter.getState(STATE_CODE);
        mPresenter.getType(TYPE_CODE);
        mPresenter.getAllCustomers(mCompanyId);
        mPresenter.getAllProduct(mCompanyId);
        if (!TextUtils.isEmpty(mServiceRecordId)) {
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
        spinner_state.setListDatas(this, mServiceRecordStateList);

        spinner_state.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mStateCode = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getType(List<DicInfo> dicInfoList) {
        mServiceRecordTypeList.clear();
        mServiceRecordTypeList.addAll(dicInfoList);
        spinner_type.setListDatas(this, mServiceRecordTypeList);

        spinner_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mTypeCode = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getAllProduct(List<DicInfo2> dicInfoList) {
        List<DicInfo> list = new ArrayList<>();
        for (DicInfo2 dicInfo2 : dicInfoList) {
            list.add(new DicInfo(dicInfo2.getName(), dicInfo2.getId()));
        }
        spinner_product.setListDatas(this, list);
        spinner_product.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mProductId = dicInfo.getCode();
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
    public void getLevels(List<DicInfo> dicInfoList) {
        mLevelList.clear();
        mLevelList.addAll(dicInfoList);
        spinner_level.setListDatas(this, mLevelList);

        spinner_level.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mLevelCode = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getCompanyServiceRecordDetail(CompanyServiceRecordInfo companyServiceRecordInfo) {
        et_service_record_name.setText(companyServiceRecordInfo.getName());
        spinner_product.setText(companyServiceRecordInfo.getProductName());
        mProductId = companyServiceRecordInfo.getProductId();
        spinner_state.setText(companyServiceRecordInfo.getStateName());
        mStateCode = companyServiceRecordInfo.getState();
        spinner_type.setText(companyServiceRecordInfo.getTypeName());
        mTypeCode = companyServiceRecordInfo.getType();
        spinner_customer_name.setText(companyServiceRecordInfo.getCustomerName());
        spinner_level.setText(companyServiceRecordInfo.getLevelName());
        mLevelCode = companyServiceRecordInfo.getLevel();
        et_phone.setText(companyServiceRecordInfo.getPhone());
        et_email.setText(companyServiceRecordInfo.getEmail());
        et_reason.setText(companyServiceRecordInfo.getReason());
        et_solution.setText(companyServiceRecordInfo.getSolution());
        et_remark.setText(companyServiceRecordInfo.getRemark());
        List<ServiceTrackingInfoBean> list = new Gson().fromJson(companyServiceRecordInfo.getTrack(), new TypeToken<List<ServiceTrackingInfoBean>>() {
        }.getType());
        mServiceTrackingInfoBeans.addAll(list);
        if (mServiceTrackingListAdapter != null) {
            mServiceTrackingListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void saveCompanyServiceRecord(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String toastString = "";
            if (TextUtils.isEmpty(mServiceRecordId)) {
                toastString = "服务记录创建成功";
            } else {
                toastString = "服务记录修改成功";
            }
            EventBus.getDefault().post(new AddOrSaveServiceRecordEvent(toastString));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }
}
