package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyPackagingDataEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyPackagingDataInfo;
import com.africa.crm.businessmanagement.main.bean.PreviewInfo;
import com.africa.crm.businessmanagement.main.dao.CompanyPackagingDataInfoDao;
import com.africa.crm.businessmanagement.main.dao.GreendaoManager;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanyPackagingDataDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyPackagingDataDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.TimeUtils;
import com.bigkoo.pickerview.TimePickerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;

import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_CHECK_DATE;
import static com.africa.crm.businessmanagement.network.api.RequestMethod.REQUEST_COMPANY_PACKAGING_DATA_DETAIL;

public class CompanyPackagingDataDetailActivity extends BaseMvpActivity<CompanyPackagingDataDetailPresenter> implements CompanyPackagingDataDetailContract.View {
    @BindView(R.id.tv_num)
    TextView tv_num;
    @BindView(R.id.tv_tradingOrder)
    TextView tv_tradingOrder;
    @BindView(R.id.tv_quotationOrder)
    TextView tv_quotationOrder;
    @BindView(R.id.tv_paymentOrder)
    TextView tv_paymentOrder;
    @BindView(R.id.tv_purchaseOrder)
    TextView tv_purchaseOrder;
    @BindView(R.id.tv_invoiceOrder)
    TextView tv_invoiceOrder;
    @BindView(R.id.tv_salesOrder)
    TextView tv_salesOrder;
    @BindView(R.id.tv_start_date)
    TextView tv_start_date;
    @BindView(R.id.tv_end_date)
    TextView tv_end_date;
    @BindView(R.id.tv_get_preview_info)
    TextView tv_get_preview_info;
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.tv_save)
    TextView tv_save;

    private String mPackageDataId = "";
    private Long mLocalId = 0l;//本地数据库ID
    private String mCompanyId = "";
    private String mUserId = "";

    private TimePickerView pvStartTime, pvEndTime;
    private Date mStartDate, mEndDate;

    private String mPreviewInfo = "";

    private GreendaoManager<CompanyPackagingDataInfo, CompanyPackagingDataInfoDao> mPackagingDataInfoDaoManager;
    private List<CompanyPackagingDataInfo> mPackagingDataLocalList = new ArrayList<>();//本地数据

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id, Long localId) {
        Intent intent = new Intent(activity, CompanyPackagingDataDetailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("localId", localId);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_packaging_data_detail);
    }

    @Override
    public void initView() {
        super.initView();
        mPackageDataId = getIntent().getStringExtra("id");
        mLocalId = getIntent().getLongExtra("localId", 0l);
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mUserId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
        titlebar_right.setVisibility(View.GONE);
        titlebar_name.setText(R.string.Package_data_details);
        tv_save.setText(R.string.Add);
        tv_save.setOnClickListener(this);
        tv_start_date.setOnClickListener(this);
        tv_end_date.setOnClickListener(this);
        tv_get_preview_info.setOnClickListener(this);
        if (TextUtils.isEmpty(mPackageDataId) && mLocalId == 0l) {
            tv_get_preview_info.setVisibility(View.VISIBLE);
            tv_save.setVisibility(View.VISIBLE);
        } else if (!TextUtils.isEmpty(mPackageDataId) || mLocalId != 0l) {
            tv_get_preview_info.setVisibility(View.GONE);
            tv_save.setVisibility(View.GONE);
            setEditTextInput(false);
        }
        initTimePicker();
        //得到Dao对象管理器
        mPackagingDataInfoDaoManager = new GreendaoManager<>(MyApplication.getInstance().getDaoSession().getCompanyPackagingDataInfoDao());        //得到本地数据
        mPackagingDataLocalList = mPackagingDataInfoDaoManager.queryAll();
    }

    private void initTimePicker() {
        pvStartTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mStartDate = date;
                if (mEndDate != null) {
                    if (mEndDate.getTime() < mStartDate.getTime()) {
                        toastMsg(getString(R.string.The_end_time_cannot_be_earlier_than_the_start_time));
                        return;
                    }
                }
                tv_start_date.setText(TimeUtils.getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true));

        pvEndTime = new TimePickerView(new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                mEndDate = date;
                if (mStartDate != null) {
                    if (mEndDate.getTime() < mStartDate.getTime()) {
                        toastMsg(getString(R.string.The_end_time_cannot_be_earlier_than_the_start_time));
                        return;
                    }
                }
                tv_end_date.setText(TimeUtils.getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isDialog(true));
    }


    @Override
    protected CompanyPackagingDataDetailPresenter injectPresenter() {
        return new CompanyPackagingDataDetailPresenter();
    }

    @Override
    protected void requestData() {
        if (!TextUtils.isEmpty(mPackageDataId) || mLocalId != 0l) {
            mPresenter.getPackagingDataDetail(mPackageDataId);
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
        tv_start_date.setEnabled(canInput);
        tv_end_date.setEnabled(canInput);
        tv_get_preview_info.setEnabled(canInput);
        tv_num.setEnabled(canInput);
        tv_tradingOrder.setEnabled(canInput);
        et_remark.setEnabled(canInput);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_start_date:
                if (pvStartTime != null)
                    pvStartTime.show();
                break;
            case R.id.tv_end_date:
                if (pvEndTime != null)
                    pvEndTime.show();
                break;
            case R.id.tv_get_preview_info:
                if (TextUtils.isEmpty(tv_start_date.getText().toString().trim())) {
                    toastMsg(getString(R.string.Please_select_the_package_start_time));
                    return;
                }
                if (TextUtils.isEmpty(tv_end_date.getText().toString().trim())) {
                    toastMsg(getString(R.string.Please_select_the_package_end_time));
                    return;
                }
                mPresenter.getPreviewInfo(mCompanyId, tv_start_date.getText().toString().trim(), tv_end_date.getText().toString().trim());
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(tv_start_date.getText().toString().trim())) {
                    toastMsg(getString(R.string.Please_select_the_package_start_time));
                    return;
                }
                if (TextUtils.isEmpty(tv_end_date.getText().toString().trim())) {
                    toastMsg(getString(R.string.Please_select_the_package_end_time));
                    return;
                }
                mPresenter.savePackagingData(mCompanyId, mUserId, tv_start_date.getText().toString().trim(), tv_end_date.getText().toString().trim(), tv_num.getText().toString().trim(), mPreviewInfo, et_remark.getText().toString().trim());
                break;
        }
    }

    @Override
    public void getPreviewInfo(String previewInfo) {
        if (!TextUtils.isEmpty(previewInfo)) {
            mPreviewInfo = previewInfo;
            PreviewInfo info = new Gson().fromJson(previewInfo, new TypeToken<PreviewInfo>() {
            }.getType());
            tv_num.setText(info.getTotal());
            tv_tradingOrder.setText(info.getTradingOrder());
            tv_quotationOrder.setText(info.getQuotationOrder());
            tv_paymentOrder.setText(info.getPaymentOrder());
            tv_purchaseOrder.setText(info.getPurchaseOrder());
            tv_invoiceOrder.setText(info.getInvoiceOrder());
            tv_salesOrder.setText(info.getSalesOrder());
        }
    }

    @Override
    public void getPackagingDataDetail(CompanyPackagingDataInfo companyPackagingDataInfo) {
        if (companyPackagingDataInfo != null) {
            tv_start_date.setText(companyPackagingDataInfo.getStartDate());
            mStartDate = TimeUtils.getDataByString(companyPackagingDataInfo.getStartDate());
            tv_end_date.setText(companyPackagingDataInfo.getEndDate());
            mEndDate = TimeUtils.getDataByString(companyPackagingDataInfo.getEndDate());
            et_remark.setText(companyPackagingDataInfo.getRemark());
            mPresenter.getPreviewInfo(mCompanyId, tv_start_date.getText().toString().trim(), tv_end_date.getText().toString().trim());

            for (CompanyPackagingDataInfo localInfo : mPackagingDataLocalList) {
                if (!TextUtils.isEmpty(localInfo.getId()) && !TextUtils.isEmpty(companyPackagingDataInfo.getId())) {
                    if (localInfo.getId().equals(companyPackagingDataInfo.getId())) {
                        companyPackagingDataInfo.setLocalId(localInfo.getLocalId());
                        mPackagingDataInfoDaoManager.correct(companyPackagingDataInfo);
                    }
                }
            }
        }
    }

    @Override
    public void savePackagingData(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String toastString = "";
            if (TextUtils.isEmpty(mPackageDataId)) {
                toastString = getString(R.string.Added_Successfully);
            } else {
                toastString = getString(R.string.Successfully_Modified);
            }
            EventBus.getDefault().post(new AddOrSaveCompanyPackagingDataEvent(toastString));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }

    @Override
    public void loadLocalData(String port) {
        super.loadLocalData(port);
        if (port.equals(REQUEST_COMPANY_PACKAGING_DATA_DETAIL)) {
            CompanyPackagingDataInfo companyPackagingDataInfo = null;
            for (CompanyPackagingDataInfo info : mPackagingDataLocalList) {
                if (mLocalId == info.getLocalId()) {
                    companyPackagingDataInfo = info;
                }
            }
            getPackagingDataDetail(companyPackagingDataInfo);
        } else if (port.equals(REQUEST_CHECK_DATE)) {
            toastMsg("网络连接失败，请检查网络是否可用");
        }
    }
}
