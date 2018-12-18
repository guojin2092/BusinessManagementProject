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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveCompanyAccountEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.CompanyAccountInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.station.contract.CompanyAccountDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.CompanyAccountDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseMvpActivity;
import com.africa.crm.businessmanagement.network.error.ErrorMsg;
import com.africa.crm.businessmanagement.widget.MySpinner;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CompanyAccountDetailActivity extends BaseMvpActivity<CompanyAccountDetailPresenter> implements CompanyAccountDetailContract.View {
    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_add_icon)
    TextView tv_add_icon;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.ll_password)
    LinearLayout ll_password;
    @BindView(R.id.et_nickname)
    EditText et_nickname;
    @BindView(R.id.et_company_name)
    EditText et_company_name;
    @BindView(R.id.et_role_name)
    EditText et_role_name;
    @BindView(R.id.et_role_code)
    EditText et_role_code;
    @BindView(R.id.spinner_role)
    MySpinner spinner_role;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.et_phone)
    EditText et_phone;

    @BindView(R.id.tv_save)
    TextView tv_save;


    @BindView(R.id.spinner_user_type)
    MySpinner spinner_user_type;
    private static final String USER_TYPE = "USERTYPE";
    private List<DicInfo> mSpinnerUserTypeList = new ArrayList<>();
    private String mUserType = "";

    @BindView(R.id.spinner_state)
    MySpinner spinner_state;
    private static final String STATE_CODE = "STATE";
    private List<DicInfo> mSpinnerStateList = new ArrayList<>();
    private String mState = "";

    private List<DicInfo> mSpinnerRoleList = new ArrayList<>();
    private String mRoleId = "";

    private String mCompanyId;//企业ID
    private String mHeadUrl = "";//头像地址

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String companyId) {
        Intent intent = new Intent(activity, CompanyAccountDetailActivity.class);
        intent.putExtra("companyId", companyId);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_company_account_detail);
    }

    @Override
    protected CompanyAccountDetailPresenter injectPresenter() {
        return new CompanyAccountDetailPresenter();
    }

    @Override
    public void initView() {
        super.initView();
        mCompanyId = getIntent().getStringExtra("companyId");
        titlebar_name.setText("企业账号详情");
        tv_save.setOnClickListener(this);
        spinner_user_type.getTextView().setEnabled(false);
        spinner_user_type.setText("企业用户");
        mUserType = "2";
        if (!TextUtils.isEmpty(mCompanyId)) {
            ll_password.setVisibility(View.GONE);
            titlebar_right.setText(R.string.edit);
            tv_save.setText(R.string.save);
            spinner_role.setEnabled(false);
            et_role_name.setEnabled(false);
            et_role_code.setEnabled(false);
            setEditTextInput(false);
        } else {
            ll_password.setVisibility(View.VISIBLE);
            titlebar_right.setVisibility(View.GONE);
            spinner_role.setEnabled(true);
            tv_save.setText(R.string.add);
            tv_save.setVisibility(View.VISIBLE);
            et_role_name.setEnabled(true);
            et_role_code.setEnabled(true);
        }
    }


    @Override
    public void initData() {

    }

    @Override
    protected void requestData() {
//        mPresenter.getCompanyType(USER_TYPE);
        mPresenter.getState(STATE_CODE);
        mPresenter.getAllRoles("");
        if (!TextUtils.isEmpty(mCompanyId)) {
            mPresenter.getCompanyAccountDetail(mCompanyId);
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
                if (TextUtils.isEmpty(et_username.getText().toString().trim())) {
                    toastMsg("尚未填写用户名");
                    return;
                }
                if (TextUtils.isEmpty(mCompanyId)) {
                    if (TextUtils.isEmpty(et_password.getText().toString().trim())) {
                        toastMsg("尚未填写密码");
                        return;
                    }
                }
                if (TextUtils.isEmpty(mRoleId)) {
                    toastMsg("尚未选择角色分类");
                    return;
                }
                /*if (TextUtils.isEmpty(mUserType)) {
                    toastMsg("尚未选择用户类型");
                    return;
                }*/
                if (TextUtils.isEmpty(mState)) {
                    toastMsg("尚未选择企业状态");
                    return;
                }
                String ownCompanyId = UserInfoManager.getUserLoginInfo(this).getCompanyId();
                mPresenter.saveCompanyAccount(mCompanyId, et_username.getText().toString().trim(), mUserType, mRoleId, et_password.getText().toString().trim(), et_nickname.getText().toString().trim(), et_phone.getText().toString().trim(), et_address.getText().toString().trim(), et_email.getText().toString().trim(), mState, ownCompanyId, mHeadUrl);
                break;
        }
    }

    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        et_username.setEnabled(canInput);
        et_nickname.setEnabled(canInput);
        et_company_name.setEnabled(canInput);
        spinner_role.getTextView().setEnabled(canInput);
        et_address.setEnabled(canInput);
        et_email.setEnabled(canInput);
        et_phone.setEnabled(canInput);
        spinner_state.getTextView().setEnabled(canInput);
    }

    @Override
    public void getUserType(List<DicInfo> dicInfoList) {
        /*mSpinnerUserTypeList.clear();
        mSpinnerUserTypeList.addAll(dicInfoList);
        spinner_user_type.setListDatas(this, mSpinnerUserTypeList);

        spinner_user_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mUserType = dicInfo.getCode();
            }
        });*/
    }

    @Override
    public void getState(List<DicInfo> dicInfoList) {
        mSpinnerStateList.clear();
        mSpinnerStateList.addAll(dicInfoList);
        spinner_state.setListDatas(this, mSpinnerStateList);

        spinner_state.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mState = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getAllRoles(final List<RoleInfoBean> roleInfoBeanList) {
        mSpinnerRoleList.clear();
        if (roleInfoBeanList.size() == 3) {
            mSpinnerRoleList.add(new DicInfo(roleInfoBeanList.get(0).getRoleName(), roleInfoBeanList.get(0).getId()));
            mSpinnerRoleList.add(new DicInfo(roleInfoBeanList.get(1).getRoleName(), roleInfoBeanList.get(1).getId()));
        } else {
            if (!ListUtils.isEmpty(roleInfoBeanList)) {
                for (RoleInfoBean roleInfoBean : roleInfoBeanList) {
                    DicInfo dicInfo = new DicInfo(roleInfoBean.getTypeName(), roleInfoBean.getId());
                    mSpinnerRoleList.add(dicInfo);
                }
            }
        }
        spinner_role.setListDatas(getBVActivity(), mSpinnerRoleList);
        spinner_role.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                et_role_code.setText(roleInfoBeanList.get(position).getRoleCode());
                et_role_name.setText(roleInfoBeanList.get(position).getRoleName());
                mRoleId = dicInfo.getCode();
            }
        });
    }

    @Override
    public void getCompanyAccountDetail(CompanyAccountInfo companyAccountInfo) {
        et_username.setText(companyAccountInfo.getUserName());
        et_nickname.setText(companyAccountInfo.getName());
        et_company_name.setText(companyAccountInfo.getCompanyName());
        et_role_name.setText(companyAccountInfo.getRoleName());
        et_role_code.setText(companyAccountInfo.getRoleCode());
        spinner_role.setText(companyAccountInfo.getRoleTypeName());
        mRoleId = companyAccountInfo.getRoleId();
        et_address.setText(companyAccountInfo.getAddress());
        et_email.setText(companyAccountInfo.getEmail());
        et_phone.setText(companyAccountInfo.getPhone());
        spinner_user_type.setText(companyAccountInfo.getTypeName());
        mUserType = companyAccountInfo.getType();
        spinner_state.setText(companyAccountInfo.getStateName());
        mState = companyAccountInfo.getState();
    }

    @Override
    public void saveCompanyAccount(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String toastString = "";
            if (TextUtils.isEmpty(mCompanyId)) {
                toastString = "企业账号创建成功";
            } else {
                toastString = "企业账号修改成功";
            }
            EventBus.getDefault().post(new AddOrSaveCompanyAccountEvent(toastString));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }
}
