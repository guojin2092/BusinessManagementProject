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
import com.africa.crm.businessmanagement.eventbus.AddOrSaveUserEvent;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.bean.DicInfo;
import com.africa.crm.businessmanagement.main.bean.DicInfo2;
import com.africa.crm.businessmanagement.main.bean.RoleInfoBean;
import com.africa.crm.businessmanagement.main.bean.UserInfo;
import com.africa.crm.businessmanagement.main.station.contract.UserDetailContract;
import com.africa.crm.businessmanagement.main.station.presenter.UserDetailPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseEasyMvpActivity;
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
 * Date：2018/11/28 0028 9:03
 * Modification  History:
 * Why & What is modified:
 */
public class UserDetailActivity extends BaseEasyMvpActivity<UserDetailPresenter> implements UserDetailContract.View {
    @BindView(R.id.titlebar_back)
    ImageView titlebar_back;
    @BindView(R.id.titlebar_name)
    TextView titlebar_name;
    @BindView(R.id.titlebar_right)
    TextView titlebar_right;
    @BindView(R.id.tv_save)
    TextView tv_save;

    @BindView(R.id.iv_icon)
    ImageView iv_icon;
    @BindView(R.id.tv_add_icon)
    TextView tv_add_icon;
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_nickname)
    EditText et_nickname;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.ll_password)
    LinearLayout ll_password;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_email)
    EditText et_email;
    @BindView(R.id.spinner_type)
    MySpinner spinner_type;
    @BindView(R.id.spinner_company)
    MySpinner spinner_company;
    @BindView(R.id.spinner_state)
    MySpinner spinner_state;
    @BindView(R.id.spinner_role)
    MySpinner spinner_role;


    private List<DicInfo> mSpinnerTypeList = new ArrayList<>();
    private String mType = "";

    private List<DicInfo> mSpinnerStateList = new ArrayList<>();
    private String mState = "";

    private List<DicInfo> mSpinnerCompanyList = new ArrayList<>();
    private String mCompanyId = "";

    private List<DicInfo> mSpinnerRoleList = new ArrayList<>();
    private String mRoleId = "";

    private String mHeadUrl = "";
    private String mUserId = "";

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, String userId) {
        Intent intent = new Intent(activity, UserDetailActivity.class);
        intent.putExtra("userId", userId);
        activity.startActivity(intent);
    }


    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_detail);
    }

    @Override
    public void initView() {
        mUserId = getIntent().getStringExtra("userId");
        titlebar_name.setText("用户详情");
        titlebar_back.setOnClickListener(this);
        titlebar_right.setOnClickListener(this);
        tv_save.setOnClickListener(this);

        if (!TextUtils.isEmpty(mUserId)) {
            ll_password.setVisibility(View.GONE);
            titlebar_right.setText(R.string.edit);
            tv_save.setText(R.string.save);
            setEditTextInput(false);
        } else {
            ll_password.setVisibility(View.VISIBLE);
            titlebar_right.setVisibility(View.GONE);
            tv_save.setText(R.string.add);
            tv_save.setVisibility(View.VISIBLE);
        }

        mSpinnerTypeList.add(new DicInfo("系统管理用户", "1"));
        mSpinnerTypeList.add(new DicInfo("企业用户", "2"));
        spinner_type.setListDatas(getBVActivity(), mSpinnerTypeList);
        mSpinnerStateList.add(new DicInfo("正常", "1"));
        mSpinnerStateList.add(new DicInfo("禁用", "2"));
        spinner_state.setListDatas(getBVActivity(), mSpinnerStateList);

        spinner_type.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mType = dicInfo.getCode();
            }
        });
        spinner_state.addOnItemClickListener(new MySpinner.OnItemClickListener() {
            @Override
            public void onItemClick(DicInfo dicInfo, int position) {
                mState = dicInfo.getCode();
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected UserDetailPresenter injectPresenter() {
        return new UserDetailPresenter();
    }

    @Override
    protected void requestData() {
        if (!TextUtils.isEmpty(mUserId))
            mPresenter.getUserInfo(mUserId);
        mPresenter.getAllCompany("");
        mPresenter.getAllRoles("");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlebar_back:
                onBackPressed();
                break;
            case R.id.titlebar_right:
                if (!TextUtils.isEmpty(mUserId)) {
                    if (titlebar_right.getText().toString().equals(getString(R.string.edit))) {
                        titlebar_right.setText(R.string.cancel);
                        tv_save.setVisibility(View.VISIBLE);
                        setEditTextInput(true);
                    } else {
                        titlebar_right.setText(R.string.edit);
                        tv_save.setVisibility(View.GONE);
                        setEditTextInput(false);
                    }
                }
                break;
            case R.id.tv_save:
                if (TextUtils.isEmpty(et_account.getText().toString().trim())) {
                    toastMsg("尚未填写账号");
                    return;
                }
                if (TextUtils.isEmpty(spinner_type.getText())) {
                    toastMsg("尚未选择账号类型");
                    return;
                }
                if (TextUtils.isEmpty(spinner_role.getText())) {
                    toastMsg("尚未选择账号角色");
                    return;
                }
                if (TextUtils.isEmpty(mUserId)) {
                    if (TextUtils.isEmpty(et_password.getText().toString().trim())) {
                        toastMsg("尚未填写密码");
                        return;
                    }
                    if (et_password.getText().toString().trim().length() < 6) {
                        toastMsg("密码不得小于6位");
                        return;
                    }
                }
                mPresenter.saveOrcreateUser(mUserId, et_account.getText().toString().trim(), mType, mRoleId, et_password.getText().toString().trim(), et_nickname.getText().toString().trim(), et_phone.getText().toString().trim(), et_address.getText().toString().trim(), et_email.getText().toString().trim(), mState, mCompanyId, mHeadUrl);
                break;
        }
    }

    /**
     * 控制输入框是否可输入
     *
     * @param canInput
     */
    private void setEditTextInput(boolean canInput) {
        tv_add_icon.setEnabled(canInput);
        et_account.setEnabled(canInput);
        et_nickname.setEnabled(canInput);
        et_password.setEnabled(canInput);
        et_phone.setEnabled(canInput);
        et_address.setEnabled(canInput);
        et_email.setEnabled(canInput);
        spinner_type.getTextView().setEnabled(canInput);
        spinner_company.getTextView().setEnabled(canInput);
        spinner_state.getTextView().setEnabled(canInput);
        spinner_role.getTextView().setEnabled(canInput);
    }

    @Override
    public void getUserInfo(UserInfo userInfo) {
        mType = userInfo.getType();
        mState = userInfo.getState();
        mRoleId = userInfo.getRoleId();
        mCompanyId = userInfo.getCompanyId();
        et_account.setText(userInfo.getUserName());
        et_nickname.setText(userInfo.getName());
        et_phone.setText(userInfo.getPhone());
        et_address.setText(userInfo.getAddress());
        et_email.setText(userInfo.getEmail());
        spinner_type.setText(userInfo.getTypeName());
        spinner_company.setText(userInfo.getCompanyName());
        spinner_state.setText(userInfo.getStateName());
        spinner_role.setText(userInfo.getRoleName());
    }

    @Override
    public void saveOrcreateUser(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            String msg = "";
            if (TextUtils.isEmpty(mUserId)) {
                msg = "用户创建成功";
            } else {
                msg = "用户修改成功";
            }
            EventBus.getDefault().post(new AddOrSaveUserEvent(msg));
            finish();
        } else {
            toastMsg(ErrorMsg.showErrorMsg(baseEntity.getReturnMsg()));
        }
    }

    @Override
    public void getAllCompany(List<DicInfo2> dicInfoList) {
        if (!ListUtils.isEmpty(dicInfoList)) {
            for (int i = 0; i < dicInfoList.size(); i++) {
                DicInfo dicInfo = new DicInfo(dicInfoList.get(i).getName(), dicInfoList.get(i).getId());
                mSpinnerCompanyList.add(dicInfo);
            }
            spinner_company.setListDatas(getBVActivity(), mSpinnerCompanyList);
            spinner_company.addOnItemClickListener(new MySpinner.OnItemClickListener() {
                @Override
                public void onItemClick(DicInfo dicInfo, int position) {
                    mCompanyId = dicInfo.getCode();
                }
            });
        }
    }

    @Override
    public void getAllRoles(List<RoleInfoBean> roleInfoBeanList) {
        if (!ListUtils.isEmpty(roleInfoBeanList)) {
            for (RoleInfoBean roleInfoBean : roleInfoBeanList) {
                DicInfo dicInfo = new DicInfo(roleInfoBean.getRoleName(), roleInfoBean.getId());
                mSpinnerRoleList.add(dicInfo);
            }
            spinner_role.setListDatas(getBVActivity(), mSpinnerRoleList);
            spinner_role.addOnItemClickListener(new MySpinner.OnItemClickListener() {
                @Override
                public void onItemClick(DicInfo dicInfo, int position) {
                    mRoleId = dicInfo.getCode();
                }
            });
        }
    }
}
