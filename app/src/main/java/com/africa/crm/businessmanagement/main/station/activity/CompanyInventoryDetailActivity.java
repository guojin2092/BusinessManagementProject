package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyInventoryEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyInventoryInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanyInventoryDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyInventoryDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.MySpinner;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CompanyInventoryDetailActivity extends BaseMvpActivity<CompanyInventoryDetailPresenter> implements CompanyInventoryDetailContract.View {
    @BindView(R.id.tv_operation_time)
    TextView tv_operation_time;
    @BindView(R.id.ll_operation_time)
    LinearLayout ll_operation_time;
    @BindView(R.id.ll_num_before)
    LinearLayout ll_num_before;
    @BindView(R.id.et_num_before)
    EditText et_num_before;
    @BindView(R.id.ll_num_after)
    LinearLayout ll_num_after;
    @BindView(R.id.et_num_after)
    EditText et_num_after;
    @BindView(R.id.spinner_product)
    MySpinner spinner_product;
    private String mProductId = "";
    @BindView(R.id.spinner_type)
    MySpinner spinner_type;
    private static final String TYPE_CODE = "STOCKTYPE";
    private List<DicInfo> mSpinnerTypeList = new ArrayList<>();
    private String mTypeCode = "";

    @BindView(R.id.et_num)
    EditText et_num;
    @BindView(R.id.et_remark)
    EditText et_remark;
    @BindView(R.id.tv_save)
    TextView tv_save;

    private String mInventoryId = "";
    private String mCompanyId = "";


    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, CompanyInventoryDetailActivity.class);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }


    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_inventory_detail);
    }

    @Override
    public void initView() {
        super.initView();
        mInventoryId = getIntent().getStringExtra("id");
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        titlebar_right.setVisibility(View.GONE);
        titlebar_name.setText("库存详情");
        tv_save.setText(R.string.add);
        tv_save.setOnClickListener(this);
        if (!TextUtils.isEmpty(mInventoryId)) {
            tv_save.setVisibility(View.GONE);
            setEditTextInput(false);
        } else {
            tv_save.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected CompanyInventoryDetailPresenter injectPresenter() {
        return new CompanyInventoryDetailPresenter();
    }

    @Override
    protected void requestData() {
        mPresenter.getAllProduct(mCompanyId);
        mPresenter.getOperationType(TYPE_CODE);
        if (!TextUtils.isEmpty(mInventoryId)) {
            ll_operation_time.setVisibility(View.VISIBLE);
            ll_num_before.setVisibility(View.VISIBLE);
            ll_num_after.setVisibility(View.VISIBLE);
            mPresenter.getInventoryDetail(mInventoryId);
        } else {
            ll_operation_time.setVisibility(View.GONE);
            ll_num_before.setVisibility(View.GONE);
            ll_num_after.setVisibility(View.GONE);
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
        spinner_product.getTextView().setEnabled(canInput);
        tv_operation_time.setEnabled(canInput);
        spinner_type.getTextView().setEnabled(canInput);
        et_num.setEnabled(canInput);
        et_num_before.setEnabled(canInput);
        et_num_after.setEnabled(canInput);
        et_remark.setEnabled(canInput);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlebar_right:
                if (titlebar_right.getText().toString().equals(getString(R.string.edit))) {
                    titlebar_right.setText(R.string.cancel);
                    tv_save.setVisibility(View.VISIBLE);
                } else {
                    titlebar_right.setText(R.string.edit);
                    tv_save.setVisibility(View.GONE);
                }
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(mProductId)) {
                    showShortToast("尚未选择产品");
                    return;
                }
                if (TextUtils.isEmpty(mTypeCode)) {
                    toastMsg("尚未选择操作类型");
                    return;
                }
                if (TextUtils.isEmpty(et_num.getText().toString().trim())) {
                    toastMsg("尚未填写数量");
                    return;
                }
                mPresenter.saveInventory(mCompanyId, mProductId, mTypeCode, et_num.getText().toString().trim(), et_remark.getText().toString().trim());
                break;
        }
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
    public void getOperationType(List<DicInfo> dicInfoList) {
        mSpinnerTypeList.clear();
        mSpinnerTypeList.addAll(dicInfoList);
        spinner_type.setListDatas(this, mSpinnerTypeList);
        spinner_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mTypeCode = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getInventoryDetail(CompanyInventoryInfo companyInventoryInfo) {
        et_num_before.setText(companyInventoryInfo.getNumBefore());
        et_num_after.setText(companyInventoryInfo.getNumAfter());
        tv_operation_time.setText(companyInventoryInfo.getCreateTime());
        spinner_product.setText(companyInventoryInfo.getProductName());
        mProductId = companyInventoryInfo.getProductId();
        spinner_type.setText(companyInventoryInfo.getTypeName());
        mTypeCode = companyInventoryInfo.getType();
        et_num.setText(companyInventoryInfo.getNum());
        et_remark.setText(companyInventoryInfo.getRemark());
    }

    @Override
    public void saveInventory(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String toastString = "";
            if (TextUtils.isEmpty(mInventoryId)) {
                toastString = "库存单创建成功";
            } else {
                toastString = "库存单修改成功";
            }
            EventBus.getDefault().post(new AddOrSaveCompanyInventoryEvent(toastString));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }
}
