package com.africa.crm.businessmanagementproject.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.africa.crm.businessmanagementproject.R;
import com.africa.crm.businessmanagementproject.main.bean.LoginInfoBean;
import com.africa.crm.businessmanagementproject.network.error.ComConsumer;
import com.africa.crm.businessmanagementproject.network.util.RxUtils;

import baselibrary.library.base.progress.BaseActivityProgress;
import baselibrary.library.base.progress.BaseFragmentProgress;
import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/13 0013 16:41
 * Modification  History:
 * Why & What is modified:
 */
public class LoginActivity extends BaseActivityProgress {
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_password;

    //登陆成功
    public final static int LOGIN_SUCCESS = 1002;
    public static final String LOGIN_SUCCESS_OK = "com.simplesoft.resident.login_success";

    /**
     * 在activity中请求回调,显示登录界面
     *
     * @param activity
     * @param requestCode
     */
    public static void startActivityForResult(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivityForResult(intent, requestCode);
        activity.overridePendingTransition(R.anim.right_push_in, R.anim.hold);
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
        super.initView();
        tv_login.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_login:
               /* addDisposable(mDataManager.getLoginInfo(et_username.getText().toString().trim(), et_password.getText().toString().trim())
                        .compose(RxUtils.<LoginInfoBean>ioToMain())
                        .subscribe(new Consumer<LoginInfoBean>() {
                            @Override
                            public void accept(LoginInfoBean loginInfoBean) throws Exception {
                                if (loginInfoBean != null) {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    overridePendingTransition(R.anim.anim_right_in, R.anim.anim_left_out);
                                }
                            }
                        }, new ComConsumer(LoginActivity.this)));*/
                MainActivity.startActivity(this);
                break;
        }
    }
}
