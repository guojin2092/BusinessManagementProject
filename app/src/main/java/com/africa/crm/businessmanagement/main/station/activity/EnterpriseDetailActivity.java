package com.africa.crm.businessmanagement.main.station.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagement.R;
import com.africa.crm.businessmanagement.main.bean.EnterpriseInfoBean;

import com.africa.crm.businessmanagement.baseutil.common.util.ToastUtils;
import com.africa.crm.businessmanagement.baseutil.library.base.BaseActivity;
import butterknife.BindView;

public class EnterpriseDetailActivity extends BaseActivity {
    @BindView(R.id.titlebar_back)
    ImageView titlebar_back;
    @BindView(R.id.titlebar_name)
    TextView titlebar_name;
    @BindView(R.id.titlebar_right)
    TextView titlebar_right;
    @BindView(R.id.tv_save)
    TextView tv_save;

    private EnterpriseInfoBean mEnterpriseInfoBean;

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, EnterpriseInfoBean enterpriseInfoBean) {
        Intent intent = new Intent(activity, EnterpriseDetailActivity.class);
        intent.putExtra("info", enterpriseInfoBean);
        activity.startActivity(intent);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_enterprise_detail);
    }

    @Override
    public void initView() {
        mEnterpriseInfoBean = (EnterpriseInfoBean) getIntent().getSerializableExtra("info");
        titlebar_back.setOnClickListener(this);
        titlebar_right.setOnClickListener(this);
        titlebar_right.setText(R.string.edit);
        tv_save.setOnClickListener(this);
        if (mEnterpriseInfoBean != null) {
            titlebar_name.setText(mEnterpriseInfoBean.getCompany());
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlebar_back:
                onBackPressed();
                break;
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
                ToastUtils.show(EnterpriseDetailActivity.this, "保存成功");
                break;

        }
    }

    @Override
    public void initData() {

    }
}
