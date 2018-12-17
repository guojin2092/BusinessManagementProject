package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.common.util.ListUtils;
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyClientEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyClientInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanyClientDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyClientDetailPresenter;
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
public class CompanyClientDetailActivity extends BaseMvpActivity<CompanyClientDetailPresenter> implements CompanyClientDetailContract.View {
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_add_icon)
    TextView tv_add_icon;
    @BindView(R.id.tv_save)
    TextView tv_save;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_from_company_name)
    EditText et_from_company_name;
    @BindView(R.id.et_company_staff_num)
    EditText et_company_staff_num;
    @BindView(R.id.et_income)
    EditText et_income;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_remark)
    EditText et_remark;

    @BindView(R.id.spinner_industry)
    MySpinner spinner_industry;
    private static final String CONTACT_INDUSTRY_TYPE = "INDUSTRYTYPE";
    private List<DicInfo> mSpinnerIndustryList = new ArrayList<>();
    private String mIndustryType = "";

    @BindView(R.id.ll_from_user)
    LinearLayout ll_from_user;
    @BindView(R.id.spinner_from_user)
    MySpinner spinner_from_user;
    private List<DicInfo> mSpinnerCompanyUserList = new ArrayList<>();
    private String mFromUserId = "";

    private String mClientId = "";//联系人Id
    private String mRoleCode = "";//角色code
    private String mCompanyId = "";
    private String mHeadUrl = "";//头像地址

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String id) {
        Intent intent = new Intent(activity, CompanyClientDetailActivity.class);
        intent.putExtra("clientId", id);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company_client_detail);
    }

    @Override
    protected CompanyClientDetailPresenter injectPresenter() {
        return new CompanyClientDetailPresenter();
    }

    @Override
    public void initView() {
        super.initView();
        mClientId = getIntent().getStringExtra("clientId");
        mCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
        mRoleCode = UserInfoManager.getUserLoginInfo(this).getRoleCode();
        titlebar_name.setText("客户详情");
        tv_save.setOnClickListener(this);

        if (!TextUtils.isEmpty(mClientId)) {
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
                if (TextUtils.isEmpty(et_name.getText().toString().trim())) {
                    toastMsg("尚未填写姓名");
                    return;
                }
                String userId = "";
                if (mRoleCode.equals("companySales")) {
                    userId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
                } else {
                    userId = mFromUserId;
                }
                mPresenter.saveCompanyClient(mClientId, mCompanyId, userId, mHeadUrl, et_name.getText().toString().trim(), mIndustryType, et_address.getText().toString().trim(), et_company_staff_num.getText().toString().trim(), et_phone.getText().toString().trim(), et_income.getText().toString().trim(), et_remark.getText().toString().trim());
                break;
        }
    }

    @Override
    protected void requestData() {
        if ("companyRoot".equals(mRoleCode)) {
            ll_from_user.setVisibility(View.VISIBLE);
            mPresenter.getAllCompanyUsers(mCompanyId);
        } else {
            ll_from_user.setVisibility(View.GONE);
        }
        mPresenter.getIndustry(CONTACT_INDUSTRY_TYPE);
        if (!TextUtils.isEmpty(mClientId)) {
            mPresenter.getCompanyClientDetail(mClientId);
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
        tv_add_icon.setEnabled(canInput);
        et_name.setEnabled(canInput);
        spinner_from_user.getTextView().setEnabled(canInput);
        et_from_company_name.setEnabled(canInput);
        et_company_staff_num.setEnabled(canInput);
        spinner_industry.getTextView().setEnabled(canInput);
        et_income.setEnabled(canInput);
        et_phone.setEnabled(canInput);
        et_address.setEnabled(canInput);
        et_remark.setEnabled(canInput);
    }

    @Override
    public void getAllCompanyUsers(List<DicInfo2> dicInfo2List) {
        mSpinnerCompanyUserList.clear();
        if (!ListUtils.isEmpty(dicInfo2List)) {
            for (DicInfo2 dicInfo2 : dicInfo2List) {
                DicInfo dicInfo = new DicInfo(dicInfo2.getName(), dicInfo2.getId());
                mSpinnerCompanyUserList.add(dicInfo);
            }
            spinner_from_user.setListDatas(getBVActivity(), mSpinnerCompanyUserList);
            spinner_from_user.addOnItemClickListener(new MySpinner.OnItemClickListener() {
                @Override
                public void onItemClick(DicInfo dicInfo, int position) {
                    mFromUserId = dicInfo.getCode();
                }
            });
        }
    }

    @Override
    public void getIndustry(List<DicInfo> dicInfoList) {
        mSpinnerIndustryList.clear();
        mSpinnerIndustryList.addAll(dicInfoList);
        spinner_industry.setListDatas(this, mSpinnerIndustryList);

        spinner_industry.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mIndustryType = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getCompanyClientDetail(CompanyClientInfo companyClientInfo) {
        et_name.setText(companyClientInfo.getName());
        spinner_from_user.setText(companyClientInfo.getUserNickName());
        mFromUserId = companyClientInfo.getUserId();
        et_from_company_name.setText(companyClientInfo.getCompanyName());
        et_company_staff_num.setText(companyClientInfo.getIndustryName());
        mIndustryType = companyClientInfo.getIndustry();
        spinner_industry.setText(companyClientInfo.getIndustryName());
        mIndustryType = companyClientInfo.getIndustry();
        et_income.setText(companyClientInfo.getYearIncome());
        et_phone.setText(companyClientInfo.getTel());
        et_address.setText(companyClientInfo.getAddress());
        et_remark.setText(companyClientInfo.getRemark());
    }

    @Override
    public void saveCompanyClient(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String toastString = "";
            if (TextUtils.isEmpty(mClientId)) {
                toastString = "企业客户创建成功";
            } else {
                toastString = "企业客户修改成功";
            }
            EventBus.getDefault().post(new AddOrSaveCompanyClientEvent(toastString));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }
}
