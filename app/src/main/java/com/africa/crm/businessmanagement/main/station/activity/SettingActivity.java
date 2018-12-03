package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.WorkStationInfo;

import baseutil.common.util.ToastUtils;
import baseutil.library.base.BaseActivity;
import butterknife.BindView;

/**
 * Project：BusinessManagementProject
 * Author:  guojin
 * Version:
 * Description：
 * Date：2018/11/27 0027 17:57
 * Modification  History:
 * Why & What is modified:
 */
public class SettingActivity extends BaseActivity {
    @BindView(R.id.titlebar_back)
    ImageView titlebar_back;
    @BindView(R.id.titlebar_name)
    TextView titlebar_name;
    @BindView(R.id.tv_update_data)
    TextView tv_update_data;
    @BindView(R.id.tv_unload_data)
    TextView tv_unload_data;
    @BindView(R.id.tv_login_out)
    TextView tv_login_out;

    private WorkStationInfo mWorkStationInfo;


    /**
     * @param activity
     */
    public static void startActivity(Activity activity, WorkStationInfo workStationInfo) {
        Intent intent = new Intent(activity, SettingActivity.class);
        intent.putExtra("info", workStationInfo);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void initView() {
        mWorkStationInfo = (WorkStationInfo) getIntent().getSerializableExtra("info");
        if (mWorkStationInfo != null) {
            titlebar_name.setText(mWorkStationInfo.getWork_name());
        }
        titlebar_back.setOnClickListener(this);
        tv_update_data.setOnClickListener(this);
        tv_unload_data.setOnClickListener(this);
        tv_login_out.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlebar_back:
                onBackPressed();
                break;
            case R.id.tv_update_data:
                ToastUtils.show(getBVActivity(), "更新数据");
                break;
            case R.id.tv_unload_data:
                ToastUtils.show(getBVActivity(), "上传数据");
                break;
            case R.id.tv_login_out:
                ToastUtils.show(getBVActivity(), "退出登录");
                break;
        }
    }

    @Override
    public void initData() {

    }
}
