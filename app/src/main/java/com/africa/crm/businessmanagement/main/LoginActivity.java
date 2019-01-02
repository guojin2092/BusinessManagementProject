package com.africa.crm.businessmanagement.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.library.base.progress.BaseFragmentProgress;
import com.africa.crm.businessmanagement.main.bean.LoginInfoBean;
import com.africa.crm.businessmanagement.main.contract.LoginContract;
import com.africa.crm.businessmanagement.main.dao.UserInfoManager;
import com.africa.crm.businessmanagement.main.presenter.LoginPresenter;
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
public class LoginActivity extends BaseEasyMvpActivity<LoginPresenter> implements LoginContract.View {
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.tv_forget_password)
    TextView tv_forget_password;

    private long firstTime = 0;

    //登陆成功
    public final static int LOGIN_SUCCESS = 1002;

    /**
     * 在activity中请求回调,显示登录界面
     *
     * @param activity
     * @param requestCode
     */
    public static void startActivityForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 在fragment中请求回调,显示登录界面
     *
     * @param fragment
     * @param requestCode
     */
    public static void startActivityForResult(BaseFragmentProgress fragment, int requestCode) {
        Intent intent = new Intent(fragment.getActivity(), LoginActivity.class);
        fragment.startActivityForResult(intent, requestCode);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {
        tv_login.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_login:
                if (TextUtils.isEmpty(et_username.getText().toString().trim())) {
                    toastMsg("用户名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(et_password.getText().toString().trim())) {
                    toastMsg("密码不能为空");
                    return;
                }
                mPresenter.getLoginInfo(et_username.getText().toString().trim(), et_password.getText().toString().trim());
                break;
            case R.id.tv_forget_password:
                ChangePasswordActivity.startActivity(this);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            long secondTime = System.currentTimeMillis();
            if (secondTime - firstTime > 2000) {
                Toast.makeText(this, R.string.exit_app, Toast.LENGTH_SHORT).show();
                firstTime = secondTime;//更新firstTime  
                return true;
            } else {
                finish();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected LoginPresenter injectPresenter() {
        return new LoginPresenter();
    }

    @Override
    protected void requestData() {
    }

    @Override
    public void getLoginInfo(LoginInfoBean loginInfoBean) {
        if (loginInfoBean != null) {
            UserInfoManager.saveUserLoginInfo(this, loginInfoBean);
            MainActivity.startActivity(this);
            finish();
        }
    }

}
