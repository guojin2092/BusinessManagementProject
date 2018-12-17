package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanySupplierEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanySupplierInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanySupplierDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanySupplierDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.MySpinner;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/15 0015 10:34
 * Modification  History:
 * Why & What is modified:
 */
public class CompanySupplierDetailActivity extends BaseMvpActivity<CompanySupplierDetailPresenter> implements CompanySupplierDetailContract.View {
    public final static String SUPPLIER_ID = "supplier_id";
    private String mSupplierId = "";
    @BindView(R.id.et_supplier_name)
    EditText et_supplier_name;
    @BindView(R.id.et_company_name)
    EditText et_company_name;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_area)
    EditText et_area;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_zip_code)
    EditText et_zip_code;
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_save)
    TextView tv_save;

    @BindView(R.id.spinner_type)
    MySpinner spinner_type;
    private static final String SUPPLIER_TYPE_CODE = "SUPPLIERTYPE";
    private List<DicInfo> mSpinnerCompanyTypeList = new ArrayList<>();
    private String mCompanyType = "";
    private String mCompanyId = "";
    private String mHeadUrl = "";//头像地址

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String supplierId) {
        Intent intent = new Intent(activity, CompanySupplierDetailActivity.class);
        intent.putExtra(SUPPLIER_ID, supplierId);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company_supplier_detail);
    }

    @Override
    protected CompanySupplierDetailPresenter injectPresenter() {
        return new CompanySupplierDetailPresenter();
    }

    @Override
    public void initView() {
        super.initView();
        mSupplierId = getIntent().getStringExtra(SUPPLIER_ID);
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        tv_save.setOnClickListener(this);
        titlebar_right.setText(R.string.edit);
        titlebar_name.setText("供应商详情");

        if (!TextUtils.isEmpty(mSupplierId)) {
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
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlebar_back:
                onBackPressed();
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
                if (TextUtils.isEmpty(et_supplier_name.getText().toString().trim())) {
                    toastMsg("尚未填写供应商名称");
                    return;
                }
                mPresenter.saveCompanySupplier(mSupplierId, mCompanyId, mHeadUrl, et_supplier_name.getText().toString().trim(), mCompanyType, et_address.getText().toString().trim(), et_phone.getText().toString().trim(), et_email.getText().toString().trim(), et_zip_code.getText().toString().trim(), et_area.getText().toString().trim(), et_remark.getText().toString().trim());
                break;
        }
    }

    @Override
    protected void requestData() {
        mPresenter.getSupplierType(SUPPLIER_TYPE_CODE);
        if (!TextUtils.isEmpty(mSupplierId)) {
            mPresenter.getCompanySupplierDetail(mSupplierId);
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
        et_supplier_name.setEnabled(canInput);
        et_company_name.setEnabled(canInput);
        spinner_type.getTextView().setEnabled(canInput);
        et_email.setEnabled(canInput);
        et_phone.setEnabled(canInput);
        et_area.setEnabled(canInput);
        et_address.setEnabled(canInput);
        et_zip_code.setEnabled(canInput);
        et_remark.setEnabled(canInput);
    }

    @Override
    public void getSupplierType(List<DicInfo> dicInfoList) {
        mSpinnerCompanyTypeList.clear();
        mSpinnerCompanyTypeList.addAll(dicInfoList);
        spinner_type.setListDatas(this, mSpinnerCompanyTypeList);

        spinner_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mCompanyType = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getCompanySupplierDetail(CompanySupplierInfo companySupplierInfo) {
        et_supplier_name.setText(companySupplierInfo.getName());
        et_company_name.setText(companySupplierInfo.getCompanyName());
        spinner_type.setText(companySupplierInfo.getTypeName());
        mCompanyType = companySupplierInfo.getType();
        et_email.setText(companySupplierInfo.getEmail());
        et_phone.setText(companySupplierInfo.getPhone());
        et_area.setText(companySupplierInfo.getArea());
        et_address.setText(companySupplierInfo.getAddress());
        et_zip_code.setText(companySupplierInfo.getZipCode());
        et_remark.setText(companySupplierInfo.getRemark());
    }

    @Override
    public void saveCompanySupplier(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String toastString = "";
            if (TextUtils.isEmpty(mSupplierId)) {
                toastString = "企业供应商创建成功";
            } else {
                toastString = "企业供应商修改成功";
            }
            EventBus.getDefault().post(new AddOrSaveCompanySupplierEvent(toastString));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }
}
