package com.africa.crm.businessmanagement.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.africa.crm.businessmanagement.MyApplication;
import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.library.base.progress.BaseFragmentProgress;
import com.africa.crm.businessmanagement.main.bean.BaseEntity;
import com.africa.crm.businessmanagement.main.contract.ChangePasswordContract;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.presenter.ChangePasswordPresenter;
import com.africa.crm.businessmanagement.mvp.activity.BaseEasyMvpActivity;

import butterknife.BindView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/13 0013 16:41
 * Modification  History:
 * Why & What is modified:
 */
public class ChangePasswordActivity extends BaseEasyMvpActivity<ChangePasswordPresenter> implements ChangePasswordContract.View {
    @BindView(R.id.et_old_password)
    EditText et_old_password;
    @BindView(R.id.et_old_password_again)
    EditText et_old_password_again;
    @BindView(R.id.et_new_password)
    EditText et_new_password;
    @BindView(R.id.tv_sure)
    TextView tv_sure;

    /**
     * 在activity中请求回调,显示登录界面
     *
     * @param activity
     */
    public static void startActivity(Activity activity) {
        Intent intent = new Intent(activity, ChangePasswordActivity.class);
        activity.startActivity(intent);
    }

    /**
     * 在fragment中请求回调,显示登录界面
     *
     * @param fragment
     * @param requestCode
     */
    public static void startActivityForResult(BaseFragmentProgress fragment, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), ChangePasswordActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_change_password);
    }

    @Override
    public void initView() {
        tv_sure.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_sure:
                if (TextUtils.isEmpty(et_old_password.getText().toString().trim())) {
                    toastMsg("请输入旧密码");
                    return;
                }
                if (TextUtils.isEmpty(et_old_password_again.getText().toString().trim())) {
                    toastMsg("请确认旧密码");
                    return;
                }
                if (!et_old_password.getText().toString().trim().equals(et_old_password_again.getText().toString().trim())) {
                    toastMsg("两次密码不一致");
                    return;
                }
                if (TextUtils.isEmpty(et_new_password.getText().toString().trim())) {
                    toastMsg("请输入新密码");
                    return;
                }
                String userId = String.valueOf(UserInfoManager.getUserLoginInfo(this).getId());
                mPresenter.changePassword(userId, et_old_password_again.getText().toString().trim(), et_new_password.getText().toString().trim());
                break;
        }
    }

    @Override
    protected ChangePasswordPresenter injectPresenter() {
        return new ChangePasswordPresenter();
    }

    @Override
    protected void requestData() {

    }

    @Override
    public void changePassword(BaseEntity baseEntity) {
        if (baseEntity.isSuccess()) {
            UserInfoManager.deleteUserInfo(ChangePasswordActivity.this);
            MyApplication.getInstance().finishAllActivities();
            LoginActivity.startActivityForResult(ChangePasswordActivity.this, 0);
        }
    }
}
