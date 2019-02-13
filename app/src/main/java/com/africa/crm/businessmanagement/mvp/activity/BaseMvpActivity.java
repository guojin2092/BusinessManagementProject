package com.africa.crm.businessmanagement.mvp.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.baseutil.library.base.BaseActivity;
import com.africa.crm.businessmanagement.baseutil.library.http.MyNetworkUtil;
import com.africa.crm.businessmanagement.mvp.presenter.BasePresenter;

import butterknife.BindView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/22 0022 16:43
 * Modification  History:
 * Why & What is modified:
 */
public abstract class BaseMvpActivity<P extends BasePresenter> extends BaseActivity {
    @BindView(R.id.layout_no_data)
    protected LinearLayout layout_no_data;
    @BindView(R.id.layout_network_error)
    protected LinearLayout layout_network_error;
    @BindView(R.id.titlebar_back)
    protected ImageView titlebar_back;
    @BindView(R.id.titlebar_name)
    protected TextView titlebar_name;
    @BindView(R.id.titlebar_right)
    protected TextView titlebar_right;
    @BindView(R.id.tv_back)
    protected TextView tv_back;
    @BindView(R.id.tv_refresh)
    protected TextView tv_refresh;
    @BindView(R.id.tv_load_local)
    protected TextView tv_load_local;

    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = injectPresenter();
        mPresenter.attach(this);
        requestData();
    }

    protected abstract P injectPresenter();

    @Override
    public void initView() {
        titlebar_back.setOnClickListener(this);
        titlebar_right.setOnClickListener(this);
        tv_back.setOnClickListener(this);
        tv_refresh.setOnClickListener(this);
        tv_load_local.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlebar_back:
            case R.id.tv_back:
                onBackPressed();
                break;
            case R.id.tv_refresh:
                if (MyNetworkUtil.isNetworkConnected(getBVActivity())) {
                    requestData();
                } else {
                    toastMsg("网络连接失败，请检查网络是否可用");
                }
                break;
            case R.id.tv_load_local:
                toastMsg("加载本地数据");
                break;
        }
    }

    /**
     * 请求初始化数据
     */
    protected abstract void requestData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }

}
