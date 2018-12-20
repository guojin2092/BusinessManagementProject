package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyProductEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyProductInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanyProductDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyProductDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.MySpinner;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CompanyProductDetailActivity extends BaseMvpActivity<CompanyProductDetailPresenter> implements CompanyProductDetailContract.View {
    @BindView(R.id.et_product_name)
    EditText et_product_name;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.et_maker_name)
    EditText et_maker_name;
    @BindView(R.id.et_price)
    EditText et_price;
    @BindView(R.id.et_unit)
    EditText et_unit;
    @BindView(R.id.et_inventory_num)
    EditText et_inventory_num;
    @BindView(R.id.et_warn_num)
    EditText et_warn_num;
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.tv_save)
    TextView tv_save;

    @BindView(R.id.spinner_supplier_name)
    MySpinner spinner_supplier_name;

    @BindView(R.id.spinner_product_type)
    MySpinner spinner_product_type;
    private static final String CONTACT_PRODUCT_TYPE = "PRODUCTTYPE";
    private List<DicInfo> mSpinnerProductList = new ArrayList<>();
    private String mProductType = "";

    private String mProductId = "";
    private String mCompanyId = "";

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String productId) {
        Intent intent = new Intent(activity, CompanyProductDetailActivity.class);
        intent.putExtra("productId", productId);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company_product_detail);
    }

    @Override
    protected CompanyProductDetailPresenter injectPresenter() {
        return new CompanyProductDetailPresenter();
    }

    @Override
    public void initView() {
        super.initView();
        mProductId = getIntent().getStringExtra("productId");
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        titlebar_name.setText("产品详情");
        tv_save.setOnClickListener(this);

        if (!TextUtils.isEmpty(mProductId)) {
            titlebar_right.setText(R.string.edit);
            tv_save.setText(R.string.save);
            setEditTextInput(false);
            et_inventory_num.setEnabled(false);
        } else {
            titlebar_right.setVisibility(View.GONE);
            tv_save.setText(R.string.add);
            tv_save.setVisibility(View.VISIBLE);
            et_inventory_num.setEnabled(true);
        }
    }

    @Override
    public void initData() {

    }

    @Override
    protected void requestData() {
        mPresenter.getAllSuppliers(mCompanyId);
        mPresenter.getProductType(CONTACT_PRODUCT_TYPE);
        if (!TextUtils.isEmpty(mProductId)) {
            mPresenter.getCompanyProductDetail(mProductId);
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
            case R.id.tv_save:
                if (TextUtils.isEmpty(et_product_name.getText().toString().trim())) {
                    toastMsg("尚未填写产品名称");
                    return;
                }
                mPresenter.saveCompanyProduct(mProductId, mCompanyId, et_product_name.getText().toString().trim(), et_code.getText().toString().trim(), spinner_supplier_name.getText(), et_maker_name.getText().toString().trim(), mProductType, et_price.getText().toString().trim(), et_unit.getText().toString().trim(), et_inventory_num.getText().toString().trim(), et_warn_num.getText().toString().trim(), et_remark.getText().toString().trim());
                break;
        }
    }

    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        et_product_name.setEnabled(canInput);
        et_code.setEnabled(canInput);
        spinner_supplier_name.getTextView().setEnabled(canInput);
        et_maker_name.setEnabled(canInput);
        spinner_product_type.getTextView().setEnabled(canInput);
        et_price.setEnabled(canInput);
        et_unit.setEnabled(canInput);
        et_warn_num.setEnabled(canInput);
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
    public void getProductType(List<DicInfo> dicInfoList) {
        mSpinnerProductList.clear();
        mSpinnerProductList.addAll(dicInfoList);
        spinner_product_type.setListDatas(this, mSpinnerProductList);

        spinner_product_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mProductType = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getCompanyProductDetail(CompanyProductInfo companyProductInfo) {
        et_product_name.setText(companyProductInfo.getName());
        et_code.setText(companyProductInfo.getCode());
        spinner_supplier_name.setText(companyProductInfo.getSupplierName());
        et_maker_name.setText(companyProductInfo.getMakerName());
        spinner_product_type.setText(companyProductInfo.getTypeName());
        mProductType = companyProductInfo.getType();
        et_price.setText(companyProductInfo.getUnitPrice());
        et_unit.setText(companyProductInfo.getUnit());
        et_inventory_num.setText(companyProductInfo.getStockNum());
        et_warn_num.setText(companyProductInfo.getWarnNum());
        et_remark.setText(companyProductInfo.getRemark());
    }

    @Override
    public void saveCompanyProduct(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String toastString = "";
            if (TextUtils.isEmpty(mProductId)) {
                toastString = "企业产品创建成功";
            } else {
                toastString = "企业产品修改成功";
            }
            EventBus.getDefault().post(new AddOrSaveCompanyProductEvent(toastString));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }
}
