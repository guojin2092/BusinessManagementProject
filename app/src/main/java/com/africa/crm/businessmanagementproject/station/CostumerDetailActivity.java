package com.africa.crm.businessmanagementproject.station;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.africa.crm.businessmanagementproject.R;
import com.africa.crm.businessmanagementproject.station.bean.CostumerInfoBean;

import baselibrary.library.base.progress.BaseActivityProgress;
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
public class CostumerDetailActivity extends BaseActivityProgress {
    private final static String COSTUMER_INFO = "costumer_info";
    private CostumerInfoBean mCostumerInfoBean;
    @BindView(R.id.titlebar_back)
    ImageView titlebar_back;
    @BindView(R.id.titlebar_name)
    TextView titlebar_name;

    /**
     * @param activity
     */
    public static void startActivity(Activity activity, CostumerInfoBean costumerInfoBean) {
        Intent intent = new Intent(activity, CostumerDetailActivity.class);
        intent.putExtra(COSTUMER_INFO, costumerInfoBean);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.right_push_in, R.anim.hold);
    }

    @Override
    public void setView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_customer_detail);
    }

    @Override
    public void initView() {
        super.initView();
        titlebar_back.setOnClickListener(this);
        mCostumerInfoBean = (CostumerInfoBean) getIntent().getSerializableExtra(COSTUMER_INFO);
        if (mCostumerInfoBean != null) {
            titlebar_name.setText(mCostumerInfoBean.getCompany());
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.titlebar_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void initData() {

    }
}
